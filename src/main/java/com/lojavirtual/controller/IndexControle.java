package com.lojavirtual.controller;

import com.lojavirtual.modelos.Produtos;
import com.lojavirtual.repositorios.ProdutosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexControle {

    @Autowired
    private ProdutosRepositorio produtosRepositorio;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("listaProdutos",produtosRepositorio.findAll());
        return mv;
    }





}
