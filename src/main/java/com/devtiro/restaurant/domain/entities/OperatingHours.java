package com.devtiro.restaurant.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours {
    @Field(type = FieldType.Keyword)
    private TimeRange monday;
    @Field(type = FieldType.Keyword)
    private TimeRange tuesday;
    @Field(type = FieldType.Keyword)
    private TimeRange wednesday;
    @Field(type = FieldType.Keyword)
    private TimeRange thursday;
    @Field(type = FieldType.Keyword)
    private TimeRange friday;
    @Field(type = FieldType.Keyword)
    private TimeRange saturday;
    @Field(type = FieldType.Keyword)
    private TimeRange sunday;
}
