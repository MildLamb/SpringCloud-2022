package com.mildlamb.controller;

import com.mildlamb.entities.CommonResult;
import com.mildlamb.entities.Payment;
import com.mildlamb.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService service;

    @GetMapping("/order/pay/{id}")
    public CommonResult<Payment> getById(@PathVariable("id") Long id){
        return service.getPById(id);
    }

    @GetMapping("/order/feign/timeout")
    public String paymentFeignTimeout(){
        // openfeign-ribbon , 客户端一般默认等待1秒钟
        return service.paymentFeignTimeout();
    }
}
