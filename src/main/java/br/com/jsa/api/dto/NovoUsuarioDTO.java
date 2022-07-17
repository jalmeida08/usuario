package br.com.jsa.api.dto;

import br.com.jsa.infra.model.Usuario;

public class NovoUsuarioDTO extends UsuarioForm {

	private String funcionarioId;

	public NovoUsuarioDTO(String funcionarioId, String email, String nome) {
		this.funcionarioId = funcionarioId;
		this.setEmail(email);
		this.setNome(nome);
	}

	public NovoUsuarioDTO() {}
	
	public Usuario toUsuario() {
		Usuario u = new Usuario();
		u.setEmail(this.getEmail());
		u.setFuncionarioId(funcionarioId);
		return u;
	}

	public void setFuncionarioId(String funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getFuncionarioId() {
		return funcionarioId;
	}

	@Override
	public String toString() {
		return "NovoUsuarioForm ["
				+ "funcionarioId=" + funcionarioId + "'/',"
				+ "getNome()=" + getNome() + ","
				+ "getEmail()="+ getEmail() + " '/'"
			+ "]";
	}

	
}
