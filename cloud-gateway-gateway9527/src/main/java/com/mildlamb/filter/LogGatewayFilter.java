package com.mildlamb.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class LogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(" >>>>>>>>>> Come in LogGatewayFilter ：" + new Date());
        String rname = exchange.getRequest().getQueryParams().getFirst("rname");
        if (rname == null){
            System.out.println(" >>>>>>>>>> 用户名为空 , 不允许访问");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);  // 放行
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
