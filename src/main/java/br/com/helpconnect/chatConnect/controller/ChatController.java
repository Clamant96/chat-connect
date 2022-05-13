package br.com.helpconnect.chatConnect.controller;

import java.util.List;

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

import br.com.helpconnect.chatConnect.model.Chat;
import br.com.helpconnect.chatConnect.repository.ChatRepository;
import br.com.helpconnect.chatConnect.service.ChatService;

@RestController
@RequestMapping("/chats")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

	@Autowired
	private ChatRepository repository;
	
	@Autowired
	private ChatService chatService;
	
	@GetMapping
	public ResponseEntity<List<Chat>> findAllChats() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	/*public ResponseEntity<Chat> findByIdChat(@PathVariable long id) {
		 return chatService.decodificaConversasChat(id);
	}*/
	public ResponseEntity<Chat> findByIdChat(@PathVariable long id) {
		 return repository.findById(id)
				 .map(resp -> ResponseEntity.ok(resp))
				 .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/conversas-chat/{id}")
	public ResponseEntity<List<Chat>> findByIdChatsUsuario(@PathVariable long id) {
		 return ResponseEntity.ok(chatService.armazenaChatsUsuarioPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<Chat> postChat(@RequestBody Chat chat){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(chat));
	}
	
	@PutMapping
	public ResponseEntity<Chat> putChat(@RequestBody Chat chat){
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(chat));
	}
	
	@DeleteMapping("/{id}")
	public void deletarChat(@PathVariable long id) {
		
		repository.deleteById(id);	
	}
	
}
