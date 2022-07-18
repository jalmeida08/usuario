package br.com.jsa.infra.kafka.consumer.cadastroBasico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.jsa.api.dto.NovoUsuarioDTO;
import br.com.jsa.api.service.UsuarioService;

@Component
public class FuncionarioConsumer {
	
	@Autowired
	private UsuarioService usuarioService;

	@KafkaListener(topics = "${app.kafka.cadastro-basico.novo-usuario-funcionario.topic}", groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void consumerNovoUsuarioFuncionario(NovoUsuarioDTO dto) {
		System.out.println("INÍCIO :: COMSUMINDO SOLICITACAO NOVO USUARIO FUNCIONARIO ");
		System.out.println(dto.toString());
		this.usuarioService.salva(dto);
		System.out.println("FIM :: NOVO USUARIO FUNCIONARIO SALVO ");
	}
}
