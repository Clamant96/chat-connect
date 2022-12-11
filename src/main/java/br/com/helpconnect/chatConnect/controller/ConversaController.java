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

import br.com.helpconnect.chatConnect.model.Conversa;
import br.com.helpconnect.chatConnect.repository.ConversaRepository;
import br.com.helpconnect.chatConnect.service.ConversaService;

@RestController
@RequestMapping("/conversas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConversaController {
	
	@Autowired
	private ConversaRepository repository;
	
	@Autowired
	private ConversaService conversaService;
	
	@GetMapping
	public ResponseEntity<List<Conversa>> findAllConversas() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Conversa> findByIdConversa(@PathVariable long id) {
		 return repository.findById(id)
				 .map(resp -> ResponseEntity.ok(resp))
				 .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Conversa> postConversas(@RequestBody Conversa conversa){
		System.out.println(conversa.getUsuario().getId());
		System.out.println(conversa.getChat().getId());
		System.out.println(conversa.getConteudo());
		System.out.println(conversa.getImg());
		
		//conversa = conversaService.codificaConversasChat(conversa);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(conversa));
	}
	
	@PutMapping
	public ResponseEntity<Conversa> putConversas(@RequestBody Conversa conversa){
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(conversa));
	}
	
	@DeleteMapping("/{id}")
	public void deletarConversas(@PathVariable long id) {
		
		repository.deleteById(id);	
	}
	
}
