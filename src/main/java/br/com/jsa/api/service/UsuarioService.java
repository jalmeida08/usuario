package br.com.jsa.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import br.com.jsa.api.dto.AlteraSenhaUsuarioForm;
import br.com.jsa.api.dto.NovoUsuarioDTO;
import br.com.jsa.api.dto.UsuarioDTO;
import br.com.jsa.dominio.usuario.AlteraSenhaBO;
import br.com.jsa.dominio.usuario.PreparaEmailUsuarioBO;
import br.com.jsa.infra.exception.EmailCadastradoRuntimeException;
import br.com.jsa.infra.kafka.produce.email.EmailProduce;
import br.com.jsa.infra.model.Acesso;
import br.com.jsa.infra.model.Usuario;
import br.com.jsa.infra.repository.AcessoRepository;
import br.com.jsa.infra.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private EmailProduce emailProduce;
		
	private Acesso getAcesso(String idAcesso) {
		return this.acessoRepository
				.findById(idAcesso)
				.orElseThrow();
	}
	
	public void salva(NovoUsuarioDTO dto) {
		if(this.buscarPorEmail(dto.getEmail()).isPresent())
			throw new EmailCadastradoRuntimeException("E-mail já cadastrado");
		
		Usuario u = dto.toUsuario();
		
		u.setAtivo(false);
		u.setChavePendenciaAlteracaoDadoUsuario(KeyGenerators.string().generateKey());
		
		var usuarioSalvo = this.usuarioRepository.save(u);
		var emailDTO = new PreparaEmailUsuarioBO()
			.preparaEmailNovoUsuario(
					usuarioSalvo.getEmail(),
					usuarioSalvo.getChavePendenciaAlteracaoDadoUsuario());
		this.emailProduce.enviaEmailNovoUsuario(emailDTO);
		
		
	}

	public List<UsuarioDTO> listaUsuario() {
		return usuarioRepository
				.findAll()
				.stream()
				.map(UsuarioDTO::new)
				.collect(Collectors.toList());
	}
	
	public Usuario getUsuarioPorId(String id) {
		return usuarioRepository
				.findById(id)
				.orElseThrow(() -> new IllegalArgumentException());
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		return this.usuarioRepository.findByEmail(email);
	}

	public void alteraSenhaUsuario(AlteraSenhaUsuarioForm alteraSenhaUsuarioForm) {
		Usuario u = usuarioRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get();
		new AlteraSenhaBO(alteraSenhaUsuarioForm, u.getSenha());
		
		u.setSenha(alteraSenhaUsuarioForm.getNovaSenha());
		this.usuarioRepository.save(u);
	}

	public void concedeACesso(String idUsuario, String idAcesso) {
		
		Acesso a = getAcesso(idAcesso);
		Usuario u = getUsuarioPorId(idUsuario);
		u.setAcesso(List.of(a));
		usuarioRepository.save(u);
	}

	public void removeACesso(String idUsuario, String idAcesso) {
		Acesso a = getAcesso(idAcesso);
		Usuario u = getUsuarioPorId(idUsuario);
		u.setAcesso(u.getAcesso()
			.stream()
			.filter( acesso -> !a.getId().equals(acesso.getId()))
			.collect(Collectors.toList()));
		usuarioRepository.save(u);
	}
}
