package com.api.controleacesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ControleAcessoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleAcessoApplication.class, args);

	}

	@GetMapping("/")
	public String index() {
		return "Olá Mundo";

	}
}
