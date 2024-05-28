package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;

import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import jakarta.persistence.*;

import java.util.Optional;


@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


   private String titulo;

    @ManyToOne()
   private Autor autor;

   private String lenguajes;

   private Integer numeroDeDescargas;


    public Libro() {
    }

    public Libro (LibroAPI libroAPI) {
        this.titulo = libroAPI.titulo();
        this.lenguajes = libroAPI.lenguajes().get(0);
        this.numeroDeDescargas = libroAPI.numeroDeDescargas();
        this.autor = new Autor(libroAPI.autores().get(0));
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }


    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    @Override
    public String toString() {
        return  " - - - - Libro - - - - \n" +
                " Titulo: " + titulo + "\n" +
                " Autores: " + autor +"\n" +
                " Lenguajes: " + lenguajes +"\n" +
                " Numero De Descargas: " + numeroDeDescargas +"\n" ;
    }


}
