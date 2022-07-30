package br.com.jsa.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jsa.api.dto.AlteraSenhaUsuarioForm;
import br.com.jsa.api.dto.UsuarioFuncionarioDTO;
import br.com.jsa.api.form.DadosPrimeiroAcessoUsuarioFuncionarioForm;
import br.com.jsa.api.form.IdAcessoForm;
import br.com.jsa.api.service.UsuarioFuncionarioService;

@RestController
@RequestMapping("funcionario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioFuncionarioController {
	
	@Autowired
	private UsuarioFuncionarioService usuarioFuncionarioService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')")  
	public ResponseEntity<?> listaUsuario() {
		List<UsuarioFuncionarioDTO> u = usuarioFuncionarioService.listaUsuario();
		return ResponseEntity.ok(u);
	}
	
	@GetMapping("/publico/busca-dados-por-chave")
	public ResponseEntity<?> recuperaDadosUsuarioFuncionarioPorChaveAlteracaoDados(
			@QueryParam("key") String key) {
		var dto = this.usuarioFuncionarioService.recuperaDadosUsuarioFuncionarioPorChaveAlteracaoDados(key);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/publico/{idUsuario}/{chavePendenciaAlteracaoDados}/primeiro-acesso")
	@Transactional
	public ResponseEntity<?> primeiroAcessoUsuarioFuncionario(
			@PathVariable("idUsuario") String idUsuario,
			@PathVariable("chavePendenciaAlteracaoDados") String chavePendenciaAlteracaoDados,
			@RequestBody @Valid DadosPrimeiroAcessoUsuarioFuncionarioForm form) {
		
		usuarioFuncionarioService.primeiroAcesso(idUsuario, chavePendenciaAlteracaoDados, form);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaUsuarioPorId(@PathVariable("id") String id){
		return ResponseEntity.ok(this.usuarioFuncionarioService.getUsuarioPorId(id));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> alteraSenhaFuncionario( @RequestBody AlteraSenhaUsuarioForm alteraSenhaUsuarioForm) {
		this.usuarioFuncionarioService.alteraSenhaUsuario(alteraSenhaUsuarioForm);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idUsuario}/concede-acesso")
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')") 
	public ResponseEntity<?> concedeAcessoAUsuario(@PathVariable String idUsuario, @RequestBody IdAcessoForm acessoForm){
		usuarioFuncionarioService.concedeACesso(idUsuario, acessoForm.getAcesso());
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idUsuario}/remove-acesso")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')") 
	@Transactional
	public ResponseEntity<?> removeAcessoAUsuario(@PathVariable String idUsuario, @RequestBody IdAcessoForm acessoForm){
		usuarioFuncionarioService.removeACesso(idUsuario, acessoForm.getAcesso());
		return ResponseEntity.ok().build();
	}

}
