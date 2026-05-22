package com.devtiro.restaurant.domain.dtos;

import com.devtiro.restaurant.domain.entities.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateDtoRequest {
    @NotBlank(message = "Restaurant name is required")
    private String name;
    @NotBlank(message = "Cuisine type is required")
    private String cuisineType;
    @NotBlank(message = "Contact information is required")
    private String contactInformation;

    private AddressDto address;
    private OperatingHoursDto operatingHours;
    private List<String> photoIds;
}
