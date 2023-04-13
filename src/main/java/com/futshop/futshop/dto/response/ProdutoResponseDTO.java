package com.futshop.futshop.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResponseDTO {

    private Long codigo;
    private String nomeProduto;
    private String imagem;
    private String descricao;
    private String tipo;
    private Integer promocao;
    private Integer quantidadeEstoque;
    private String tamanho;
    private Double valorBase;
    private Double valorComDesconto;
    private String dataPostagem;
}
