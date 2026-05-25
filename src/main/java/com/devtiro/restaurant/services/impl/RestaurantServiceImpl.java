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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Restaurant> searchRestaurants(String query, Float minRating, Float latitude,
                                              Float longitude, Float radius, Pageable pageable) {
//        To optimize search, we can prioritize filters. If minRating is provided without a query, we can directly filter by rating.
        if(null!=minRating &&(null==query || query.isEmpty()) ){
            return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating,pageable);
        }
//         If a query is provided, we can combine it with the rating filter.
        Float searchMinRating=null==minRating?0f:minRating;
        if(null!=query && !query.trim().isEmpty()){
            return restaurantRepository.findByQueryAndMinRating(query,searchMinRating,pageable);
        }
//        If location parameters are provided, we can use them to further narrow down results.
        if(null!=latitude && null!=longitude && null!=radius){
            return restaurantRepository.findByLocationNear(latitude,longitude,radius,pageable);
        }
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }
}