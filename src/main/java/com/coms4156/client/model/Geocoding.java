package com.coms4156.client.model;

import java.util.List;

/**
 * Represents a food listing from the service.
 */
public class Geocoding {

    private List<GeocodingResult> results;

    /**
     * Gets the geocoding results.
     *
     * @return the geocoding results.
     */
    public List<GeocodingResult> getResults() {
        return results;
    }

    /**
     * Sets the geocoding results.
     */
    public void setResults(List<GeocodingResult> results) {
        this.results = results;
    }
}
