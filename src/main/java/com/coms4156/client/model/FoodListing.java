package com.coms4156.client.model;

import java.time.LocalDateTime;

/**
 * Represents a food listing from the service.
 */
public class FoodListing {

    private int listingId;
    private String foodType;
    private int quantityListed;
    private LocalDateTime earliestPickUpTime;
    private float latitude;
    private float longitude;
    private String formattedPickUpTime;

    // This isn't part of the schema. We have this field so we can pass it to the Thymeleaf
    // template.
    private String formattedAddress;

    /**
     * Gets the unique ID of the food listing.
     *
     * @return the listing ID.
     */
    public int getListingId() {
        return listingId;
    }

    /**
     * Sets the unique ID of the food listing.
     *
     * @param listingId the listing ID to set.
     */
    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    /**
     * Gets the type of food in the listing.
     *
     * @return the food type.
     */
    public String getFoodType() {
        return foodType;
    }

    /**
     * Sets the type of food in the listing.
     *
     * @param foodType the food type to set.
     */
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    /**
     * Gets the quantity of food listed.
     *
     * @return the quantity listed.
     */
    public int getQuantityListed() {
        return quantityListed;
    }

    /**
     * Sets the quantity of food listed.
     *
     * @param quantityListed the quantity to set.
     */
    public void setQuantityListed(int quantityListed) {
        this.quantityListed = quantityListed;
    }

    /**
     * Gets the earliest pick-up time for the food listing.
     *
     * @return the earliest pick-up time as a LocalDateTime.
     */
    public LocalDateTime getEarliestPickUpTime() {
        return earliestPickUpTime;
    }

    /**
     * Sets the earliest pick-up time for the food listing.
     *
     * @param earliestPickUpTime the pick-up time to set.
     */
    public void setEarliestPickUpTime(LocalDateTime earliestPickUpTime) {
        this.earliestPickUpTime = earliestPickUpTime;
    }

    /**
     * Gets the formatted string representation of the pick-up time.
     *
     * @return the formatted pick-up time.
     */
    public String getFormattedPickUpTime() {
        return formattedPickUpTime;
    }

    /**
     * Sets the formatted string representation of the pick-up time.
     *
     * @param formattedPickUpTime the formatted pick-up time to set.
     */
    public void setFormattedPickUpTime(String formattedPickUpTime) {
        this.formattedPickUpTime = formattedPickUpTime;
    }

    /**
     * Gets the latitude of the food listing's location.
     *
     * @return the latitude.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the food listing's location.
     *
     * @param latitude the latitude to set.
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the food listing's location.
     *
     * @return the longitude.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the food listing's location.
     *
     * @param longitude the longitude to set.
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the formatted address of the food listing's location.
     *
     * @return the formatted address.
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * Sets the formatted address of the food listing's location.
     *
     * @param formattedAddress the formatted address to set.
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
