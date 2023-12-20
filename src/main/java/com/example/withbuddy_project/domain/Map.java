package com.example.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Map {
    private Long mapId;
    private Long addressId;
    private Long usercount;
    private String addressName;
    private String mapX;
    private String mapY;
}


