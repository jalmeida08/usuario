package br.com.jsa.api.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class DadosPrimeiroAcessoUsuarioFuncionarioForm {
	
	@NotBlank(message = "E-mail obrigatório")
	private String email;
	@NotBlank(message = "Senha obrigatório")
	@Length(min = 6, message = "Mínimo de 6 caracteres")
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
