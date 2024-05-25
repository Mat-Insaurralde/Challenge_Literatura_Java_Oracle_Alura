package com.lastbyte.Challenge_Literatura_Java_Alura.Main;

import java.util.Scanner;

public class Main {

    private Scanner scanner;


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
                0) Salir
                Elija una opcion...
                """;


        System.out.println(bienvenida);

        while (opc != 0) {
            System.out.println(menu);
            opc = scanner.nextInt();
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
                default:
                    System.out.println("Opcion invalida!");
                    break;
            }


        }

    }


    ///METODOS
    private void buscarLibroPorTitulo() {
    }

    private void listarLibrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivosEnUnAño() {
    }

    private void listarLibrosPorIdioma() {
    }
}
