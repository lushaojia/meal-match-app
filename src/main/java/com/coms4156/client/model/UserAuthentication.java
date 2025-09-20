package com.coms4156.client.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class UserAuthentication {
    private static final String WEB_API_KEY = System.getenv("FIREBASE_API_KEY");

    /**
     * Registers a user with the given credentials using the Firebase Authentication API.
     * If a user with `username` already exists or `username` is the empty string, returns false.
     * Otherwise, returns True.
     *
     * @param username Username
     * @param password Password
     * @return A JsonObject containing:
     *         - "success": A boolean for whether registration succeeded
     *         - "message": A corresponding message that can be displayed to the user
     */
    public JsonObject registerUser(String username, String password) throws IOException {
        System.out.println("Registering user " + username + " with password " + password);

        JsonObject result = new JsonObject();
        if (Objects.equals(username, "")) {
            result.addProperty("success", false);
            result.addProperty("message", "Failed to register user: "
                                              + "Username cannot be empty.");
            return result;
        }
        String email = username + "@app.com";

        HttpURLConnection conn = registerUserWithFirebase(email, password);
        JsonObject response = getFirebaseResponse(conn);
        int responseCode = response.get("responseCode").getAsInt();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            result.addProperty("success", true);
            result.addProperty("message", "User successfully registered!");
        } else {
            result.addProperty("success", false);
            JsonObject responseBody = response.get("responseBody").getAsJsonObject();
            JsonObject err = responseBody.get("error").getAsJsonObject();
            // Since we use username instead of email,
            // change error message returned by Firebase Authentication accordingly
            String errorMessage = err.get("message").getAsString();
            if (errorMessage.contains("EMAIL")) {
                errorMessage = errorMessage.replace("EMAIL", "USERNAME");
            }
            if (errorMessage.contains("USERNAME_EXISTS")) {
                result.addProperty("message", "Username already exists.");
            } else if (errorMessage.contains("WEAK_PASSWORD")) {
                result.addProperty("message", "Weak password: Password should be at least 6 characters.");
            } else {
                result.addProperty("message", "Failed to register user: " + errorMessage);
            }
        }
        return result;
    }

    /**
     * Authenticates a user with the given credentials using the Firebase Authentication API.
     * If a user with `username` does not exist or if the given credentials are invalid
     * (i.e. the username does not match with the password on-file), authentication will fail.
     * Otherwise, authentication will succeed.
     *
     * @param username Username
     * @param password Password
     * @return A JsonObject containing:
     *         - "success": A boolean for whether the authentication succeeded
     *         - "message": A corresponding message that can be displayed to the user
     */
    public JsonObject authenticateUser(String username, String password) throws IOException {
        System.out.println("Authenticating user " + username);

        String email = username + "@app.com";
        JsonObject result = new JsonObject();

        HttpURLConnection conn = authenticateUserWithFirebase(email, password);
        JsonObject response = getFirebaseResponse(conn);
        int responseCode = response.get("responseCode").getAsInt();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            result.addProperty("success", true);
            result.addProperty("message", "Log-in successful!");
        } else {
            JsonObject responseBody = response.get("responseBody").getAsJsonObject();
            if (responseBody == null || !responseBody.has("error")) {
                result.addProperty("success", false);
                result.addProperty("message", "Unknown error occurred during authentication.");
                return result;
            }

            result.addProperty("success", false);
            JsonObject err = responseBody.get("error").getAsJsonObject();
            // Since we use username instead of email,
            // change error message returned by Firebase Authentication accordingly
            String errorMessage = err.get("message").getAsString();
            if (errorMessage.contains("EMAIL")) {
                errorMessage = errorMessage.replace("EMAIL", "USERNAME");
            }
            result.addProperty("message", "Log-in failed: " + errorMessage);
        }

        return result;
    }

    //===BEGIN HELPER METHODS===//
    /**
     * Registers a user with Firebase Authentication using their email and password
     * by sending a POST request to the Firebase Authentication API.
     *
     * @param email User email
     * @param password User password
     * @return The HttpURLConnection object representing the open connection for the request.
     * @throws IOException if an I/O error occurs while sending or receiving data,
     *         if the connection's input or error stream cannot be accessed or read.
     *
     */
    public HttpURLConnection registerUserWithFirebase(String email, String password)
        throws IOException {
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" +
                          WEB_API_KEY);
        return getHttpURLConnection(email, password, url);
    }

    /**
     * Authenticates a user with Firebase Authentication using their email and password
     * by sending a POST request to the Firebase Authentication API.
     *
     * @param email The email address of the user attempting to authenticate.
     * @param password The password of the user attempting to authenticate.
     * @return The HttpURLConnection object representing the open connection for the request.
     * @throws IOException If an I/O error occurs while opening the connection or sending the
     *     request.
     */
    public HttpURLConnection authenticateUserWithFirebase(String email, String password)
        throws IOException {
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" 
        + WEB_API_KEY);
        return getHttpURLConnection(email, password, url);
    }

    /**
     * Establishes an HTTP connection to a given URL and prepares a POST request that
     * sends user credentials in JSON format.
     *
     * @param email The email address of the user attempting to authenticate.
     * @param password The password of the user attempting to authenticate.
     * @param url The URL to which the POST request is sent, typically the Firebase Authentication
     *     API endpoint.
     * @return The HttpURLConnection object representing the open connection for the request.
     * @throws IOException If an I/O error occurs while opening the connection or writing the
     *     request body.
     */
    public HttpURLConnection getHttpURLConnection(String email, String password, URL url)
        throws IOException {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        JsonObject payload = new JsonObject();
        payload.addProperty("email", email);
        payload.addProperty("password", password);
        payload.addProperty("returnSecureToken", true);

        OutputStream os = conn.getOutputStream();
        os.write(payload.toString().getBytes());
        os.flush();

        return conn;
    }

    /**
     * Retrieves the response from a Firebase API call, including the HTTP response code
     * and the response body (for both success and error responses).
     *
     * @param conn The HttpURLConnection object used for the Firebase API call.
     * @return A JsonObject containing:
     *         - "responseCode": The HTTP response code as an integer.
     *         - "responseBody": The response body as a string.
     * @throws IOException If an I/O error occurs while reading the response stream or
     *         retrieving the HTTP response code.
     */
    public JsonObject getFirebaseResponse(HttpURLConnection conn) throws IOException {
        JsonObject response = new JsonObject();
        response.addProperty("responseCode", conn.getResponseCode());

        InputStream responseStream;
        responseStream = (conn.getResponseCode() >=
                          HttpURLConnection.HTTP_BAD_REQUEST)  // Depending on response code,
                             ? conn.getErrorStream()           // get error or
                             : conn.getInputStream();          // response body

        JsonObject responseBody =
            JsonParser.parseReader(new InputStreamReader(responseStream)).getAsJsonObject();
        response.add("responseBody", responseBody);

        return response;
    }

    /**
     * Calls the `/create` endpoint of the service to create an account profile and retrieve the
     * accountId.
     *
     * @param clientId    The ID of the client.
     * @param accountType The type of the account (e.g., PROVIDER, RECIPIENT).
     * @param phoneNumber The phone number associated with the account.
     * @param name        The name of the account holder.
     * @return A JsonObject containing:
     *         - "success" (boolean): Whether the operation was successful.
     *         - "accountId" (String): The generated account ID (on success).
     *         - "message" (String): A message indicating success or failure.
     * @throws IOException If there's an error during the HTTP request.
     */
    public JsonObject createAccountProfile(int clientId, String accountType, String phoneNumber,
                                           String name) throws IOException {
        String endpoint = "/api/accountProfiles/create";
        URL url = new URL("https://backend-api-561546311937.us-east1.run.app" + endpoint);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String params = String.format("clientId=%d&accountType=%s&phoneNumber=%s&name=%s",
                                      clientId, URLEncoder.encode(accountType, "UTF-8"),
                                      URLEncoder.encode(phoneNumber, "UTF-8"),
                                      URLEncoder.encode(name, "UTF-8"));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes("UTF-8"));
            os.flush();
        }

        // Read the response
        int responseCode = conn.getResponseCode();

        // System.out.println("Response code is " + responseCode);

        InputStream responseStream = (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST)
                                         ? conn.getErrorStream()   // Error response
                                         : conn.getInputStream();  // Success response

        JsonObject response = new JsonObject();
        response.addProperty("responseCode", responseCode);

        try (InputStreamReader isr = new InputStreamReader(responseStream, "UTF-8")) {
            JsonObject responseBody = JsonParser.parseReader(isr).getAsJsonObject();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Success
                response.addProperty("success", true);
                response.addProperty("accountId", responseBody.get("accountId").getAsString());
            } else {
                // Error
                response.addProperty("success", false);
                String errorMessage = responseBody.has("message")
                                          ? responseBody.get("message").getAsString()
                                          : "Failed to create account profile.";
                response.addProperty("message", errorMessage);
            }
        }

        return response;
    }

    //===END HELPER METHODS===//
}
