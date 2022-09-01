package com.lojavirtual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutControle {


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("/negado");
        return mv;
    }





}
