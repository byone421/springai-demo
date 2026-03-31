package com.byone421.ai.response;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "创建订单返回结果")
public class CreateOrderResponse {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "状态")
    private String status;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}