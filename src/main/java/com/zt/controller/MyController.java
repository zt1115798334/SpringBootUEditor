package com.zt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangtong
 * Created by on 2017/11/27
 */
@Controller
public class MyController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam String content) {
        System.out.println("content = " + content);
        return content;
    }
}
