package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorAPI(
                 @JsonAlias("name") String nombre,
                 @JsonAlias("birth_year") Integer fechaDeNacimiento,
                 @JsonAlias("death_year") Integer fechaDeFallecimiento



) {



}
