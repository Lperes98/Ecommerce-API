package com.lojavirtual.controller;

import com.lojavirtual.modelos.EntradaItens;
import com.lojavirtual.modelos.EntradaProduto;
import com.lojavirtual.modelos.Produtos;
import com.lojavirtual.repositorios.EntradaItensRepositorio;
import com.lojavirtual.repositorios.EntradaProdutoRepositorio;
import com.lojavirtual.repositorios.FuncionarioRepositorio;
import com.lojavirtual.repositorios.ProdutosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaProdutoControle {

    private List<EntradaItens> listaItens = new ArrayList<>();

    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;
    @Autowired
    private EntradaItensRepositorio entradaItensRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
    @Autowired
    private ProdutosRepositorio produtosRepositorio;

    @RequestMapping(value = "entrada/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens){
        ModelAndView mv = new ModelAndView("admin/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("listaItens", this.listaItens);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaProdutos", produtosRepositorio.findAll());
        return mv;
    }

    @RequestMapping(value = "entrada/salvar",method = RequestMethod.POST)
    public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens)  {

        if(acao.equals("itens")){
            this.listaItens.add(entradaItens);
        } else if(acao.equals("salvar")){
            entradaProdutoRepositorio.save(entrada);
            for(EntradaItens lista: listaItens){
                lista.setEntradaProduto(entrada);
                entradaItensRepositorio.save(lista);
                Optional<Produtos> prod = produtosRepositorio.findById(lista.getProdutos().getId());
                Produtos produtos = prod.get();
                produtos.setQtEstoque((produtos.getQtEstoque() + lista.getQuantidade()));
                produtos.setValorVenda(lista.getValorVenda());
                produtosRepositorio.save(produtos);
                this.listaItens = new ArrayList<>();
            }

            return cadastrar(new EntradaProduto(), new EntradaItens());
        }



        return cadastrar(entrada, new EntradaItens());
    }

//    @RequestMapping(value = "produtos/listar", method = RequestMethod.GET)
//    public ModelAndView listar(){
//        ModelAndView mv = new ModelAndView("admin/produtos/lista");
//       mv.addObject("listaProdutos",produtosRepositorio.findAll());
//       return mv;
//    }



//    @RequestMapping(value = "/produtos/editar/{id}",method = RequestMethod.GET)
//    public ModelAndView editar(@PathVariable("id") Long id){
//        Optional<Produtos> produtos = produtosRepositorio.findById(id);
//        return cadastrar(produtos.get());
//    }

//    @RequestMapping(value = "/produtos/remover/{id}", method = RequestMethod.GET)
//    public ModelAndView remover(@PathVariable("id") Long id){
//        Optional<Produtos> produtos = produtosRepositorio.findById(id);
//        produtosRepositorio.delete(produtos.get());
//        return listar();
//    }
//    @RequestMapping(value = "/produtos/buscar/{nome}", method = RequestMethod.GET)
//    public ModelAndView buscarPorNome(@PathVariable("nome") String nome){
//        Produtos produtos = produtosRepositorio.findByNomeIgnoringCase(nome);
//        return listar();
//    }


}
