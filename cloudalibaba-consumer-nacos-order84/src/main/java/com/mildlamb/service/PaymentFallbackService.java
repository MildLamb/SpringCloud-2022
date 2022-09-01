package com.mildlamb.service;

import com.mildlamb.entities.CommonResult;
import com.mildlamb.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444,"服务降级返回,---PaymentFallbackService,OpenFeign",new Payment(id,"errorSerial"));
    }
}
