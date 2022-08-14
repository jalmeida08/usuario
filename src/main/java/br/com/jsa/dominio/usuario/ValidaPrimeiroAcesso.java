package br.com.jsa.dominio.usuario;

import br.com.jsa.dominio.model.Usuario;

public class ValidaPrimeiroAcesso {
	
	private final Usuario usuario;
	private final String chave;
	
	public ValidaPrimeiroAcesso(Usuario u, String chave) {
		this.usuario = u;
		this.chave = chave;
	}
	
	public void isValid() {
		
		var isPossuiPrimeiroAcessoPendente = validaSeUsuarioPossuiPrimeiroAcessoPendente();
		var isPrimeiroAcesso = validaSeUsuarioPossuiSenhaCadastrada();
		if(!isPossuiPrimeiroAcessoPendente)
			throw new IllegalArgumentException("Chave de primeiro acesso invalida");
		if(isPrimeiroAcesso)
			throw new IllegalArgumentException("Usuário já possui acesso cadastro ao sistema");
	}
	
	private boolean validaSeUsuarioPossuiPrimeiroAcessoPendente() {
		return usuario.getChavePendenciaAlteracaoDadoUsuario()
				.equals(chave);		
	}
	
	private boolean validaSeUsuarioPossuiSenhaCadastrada() {
		return this.usuario.getSenha() != null;
	}
	

}
