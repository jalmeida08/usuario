package br.com.jsa.dominio.usuario;

import java.util.List;

import br.com.jsa.api.form.EmailDTO;

public class PreparaEmailUsuarioBO {
	
	public EmailDTO preparaEmailNovoUsuario(String email, String chaveAtivacao) {
		return new EmailDTO(List.of(email), List.of(), "Para ativar acesse o link", "<html><body><h1><strong>"+ chaveAtivacao +"</strong></h1></body></html>");
	}

}
