package com.byone421.ai.tools;

import com.byone421.ai.request.CreateOrderRequest;
import com.byone421.ai.request.WeatherRequest;
import com.byone421.ai.response.CreateOrderResponse;
import org.springframework.ai.tool.annotation.Tool;

public class AiTools {

    @Tool(description = "根据城市查询天气信息")
    public String getWeather(WeatherRequest request) {
        //通过天气api查询一下天气
        return request.getLocation() + "的天气是晴天";
    }


    @Tool(description = "创建订单工具，用户下单时调用，需要提供用户ID、商品列表和数量")
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        // 1. 参数校验
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("订单商品不能为空");
        }

        // 2. 模拟业务逻辑
        String orderNo = "order" + System.currentTimeMillis();

        // 3. 返回结果
        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderNo(orderNo);
        response.setStatus("success");

        return response;
    }




}
