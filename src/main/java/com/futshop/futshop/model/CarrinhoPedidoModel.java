package com.futshop.futshop.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CarrinhoPedidoModel {
    private Long codigo;
    private String imagem;
    private String descricaoProduto;
    private Integer quantidade;
    private String tamanho;
    private Double precoUnitario;
    private Double precoFinal;
}
