package br.com.jsa.api.kafka.consumer.usuario;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.jsa.api.dto.UsuarioForm;
import br.com.jsa.api.kafka.producer.usuario.NovoUsuarioErroProducer;
import br.com.jsa.api.service.UsuarioService;
import br.com.jsa.infra.exception.EmailCadastradoRuntimeException;

@Component
public class UsuarioConsumer {
	
	public static final Logger logger = LoggerFactory.getLogger(UsuarioConsumer.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private NovoUsuarioErroProducer novoUsuarioErroProducer;
	
	@KafkaListener(
			topics = "${app.kafka.consumer.studio.novo-funcionario.topic}",
			groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void consumerNovoUsuario(UsuarioForm form) {
		try {
			logger.info("INÍCIO :: COMSUMINDO SOLICITACAO NOVO USUARIO ");
			logger.info(form.toString());
			this.usuarioService.salva(form);
			logger.info("FIM :: NOVO USUARIO SALVO ");
		} catch (EmailCadastradoRuntimeException e) {
			logger.error("INICIO ERRO :: ERRO AO SALVAR USUARIO, ENVIANDO MENSAGEM DE ERRO");
			novoUsuarioErroProducer.enviaMensagemErroAoSalvarUsuario(form);
		}
	}
}
