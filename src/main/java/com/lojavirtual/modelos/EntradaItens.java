package com.lojavirtual.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "entrada_itens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaItens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EntradaProduto entradaProduto;
    @ManyToOne
    private Produtos produtos;
    private Double quantidade =0.0;
    private Double valorProduto =0.0;
    private Double valorVenda =0.0;





}
