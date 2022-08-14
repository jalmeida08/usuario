package br.com.jsa.api.form;

import javax.validation.constraints.Email;

public class ConsultaEmailForm {

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim().toLowerCase();
	}
	
	
}
