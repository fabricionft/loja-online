package com.futshop.futshop.DTO.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
