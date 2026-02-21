package com.shazan.restrudent.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeRange {

    @Field(type = FieldType.Keyword)
    private String opentTime; // Format: "HH:mm"

    @Field(type = FieldType.Keyword)
    private String closeTime;   // Format: "HH:mm"
}
