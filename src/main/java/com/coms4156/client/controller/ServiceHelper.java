package com.coms4156.client.controller;

import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import com.coms4156.client.model.Geocoding;
import com.coms4156.client.model.GeocodingResult;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * A helper class for sending API requests to the service for
 * finding nearby listings and creating requests for them.
 */
@Service
public class ServiceHelper {

    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    /**
     * Constructs a ServiceHelper instance with the given RestTemplate.
     * Configures the RestTemplate and initializes the base URI to make
     * API requests to the service.
     *
     * @param restTemplate the RestTemplate instance used to send API requests to the service.
     */
    public ServiceHelper(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.restTemplate = restTemplate;

        this.uriBuilder =
            UriComponentsBuilder.newInstance().scheme("https").host("backend-api-561546311937.us-east1.run.app");
    }

    /**
     * Makes an API request to the service to find listings within `maxDistance`
     * of a query location specified by (`latitude`, `longitude`).
     *
     * @param latitude The latitude of the query location
     * @param longitude The longitude of the query location
     * @param maxDistance Max distance to search for listings from the query location
     * @return A list of food listings within `maxDistance` of the query location (`latitude`,
     *         `longitude`)
     */
    public List<FoodListing> getNearbyListings(float latitude, float longitude, int maxDistance) {
        try {
            URL url = uriBuilder.cloneBuilder()
                          .path("getNearbyListings")
                          .queryParam("clientId", 1)
                          .queryParam("latitude", latitude)
                          .queryParam("longitude", longitude)
                          .queryParam("maxDistance", maxDistance)
                          .build()
                          .toUri()
                          .toURL();

            FoodListing[] listings =
                restTemplate.getForObject(url.toString(), FoodListing[].class);
            return Arrays.asList(listings);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Makes an API request to the service to fulfill a request for a listing.
     *
     * @param listingId The ID of the listing used to fulfill the request
     * @param quantityRequested The quantity of food being requested to fulfill
     * @return A FoodRequest object containing details of the fulfilled request,
     *         or null if an error occurs
     */
    public FoodRequest fulfillRequest(int listingId, int quantityRequested) {
        try {
            URL url = uriBuilder.cloneBuilder()
                          .path("fulfillRequest")
                          .queryParam("clientId", 1)
                          .queryParam("listingId", listingId)
                          .queryParam("quantityRequested", quantityRequested)
                          .build()
                          .toUri()
                          .toURL();

            FoodRequest foodRequest =
                restTemplate.patchForObject(url.toString(), null, FoodRequest.class);
            return foodRequest;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Call the Google Maps Geocoding API and return the first formatted address.
     * 
     */
    public String geoencode(float latitude, float longitude) {
        try {
            UriComponentsBuilder googleMapsUriBuilder = UriComponentsBuilder.newInstance()
                                                            .scheme("https")
                                                            .host("maps.googleapis.com")
                                                            .path("/maps/api/geocode/json");
            URL url = googleMapsUriBuilder.cloneBuilder()
                          .queryParam("latlng", "" + latitude + "," + longitude)
                          .queryParam("key", System.getenv("GOOGLE_MAPS_API_KEY"))
                          .build()
                          .toUri()
                          .toURL();

            Geocoding geocoding = restTemplate.getForObject(url.toString(), Geocoding.class);
            if (geocoding == null) {
                return null;
            }

            List<GeocodingResult> results = geocoding.getResults();
            if (!results.isEmpty()) {
                var result = results.get(0);
                return result.getFormattedAddress();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("ServiceHelper.geoencode() error: " + e.getMessage() +
                               ", exception type: " + e.getClass().getSimpleName());
            return null;
        }
    }
}
