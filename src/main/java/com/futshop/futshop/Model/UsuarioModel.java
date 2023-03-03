package com.futshop.futshop.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    private Boolean adm = false;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String senha;
    private String celular;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;

    @ElementCollection
    private List<CarrinhoModelUsuario> itens = null;
    private Integer quantidadeItens = 0;
    private Double valorTotalItens = 0.0;

    public void setItens(CarrinhoModelUsuario item) {
        this.itens.add(item);
    }
}