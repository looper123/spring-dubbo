package com.itcast.dubbo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zebon lu on 2017/5/19.
 */
@Controller
@RequestMapping("userManager")
public class UserManagerController  {

    public ModelAndView skipToRegister(ModelAndView modelAndView){
        modelAndView.setViewName("/register");
        return modelAndView;
    }

}
