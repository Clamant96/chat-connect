package br.com.helpconnect.chatConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.helpconnect.chatConnect.model.Chat;
import br.com.helpconnect.chatConnect.model.Figurinha;
import br.com.helpconnect.chatConnect.repository.FigurinhaRepository;

@Controller
@RequestMapping("/figurinha")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FigurinhaController {
	
	@Autowired
	private FigurinhaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Figurinha>> findAllFigurinhas() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/figurinhas-usuario/{id}")
	public ResponseEntity<List<Figurinha>> findAllFigurinhas(@PathVariable("id") long id) {
		
		System.out.println("QTD Figurinhas: "+ repository.findByUsuarioId(id).size());
		
		return ResponseEntity.ok(repository.findByUsuarioId(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Figurinha> findByIdFigurinha(@PathVariable long id) {
		 return repository.findById(id)
				 .map(resp -> ResponseEntity.ok(resp))
				 .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Figurinha> postFigurinha(@RequestBody Figurinha figurinha){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(figurinha));
	}
	
	@PutMapping
	public ResponseEntity<Figurinha> putFigurinha(@RequestBody Figurinha figurinha){
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(figurinha));
	}
	
	@DeleteMapping("/{id}")
	public void deletarFigurinha(@PathVariable long id) {
		
		repository.deleteById(id);	
	}

}
