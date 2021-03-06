package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
@CacheConfig(cacheNames="personajes")
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

	@CacheEvict(allEntries=true)//eliminar cache cuando haya nueva entrada
	Personaje save(Personaje personaje);
	
	
	List<Personaje> findByIsDefaultAndTieneFormacion(boolean isDefault, boolean tieneFormacion);

	Optional<Personaje> findByNombre(String nombre);
	Page<Personaje> findByIsDefaultAndTieneFormacion(boolean isDefault, boolean tieneFormacion,Pageable page);
	@Cacheable
	Page<Personaje> findByIsDefault(boolean isDefault,Pageable page);
	Optional<Personaje> findById(Long id);
	void deleteById(Long id);
	void deleteByNombre(Long id);
}
