package com.lastbyte.Challenge_Literatura_Java_Alura.Main;

import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorRepository;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorService;
import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.*;

import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ConversorJsonADatos;
import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ObtenerJsonAPI;



import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ObtenerJsonAPI ObtenerJsonAPI = new ObtenerJsonAPI();

    private ConversorJsonADatos conversorJsonADatos = new ConversorJsonADatos();

    LibroRepository libroRepository;

    AutorRepository autorRepository;

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
    this.libroRepository = libroRepository;
    this.autorRepository = autorRepository;
    }

    private List<Libro> listaDeLibros = new ArrayList<>();




    public void menuApp() {

        var opc = -1;

        var bienvenida = """
                Bienvenido/a a la libreria de Alura 
                 
                 """;
        var menu = """
                Menu
                ----
                1) Buscar libro por titulo
                2) Listar libros registrados
                3) Listar autores registrados
                4) Listar autores vivos en un determinado año
                5) Listar libros por idioma
                6) Eliminar autor y sus libros de la BD
                7) Eliminar libro de la BD
                0) Salir
                Elija una opcion...
                """;


        System.out.println(bienvenida);

        while (opc != 0) {
            System.out.println(menu);

            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnUnAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    eliminarAutorDeLaBD();
                    break;
                case 7:
                    eliminarLibroDeLaBD();
                    break;

                case 0:
                    System.out.println("Saliendo de la libreria....");
                    break;
                default:
                    System.out.println("Opcion invalida!");
                    break;
            }


        }

    }



    ///METODOS
    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro: ");
        var titulo = scanner.nextLine();

        var jsonLibro = ObtenerJsonAPI.obtenerJsonAPI("?search=" + titulo.replace(" ", "%20"));

        var resultadosLibrosAPI = conversorJsonADatos.conversorJsonADatos(jsonLibro, ResultadosLibros.class);


        List<Libro> librosAGuardar = new ArrayList<>();


        //si no se trajeron libros de la API
        if ( resultadosLibrosAPI.resultadosLibrosAPI().isEmpty() ) {

            System.out.println("No se han encontrado resultados!");

        } else {




            //Mostramos libros
            listaDeLibros.forEach(l-> System.out.println(l.toString()));
        }


    }

    private void listarLibrosRegistrados() {
        List<Libro> listaLibros = libroRepository.findAll();

        if(!listaLibros.isEmpty()){
            System.out.println("Listado de libros en la BD");
            listaLibros.forEach(a-> System.out.println(a.toString()));
        }else{
            System.out.println("No se han encontrado libros!");
        }

    }

    private void listarAutoresRegistrados() {

        List<Autor> listaAutores = autorRepository.findAll();

        if(!listaAutores.isEmpty()){
            System.out.println("Listado de autores en la BD");
          listaAutores.forEach(a-> System.out.println(a.toString()));
        }else{
            System.out.println("No se han encontrado autores!");
        }

    }

    private void listarAutoresVivosEnUnAño() {
    }

    private void listarLibrosPorIdioma() {
    }

    private void eliminarAutorDeLaBD() {

        System.out.println("Ingrese el nombre del autor: ");
        var nombre = scanner.nextLine();

        var autor = autorRepository.findByNombreIgnoreCase(nombre);

        if (autor.isPresent()) {
            autorRepository.delete(autor.get());
            System.out.println("Autor Eliminado!");
        } else {
            System.out.println("Autor no encontrado!");
        }

    }
    private void eliminarLibroDeLaBD() {
        System.out.println("Ingrese el nombre del libro: ");
        var titulo = scanner.nextLine();

        var libro = libroRepository.findByTituloIgnoreCase(titulo);

        if (libro.isPresent()) {
          libroRepository.delete(libro.get());
            System.out.println("Libro Eliminado!");
        } else {
            System.out.println("Libro no encontrado!");
        }

    }

}
