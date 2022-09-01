package com.lojavirtual.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "entrada_produto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Funcionario funcionario;
    private Date dataEntrada = new Date();
    private String observacao;
    private String fornecedor;




}
