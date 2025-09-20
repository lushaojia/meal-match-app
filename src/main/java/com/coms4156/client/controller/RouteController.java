package com.coms4156.client.controller;

import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @Autowired private ServiceHelper serviceHelper;

    /**
     * Connects to the /home page in /static/templates.
     *
     * @return The URL page as a string.
     */
    @GetMapping("/home")
    public String home(Model model) {
        String apiKey = System.getenv("GOOGLE_MAPS_API_KEY");
        model.addAttribute("googleMapsApiKey", apiKey);
        return "home";
    }

    /**
     * Connects to the /search-results page in /static/templates.
     *
     * @return The URL page as a string.
     */
    @GetMapping("/search-results")
    public String searchResults(Model model, @RequestParam("latitude") float latitude,
                                @RequestParam("longitude") float longitude) {
        List<FoodListing> listings = serviceHelper.getNearbyListings(latitude, longitude, 500);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        listings.forEach(listing -> {
            if (listing.getEarliestPickUpTime() != null) {
                listing.setFormattedPickUpTime(listing.getEarliestPickUpTime().format(formatter));
            }

            float lat = listing.getLatitude();
            float lng = listing.getLongitude();
            String address = serviceHelper.geoencode(lat, lng);
            listing.setFormattedAddress(address);
        });
        model.addAttribute("foodListings", listings);
        return "search-results";
    }

    /**
     * Connects to the /quantity-request page in /static/templates.
     *
     * @return The URL page as a string.
     */
    @PostMapping("/quantity-request")
    public String quantityRequest(@RequestParam("listingId") int listingId,
                                  @RequestParam("foodType") String foodType,
                                  @RequestParam("quantityListed") int quantityListed,
                                  @RequestParam("formattedPickUpTime") String formattedPickUpTime,
                                  @RequestParam("latitude") float latitude,
                                  @RequestParam("longitude") float longitude, Model model) {
        model.addAttribute("listingId", listingId);
        model.addAttribute("foodType", foodType);
        model.addAttribute("quantityListed", quantityListed);
        model.addAttribute("formattedPickUpTime", formattedPickUpTime);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        return "quantity-request";
    }

    /**
     * An endpoint that submits the request to the service, then returns the result to the
     * frontend.
     *
     * @return The URL page as a string.
     */
    @PostMapping("/submit-request")
    public ResponseEntity<?>
    submitRequest(@RequestParam("listingId") int listingId,
                  @RequestParam("quantityRequested") int quantityRequested) {
        FoodRequest foodRequest = serviceHelper.fulfillRequest(listingId, quantityRequested);
        Map<String, Object> body = new HashMap<>();
        if (foodRequest != null) {
            body.put("message", "Successfully submitted.");
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            body.put("error", "Failed to submit.");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    // DEV: This is used for testing.
    public void setServiceHelper(ServiceHelper serviceHelper) {
        this.serviceHelper = serviceHelper;
    }
}
