package br.com.clinica.consultorio.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.clinica.consultorio.model.Pessoa;

public interface PessoaService {

	public Pessoa salvar(Pessoa pessoa) throws Exception;

	public boolean verificarSeRegistroJaExiste(Pessoa pessoa) throws Exception;

	public Pessoa buscarPorId(Long id) throws Exception;

	public Map<String, Object> execute(HttpServletRequest request);
	
}
