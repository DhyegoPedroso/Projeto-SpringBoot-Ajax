package com.dhyego.demoajax.repository;

import com.dhyego.demoajax.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dhyego Pedroso
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
