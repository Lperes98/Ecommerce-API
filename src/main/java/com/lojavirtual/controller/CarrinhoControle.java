package com.lojavirtual.controller;

import com.lojavirtual.modelos.Cliente;
import com.lojavirtual.modelos.Compra;
import com.lojavirtual.modelos.ItensCompra;
import com.lojavirtual.modelos.Produtos;
import com.lojavirtual.repositorios.ClienteRepositorio;
import com.lojavirtual.repositorios.CompraRepositorio;
import com.lojavirtual.repositorios.ItensCompraRepositorio;
import com.lojavirtual.repositorios.ProdutosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoControle {

    private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    @Autowired
    private ProdutosRepositorio produtosRepositorio;

    @Autowired
    private CompraRepositorio compraRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ItensCompraRepositorio itensCompraRepositorio;

    private void calcularTotal(){
        compra.setValorTotal(0.);
        for(ItensCompra it: itensCompra){
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    private void buscarUsuarioLogado(){
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if(!(autenticado instanceof AnonymousAuthenticationToken)){
            String email = autenticado.getName();
            cliente = clienteRepositorio.buscarClienteEmail(email).get(0);
        }
    }

    @RequestMapping(value = "/finalizar", method = RequestMethod.GET)
    public ModelAndView chamarFinalizar() {
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("cliente/finalizar");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @RequestMapping(value = "/finalizar/confirmar", method = RequestMethod.POST)
    public ModelAndView confirmarCompra(String formaPagamento){
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        compraRepositorio.save(compra);

        for(ItensCompra it: itensCompra){
            it.setCompra(compra);
            itensCompraRepositorio.save(it);
        }

        itensCompra = new ArrayList<>();
        compra = new Compra();

        return mv;
    }


    @RequestMapping(value = "/carrinho", method = RequestMethod.GET)
    public ModelAndView chamarCarrinho() {
      ModelAndView mv = new ModelAndView("cliente/carrinho");
      calcularTotal();
      mv.addObject("compra", compra);
      mv.addObject("listaItens", itensCompra);
      return mv;
    }

    @RequestMapping(value = "/removerProduto/{id}", method = RequestMethod.GET)
    public String removerProduto(@PathVariable Long id) {

        for(ItensCompra it: itensCompra){
            if(it.getProdutos().getId().equals(id)){
                itensCompra.remove(it);
                break;
            }
        }


        return "redirect:/carrinho";
    }

    @RequestMapping(value = "/alterarQuantidade/{id}/{acao}", method = RequestMethod.GET)
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

        for(ItensCompra it: itensCompra){
            if(it.getProdutos().getId().equals(id)){
                if(acao.equals(1)){
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                } else if(acao==0){
                    if(it.getQuantidade() < 1){
                        itensCompra.remove(it);

                    }
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                }


                break;
            }
        }

        return "redirect:/carrinho";
    }


    @RequestMapping(value = "/adicionarCarrinho/{id}", method = RequestMethod.GET)
    public String adicionarCarrinho(@PathVariable Long id) {
        Optional<Produtos> produtos = produtosRepositorio.findById(id);
        Produtos produtos1 = produtos.get();
        int controle = 0;
        for(ItensCompra it: itensCompra){
            if(it.getProdutos().getId().equals(produtos1.getId())){
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                controle=1;
                break;
            }
        }
        if(controle==0){
            ItensCompra itensCompra1 = new ItensCompra();
            itensCompra1.setProdutos(produtos1);
            itensCompra1.setValorUnitario(produtos1.getValorVenda());
            itensCompra1.setQuantidade(itensCompra1.getQuantidade() + 1);
            itensCompra1.setValorTotal(itensCompra1.getValorTotal() + (itensCompra1.getQuantidade() * itensCompra1.getValorUnitario()));
            itensCompra.add(itensCompra1);
        }
        return "redirect:/carrinho";
    }


}
