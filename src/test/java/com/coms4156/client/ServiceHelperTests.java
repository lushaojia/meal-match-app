package com.coms4156.client;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.coms4156.client.controller.ServiceHelper;
import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ServiceHelperTests {

    @Test
    void testGetNearbyListings() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        FoodListing[] mockResponse = {new FoodListing()};
        when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
            .thenReturn(mockResponse);

        List<FoodListing> nearbyListings = serviceHelper.getNearbyListings(40.7128f, -74.006f, 5);

        assertEquals(1, nearbyListings.size());
        verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
    }

    @Test
    void testGetNearbyListingsEmptyResponse() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
            .thenReturn(new FoodListing[0]);

        List<FoodListing> nearbyListings = serviceHelper.getNearbyListings(40.7128f, -74.006f, 5);
        assertEquals(0, nearbyListings.size());
        verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
    }

    @Test
    void testGetNearbyListingsNotFound() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        List<FoodListing> nearbyListings = serviceHelper.getNearbyListings(40.7128f, -74.006f, 5);
        assertEquals(0, nearbyListings.size());
        verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
    }

    @Test
    void testGetNearbyListingsMalformedHttp() throws Exception {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        URI uriMock = mock(URI.class);
        when(uriMock.toURL()).thenThrow(new MalformedURLException());

        List<FoodListing> nearbyListings = serviceHelper.getNearbyListings(40.7128f, -74.006f, 5);
        assertEquals(0, nearbyListings.size());
    }

    @Test
    void testFulfillRequest() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        FoodRequest mockResponse = new FoodRequest();
        when(restTemplateMock.patchForObject(anyString(), isNull(), eq(FoodRequest.class)))
            .thenReturn(mockResponse);

        FoodRequest foodRequest = serviceHelper.fulfillRequest(13, 1);
        assertNotNull(foodRequest);
        verify(restTemplateMock, times(1))
            .patchForObject(anyString(), isNull(), eq(FoodRequest.class));
    }

    @Test
    void testFulfillRequestNullResponse() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        when(restTemplateMock.patchForObject(anyString(), isNull(), eq(FoodRequest.class)))
            .thenReturn(null);

        FoodRequest foodRequest = serviceHelper.fulfillRequest(13, 1);
        assertNull(foodRequest);
        verify(restTemplateMock, times(1))
            .patchForObject(anyString(), isNull(), eq(FoodRequest.class));
    }

    @Test
    void testFulfillRequestNotFound() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        when(restTemplateMock.patchForObject(anyString(), isNull(), eq(FoodRequest.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        FoodRequest foodRequest = serviceHelper.fulfillRequest(13, 1);
        assertNull(foodRequest);
        verify(restTemplateMock, times(1))
            .patchForObject(anyString(), isNull(), eq(FoodRequest.class));
    }

    @Test
    void testFulfillRequestMalformedHttp() throws Exception {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        ServiceHelper serviceHelper = new ServiceHelper(restTemplateMock);

        URI uriMock = mock(URI.class);
        when(uriMock.toURL()).thenThrow(new MalformedURLException());

        FoodRequest foodRequest = serviceHelper.fulfillRequest(13, 1);
        assertNull(foodRequest);
    }
}
