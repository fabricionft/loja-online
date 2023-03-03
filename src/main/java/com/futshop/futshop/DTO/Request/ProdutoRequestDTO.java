package com.futshop.futshop.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequestDTO {

    private String nomeProduto;
    private String imagem;
    private String descricao;
    private Integer promocao;
    private Integer quantidadeEstoque;
    private String tamanho;
    private Double valorBase;
}
