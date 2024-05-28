package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.Libro;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer fechaDeNacimiento;

    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<Libro> libros;


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
        libros.forEach(l-> l.setAutor(this));
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
        return " Autor " +
                " Id: " + id +
                " Nombre: " + nombre +
                " Fecha De Nacimiento: " + fechaDeNacimiento +
                " Fecha De Fallecimiento: " + fechaDeFallecimiento +
                " Libros: " + libros ;
    }
}
