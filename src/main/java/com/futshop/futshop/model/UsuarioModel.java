package com.futshop.futshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class UsuarioModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @Column(length = 10, nullable = false)
    private Boolean admin = false;

    @Column(length = 20, nullable = false)
    private String role = "ROLE_USER";

    @Column(length = 80, nullable = false)
    private String nome;

    @Column(length = 16, nullable = false)
    private String dataNascimento;

    @Column(length = 20, nullable = false)
    private String cpf;

    @Column(length = 80, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 15, nullable = false)
    private String celular;

    @Column(length = 10, nullable = false)
    private String cep;

    @Column(length = 3, nullable = false)
    private String estado;

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(length = 50, nullable = false)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String rua;

    @Column(length = 8, nullable = false)
    private Integer numero;

    @Column(length = 80)
    private String complemento;

    @ElementCollection
    private List<CarrinhoUsuarioModel> itens = null;

    @Column(length = 5, nullable = false)
    private Integer quantidadeItens = 0;

    @Column(length = 10, nullable = false)
    private Double valorTotalItens = 0.0;

    public void setItens(CarrinhoUsuarioModel item) {
        this.itens.add(item);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}