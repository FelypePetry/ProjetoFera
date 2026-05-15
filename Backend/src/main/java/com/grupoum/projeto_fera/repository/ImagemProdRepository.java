package com.grupoum.projeto_fera.repository;

import com.grupoum.projeto_fera.model.ImagemProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemProdRepository extends JpaRepository<ImagemProd, Long> {
}
