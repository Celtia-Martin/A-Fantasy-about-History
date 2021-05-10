package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Repository
//@CacheConfig(cacheNames="formacion")
public interface FormacionRepository extends JpaRepository<Formacion, Long> {
	
	//@CacheEvict(allEntries=true)//eliminar cache cuando haya nueva entrada
	Formacion save(Formacion formacion);

	

	
}
