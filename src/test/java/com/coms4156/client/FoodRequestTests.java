package com.coms4156.client;

import com.coms4156.client.model.FoodRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodRequestTests {

  @Test
  void testFoodRequestGettersAndSetters() {
    FoodRequest foodRequest = new FoodRequest();

    // Test requestId
    foodRequest.setRequestId(101);
    assertEquals(101, foodRequest.getRequestId());

    // Test quantityRequested
    foodRequest.setQuantityRequested(5);
    assertEquals(5, foodRequest.getQuantityRequested());

    // Test requestTime
    String requestTime = "2024-11-26 18:00";
    foodRequest.setRequestTime(requestTime);
    assertEquals(requestTime, foodRequest.getRequestTime());
  }

  @Test
  void testFoodRequestDefaultValues() {
    FoodRequest foodRequest = new FoodRequest();

    // Assert default values are correct
    assertEquals(0, foodRequest.getRequestId());
    assertEquals(0, foodRequest.getQuantityRequested());
    assertNull(foodRequest.getRequestTime());
  }
}

