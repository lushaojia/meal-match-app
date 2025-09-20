package com.coms4156.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a geocoding result.
 */
public class GeocodingResult {

    @JsonProperty("formatted_address") private String formattedAddress;

    /**
     * Gets the formatted address.
     *
     * @return the formatted address.
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * Set the formatted address.
     *
     * @return the formatted address.
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
