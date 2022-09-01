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
public class PapelControle {

    @Autowired
    private PapelRepositorio papelRepositorio;

    @RequestMapping(value = "papel/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(Papel papel){
        ModelAndView mv = new ModelAndView("admin/papeis/cadastro");
        mv.addObject("papel", papel);
        return mv;
    }

    @RequestMapping(value = "papel/listar", method = RequestMethod.GET)
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("admin/papeis/lista");
       mv.addObject("listaPapeis",papelRepositorio.findAll());
       return mv;
    }

    @RequestMapping(value = "papel/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Papel papel, BindingResult result)  {
        if(result.hasErrors()){
            return cadastrar(papel);
        }
        papelRepositorio.save(papel);
        return cadastrar(new Papel());
    }

    @RequestMapping(value = "/papel/editar/{id}",method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Papel> papel = papelRepositorio.findById(id);
        return cadastrar(papel.get());
    }

    @RequestMapping(value = "/papel/remover/{id}", method = RequestMethod.GET)
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Papel> papel = papelRepositorio.findById(id);
        papelRepositorio.delete(papel.get());
        return listar();
    }



}
