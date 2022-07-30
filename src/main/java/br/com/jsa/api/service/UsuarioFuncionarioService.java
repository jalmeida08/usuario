package br.com.jsa.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import br.com.jsa.api.dto.NovoUsuarioDTO;
import br.com.jsa.dominio.usuario.PreparaEmailUsuarioBO;
import br.com.jsa.infra.exception.EmailCadastradoRuntimeException;
import br.com.jsa.infra.kafka.producer.usuario.NovoUsuarioFuncionarioProducer;
import br.com.jsa.infra.model.Usuario;

@Service
public class UsuarioFuncionarioService extends UsuarioService{
	
	@Autowired
	private NovoUsuarioFuncionarioProducer novoUsuarioFuncionarioProducer;
	
	public void salva(NovoUsuarioDTO dto) throws EmailCadastradoRuntimeException {
		if(this.buscarPorEmail(dto.getEmail()).isPresent())
			throw new EmailCadastradoRuntimeException("E-mail já cadastrado");
		
		Usuario u = dto.toUsuario();
		
		u.setAtivo(false);
		u.setChavePendenciaAlteracaoDadoUsuario(KeyGenerators.string().generateKey());
		
		var usuarioSalvo = usuarioRepository.save(u);
		var emailDTO = new PreparaEmailUsuarioBO()
			.preparaEmailNovoUsuario(
					usuarioSalvo.getEmail(),
					usuarioSalvo.getChavePendenciaAlteracaoDadoUsuario());
		this.novoUsuarioFuncionarioProducer.enviaEmailNovoUsuario(emailDTO);
	}

}
