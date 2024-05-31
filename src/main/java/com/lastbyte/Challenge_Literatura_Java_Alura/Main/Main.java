package com.lastbyte.Challenge_Literatura_Java_Alura.Main;

import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.Autor;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorRepository;
import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorService;
import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.*;

import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ConversorJsonADatos;
import com.lastbyte.Challenge_Literatura_Java_Alura.Service.ObtenerJsonAPI;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ObtenerJsonAPI ObtenerJsonAPI = new ObtenerJsonAPI();

    private ConversorJsonADatos conversorJsonADatos = new ConversorJsonADatos();

    LibroService libroService;

    AutorService autorService;

    public Main(LibroService libroService,AutorService autorService) {
    this.libroService = libroService;
    this.autorService = autorService;
    }

    private List<Libro> listaDeLibros = new ArrayList<>();

    private List<Autor> listaDeAutores = new ArrayList<>();



    public void menuApp() {

        var opc = -1;

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
                    listarAutoresVivosEnUnAño();
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
                    break;
                default:
                    System.out.println("Opcion invalida!");
                    break;
            }


        }

    }



    ///METODOS

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro a buscar: ");

        var titulo = scanner.nextLine();

       // listaDeLibros = libroRepository.findByTituloContainsIgnoreCase(titulo);
        listaDeLibros = libroService.obtenerLibrosPorTituloParecido(titulo);

        if(listaDeLibros.isEmpty()){
            System.out.println("No se han encontrado resultados!");
        }else {
            System.out.println(listaDeLibros.size() > 0 ? "Resultados":"Resultado");
            System.out.println("................");
            listaDeLibros.forEach(System.out::println);
        }
        //Limpiamos la lista
        listaDeLibros.clear();

    }


    private void guardarLibroPorTitulo() {

        System.out.println("Ingrese el titulo del libro a guardar: ");

        var titulo = scanner.nextLine();

        var jsonLibro = ObtenerJsonAPI.obtenerJsonAPI("?search=" + titulo.replace(" ", "%20"));

        var resultadosLibrosAPI = conversorJsonADatos.conversorJsonADatos(jsonLibro, ResultadosLibros.class);


        //si no se trajeron libros de la API
        if ( resultadosLibrosAPI.resultadosLibrosAPI().isEmpty() ) {

            System.out.println("No se han encontrado resultados!");

        } else {

            for (LibroAPI libroAPI : resultadosLibrosAPI.resultadosLibrosAPI()) {

                var autorBuscado = autorService.obtenerAutorPorNombreExacto(libroAPI.autores().getFirst().nombre());

                 try {

                     Libro libro = new Libro(libroAPI);

                     if (autorBuscado.isPresent()) {

                         libro.setAutor(autorBuscado.get());

                         autorBuscado.get().getLibros().add(libro);

                         autorService.guardarAutor(autorBuscado.get());

                         System.out.println("El libro "+libro.getTitulo()+" se guardo correctamente!" );
                     } else {

                         Autor autor = new Autor(libroAPI.autores().getFirst());

                         libro.setAutor(autor);

                         autor.getLibros().add(libro);

                         autorService.guardarAutor(autor);

                         System.out.println("El libro "+libro.getTitulo()+" se guardo correctamente!" );
                     }

                 }catch (DataIntegrityViolationException e){
                     System.out.println("El libro ya se encuentra en la base de datos!");
                 }

           }

        }
    }

    private void listarLibrosRegistrados() {
          listaDeLibros = libroService.obtenerTodosLosLibros();

        if(!listaDeLibros.isEmpty()){
            System.out.println("Listado de libros en la BD");
            listaDeLibros.forEach(a-> System.out.println(a.toString()));
        }else{
            System.out.println("No se han encontrado libros!");
        }
        //Limpiamos la lista
        listaDeLibros.clear();

    }

    private void listarAutoresRegistrados() {

       listaDeAutores = autorService.obtenerTodosLosAutores();

        if(!listaDeAutores.isEmpty()){
            System.out.println("Listado de autores en la BD");
          listaDeAutores.forEach(a-> System.out.println(a.toString()));
        }else{
            System.out.println("No se han encontrado autores!");
        }

        listaDeAutores.clear();
    }

    private void listarAutoresVivosEnUnAño() {
        System.out.println("Ingresa el año: ");
        var anio = scanner.nextInt();

        listaDeAutores = autorService.obtenerAutoresVivosEnUnAnio(anio);

        if (!listaDeAutores.isEmpty()){
            System.out.println("Lista de autores vivos en el año "+anio );
            listaDeAutores.forEach(a -> System.out.println(a.toString()));
        }else {
            System.out.println("No se han encontrado autores vivos en el año "+anio );
        }
        listaDeAutores.clear();


    }

    private void buscarLibrosPorIdioma() {
        var menuIdioma = """
                Ingresa el idioma:
                Español
                Ingles
                Frances
                Português
                """;

        System.out.println(menuIdioma);

     var idioma = scanner.nextLine();

     switch (idioma.toLowerCase()){

         case "español":listaDeLibros = libroService.obtenerLibrosPorIdoma("es");
         break;
         case "ingles":listaDeLibros = libroService.obtenerLibrosPorIdoma("en");
         break;
         case "frances":listaDeLibros = libroService.obtenerLibrosPorIdoma("fr");
         break;
         case "português":listaDeLibros = libroService.obtenerLibrosPorIdoma("pt");
         break;
         default:
             System.out.println("El idioma no esta en la lista!");
     }


     if (!listaDeLibros.isEmpty()){
         DoubleSummaryStatistics estadisticas = listaDeLibros.stream()
                 .filter(l -> l.getNumeroDeDescargas()>0)
                 .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
         System.out.println("Estadisticas de libros en "+idioma );
         System.out.println("---------------------------------------");
         System.out.println("Promedio de descargas de libros: " + estadisticas.getAverage());
         System.out.println("El libro mas descargado tiene " + estadisticas.getMax() +" descargas ");
         System.out.println("EL libro menos descargado tiene " + estadisticas.getMin()+" descargas");
         System.out.println("Cantidad de libros: "+estadisticas.getCount() );

     }else{
         System.out.println("No se han encontrado libros en "+idioma);
     }

     listaDeLibros.clear();
    }




    private void eliminarAutorDeLaBD() {

        System.out.println("Ingrese el nombre del autor: ");
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
        var titulo = scanner.nextLine();

        var libro = libroService.obtenerLibroPorTituloExacto(titulo);

        if (libro.isPresent()) {
          libroService.borrarLibro(libro.get());
            System.out.println("Libro Eliminado!");
        } else {
            System.out.println("Libro no encontrado!");
        }

    }

}
