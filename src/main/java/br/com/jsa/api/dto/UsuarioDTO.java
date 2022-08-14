package br.com.jsa.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jsa.dominio.model.Usuario;

public class UsuarioDTO {
	
	private String id;
	private String pessoaId;
	private String email;
	private List<AcessoDTO> acesso;
	
	public UsuarioDTO(Usuario u) {
		this.id = u.getId();
		this.pessoaId = u.getPessoaId();
		this.email = u.getEmail();
		this.acesso = u.getAcesso().stream().map(AcessoDTO::new).collect(Collectors.toList());
	}
	
	public String getId() {
		return id;
	}
	public String getPessoaId() {
		return pessoaId;
	}
	public String getEmail() {
		return email;
	}
	public List<AcessoDTO> getAcesso() {
		return acesso;
	}
	
	
	
}
