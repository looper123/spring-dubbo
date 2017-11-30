package com.itcast.dubbo.web.server;//package com.itcast.dubbo.server;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import HelloService;
//
//import javax.annotation.Resource;
//
///**
// * Created by zebon lu on 2017/5/20.
// * 调用本地service层 并且对 外部/控制层提供服务
// */
//@Service
//public class HelloServer {
//
//    @Resource(name = "helloService")
//    private HelloService helloService;
//
//    public void sayHello(String  name){
//        name += "dubbo-web-Server\n";
//        helloService.invokeSayHello(name);
//    }
//
//
//
//}
