package br.com.jsa.api.form;

import br.com.jsa.dominio.model.Acesso;

public class AcessoForm {
	
	private String nome;
	private String descricao;
	
	public Acesso toAcesso() {
		var a = new Acesso();
		a.setNome(nome.toUpperCase());
		a.setDescricao(descricao);
		return a;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
