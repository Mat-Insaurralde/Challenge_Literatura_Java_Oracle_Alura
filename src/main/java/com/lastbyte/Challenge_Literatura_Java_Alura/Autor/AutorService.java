package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;


import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AutorService {

    @Autowired
    AutorRepository autorRepository;



    //Si tendriamos que enviar los datos a un controlador deberia usar DTO
    public Optional<Autor> obtenerAutorPorNombreExacto(String nombre){
        return autorRepository.findFirstByNombreIgnoreCase(nombre);
    }


    public void guardarAutor(Autor autor) {
        autorRepository.save(autor);
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public void borrarAutor(Autor autor) {
        autorRepository.delete(autor);
    }

    public List<Autor> obtenerAutoresVivosEnUnAnio(Integer anio) {
        return autorRepository.findByFechaDeFallecimientoGreaterThanEqualAndFechaDeNacimientoLessThanEqual(anio,anio);
    }


    public void eliminarAutor(Autor autor) {
        autorRepository.delete(autor);
    }
}
