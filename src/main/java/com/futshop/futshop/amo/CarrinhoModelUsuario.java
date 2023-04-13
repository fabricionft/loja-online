package com.futshop.futshop.amo;

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
public class CarrinhoModelUsuario {
    private Long codigo;
    private String imagem;
    private String descricaoProduto;
    private Integer quantidade;
    private String tamanho;
    private Double precoUnitario;
    private Double precoFinal;

    public CarrinhoModelUsuario(Integer quantidade){
        this.quantidade = quantidade;
    }
}
