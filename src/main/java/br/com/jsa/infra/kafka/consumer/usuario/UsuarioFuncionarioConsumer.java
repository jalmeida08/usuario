package br.com.jsa.infra.kafka.consumer.usuario;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.jsa.api.dto.NovoUsuarioDTO;
import br.com.jsa.api.service.UsuarioFuncionarioService;
import br.com.jsa.infra.exception.EmailCadastradoRuntimeException;
import br.com.jsa.infra.kafka.producer.usuario.NovoUsuarioFuncionarioErroProducer;

@Component
public class UsuarioFuncionarioConsumer {
	
	public static final Logger logger = LoggerFactory.getLogger(UsuarioFuncionarioConsumer.class);
	
	@Autowired
	private UsuarioFuncionarioService usuarioFuncionarioService;
	
	@Autowired
	private NovoUsuarioFuncionarioErroProducer novoUsuarioFuncionarioErroProducer;
	
	@KafkaListener(
			topics = "${app.kafka.consumer.cadastro-basico.novo-usuario-funcionario.topic}",
			groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void consumerNovoUsuarioFuncionario(NovoUsuarioDTO dto) {
		try {
			logger.info("INÍCIO :: COMSUMINDO SOLICITACAO NOVO USUARIO FUNCIONARIO ");
			logger.info(dto.toString());
			this.usuarioFuncionarioService.salva(dto);
			logger.info("FIM :: NOVO USUARIO FUNCIONARIO SALVO ");
		} catch (EmailCadastradoRuntimeException e) {
			logger.error("INICIO ERRO :: ERRO AO SALVAR USUARIO, ENVIANDO MENSAGEM DE ERRO");
			novoUsuarioFuncionarioErroProducer.enviaMensagemErroAoSalvarUsuario(dto);
		}
	}
}
