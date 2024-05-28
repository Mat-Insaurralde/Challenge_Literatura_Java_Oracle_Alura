package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosLibros(
                @JsonAlias("results")
                List<LibroAPI> resultadosLibrosAPI
) { }
