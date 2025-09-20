package com.coms4156.client;

import com.coms4156.client.model.FoodListing;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FoodListingTests {

  @Test
  void testFoodListingGettersAndSetters() {
    FoodListing foodListing = new FoodListing();

    // Test listingId
    foodListing.setListingId(1);
    assertEquals(1, foodListing.getListingId());

    // Test foodType
    foodListing.setFoodType("Apples");
    assertEquals("Apples", foodListing.getFoodType());

    // Test quantityListed
    foodListing.setQuantityListed(50);
    assertEquals(50, foodListing.getQuantityListed());

    // Test earliestPickUpTime
    LocalDateTime now = LocalDateTime.now();
    foodListing.setEarliestPickUpTime(now);
    assertEquals(now, foodListing.getEarliestPickUpTime());

    // Test formattedPickUpTime
    String formattedTime = "2024-11-26 15:30";
    foodListing.setFormattedPickUpTime(formattedTime);
    assertEquals(formattedTime, foodListing.getFormattedPickUpTime());

    // Test latitude
    foodListing.setLatitude(40.7128f);
    assertEquals(40.7128f, foodListing.getLatitude());

    // Test longitude
    foodListing.setLongitude(-74.006f);
    assertEquals(-74.006f, foodListing.getLongitude());
  }

  @Test
  void testFoodListingDefaultValues() {
    FoodListing foodListing = new FoodListing();

    // Assert default values are correct
    assertEquals(0, foodListing.getListingId());
    assertNull(foodListing.getFoodType());
    assertEquals(0, foodListing.getQuantityListed());
    assertNull(foodListing.getEarliestPickUpTime());
    assertNull(foodListing.getFormattedPickUpTime());
    assertEquals(0.0f, foodListing.getLatitude());
    assertEquals(0.0f, foodListing.getLongitude());
  }
}

