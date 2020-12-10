package br.com.clinica.consultorio.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.clinica.consultorio.model.Pessoa;
import br.com.clinica.consultorio.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
@RequestScope
public class PessoaController {

	private static final String PAGINA_PESSOA_CADASTRO = "pessoa/create";
	private static final String PAGINA_PESSOA_VISUALIZACAO = "pessoa/view";
	private static final String PAGINA_PESSOA_LISTA = "pessoa/list";
	private static final String REDIRECT_PESSOA_LISTA = "redirect:/pessoas/list";
	private static final String REDIRECT_PESSOA_VISUALIZACAO = "redirect:/pessoas/view/";
	
	@Autowired
	private PessoaService pessoaService;
		
	@GetMapping
	public String getPaginaCadastro(Pessoa pessoa, Model model) {
		return PAGINA_PESSOA_CADASTRO;
	}
	
	@PostMapping
	public String salvar(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

		try {
			
			if(result.hasErrors()) {
				return PAGINA_PESSOA_CADASTRO;
			}
			
			pessoaService.salvar(pessoa);
			redirectAttributes.addFlashAttribute("success", "Sucesso");
			return REDIRECT_PESSOA_VISUALIZACAO + pessoa.getId();
			
		} catch (Exception e) {
			model.addAttribute("fail", e.getMessage());
			return PAGINA_PESSOA_CADASTRO;
		}
	}
	
	@GetMapping("/view/{id}")
	public String visualizar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
		
		try {

			Pessoa pessoa = this.pessoaService.buscarPorId(id);
			model.addAttribute("pessoa", pessoa);		
			return PAGINA_PESSOA_VISUALIZACAO;
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("fail", e.getMessage());
		}
		
		return REDIRECT_PESSOA_LISTA;
	}
	
	@GetMapping("/{id}")
	public String editar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
		
		try {
			
			Pessoa pessoa = this.pessoaService.buscarPorId(id);
			model.addAttribute("pessoa", pessoa);
			model.addAttribute("idRegistro", pessoa.getId());
			return PAGINA_PESSOA_CADASTRO;
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("fail", e.getMessage());
		}
		
		return REDIRECT_PESSOA_LISTA;
	}
	
	@GetMapping("/list")
	public String lista( ) {
		return PAGINA_PESSOA_LISTA;
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request) {
		Map<String, Object> data = pessoaService.execute(request);
		return ResponseEntity.ok(data);
	}
	
	
}
