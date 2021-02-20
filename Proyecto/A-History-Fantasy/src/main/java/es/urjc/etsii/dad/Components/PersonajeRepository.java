package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

	//@Query("SELECT p FROM Personaje p WHERE")
	Optional<Personaje> findByFormacion(Formacion formacion);
	Optional<Personaje> findByNombre(String nombre);

}
