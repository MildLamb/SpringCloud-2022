package com.mildlamb.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mildlamb.entities.CommonResult;
import com.mildlamb.entities.Payment;
import com.mildlamb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "handlerBlock")  // blockHandler 负责违反sentinel控制台配置的情况
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "handlerBlock",
                        exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" +id, CommonResult.class,id);

        if (id == 4) {
            throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        }else if (result.getData() == null) {
            throw new NullPointerException ("NullPointerException,该ID没有对应记录,空指针异常");
        }

        return result;
    }

    // 当上面的(程序出现异常)，就会走fallback中的方法
    /**
     * 兜底方法生效条件：
     *      返回值类型，参数类型和个数顺序与原方法保持一致（泛型，注解修饰的不影响）
     *      对于额外的异常参数，要么不添加，要么固定写死 ，例如下面的 Throwable
     */
    public CommonResult handlerFallback(@PathVariable Long id,Throwable e) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(444,"兜底异常handlerFallback,exception内容  "+e.getMessage(),payment);
    }

    /**
     * 违反了限流规则，会抛出 BlockException
     */
    public CommonResult handlerBlock(@PathVariable Long id, BlockException blockException) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(445,"blockHandler-sentinel限流，blockException" + blockException.getMessage(),payment);
    }


    // ===================== OpenFeign
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
