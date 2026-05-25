package com.devtiro.restaurant.controllers;

import com.devtiro.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devtiro.restaurant.domain.dtos.RestaurantCreateUpdateDtoRequest;
import com.devtiro.restaurant.domain.dtos.RestaurantDto;
import com.devtiro.restaurant.domain.dtos.RestaurantSummaryDto;
import com.devtiro.restaurant.domain.entities.Restaurant;
import com.devtiro.restaurant.mappers.RestaurantMapper;
import com.devtiro.restaurant.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody RestaurantCreateUpdateDtoRequest request){
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest =restaurantMapper.toCreateUpdateRequest(request);
        Restaurant restaurant = restaurantService.createRestaurant(restaurantCreateUpdateRequest);
        RestaurantDto createdRestaurantDto=restaurantMapper.toRestaurantDto(restaurant);
        return ResponseEntity.ok(createdRestaurantDto);
    }

    @GetMapping
    public Page<RestaurantSummaryDto> searchRestaurants(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<Restaurant> searchResults=restaurantService.searchRestaurants(q,minRating,latitude,longitude,radius, PageRequest.of(page-1,size));
        return searchResults.map(restaurantMapper::toRestaurantSummaryDto);
    }

    @GetMapping(path = "/{restaurant_id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("restaurant_id") String id){
        return restaurantService.getRestaurantById(id)
                .map(restaurant -> ResponseEntity.ok(restaurantMapper.toRestaurantDto(restaurant)))
                .orElse(ResponseEntity.notFound().build());
    }
}
