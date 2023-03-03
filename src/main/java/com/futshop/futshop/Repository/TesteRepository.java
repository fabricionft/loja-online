package com.futshop.futshop.Repository;

import com.futshop.futshop.Model.TesteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends JpaRepository <TesteModel, Long> {
}
