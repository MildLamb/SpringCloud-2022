package com.mildlamb.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池 : " + Thread.currentThread().getName() + "\t paymentInfo, id : " + id + "\t (^_^)~";
    }

    @HystrixCommand(fallbackMethod = "payment_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000")
    })
    public String paymentInfo_Timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池 : " + Thread.currentThread().getName() + "\t paymentInfo_Timeout, id : " + id + "\t (T_T)~";
    }

    /**
     * 总结：熔断方法的返回值和参数列表需和原方法一致
     * @param id
     * @return
     */
    public String payment_TimeoutHandler(Integer id){
        return "温馨提示,线程池 : " + Thread.currentThread().getName() + "\t paymentInfo_TimeoutHandler,  "  + "\t /(ㄒoㄒ)/~~";
    }

    // =============  服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  // 触发熔断的最小请求数量
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "100000"),  // 100 seconds that we will sleep before trying again after tripping the circuit 熔断后多少毫秒后尝试恢复
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),   // 失败率达到多少跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
