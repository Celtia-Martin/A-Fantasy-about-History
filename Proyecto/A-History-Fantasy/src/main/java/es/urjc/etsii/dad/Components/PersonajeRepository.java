package es.urjc.etsii.dad.Components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

	//@Query("SELECT p FROM Personaje p WHERE")
}
