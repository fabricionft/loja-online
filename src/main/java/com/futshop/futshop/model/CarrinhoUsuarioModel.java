package com.futshop.futshop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoUsuarioModel {
    private Long codigo;
    private String imagem;
    private String descricaoProduto;
    private Integer quantidade;
    private String tamanho;
    private Double precoUnitario;
    private Double precoFinal;

    public CarrinhoUsuarioModel(Integer quantidade){
        this.quantidade = quantidade;
    }
}
