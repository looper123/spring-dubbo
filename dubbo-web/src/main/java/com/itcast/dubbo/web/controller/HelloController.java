package com.itcast.dubbo.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.dubbo.ser.service.DemoService;
import com.itcast.dubbo.web.cache.RedisStringCache;
import com.itcast.dubbo.web.service.HelloService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zebon lu on 2017/5/20.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource(name = "helloService")
    private HelloService helloService;

    @Reference
    private DemoService demoService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    @Resource(name = "redisStringCache")
    private RedisStringCache redisStringCache;

    @RequestMapping(value = "/say")
    @ResponseBody
    public String sayHello(){
//        String content = helloService.invokeSayHello("dubbo-web-controller");
        Object execute = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
              return   connection.setNX("abc".getBytes(), "abc".getBytes());
            }
        });
        redisStringCache.delete("foo");
        String content = demoService.sayHello("dubbo-web-controller");
        Map<String,Object>  map = new HashMap<>();
        List<Object>  list = new ArrayList<>();
        map.put("msg",content);
        String s =null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            list.add(map);
            s = objectMapper.writeValueAsString(map);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
