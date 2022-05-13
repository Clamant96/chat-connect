package br.com.helpconnect.chatConnect.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.helpconnect.chatConnect.model.Chat;
import br.com.helpconnect.chatConnect.model.Conversa;
import br.com.helpconnect.chatConnect.model.Usuario;
import br.com.helpconnect.chatConnect.repository.ChatRepository;
import br.com.helpconnect.chatConnect.repository.UsuarioRepository;

@Service
public class ChatService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	/* GERENCIAMENTO DE TABELAS ASSOCIATIVAS (MANY-TO-MANY) */
	public Usuario iniciarChat(long idUsuario, long idChat) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		Optional<Chat> chatExistente = chatRepository.findById(idChat);
		
		/* CASO O USUARIO AINDA NAO TENHA DADO LIKE, ENTAO E COMPUTADO UM NOVO LIKE NA POSTAGEM */
		if(!(usuarioExistente.get().getChats().contains(chatExistente.get()))) {
			
			if(usuarioExistente.isPresent() && chatExistente.isPresent()) {
				usuarioExistente.get().getChats().add(chatExistente.get());
				
				usuarioRepository.save(usuarioExistente.get());
				
				return usuarioRepository.save(usuarioExistente.get());
				
			}
			
		}else {
			/* CASO CONTRARIO O LIKE DO USUARIO E REMOVIDO DA POSTAGEM */
			if(usuarioExistente.isPresent() && chatExistente.isPresent()) {
				usuarioExistente.get().getChats().remove(chatExistente.get());
				
				usuarioRepository.save(usuarioExistente.get());
				
				return usuarioRepository.save(usuarioExistente.get());
				
			}
			
		}

		return null;
	}
	
	/* FILTRA OS CHATS ONDE O USUARIO ESTA CADASTRADO */
	public List<Chat> armazenaChatsUsuarioPorId(long idUsuario) {
		
		List<Chat> listaChatsDoUsuario = new ArrayList<>();
		
		for (int i = 0; i < chatRepository.findAll().size(); i++) {
			for (int j = 0; j < chatRepository.findAll().get(i).getUsuarios().size(); j++) {
				if(chatRepository.findAll().get(i).getUsuarios().get(j).getId() == idUsuario) {
					listaChatsDoUsuario.add(chatRepository.findAll().get(i));
				}
			}
		}
		
		return listaChatsDoUsuario;
	}
	
	/* DECODE CONVERSAS */
	public ResponseEntity<Chat> decodificaConversasChat(long id) {
		
		Chat chatDecodificado = new Chat();
		
		List<Conversa> listConversa = new ArrayList<>();
		
		for(int i = 0; i < chatRepository.findById(id).get().getConversas().size(); i++) {
			Conversa conversaAuth = chatRepository.findById(id).get().getConversas().get(i);
			byte[] decodedAuth = Base64.decodeBase64(conversaAuth.getConteudo().getBytes(Charset.forName("US-ASCII")));
			String decodeString = new String(decodedAuth);
			
			conversaAuth.setConteudo(decodeString); // ATUALIZA O OBJ COM O CONTEUDO DECODIFICADO
			
			listConversa.add(conversaAuth) ; // ADICIONA O ITEM A DECODIFICADO A LISTA
			
			chatDecodificado.setConversas(listConversa); // ADICIONA A LISTA DECODIFICADO NO ARRAY DE CONVERSA
			
		}
		
		chatDecodificado.setId(chatRepository.findById(id).get().getId());
		chatDecodificado.setImg(chatRepository.findById(id).get().getImg());
		chatDecodificado.setNome(chatRepository.findById(id).get().getNome());
		chatDecodificado.setTipo(chatRepository.findById(id).get().getTipo());
		chatDecodificado.setUsuarios(chatRepository.findById(id).get().getUsuarios());
		
		/*for (int i = 0; i < chatRepository.findAll().size(); i++) {
			for (int j = 0; j < chatRepository.findAll().get(i).getConversas().size(); j++) {
				
				if(chatRepository.findAll().get(i).getId() == id) {
					String auth = chatRepository.findAll().get(i).getConversas().get(j).getConteudo();
					byte[] decodedAuth = Base64.decodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
					String decodeString = new String(decodedAuth);
					
					chatRepository.findAll().get(i).getConversas().get(j).setConteudo(decodeString);
					
					chatDecodificado = chatRepository.findAll().get(i);
					
					repository.findById(id)
					 .map(resp -> ResponseEntity.ok(resp))
					 .orElse(ResponseEntity.notFound().build());
					
				}
				
			}
		}*/
		
		return ResponseEntity.ok(chatDecodificado);
	}
	
}
