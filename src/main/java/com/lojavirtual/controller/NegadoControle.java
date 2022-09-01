package com.lojavirtual.controller;

import com.lojavirtual.modelos.Papel;
import com.lojavirtual.repositorios.PapelRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class NegadoControle {


    @RequestMapping(value = "/negado", method = RequestMethod.GET)
    public ModelAndView negado(){
        ModelAndView mv = new ModelAndView("/negado");
        return mv;
    }

    @RequestMapping(value = "/negadoCliente", method = RequestMethod.GET)
    public ModelAndView negadoCliente(){
        ModelAndView mv = new ModelAndView("/negadoCliente");
        return mv;
    }



}
