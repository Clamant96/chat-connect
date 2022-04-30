package br.com.helpconnect.chatConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.helpconnect.chatConnect.model.Usuario;
import br.com.helpconnect.chatConnect.model.UsuarioLogin;
import br.com.helpconnect.chatConnect.repository.UsuarioRepository;
import br.com.helpconnect.chatConnect.service.ChatService;
import br.com.helpconnect.chatConnect.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ChatService chatService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAllUsuarios() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/usuarios-conversa/{id}")
	public ResponseEntity<List<Usuario>> findAllUsuariosConversa(@PathVariable long id) {
		
		return ResponseEntity.ok(usuarioService.findByUsusuariosConversa(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findByIdUsuario(@PathVariable long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticaUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
		return usuarioService.Logar(usuarioLogin).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.CadastrarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
			
		}
		
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.atualizarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
			
		}
		
	}
	
	@PostMapping("/usuario_chats/conversa/{idUsuario}/{idChat}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable long idUsuario, @PathVariable long idChat) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(chatService.iniciarChat(idUsuario, idChat));
	}
	
	@DeleteMapping("/{id}")
	public void deletarUsuario(@PathVariable long id) {
		
		repository.deleteById(id);
	}
	
}
