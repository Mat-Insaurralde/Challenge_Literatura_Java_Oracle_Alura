package com.lastbyte.Challenge_Literatura_Java_Alura.Autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNombreIgnoreCaseLimit1(String nombre);

    //LessThanEqual, para buscar registros menores o iguales a un valor
    List<Autor> findByFechaDeFallecimientoGreaterThanEqualAndFechaDeNacimientoLessThanEqual(Integer fechaDeFallecimiento, Integer fechaDeNacimiento);



}
