package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FormacionRepository extends JpaRepository<Formacion, Long> {

	Optional<Formacion> findByPropietario(User user);
	
}
