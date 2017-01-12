package com.springmvc4.test.controller;

import com.springmvc4.test.util.CommonPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

/**
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public ModelAndView index(HttpServletRequest req) throws MalformedURLException {
//        System.out.println("测试："+ CommonPlaceholderConfigurer.getContextProperty("test"));
//        System.out.println(this.getClass().getResource("/"));
//        System.out.println(req.getContextPath());
//        System.out.println("2");
//        System.out.println(req.getServletContext().getRealPath("/config.json"));
//        System.out.println(req.getRequestURI());
//        System.out.println(req.getRequestURL());
        return new ModelAndView("index");
    }

}
