package com.futshop.futshop.Repository;

import com.futshop.futshop.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    @Query(value = "select * from usuarios where codigo = ?", nativeQuery = true)
    public UsuarioModel buscarPorID(Long codigo);

    @Query(value = "select * from usuarios where codigo = ?", nativeQuery = true)
    public Optional<UsuarioModel> buscarOPTPorID(Long codigo);

    @Query(value = "select * from usuarios where email = ?", nativeQuery = true)
    public Optional<UsuarioModel> buscarOPTPorEmail(String email);

    @Query(value = "select * from usuarios where email = ?", nativeQuery = true)
    public UsuarioModel buscarPorLogin(String login);
}
