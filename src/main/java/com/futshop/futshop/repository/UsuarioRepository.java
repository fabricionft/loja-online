package com.futshop.futshop.repository;

import com.futshop.futshop.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    @Query(value = "select * from usuarios where codigo = ?", nativeQuery = true)
    public Optional<UsuarioModel> buscarPorId(Long codigo);

    @Query(value = "select * from usuarios where email = ?", nativeQuery = true)
    public Optional<UsuarioModel> buscarPorEmail(String email);

}
