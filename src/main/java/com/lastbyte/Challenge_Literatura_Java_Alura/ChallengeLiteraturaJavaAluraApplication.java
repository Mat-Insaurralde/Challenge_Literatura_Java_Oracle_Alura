package com.lastbyte.Challenge_Literatura_Java_Alura;

import com.lastbyte.Challenge_Literatura_Java_Alura.Main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaJavaAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaJavaAluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		 Main main = new Main();

		 main.menuApp();


	}
}
