package com.mildlamb.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.mildlamb.dao"})
public class MyBatisConfig {
}
