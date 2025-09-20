package com.coms4156.client.model;

/**
 * Represents a food request from the service.
 */
public class FoodRequest {
    private int requestId;
    private int quantityRequested;
    private String requestTime;

    /**
     * Returns the ID of the food request.
     *
     * @return The request ID.
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Sets the ID of the food request.
     *
     * @param requestId The request ID.
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * Returns the number of food items requested.
     *
     * @return The quantity requested.
     */
    public int getQuantityRequested() {
        return quantityRequested;
    }

    /**
     * Sets the number of food items requested.
     *
     * @param quantityRequested The quantity requested.
     */
    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    /**
     * Returns the time the request was made.
     *
     * @return The request time.
     */
    public String getRequestTime() {
        return requestTime;
    }

    /**
     * Sets the time the request was made.
     *
     * @param requestTime The new request time.
     */
    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
