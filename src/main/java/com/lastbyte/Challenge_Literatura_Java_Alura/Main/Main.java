package com.lastbyte.Challenge_Literatura_Java_Alura.Main;

import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorRepository;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorService;
import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.*;

import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ConversorJsonADatos;
import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ObtenerJsonAPI;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.*;
import java.util.stream.Collectors;


public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ObtenerJsonAPI ObtenerJsonAPI = new ObtenerJsonAPI();

    private ConversorJsonADatos conversorJsonADatos = new ConversorJsonADatos();

    LibroService libroService;

    AutorService autorService;

    public Main(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    private List<Libro> listaDeLibros = new ArrayList<>();

    private List<Autor> listaDeAutores = new ArrayList<>();

    int opc = -1;

    public void menuApp() {


        var bienvenida = """
                                
                -----------------------------------
                Bienvenido/a a la libreria de Alura
                ----------------------------------- 
                 """;
        var menu = """
                               
                  - - - - - Menu - - - - -
                1) Guardar libro por titulo
                2) Buscar libro por titulo
                3) Listar libros registrados
                4) Listar autores registrados
                5) Listar autores vivos en un determinado año
                6) Estadisticas de libros por idioma
                7) Eliminar autor y sus libros 
                8) Eliminar libro 
                0) Salir
                Elija una opcion...
                """;


        System.out.println(bienvenida);

        while (opc != 0) {
            System.out.println(menu);
            try {

                opc = scanner.nextInt();
                scanner.nextLine();

                switch (opc) {
                    case 1:
                        guardarLibroPorTitulo();
                        break;
                    case 2:
                        buscarLibroPorTitulo();
                        break;
                    case 3:
                        listarLibrosRegistrados();
                        break;
                    case 4:
                        listarAutoresRegistrados();
                        break;
                    case 5:
                        listarAutoresVivosEnUnAnio();
                        break;
                    case 6:
                        buscarLibrosPorIdioma();
                        break;
                    case 7:
                        eliminarAutorDeLaBD();
                        break;
                    case 8:
                        eliminarLibroDeLaBD();
                        break;

                    case 0:
                        System.out.println("Saliendo de la libreria....");
                        scanner.close();
                        break;
                    default:
                        System.out.println("Opcion invalida!");
                        break;
                }


                //Limpiamos la lista
                listaDeLibros.clear();
                //Limpiamos la lista
                listaDeAutores.clear();

            } catch (InputMismatchException e) {
                System.out.println("Error al ingresar la opcion! " + e.getMessage());
                scanner.nextLine();
            }
        }

    }


    ///METODOS

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro a buscar: ");


        var titulo = scanner.nextLine();
        if (!titulo.isEmpty()) {
            listaDeLibros = libroService.obtenerLibrosPorTituloParecido(titulo);
        }

        if (listaDeLibros.isEmpty()) {
            System.out.println("No se han encontrado resultados!");
        } else {
            System.out.println(listaDeLibros.size() > 0 ? "Resultados" : "Resultado");
            System.out.println("------------");
            listaDeLibros.forEach(System.out::println);
        }


    }


    private void guardarLibroPorTitulo() {
        boolean libroEncontrado = false;
        System.out.println("Ingrese el titulo del libro a guardar: ");


        var titulo = scanner.nextLine();

        if (!titulo.isEmpty()) {


            var jsonLibro = ObtenerJsonAPI.obtenerJsonAPI("?search=" + titulo.replace(" ", "%20"));


            var resultadosLibrosAPI = conversorJsonADatos.conversorJsonADatos(jsonLibro, ResultadosLibros.class);


            //si no se trajeron libros de la API
            if (resultadosLibrosAPI.resultadosLibrosAPI().isEmpty()) {

                System.out.println("Libro no encontrado!");

            } else {

                for (LibroAPI libroAPI : resultadosLibrosAPI.resultadosLibrosAPI()) {
                    try {

                        if (libroAPI.titulo().toLowerCase().equals(titulo.toLowerCase())) {

                            var autorBuscado = autorService.obtenerAutorPorNombreExacto(libroAPI.autores().getFirst().nombre());


                            Libro libro = new Libro(libroAPI);

                            if (autorBuscado.isPresent()) {

                                libro.setAutor(autorBuscado.get());

                                libroService.guardarLibro(libro);

                                System.out.println("El libro " + libro.getTitulo() + " se guardo correctamente!");
                                libroEncontrado=true;
                            } else {

                                Autor autor = new Autor(libroAPI.autores().getFirst());

                                autorService.guardarAutor(autor);

                                libro.setAutor(autor);

                                libroService.guardarLibro(libro);

                                System.out.println("El libro " + libro.getTitulo() + " se guardo correctamente!");
                                libroEncontrado=true;
                            }
                        }

                    } catch (DataIntegrityViolationException e) {
                        libroEncontrado=true;
                        System.out.println("El libro ya se encuentra en la base de datos!");
                    }

                }
                if (!libroEncontrado){
                    System.out.println("Libro no encontrado!");
                }

            }
        }

    }

    private void listarLibrosRegistrados() {
        listaDeLibros = libroService.obtenerTodosLosLibros();

        if (!listaDeLibros.isEmpty()) {
            System.out.println("Listado de libros en la BD");
            System.out.println("---------------------------");
            listaDeLibros.forEach(a -> System.out.println(a.toString()));
        } else {
            System.out.println("No se han encontrado libros!");
        }

    }

    private void listarAutoresRegistrados() {

        listaDeAutores = autorService.obtenerTodosLosAutores();

        if (!listaDeAutores.isEmpty()) {
            System.out.println("Listado de autores en la BD");
            System.out.println("---------------------------");
            listaDeAutores.forEach(a -> System.out.println(a.toString()));
        } else {
            System.out.println("No se han encontrado autores!");
        }

    }

    private void listarAutoresVivosEnUnAnio() {
        System.out.println("Ingresa el año: ");
        try {

            var anio = scanner.nextInt();
            listaDeAutores = autorService.obtenerAutoresVivosEnUnAnio(anio);

            if (!listaDeAutores.isEmpty()) {
                System.out.println("Lista de autores vivos en el año " + anio);
                listaDeAutores.forEach(a -> System.out.println(a.toString()));
            } else {
                System.out.println("No se han encontrado autores vivos en el año " + anio);
            }

            listaDeAutores.clear();
        } catch (InputMismatchException e) {
            System.out.println("Error al cargar el año " + e.getMessage());
            scanner.nextLine();
        }

    }

    private void buscarLibrosPorIdioma() {
        var menuIdioma = """
                Ingresa un idioma:
                ------------------
                Español
                Ingles
                Frances
                Português
                """;

        System.out.println(menuIdioma);

        var idioma = scanner.nextLine();

        switch (idioma.toLowerCase()) {

            case "español":
                listaDeLibros = libroService.obtenerLibrosPorIdoma("es");
                break;
            case "ingles":
                listaDeLibros = libroService.obtenerLibrosPorIdoma("en");
                break;
            case "frances":
                listaDeLibros = libroService.obtenerLibrosPorIdoma("fr");
                break;
            case "português":
                listaDeLibros = libroService.obtenerLibrosPorIdoma("pt");
                break;
            default:
                System.out.println("El idioma no esta en la lista!");
        }


        if (!listaDeLibros.isEmpty()) {
            DoubleSummaryStatistics estadisticas = listaDeLibros.stream()
                    .filter(l -> l.getNumeroDeDescargas() > 0)
                    .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
            System.out.println("Estadisticas de libros en " + idioma);
            System.out.println("---------------------------------------");
            System.out.println("Promedio de descargas de libros: " + estadisticas.getAverage());
            System.out.println("El libro mas descargado tiene " + estadisticas.getMax() + " descargas ");
            System.out.println("EL libro menos descargado tiene " + estadisticas.getMin() + " descargas");
            System.out.println("Cantidad de libros: " + estadisticas.getCount());

        } else {
            System.out.println("No se han encontrado libros en " + idioma);
        }

    }


    private void eliminarAutorDeLaBD() {

        System.out.println("Ingrese el nombre del autor: ");
        System.out.println("-----------------------------");
        var nombre = scanner.nextLine();

        var autor = autorService.obtenerAutorPorNombreExacto(nombre);

        if (autor.isPresent()) {
            autorService.borrarAutor(autor.get());
            System.out.println("Autor Eliminado!");
        } else {
            System.out.println("Autor no encontrado!");
        }


    }

    private void eliminarLibroDeLaBD() {
        System.out.println("Ingrese el nombre del libro: ");
        System.out.println("-----------------------------");
        var titulo = scanner.nextLine();

        var libro = libroService.obtenerLibroPorTituloExacto(titulo);

        if (libro.isPresent()) {
            Autor autor = libro.get().getAutor();

            autor.eliminarLibro(libro.get());

            if (autor.getLibros().isEmpty()) {
                autorService.eliminarAutor(autor);
            } else {
                autorService.guardarAutor(autor);
            }


            libroService.borrarLibro(libro.get());


            System.out.println("Libro Eliminado!");
        } else {
            System.out.println("Libro no encontrado!");
        }

    }

}
