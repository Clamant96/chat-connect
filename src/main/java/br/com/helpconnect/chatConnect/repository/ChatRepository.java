package br.com.helpconnect.chatConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.chatConnect.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}
