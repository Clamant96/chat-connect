package br.com.helpconnect.chatConnect.service;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import br.com.helpconnect.chatConnect.model.Conversa;

@Service
public class ConversaService {
	
	/* DECODE CONVERSAS */
	public Conversa codificaConversasChat(Conversa conversa) {
		
		Conversa novaConversa = new Conversa();
		
		String encode = conversa.getConteudo();
		byte[] encodedAuth = Base64.encodeBase64(encode.getBytes(Charset.forName("US-ASCII")));
		String encodeString = new String(encodedAuth);
		
		novaConversa.setConteudo(encodeString);
		novaConversa.setChat(conversa.getChat());
		novaConversa.setUsuario(conversa.getUsuario());
		novaConversa.setData(conversa.getData());
		
		return novaConversa;
	}
	
}
