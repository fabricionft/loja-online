package com.futshop.futshop.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @Column(length = 10, nullable = false)
    private Long codigoCliente;

    @Column(length = 10, nullable = false)
    private Long numero;

    @Column(length = 25, nullable = false)
    private String data;

    @Column(length = 30, nullable = false)
    private String status = "Aguardando confirmação";

    @Column(length = 300)
    private String motivoRejeicao;

    @Column(length = 30, nullable = false)
    private String pagamento;

    @Column(length = 3, nullable = false)
    private Integer quantidaeParcelas;

    @Column(length = 10, nullable = false)
    private Double valorParcela;

    @Column(length = 80, nullable = false)
    private String nomeCliente;

    @Column(length = 15, nullable = false)
    private String cpfCliente;

    @Column(length = 15, nullable = false)
    private String celular;

    @Column(length = 80, nullable = false)
    private String email;

    @Column(length = 250, nullable = false)
    private String endereco;

    @ElementCollection
    private List<CarrinhoModelPedido> itens;

    @Column(length = 5, nullable = false)
    private Integer quantidadeItens;

    @Column(length = 10, nullable = false)
    private Double valor;
}
