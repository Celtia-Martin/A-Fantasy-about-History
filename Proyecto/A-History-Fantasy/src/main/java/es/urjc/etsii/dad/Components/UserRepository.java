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
//@CacheConfig(cacheNames="user")
public interface UserRepository extends JpaRepository<User, Long> {
	
	//@CacheEvict(allEntries=true)//eliminar cache cuando haya nueva entrada
	//User save(User user);

	//@Cacheable
	Optional<User> findByNombre( String Nombre);
	//@Cacheable
	Optional<User> findById( Long id);
	void deleteByNombre(String Nombre);
	//@Cacheable
	List <User>findTop10ByOrderByPuntosDesc();
	
	
}






