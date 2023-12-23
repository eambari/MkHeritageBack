package com.heritage.mkheritageback.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HeritageSite {
    private String id;
    private String name;
    private String historic;
    private String natural;
    private String tourism;
    private String address;
    private String lat;
    private String lon;
}
