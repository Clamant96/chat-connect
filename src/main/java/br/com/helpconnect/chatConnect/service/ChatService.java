package br.com.helpconnect.chatConnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.helpconnect.chatConnect.model.Chat;
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
	
}
