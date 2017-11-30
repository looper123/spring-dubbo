package com.itcast.dubbo.pro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itcast.dubbo.ser.service.DemoService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by zebon lu on 2017/5/15.
 * 服务提供者 提供的具体功能类
 */
@Service
public class DemoServiceImpl implements DemoService {


    public String sayHello(String name) {
        return "Hello " + name;
    }

}
