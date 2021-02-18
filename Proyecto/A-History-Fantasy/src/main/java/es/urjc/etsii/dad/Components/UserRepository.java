package es.urjc.etsii.dad.Components;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByNombre( String Nombre);
	//@Query("SELECT user FROM User user WHERE")
}






