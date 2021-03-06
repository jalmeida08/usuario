package br.com.jsa.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailCadastradoRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8126105629154477168L;

	public EmailCadastradoRuntimeException(String message) {
		super(message);
	}
}