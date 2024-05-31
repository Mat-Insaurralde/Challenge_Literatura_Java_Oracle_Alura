package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByTituloContainsIgnoreCase(String titulo);

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    List<Libro> findByLenguajesIgnoreCase(String idioma);


}
