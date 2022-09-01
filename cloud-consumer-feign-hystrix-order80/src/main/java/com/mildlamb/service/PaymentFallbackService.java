package com.mildlamb.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String payment_OK(Integer id) {
        return " >>>>>>> PaymentFallbackService Fallback - payment_OK , /(ㄒoㄒ)/~~";
    }

    @Override
    public String payment_Timeout(Integer id) {
        return " >>>>>>> PaymentFallbackService Fallback - payment_Timeout , /(ㄒoㄒ)/~~";
    }
}
