package com.futshop.futshop.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teste")
public class TesteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer codigo;

    @ElementCollection
    private List<CarrinhoModel> itens;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<CarrinhoModel> getItens() {
        return itens;
    }

    public void setItens(CarrinhoModel itens) {
        this.itens.add(itens);
    }
}
