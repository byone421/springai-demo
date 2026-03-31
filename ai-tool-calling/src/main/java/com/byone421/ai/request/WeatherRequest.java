package com.byone421.ai.request;

import io.swagger.v3.oas.annotations.media.Schema;


public class WeatherRequest {
    @Schema(description = "城市名称，比如北京、上海、东京")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}