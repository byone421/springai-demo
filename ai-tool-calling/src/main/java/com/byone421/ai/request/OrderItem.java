package com.byone421.ai.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "订单商品")
public class OrderItem {

    @Schema(description = "商品ID")
    private Long productId;

    @Schema(description = "购买数量")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}