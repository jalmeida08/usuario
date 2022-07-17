package br.com.jsa.infra.kafka.produce.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jsa.api.form.EmailDTO;

@Component
public class EmailProduce {
	
	@Value("${app.kafka.email.topic}")
	private String emailNovoUsuarioFuncionario;

	private final KafkaTemplate<String, EmailDTO> kafkaTemplate;

	public EmailProduce(final KafkaTemplate<String, EmailDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void enviaEmailNovoUsuario(final @RequestBody EmailDTO dto) {
		var chaveEnvio = dto.getListaDestinatario().get(0);
		System.out.println("## ENVIANDO ###");
		kafkaTemplate.send(emailNovoUsuarioFuncionario, chaveEnvio, dto);
		System.out.println("## MENSAGEM ENVIADA ###");
	}
}
