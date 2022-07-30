package br.com.jsa.dominio.usuario;

import br.com.jsa.api.form.DadosPrimeiroAcessoUsuarioFuncionarioForm;
import br.com.jsa.infra.exception.NegocioException;
import br.com.jsa.infra.model.Usuario;

public class UsuarioValidaPrimeiroAcessoBO {
	
	public static boolean validaAcaoUsuarioAlteracao(
			Usuario u,
			String chavePendenciaAlteracaoDados,
			DadosPrimeiroAcessoUsuarioFuncionarioForm form) {
		
		final var isPossuiChavePendencia = !u.getChavePendenciaAlteracaoDadoUsuario().isBlank();
		final var isChaveEquals = u.getChavePendenciaAlteracaoDadoUsuario().equals(chavePendenciaAlteracaoDados);
		final var isEmailEquals = u.getEmail().equals(form.getEmail());
		
		if(!isPossuiChavePendencia)
			throw new NegocioException("Usuário não possui solicitacao de alteração de dados");
		if(!isChaveEquals)
			throw new NegocioException("Chave de alteracao de dados inválida");
		if(!isEmailEquals)
			throw new NegocioException("E-mail informado não foi localizado");
		
		return true;
	}


}
