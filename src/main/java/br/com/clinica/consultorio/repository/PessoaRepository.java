package br.com.clinica.consultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.clinica.consultorio.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	public Long countByNome(String nome);
	
	public Long countByNomeAndIdNot(String nome, Long id);
	
}
