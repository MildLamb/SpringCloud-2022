package com.mildlamb.controller;

import com.mildlamb.entities.CommonResult;
import com.mildlamb.entities.Payment;
import com.mildlamb.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info(" ****** 插入结果：" + result);

        if (result > 0){
            return new CommonResult(200,"插入数据成功,serverPort:" + serverPort,result);
        } else {
            return new CommonResult(500,"数据插入失败");
        }
    }

    @GetMapping("/getSerial/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info(" ****** 插入结果：" + payment);
        if (payment != null){
            return new CommonResult(200,"读取数据成功,serverPort:" + serverPort,payment);
        } else {
            return new CommonResult(500,"查询支付记录失败,id：" + id);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
