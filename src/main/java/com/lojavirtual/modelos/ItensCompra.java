package com.lojavirtual.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "itens_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItensCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Produtos produtos;

    @ManyToOne
    private Compra compra;

    private Integer quantidade = 0;

    private Double valorUnitario=0.;

    private Double valorTotal =0.;




}
