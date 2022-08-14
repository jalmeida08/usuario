package br.com.jsa.dominio.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.jsa.dominio.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	
	public Optional<Usuario> findByEmail(String email);
	
	public Optional<Usuario> findByChavePendenciaAlteracaoDadoUsuario(String chaveAtivacao);

}
