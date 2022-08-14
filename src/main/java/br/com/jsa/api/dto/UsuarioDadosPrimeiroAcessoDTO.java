package br.com.jsa.api.dto;

public class UsuarioDadosPrimeiroAcessoDTO {

	private String nome;
	private String email;

	public UsuarioDadosPrimeiroAcessoDTO(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

}
