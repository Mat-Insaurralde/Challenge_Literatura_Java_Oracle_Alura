package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);



}
