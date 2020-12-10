package br.com.clinica.consultorio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestScope
@RequestMapping("/")
public class InicioController {

	@GetMapping
	public String getInicio() {
		return "index";
	}
}
