package com.futshop.futshop.dto.response;

import com.futshop.futshop.model.CarrinhoModelUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarrinhoResponseDTO {

    private List<CarrinhoModelUsuario> itens;
    private Integer quantidadeItens;
    private Double valorTotalItens;
}
