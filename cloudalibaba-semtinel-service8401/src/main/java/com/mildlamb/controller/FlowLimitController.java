package com.mildlamb.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Sentinel 1.8.1 服务降级 相关博客链接 ： https://blog.csdn.net/cdxsza/article/details/120777343
 */

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "testB");
        return "------testB";
    }

    @GetMapping("/testD")
    public String testD() {
        //暂停几秒钟线程
        /*try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
//        log.info("testD 测试RT");

        log.info("testD 测试异常比例");
//        int age = 10/0;
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常比例");
        int age = 10/0;
        return "------testE 测试异常比例";
    }


    // 热点 key
    @GetMapping({"/testHotKey/{p1}/{p2}","/testHotKey/{p1}","/testHotKey"})
    @SentinelResource(value = "Hotkey",blockHandler = "deal_hotKey")
    public String testHotKey(@PathVariable(value = "p1",required = false) String p1,
                             @PathVariable(value = "p2",required = false) String p2){
        return "test HotKey" + "\t" + p1 + "\t" + p2;
    }

    public String deal_hotKey(String p1, String p2, BlockException blockException){
        return "This key is hot , /(ㄒoㄒ)/~~";
    }
}
