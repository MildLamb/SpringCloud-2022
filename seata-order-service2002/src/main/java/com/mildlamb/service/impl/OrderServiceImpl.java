package com.mildlamb.service.impl;

import com.mildlamb.dao.OrderDao;
import com.mildlamb.domain.Order;
import com.mildlamb.service.AccountService;
import com.mildlamb.service.OrderService;
import com.mildlamb.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired(required = false)
    private AccountService accountService;
    @Autowired(required = false)
    private StorageService storageService;



    @Override
    public void create(Order order) {
        // 新建订单
        log.info(" ---------->> 开始新建订单");
        orderDao.create(order);

        // 扣减库存
        log.info(" ---------->> 订单微服务开始调用库存，做减库存操作");
        storageService.decrease(order.getProductId(),order.getCount());

        // 扣减余额
        log.info(" ---------->> 订单微服务开始操作账户，做减余额操作");
        accountService.decrease(order.getUserId(),order.getMoney());

        // 修改订单状态，从0到1，1代表已经完成
        log.info(" --------->> 修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info(" --------->> 修改订单状态结束");

        log.info(" --------->> 下订单结束了，O(∩_∩)O ~~~");
    }
}
