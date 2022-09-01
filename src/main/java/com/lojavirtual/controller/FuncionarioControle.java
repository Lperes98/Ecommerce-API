package com.lojavirtual.controller;

import com.google.gson.Gson;
import com.lojavirtual.modelos.Funcionario;
import com.lojavirtual.repositorios.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

@Controller
public class FuncionarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @RequestMapping(value = "funcionarios/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("admin/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @RequestMapping(value = "funcionarios/listar", method = RequestMethod.GET)
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("admin/funcionarios/lista");
       mv.addObject("listaFuncionarios",funcionarioRepositorio.findAll());
       return mv;
    }

    @RequestMapping(value = "funcionarios/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result) throws Exception {
        if(result.hasErrors()){
            return cadastrar(funcionario);
        }
        URL url = new URL("https://viacep.com.br/ws/"+funcionario.getCep()+"/json/");
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();
        while((cep = br.readLine())!= null){
            jsonCep.append(cep);
        }

        Funcionario funcionarioAux = new Gson().fromJson(jsonCep.toString(),Funcionario.class);

        funcionario.setCep(funcionarioAux.getCep());
        funcionario.setLocalidade(funcionarioAux.getLocalidade());
        funcionario.setBairro(funcionarioAux.getBairro());
        funcionario.setComplemento(funcionarioAux.getComplemento());
        funcionario.setLogradouro(funcionarioAux.getLogradouro());
        funcionario.setUf(funcionarioAux.getUf());

        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
        funcionarioRepositorio.save(funcionario);
        return cadastrar(new Funcionario());
    }

    @RequestMapping(value = "/funcionarios/editar/{id}",method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        return cadastrar(funcionario.get());
    }

    @RequestMapping(value = "/funcionarios/remover/{id}", method = RequestMethod.GET)
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        funcionarioRepositorio.delete(funcionario.get());
        return listar();
    }
    @RequestMapping(value = "/funcionarios/buscar/{nome}", method = RequestMethod.GET)
    public ModelAndView buscarPorNome(@PathVariable("nome") String nome){
        Funcionario funcionario = funcionarioRepositorio.findByNomeIgnoringCase(nome);
        return listar();
    }


}
