package com.futshop.futshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private Long codigo;
    private String token;
    private Boolean admin;
    private String email;
    private String nome;
    private Integer quantidadeItens;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
}
