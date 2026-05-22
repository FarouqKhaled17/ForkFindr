package com.devtiro.restaurant.services.impl;

import com.devtiro.restaurant.domain.GeoLocation;
import com.devtiro.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devtiro.restaurant.domain.entities.Address;
import com.devtiro.restaurant.domain.entities.Photo;
import com.devtiro.restaurant.domain.entities.Restaurant;
import com.devtiro.restaurant.respositories.RestaurantRepository;
import com.devtiro.restaurant.services.GeoLocationService;
import com.devtiro.restaurant.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;
    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request) {
        Address address=request.getAddress();
        GeoLocation geoLocation=geoLocationService.getGeoLocation(address);
        GeoPoint geoPoint=new GeoPoint(geoLocation.getLatitude(),geoLocation.getLongitude());

        List<String> photos=request.getPhotoIds();
        List<Photo> photoIds=request.getPhotoIds().stream()
                .map(photoUrl->Photo.builder()
                        .url(photoUrl)
                        .build())
                .toList();
        Restaurant restaurant=Restaurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInfo(request.getContactInformation())
                .averageRating(0.0f)
                .geoLocation(geoPoint)
                .address(address)
                .operatingHours(request.getOperatingHours())
                .photos(photoIds)
                .build();
        return restaurantRepository.save(restaurant);
    }
}
