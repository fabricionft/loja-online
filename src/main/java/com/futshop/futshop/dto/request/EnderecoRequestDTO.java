package com.futshop.futshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
}
