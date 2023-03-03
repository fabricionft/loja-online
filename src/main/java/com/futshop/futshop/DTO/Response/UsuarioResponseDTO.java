package com.futshop.futshop.DTO.Response;

import com.futshop.futshop.Model.CarrinhoModelUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long codigo;
    private Boolean adm;
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
    private List<CarrinhoModelUsuario> itens;
    private Integer quantidadeItens;
    private Double valorTotalItens;
}
