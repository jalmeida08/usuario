package br.com.jsa.api.form;

import java.time.LocalDate;

public class PessoaForm {

	private String nome;
	private LocalDate dataNascimento;

	public PessoaForm() {}
	
	public PessoaForm(String nome, LocalDate dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	
	
}
