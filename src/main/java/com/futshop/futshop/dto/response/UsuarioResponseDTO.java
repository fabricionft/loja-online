package com.futshop.futshop.dto.response;

import com.futshop.futshop.model.CarrinhoUsuarioModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long codigo;
    private Boolean admin;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String celular;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private List<CarrinhoUsuarioModel> itens;
    private Integer quantidadeItens;
    private Double valorTotalItens;
}
