package com.futshop.futshop.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @Column(length = 50, nullable = false)
    private String nomeProduto;

    @Lob
    @Column(length = 2000)
    private byte[] imagem = null;

    @Column(length = 400, nullable = false)
    private String descricao;

    @Column(length = 3, nullable = false)
    private Integer promocao;

    @Column(length = 5, nullable = false)
    private Integer quantidadeEstoque;

    @Column(length = 5, nullable = false)
    private String tamanho;

    @Column(length = 10, nullable = false)
    private Double valor;

    @Column(length = 50, nullable = false)
    private Date dataPostagem = new Date();

    //Gets e Sets
    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPromocao() {
        return promocao;
    }

    public void setPromocao(Integer promocao) {
        this.promocao = promocao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}
