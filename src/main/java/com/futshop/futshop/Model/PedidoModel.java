package com.futshop.futshop.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    private Long codigoCliente;

    private Long numero;

    private String data;

    private String status = "Aguardando confirmação";

    private String pagamento;

    private Integer quantidaeParcelas;

    private Double valorParcela;

    private String nomeCliente;

    private String celular;

    private String email;

    private String endereco;

    @ElementCollection
    private List<CarrinhoModelPedido> itens;

    private Integer quantidadeItens;

    private Double valor;
}
