package br.com.jsa.infra.exception;

public class EmailCadastradoRuntimeException extends Exception {

	private static final long serialVersionUID = -8126105629154477168L;

	public EmailCadastradoRuntimeException(String message) {
		super(message);
	}
}