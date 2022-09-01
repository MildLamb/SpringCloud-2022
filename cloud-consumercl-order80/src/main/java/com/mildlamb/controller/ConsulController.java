package com.mildlamb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consul")
public class ConsulController {
    private static final String INVOKE_URL = "http://consul-provider-payment";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment/mildlamb")
    public String paymentInfo(){
        String result = restTemplate.getForObject(INVOKE_URL+"/consul/payment",String.class);
        return result;
    }
}
