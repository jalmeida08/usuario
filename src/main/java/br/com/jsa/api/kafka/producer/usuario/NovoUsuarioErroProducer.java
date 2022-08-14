package br.com.jsa.api.kafka.producer.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jsa.api.dto.UsuarioForm;

@Component
public class NovoUsuarioErroProducer {

	public static final Logger logger = LoggerFactory.getLogger(NovoUsuarioErroProducer.class);
	
	private final KafkaTemplate<String, UsuarioForm> kafkaTemplate;
	
	public NovoUsuarioErroProducer(KafkaTemplate<String, UsuarioForm> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Value("${app.kafka.producer.usuario.erro-novo-usuario.topic}")
	private String erroAoSalvarNovoUsuarioFuncionario;
	
	public void enviaMensagemErroAoSalvarUsuario(final @RequestBody UsuarioForm dto) {
		logger.info("INICIO :: ENVIANDO MENSAGEM DE ERRO AO SALVAR USUÁRIO");
		kafkaTemplate.send(erroAoSalvarNovoUsuarioFuncionario, dto.getEmail(), dto);
		logger.info("FIM :: ENVIANDO MENSAGEM DE ERRO AO SALVAR USUÁRIO");
	}
}
