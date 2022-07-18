package br.com.jsa.api.form;

import java.util.List;

public class EmailDTO {

	private List<String> listaDestinatario;
	private List<String> listaComCopia;
	private String titulo;
	private String corpoEmail;
	
	public EmailDTO(List<String> listaDestinatario, List<String> listaComCopia, String titulo, String corpoEmail) {
		this.listaDestinatario = listaDestinatario;
		this.listaComCopia = listaComCopia;
		this.titulo = titulo;
		this.corpoEmail = corpoEmail;
	}

	public List<String> getListaDestinatario() {
		return listaDestinatario;
	}

	public List<String> getListaComCopia() {
		return listaComCopia;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCorpoEmail() {
		return corpoEmail;
	}

}
