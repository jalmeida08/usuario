package br.com.jsa.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import br.com.jsa.api.client.PessoaClient;
import br.com.jsa.api.dto.AlteraSenhaUsuarioForm;
import br.com.jsa.api.dto.DadoNomePessoaDTO;
import br.com.jsa.api.dto.UsuarioDTO;
import br.com.jsa.api.dto.UsuarioDadosPrimeiroAcessoDTO;
import br.com.jsa.api.dto.UsuarioForm;
import br.com.jsa.api.form.BuscaDadosUsuarioPrimeiroAcessoForm;
import br.com.jsa.api.form.CadastraSenhaUsuarioForm;
import br.com.jsa.api.form.ConsultaEmailForm;
import br.com.jsa.api.kafka.producer.usuario.NovoUsuarioProducer;
import br.com.jsa.dominio.model.Acesso;
import br.com.jsa.dominio.model.Usuario;
import br.com.jsa.dominio.repository.AcessoRepository;
import br.com.jsa.dominio.repository.UsuarioRepository;
import br.com.jsa.dominio.usuario.AlteraSenhaBO;
import br.com.jsa.dominio.usuario.PreparaEmailUsuarioBO;
import br.com.jsa.dominio.usuario.ValidaPrimeiroAcesso;
import br.com.jsa.infra.exception.EmailCadastradoRuntimeException;

@Service
public class UsuarioService {
	
	@Autowired
	protected UsuarioRepository usuarioRepository;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private PessoaClient pessoaClient;
	
	@Autowired
	private NovoUsuarioProducer novoUsuarioFuncionarioProducer;
		
	private Acesso getAcesso(String idAcesso) {
		return this.acessoRepository
				.findById(idAcesso)
				.orElseThrow();
	}

	
	public void salva(UsuarioForm form) throws EmailCadastradoRuntimeException {
		if(this.buscarPorEmail(form.getEmail()).isPresent())
			throw new EmailCadastradoRuntimeException("E-mail já cadastrado");
		
		Usuario u = form.toUsuario();
		
		u.setAtivo(false);
		u.setChavePendenciaAlteracaoDadoUsuario(KeyGenerators.string().generateKey());
		
		var usuarioSalvo = usuarioRepository.save(u);
		var emailDTO = new PreparaEmailUsuarioBO()
			.preparaEmailNovoUsuario(
					usuarioSalvo.getEmail(),
					usuarioSalvo.getChavePendenciaAlteracaoDadoUsuario());
		this.novoUsuarioFuncionarioProducer.enviaEmailNovoUsuario(emailDTO);
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


	public UsuarioDadosPrimeiroAcessoDTO buscaDadosUsuarioPrimeiroAcesso(@Valid BuscaDadosUsuarioPrimeiroAcessoForm form) {
		var u = usuarioRepository.findByEmail(form.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("E-mail informado não foi localizado"));
		
		new ValidaPrimeiroAcesso(u, form.getChaveAlteracaoPendenciaDados())
			.isValid();
		
		DadoNomePessoaDTO p = pessoaClient
				.buscaNomePessoaPorId(u.getPessoaId())
				.orElseThrow(() -> 
					new IllegalArgumentException("Não foi possível localizar os dados informado na base. Entre em contato com o administrador do sistema"));
		return new UsuarioDadosPrimeiroAcessoDTO(p.getNome(), u.getEmail());
	}


	public boolean consultaSeEmailIsValid(ConsultaEmailForm form) {
		var dados = this.usuarioRepository
			.findByEmail(form.getEmail());
		return dados.isPresent();
	}


	public void cadastraSenhaUsuarioPrimeiroAcesso(CadastraSenhaUsuarioForm form) {
		var usuario = this.usuarioRepository.findByEmail(form.getEmail());
		var u = usuario
				.orElseThrow(() -> new IllegalArgumentException("E-mail não localizado"));
		
		new ValidaPrimeiroAcesso(u, form.getChaveAlteracaoDadosUsuario())
			.isValid();
		
		u.setSenha(form.getSenha());
		u.setChavePendenciaAlteracaoDadoUsuario("");
		u.setAtivo(true);
		this.usuarioRepository.save(u);
	}


	public boolean emailJaPossuiCadastro(ConsultaEmailForm form) {
		var usuario = this.usuarioRepository.findByEmail(form.getEmail());
		return usuario.isPresent();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
