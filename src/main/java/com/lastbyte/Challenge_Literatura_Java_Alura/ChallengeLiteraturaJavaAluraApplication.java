package com.lastbyte.Challenge_Literatura_Java_Alura;


import com.lastbyte.Challenge_Literatura_Java_Alura.Autor.AutorService;
import com.lastbyte.Challenge_Literatura_Java_Alura.Libro.LibroService;
import com.lastbyte.Challenge_Literatura_Java_Alura.Main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaJavaAluraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraturaJavaAluraApplication.class, args);
    }


    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;

    @Override
    public void run(String... args) throws Exception {

        Main main = new Main(libroService,autorService);
        main.menuApp();


    }
}
