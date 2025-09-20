package com.coms4156.client.controller;

import com.coms4156.client.model.UserAuthentication;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller responsible for handling user authentication-related endpoints.
 * This class provides RESTful endpoints for user registration and authentication,
 * and it uses Firestore for data persistence (i.e. so that users can register once
 * in a session and use the same credential to authenticate themselves in a new session)
 */
@Controller
@RequestMapping("/login")
public class UserAuthenticationController {

    private final UserAuthentication userAuthentication;

    /**
     * Constructor for UserAuthenticationController.
     *
     * @param userAuthentication The user authentication instance used for handling registration
     *                           and authentication logic.
     */
    public UserAuthenticationController(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    /**
     * Endpoint for registering a new user with username `username` and password `password`.
     *
     * @param username The username for the new user
     * @param password The password for the new user
     * @return A ResponseEntity object containing a string representation of a JSON object
     *          with success status and the ID of the new account created for the user
     *          if registration was successful or an error message otherwise.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password) {
        try {
            JsonObject authResult = userAuthentication.registerUser(username, password);
            if (!authResult.get("success").getAsBoolean()) {
                return ResponseEntity.status(400).body(authResult.toString());
            }

            int accountId = 1;
            authResult.addProperty("accountId", accountId);
            return ResponseEntity.ok(authResult.toString());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    /**
     *  Endpoint for authenticating a user with the credentials
     *  `username` and `password`.
     *
     * @param username The username that the user is attempting to authenticate with
     * @param password The password that the user is attempting to authenticate with
     * @return A ResponseEntity object containing a string representation of a JSON object
     *          with success status and the user's account ID
     *          if authentication was successful or an error message otherwise.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestParam String username,
                                        @RequestParam String password) {
        Map<String, Object> body = new HashMap<>();
        try {
            JsonObject authResult = userAuthentication.authenticateUser(username, password);
            if (!authResult.get("success").getAsBoolean()) {
                body.put("message", "Failed to authenticate user.");
                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            } else {
                body.put("message", "Successfully logged in!");
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
//            Firestore db = FirestoreClient.getFirestore();
//            DocumentReference docRef = db.collection("users").document(username);
//            ApiFuture<DocumentSnapshot> future = docRef.get();
//            DocumentSnapshot document = future.get();
//            System.out.println("document.exists?" + document);
//
//            if (document.exists()) {
//                Long accountIdLong = document.getLong("accountId");
//                // System.out.println(accountIdLong);
//                if (accountIdLong != null) {
//                    int accountId = accountIdLong.intValue();
//                    body.put("message", "Successfully logged in!");
//                    return new ResponseEntity<>(body, HttpStatus.OK);
//                } else {
//                    body.put("message", "Account ID not found for user.");
//                    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                body.put("message", "User not found in Firestore.");
//                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//            }
        } catch (IOException e) {
            body.put("message", "Exception type: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
