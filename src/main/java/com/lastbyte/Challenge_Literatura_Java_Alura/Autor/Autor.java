package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.Libro;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer fechaDeNacimiento;

    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();


    public Autor() {
    }

    public Autor(AutorAPI autorAPI) {
        this.nombre = autorAPI.nombre();
        this.fechaDeNacimiento = autorAPI.fechaDeNacimiento();
        this.fechaDeFallecimiento = autorAPI.fechaDeFallecimiento();

    }


    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Long getId() {
        return id;
    }


    @Override
    public String toString() {

        String librosS = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", "));


        return "- - -  Autor - - - " + "\n"+
                " Nombre: " + nombre +"\n"+
                (fechaDeNacimiento != null ? " Fecha De Nacimiento: "+fechaDeNacimiento+"\n" :"" )+
                (fechaDeFallecimiento != null ? " Fecha De Fallecimiento: "+fechaDeFallecimiento +"\n":"" )+
                " Libros: " + librosS +"\n";
    }
}