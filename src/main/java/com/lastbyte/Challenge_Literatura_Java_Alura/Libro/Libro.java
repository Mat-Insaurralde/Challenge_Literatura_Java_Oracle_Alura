package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;

import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String titulo;

   private List<Autor> autores;

   private List<String> lenguajes;

   private Integer numeroDeDescargas;




}
