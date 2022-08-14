package br.com.jsa.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jsa.api.dto.AlteraSenhaUsuarioForm;
import br.com.jsa.api.dto.UsuarioDTO;
import br.com.jsa.api.form.BuscaDadosUsuarioPrimeiroAcessoForm;
import br.com.jsa.api.form.CadastraSenhaUsuarioForm;
import br.com.jsa.api.form.ConsultaEmailForm;
import br.com.jsa.api.form.IdAcessoForm;
import br.com.jsa.api.service.UsuarioService;

@RestController
@RequestMapping("usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	
	@PostMapping("/publico/primeiro-acesso/busca-dados-usuario")
	@Transactional
	public ResponseEntity<?> buscaDadosUsuarioPrimeiroAcesso(@RequestBody @Valid BuscaDadosUsuarioPrimeiroAcessoForm form) {
		var dto = usuarioService.buscaDadosUsuarioPrimeiroAcesso(form);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/publico/primeiro-acesso/finaliza-cadastro")
	@Transactional
	public ResponseEntity<?> finalizaCadastroUsuario(@RequestBody @Validated CadastraSenhaUsuarioForm form) {
		this.usuarioService.cadastraSenhaUsuarioPrimeiroAcesso(form);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/publico/consulta-email-cadastrado")
	public ResponseEntity<?> emailJaPPossuiCadastro(@RequestBody @Validated ConsultaEmailForm form) {
		var isEmailCadastrado = usuarioService.emailJaPossuiCadastro(form);
		return ResponseEntity.ok(isEmailCadastrado);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')")  
	public ResponseEntity<?> listaUsuario() {
		List<UsuarioDTO> u = usuarioService.listaUsuario();
		return ResponseEntity.ok(u);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaUsuarioPorId(@PathVariable("id") String id){
		return ResponseEntity.ok(this.usuarioService.getUsuarioPorId(id));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> alteraSenhaFuncionario( @RequestBody AlteraSenhaUsuarioForm alteraSenhaUsuarioForm) {
		this.usuarioService.alteraSenhaUsuario(alteraSenhaUsuarioForm);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idUsuario}/concede-acesso")
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')") 
	public ResponseEntity<?> concedeAcessoAUsuario(@PathVariable String idUsuario, @RequestBody IdAcessoForm acessoForm){
		usuarioService.concedeACesso(idUsuario, acessoForm.getAcesso());
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idUsuario}/remove-acesso")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CEO')") 
	@Transactional
	public ResponseEntity<?> removeAcessoAUsuario(@PathVariable String idUsuario, @RequestBody IdAcessoForm acessoForm){
		usuarioService.removeACesso(idUsuario, acessoForm.getAcesso());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/valida-email")
	public ResponseEntity<?> consultaSeEmailIsValid(@RequestBody ConsultaEmailForm form){
		var isValid = usuarioService.consultaSeEmailIsValid(form);
		return ResponseEntity.ok(isValid);
	}

}
