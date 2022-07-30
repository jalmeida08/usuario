package br.com.jsa.infra.kafka.producer.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jsa.api.dto.NovoUsuarioDTO;

@Component
public class NovoUsuarioFuncionarioErroProducer {

	public static final Logger logger = LoggerFactory.getLogger(NovoUsuarioFuncionarioErroProducer.class);
	
	private final KafkaTemplate<String, NovoUsuarioDTO> kafkaTemplate;
	
	public NovoUsuarioFuncionarioErroProducer(KafkaTemplate<String, NovoUsuarioDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Value("${app.kafka.producer.usuario.erro-novo-usuario-funcionario.topic}")
	private String erroAoSalvarNovoUsuarioFuncionario;
	
	public void enviaMensagemErroAoSalvarUsuario(final @RequestBody NovoUsuarioDTO dto) {
		logger.info("INICIO :: ENVIANDO MENSAGEM DE ERRO AO SALVAR USUÁRIO");
		kafkaTemplate.send(erroAoSalvarNovoUsuarioFuncionario, dto.getEmail(), dto);
		logger.info("FIM :: ENVIANDO MENSAGEM DE ERRO AO SALVAR USUÁRIO");
	}
}
