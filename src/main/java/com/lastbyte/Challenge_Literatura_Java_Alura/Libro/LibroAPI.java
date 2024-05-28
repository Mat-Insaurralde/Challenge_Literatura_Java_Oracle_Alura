package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorAPI;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroAPI(
        @JsonAlias("title")String titulo,
        @JsonAlias("authors")List<AutorAPI> autores,
        @JsonAlias("languages")List<String> lenguajes,
        @JsonAlias("download_count")Integer numeroDeDescargas

) {}
