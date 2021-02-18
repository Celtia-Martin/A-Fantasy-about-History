package es.urjc.etsii.dad.Components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//@Query("SELECT user FROM User user WHERE")
}






