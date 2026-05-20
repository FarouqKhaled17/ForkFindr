package com.devtiro.restaurant.services.impl;

import com.devtiro.restaurant.domain.GeoLocation;
import com.devtiro.restaurant.domain.entities.Address;
import com.devtiro.restaurant.services.GeoLocationService;

import java.util.Random;

public class GeoLocationServiceImpl implements GeoLocationService {
    private static final float MIN_LATITUDE = -180;
    private static final float MAX_LATITUDE = 180;
    private static final float MIN_LONGITUDE = -90;
    private static final float MAX_LONGITUDE = 90;

    @Override
    public GeoLocation getGeoLocation(Address address) {
        Random random = new Random();
        double latitude = MIN_LATITUDE +  random.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
        double longitude = MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);
        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

    }
}
