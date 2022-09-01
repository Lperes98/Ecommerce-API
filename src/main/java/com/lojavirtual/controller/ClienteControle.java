package com.lojavirtual.controller;

import com.google.gson.Gson;
import com.lojavirtual.modelos.Cliente;
import com.lojavirtual.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;


    @RequestMapping(value = "cliente/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(Cliente cliente){
        ModelAndView mv = new ModelAndView("cliente/cadastroCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }

    @RequestMapping(value = "cliente/listar", method = RequestMethod.GET)
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("admin/clientes/lista");
       mv.addObject("listaClientes",clienteRepositorio.findAll());
       return mv;
    }

    @RequestMapping(value = "cliente/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Cliente cliente) throws Exception {
//        if(result.hasErrors()){
//            return cadastrar(cliente);
//        }
        URL url = new URL("https://viacep.com.br/ws/"+cliente.getCep()+"/json/");
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();
        while((cep = br.readLine())!= null){
            jsonCep.append(cep);
        }

        Cliente clientAux = new Gson().fromJson(jsonCep.toString(),Cliente.class);

        cliente.setCep(clientAux.getCep());
        cliente.setLocalidade(clientAux.getLocalidade());
        cliente.setBairro(clientAux.getBairro());
//        cliente.setComplemento(clientAux.getComplemento());
        cliente.setLogradouro(clientAux.getLogradouro());
        cliente.setUf(clientAux.getUf());

        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        clienteRepositorio.save(cliente);
        return cadastrar(new Cliente());
    }

    @RequestMapping(value = "/cliente/editar/{id}",method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cadastrar(cliente.get());
    }

    @RequestMapping(value = "/cliente/remover/{id}", method = RequestMethod.GET)
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        clienteRepositorio.delete(cliente.get());
        return listar();
    }
    @RequestMapping(value = "/cliente/buscar/{nome}", method = RequestMethod.GET)
    public ModelAndView buscarPorNome(@PathVariable("nome") String nome){
        Cliente cliente = clienteRepositorio.findByNomeIgnoringCase(nome);
        return listar();
    }


}
