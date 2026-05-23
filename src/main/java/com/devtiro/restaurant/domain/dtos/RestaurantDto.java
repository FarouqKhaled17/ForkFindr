package com.devtiro.restaurant.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {
    private String id;
    private String name;
    private String cuisineType;
    private String contactInfo;
    private Float averageRating;
    private GeoPointDto geoLocation;
    private AddressDto address;
    private OperatingHoursDto operatingHours;
    private List<ReviewDto> reviews = new ArrayList<>();
    private List<PhotoDto> photos = new ArrayList<>();
    private UserDto createdBy;
}
