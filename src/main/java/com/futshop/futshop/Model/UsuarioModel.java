package com.futshop.futshop.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 14, nullable = false)
    private String dataNascimento;

    @Column(length = 15, nullable = false)
    private String cpf;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String senha;

    @Column(length = 17, nullable = false)
    private String celular;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 3, nullable = false)
    private String estado;

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(length = 75, nullable = false)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String rua;

    @Column(length = 5, nullable = false)
    private Integer numero;

    @Column(length = 100, nullable = false)
    private String complemento;

    @ElementCollection
    private List<CarrinhoModel> itens = null;


    //Gets e sets
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public List<CarrinhoModel> getItens() {
        return itens;
    }

    public void setItens(CarrinhoModel itens) {
        this.itens.add(itens);
    }
}