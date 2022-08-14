package br.com.jsa.api.dto;

import br.com.jsa.dominio.model.Usuario;

public class UsuarioForm {

	private String pessoaId;
	private String email;

	public String getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(String pessoaId) {
		this.pessoaId = pessoaId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UsuarioForm [pessoaId=" + pessoaId + ", email=" + email + "]";
	}

	public Usuario toUsuario() {
		var u = new Usuario();
		u.setEmail(this.email);
		u.setPessoaId(pessoaId);
		return u;
	}
	
}
