package br.com.helpconnect.chatConnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.chatConnect.model.Figurinha;

@Repository
public interface FigurinhaRepository extends JpaRepository<Figurinha, Long> {
	
	public List<Figurinha>  findByUsuarioId(long id);
	
}
