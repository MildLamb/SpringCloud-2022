package com.mildlamb.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 配置网关转发规则   localhost:9527/guonei   转发到  http://news.baidu.com/guonei
        routes.route("path_route_mildlamb",(r) -> r.path("/guonei").uri("http://news.baidu.com/guonei"));

        return routes.build();
    }
}
