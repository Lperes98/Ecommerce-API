package com.lojavirtual.controller;

import com.lojavirtual.modelos.Permissao;
import com.lojavirtual.repositorios.FuncionarioRepositorio;
import com.lojavirtual.repositorios.PapelRepositorio;
import com.lojavirtual.repositorios.PermissaoRepositorio;
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
public class PermissaoControle {

    @Autowired
    private PermissaoRepositorio permissaoRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
    @Autowired
    private PapelRepositorio papelRepositorio;

    @RequestMapping(value = "permissao/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(Permissao permissao){
        ModelAndView mv = new ModelAndView("admin/permissoes/cadastro");
        mv.addObject("permissao", permissao);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaPapeis", papelRepositorio.findAll());
        return mv;
    }

    @RequestMapping(value = "permissao/listar", method = RequestMethod.GET)
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("admin/permissoes/lista");
       mv.addObject("listaPermissoes",permissaoRepositorio.findAll());
       return mv;
    }

    @RequestMapping(value = "permissao/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Permissao permissao, BindingResult result)  {
        if(result.hasErrors()){
            return cadastrar(permissao);
        }
        permissaoRepositorio.save(permissao);
        return cadastrar(new Permissao());
    }

    @RequestMapping(value = "/permissao/editar/{id}",method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Permissao> permissao = permissaoRepositorio.findById(id);
        return cadastrar(permissao.get());
    }

    @RequestMapping(value = "/permissao/remover/{id}", method = RequestMethod.GET)
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Permissao> permissao = permissaoRepositorio.findById(id);
        permissaoRepositorio.delete(permissao.get());
        return listar();
    }



}
