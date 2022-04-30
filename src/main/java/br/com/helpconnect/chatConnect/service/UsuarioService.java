package br.com.helpconnect.chatConnect.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.helpconnect.chatConnect.model.Usuario;
import br.com.helpconnect.chatConnect.model.UsuarioLogin;
import br.com.helpconnect.chatConnect.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {	
		
		/* CONDICAO PARA INPEDIR A CRIACAO DE UM USUARIO DUPLICADO DENTRO DA APLICACAO */
		if(repository.findByUsername(usuario.getUsername()).isPresent() && usuario.getId() == 0) {
			return null;
			
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getPassword());
		usuario.setPassword(senhaEncoder);
		
		/* REGISTRA O USUARIO NA BASE DE DADOS */
		repository.save(usuario);
		
		return Optional.of(repository.save(usuario));
	}

	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> usuarioLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsername(usuarioLogin.get().getUsername());

		if (usuario.isPresent()) {
			if (encoder.matches(usuarioLogin.get().getPassword(), usuario.get().getPassword())) {

				String auth = usuarioLogin.get().getUsername() + ":" + usuarioLogin.get().getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				usuarioLogin.get().setToken(authHeader);	
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setImg(usuario.get().getImg());
				usuarioLogin.get().setPassword(usuario.get().getPassword()); 
				usuarioLogin.get().setUsername(usuario.get().getUsername());

				return usuarioLogin;

			}
		}
		return null;
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getPassword());
		usuario.setPassword(senhaEncoder);
		
		return Optional.of(repository.save(usuario));
	}
	
	public List<Usuario> findByUsusuariosConversa(long id) {
		List<Usuario> listaUsuario = new ArrayList<>();
		
		for(int i = 0; i < repository.findAll().size(); i++) {
			if(repository.findAll().get(i).getId() != id) {
				listaUsuario.add(repository.findAll().get(i));
			}
		}
		
		return listaUsuario;
	}
	
}
