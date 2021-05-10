package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface UserRepository extends JpaRepository<User, Long> {
	

	Optional<User> findByNombre( String Nombre);

	Optional<User> findById( Long id);
	void deleteByNombre(String Nombre);

	List <User>findTop10ByOrderByPuntosDesc();
	
	
}





