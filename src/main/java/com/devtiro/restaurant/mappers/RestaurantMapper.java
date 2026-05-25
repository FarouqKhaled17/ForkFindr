package com.devtiro.restaurant.mappers;

import com.devtiro.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devtiro.restaurant.domain.dtos.GeoPointDto;
import com.devtiro.restaurant.domain.dtos.RestaurantCreateUpdateDtoRequest;
import com.devtiro.restaurant.domain.dtos.RestaurantDto;
import com.devtiro.restaurant.domain.dtos.RestaurantSummaryDto;
import com.devtiro.restaurant.domain.entities.Restaurant;
import com.devtiro.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantCreateUpdateRequest toCreateUpdateRequest(RestaurantCreateUpdateDtoRequest dto);

    RestaurantDto toRestaurantDto(Restaurant restaurant);

    RestaurantSummaryDto toRestaurantSummaryDto(Restaurant restaurant);

//    To calculate the total number of reviews for a restaurant, we can use a custom mapping method in MapStruct. This method will take the list of reviews from the Restaurant entity and return the count of reviews, which will be mapped to the totalReviews field in the RestaurantSummaryDto.
    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    @Named("populateTotalReviews")
    default Integer populateTotalReviews(List<Review> reviews) {
        return reviews == null ? 0 : reviews.size();
    }

    @Mapping(target = "latitude", expression = "java(geoPoint == null ? null : geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint == null ? null : geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}
