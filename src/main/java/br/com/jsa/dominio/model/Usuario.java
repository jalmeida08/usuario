package br.com.jsa.dominio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8622058730971581293L;

	@Id
	private String id;
	private String pessoaId;
	private String email;
	private String senha;
	private String chavePendenciaAlteracaoDadoUsuario;
	private boolean ativo;
	@DBRef
	private List<Acesso> acesso = new ArrayList<Acesso>();
	@Version
	private Long versao;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Acesso> getAcesso() {
		return acesso;
	}

	public void setAcesso(List<Acesso> acesso) {
		this.acesso = acesso;
	}

	public String getId() {
		return id;
	}

	public String getChavePendenciaAlteracaoDadoUsuario() {
		return chavePendenciaAlteracaoDadoUsuario;
	}

	public void setChavePendenciaAlteracaoDadoUsuario(String chavePendenciaAlteracaoDadoUsuario) {
		this.chavePendenciaAlteracaoDadoUsuario = chavePendenciaAlteracaoDadoUsuario;
	}

	public Long getVersao() {
		return versao;
	}

}
