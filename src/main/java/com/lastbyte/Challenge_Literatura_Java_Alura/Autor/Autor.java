package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDate fechaDeNacimiento;

    private LocalDate fechaDeFallecimiento;



}
