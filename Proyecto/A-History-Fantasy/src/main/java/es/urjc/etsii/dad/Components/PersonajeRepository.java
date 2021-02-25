package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

	//@Query("SELECT p FROM Personaje p WHERE")
	//@Query ("SELECT * FROM Personaje  WHERE nombre == 'Celtia' ")
	List<Personaje> findByIsDefaultAndTieneFormacion(boolean isDefault, boolean tieneFormacion);
	Optional<Personaje> findByFormacion(Formacion formacion);
	Optional<Personaje> findByNombre(String nombre);
	Page<Personaje> findByIsDefaultAndTieneFormacion(boolean isDefault, boolean tieneFormacion,Pageable page);
	Optional<Personaje> findById(Long id);
	void deleteById(Long id);
}
