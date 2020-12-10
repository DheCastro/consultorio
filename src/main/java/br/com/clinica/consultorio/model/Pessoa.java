package br.com.clinica.consultorio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 5, max = 255)
	private String nome;
	
	@NotBlank
	@Size(min = 5, max = 255)
	private String endereco;
	
	@NotBlank
	@Size(min = 5, max = 50)
	private String contato;
	
	@NotBlank
	@Size(min = 5, max = 50)
	private String contatoFamiliar;
	
	@NotBlank
	@Size(min = 5, max = 100)
	private String email;
	
	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getContatoFamiliar() {
		return contatoFamiliar;
	}

	public void setContatoFamiliar(String contatoFamiliar) {
		this.contatoFamiliar = contatoFamiliar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public static String[] cols = { "id", "nome", "endereco", "contato", "contatoFamiliar", "email", "cpf" };
	
}
