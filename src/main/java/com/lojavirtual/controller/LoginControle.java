package com.lojavirtual.controller;

import com.lojavirtual.modelos.Papel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControle {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }


//    @RequestMapping(value = "/finalizar/login", method = RequestMethod.GET)
//    public ModelAndView loginCliente(){
//        ModelAndView mv = new ModelAndView("cliente/cadastroCliente");
//        return mv;
//    }


}
