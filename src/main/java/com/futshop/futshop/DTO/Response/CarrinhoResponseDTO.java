package com.futshop.futshop.DTO.Response;

import com.futshop.futshop.Model.CarrinhoModelUsuario;
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
