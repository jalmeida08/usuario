package br.com.jsa.api.client;

import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jsa.api.dto.DadoNomePessoaDTO;
import br.com.jsa.api.dto.PessoaDTO;
import br.com.jsa.api.form.PessoaForm;
import feign.Headers;

@FeignClient(value = "cadastro-basico", path = "/pessoa", contextId = "cadastroBasicoPessoa")
public interface PessoaClient {
	
	@RequestMapping(
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON,
		consumes = MediaType.APPLICATION_JSON,
		value = "/pessoa"
	)
	@Headers("Accept: application/json")
	public PessoaDTO cadastraDadosPessoa(PessoaForm form);
	
	@GetMapping("/publico/busca-nome/{id}")
	public Optional<DadoNomePessoaDTO> buscaNomePessoaPorId(@PathVariable("id") String id);

}
