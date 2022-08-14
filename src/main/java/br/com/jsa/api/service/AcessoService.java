package br.com.jsa.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jsa.api.dto.AcessoDTO;
import br.com.jsa.dominio.model.Acesso;
import br.com.jsa.dominio.repository.AcessoRepository;
import br.com.jsa.infra.exception.ParametroException;

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;
	
	private Acesso getAcesso(String id) {
		return acessoRepository
				.findById(id)
				.orElseThrow(() -> new ParametroException());
	}
	
	public AcessoDTO adicionaAcesso(Acesso a) {
		var acesso = acessoRepository.save(a);
		return new AcessoDTO(acesso);
	}
	
	public List<AcessoDTO> listaAcesso(){
		return acessoRepository
				.findAll()
				.stream()
				.map(AcessoDTO::new)
				.collect(Collectors.toList());
	}

	public AcessoDTO buscaAcessoPorId(String id) {
		return new AcessoDTO(getAcesso(id));
	}
}
