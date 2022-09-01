package com.lojavirtual.controller;

import com.google.gson.Gson;
import com.lojavirtual.modelos.Funcionario;
import com.lojavirtual.modelos.Produtos;
import com.lojavirtual.repositorios.FuncionarioRepositorio;
import com.lojavirtual.repositorios.ProdutosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProdutosControle {

    private static String caminhoImagens = "D://Trabalhos/AUTORAIS/Lucas/imagens/";
    @Autowired
    private ProdutosRepositorio produtosRepositorio;

    @RequestMapping(value = "produtos/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(Produtos produto){
        ModelAndView mv = new ModelAndView("admin/produtos/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }

    @RequestMapping(value = "produtos/listar", method = RequestMethod.GET)
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("admin/produtos/lista");
       mv.addObject("listaProdutos",produtosRepositorio.findAll());
       return mv;
    }

    @RequestMapping(value = "produtos/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Produtos produtos, BindingResult result, @RequestParam("file") MultipartFile arquivo) throws Exception {
        if(result.hasErrors()){
            return cadastrar(produtos);
        }


        try{
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoImagens + String.valueOf(produtos.getId()) + arquivo.getOriginalFilename());
                Files.write(caminho,bytes);

                produtos.setNomeImagem(String.valueOf(produtos.getId()) + arquivo.getOriginalFilename());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        produtosRepositorio.save(produtos);

        return cadastrar(new Produtos());
    }

    @RequestMapping(value = "/produtos/editar/{id}",method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Produtos> produtos = produtosRepositorio.findById(id);
        return cadastrar(produtos.get());
    }

    @RequestMapping(value = "/produtos/remover/{id}", method = RequestMethod.GET)
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Produtos> produtos = produtosRepositorio.findById(id);
        produtosRepositorio.delete(produtos.get());
        return listar();
    }

    @RequestMapping(value = "/produtos/mostrarImagem/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] mostrarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(caminhoImagens + imagem);

        if(imagem!=null || imagem.trim().length()>0){
            return Files.readAllBytes(imagemArquivo.toPath());
        } else {
            return null;
        }


    }

    @RequestMapping(value = "/produtos/buscar/{nome}", method = RequestMethod.GET)
    public ModelAndView buscarPorNome(@PathVariable("nome") String nome){
        Produtos produtos = produtosRepositorio.findByNomeIgnoringCase(nome);
        return listar();
    }


}
