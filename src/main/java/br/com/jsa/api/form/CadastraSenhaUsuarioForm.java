package br.com.jsa.api.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastraSenhaUsuarioForm {
	@Email
	private String email;
	@NotBlank(message = "Campo obrigatório")
	private String chaveAlteracaoDadosUsuario;
	@Size(min = 6, max = 16, message = "Mínimo de 6 caracteres e máximo de 16")
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getChaveAlteracaoDadosUsuario() {
		return chaveAlteracaoDadosUsuario;
	}

	public void setChaveAlteracaoDadosUsuario(String chaveAlteracaoDadosUsuario) {
		this.chaveAlteracaoDadosUsuario = chaveAlteracaoDadosUsuario.trim();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha.trim();
	}
}
