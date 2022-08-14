package br.com.jsa.api.kafka.producer.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jsa.api.form.EmailDTO;

@Component
public class NovoUsuarioProducer {

	public static final Logger logger = LoggerFactory.getLogger(NovoUsuarioProducer.class);
	
	@Value("${app.kafka.producer.usuario.novo-usuario.topic}")
	private String emailNovoUsuario;

	private final KafkaTemplate<String, EmailDTO> kafkaTemplate;

	public NovoUsuarioProducer(final KafkaTemplate<String, EmailDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void enviaEmailNovoUsuario(final @RequestBody EmailDTO dto) {
		var chaveEnvio = dto.getListaDestinatario().get(0);
		logger.info("INICIO :: ENVIANDO");
		kafkaTemplate.send(emailNovoUsuario, chaveEnvio, dto);
		logger.info("FIM :: MENSAGEM ENVIADA");
	}
}
