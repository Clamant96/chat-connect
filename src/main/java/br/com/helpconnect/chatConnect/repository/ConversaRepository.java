package br.com.helpconnect.chatConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.chatConnect.model.Conversa;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

}
