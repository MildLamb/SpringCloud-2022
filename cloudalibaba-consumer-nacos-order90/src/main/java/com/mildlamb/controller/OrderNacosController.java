package com.mildlamb.controller;

import com.mildlamb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderNacosController {

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @Autowired
    private RestTemplate restTemplate;

    // openFeign服务接口
    @Autowired
    private PaymentService paymentService;

    /**
     * restTemplate + ribbon
     * @param id
     * @return
     */
//    @GetMapping(value = "/consumer/payment/nacos/{id}")
//    public String getPayment(@PathVariable("id") Integer id) {
//        return restTemplate.getForObject(serverURL+"/payment/nacos/" + id,String.class);
//    }

    /**
     * openFeign
     */
    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return paymentService.getPayment(id);
    }



}
