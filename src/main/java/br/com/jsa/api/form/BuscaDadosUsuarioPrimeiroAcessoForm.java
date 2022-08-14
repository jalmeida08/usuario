package br.com.jsa.api.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class BuscaDadosUsuarioPrimeiroAcessoForm {

	@Email
	@NotEmpty(message = "E-mail obrigatório")
	private String email;
	@NotEmpty(message = "Chave de alteracao de dados obrigatório")
	private String chaveAlteracaoPendenciaDados;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChaveAlteracaoPendenciaDados() {
		return chaveAlteracaoPendenciaDados;
	}

	public void setChaveAlteracaoPendenciaDados(String chaveAlteracaoPendenciaDados) {
		this.chaveAlteracaoPendenciaDados = chaveAlteracaoPendenciaDados;
	}

}
