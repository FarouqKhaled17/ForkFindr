package com.devtiro.restaurant.mappers;

import com.devtiro.restaurant.domain.dtos.PhotoDto;
import com.devtiro.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

// MapStruct mapper to convert Photo entity to PhotoDto. It ignores unmapped target properties to avoid compilation errors if the Photo entity has fields that are not present in the PhotoDto.
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);
}
