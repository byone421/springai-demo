package com.byone421.ai.request;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Schema(description = "创建订单请求参数")
public class CreateOrderRequest {

    @Schema(description = "用户ID")
    @JsonProperty(required = true)
    private Long userId;

    @Schema(description = "商品列表")
    @JsonProperty(required = true)
    private List<OrderItem> items;

    @Schema(description = "收货地址")
    private String address;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}