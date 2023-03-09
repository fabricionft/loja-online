package com.futshop.futshop;

import com.futshop.futshop.Services.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FutshopApplication {

	static UsuarioService usuario = new UsuarioService();
	public static void main(String[] args){
		SpringApplication.run(FutshopApplication.class, args);
	}
}
