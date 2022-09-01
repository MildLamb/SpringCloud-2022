package com.mildlamb.service;

import com.mildlamb.entities.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
