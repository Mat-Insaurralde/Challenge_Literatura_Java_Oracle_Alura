package com.lastbyte.Challenge_Literatura_Java_Alura.Libro;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    //Si lo usariamos en un controlador lo tendria que pasar a DTO
    public List<Libro> obtenerLibrosPorTituloParecido(String titulo){
        return libroRepository.findByTituloContainsIgnoreCase(titulo);
    }

    public Optional<Libro> obtenerLibroPorTituloExacto(String titulo){
        return libroRepository.findByTituloIgnoreCase(titulo);
    }


    public List<Libro> obtenerTodosLosLibros() {
         return libroRepository.findAll();
    }

    public void borrarLibro(Libro libro) {
        libroRepository.delete(libro);
    }

    public List<Libro> obtenerLibrosPorIdoma(String idioma) {
        return libroRepository.findByLenguajesIgnoreCase(idioma);
    }
}
