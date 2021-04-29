package es.urjc.etsii.dad.Components;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Repository
@CacheConfig(cacheNames="mercado")
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
	@CacheEvict(allEntries=true)//eliminar cache cuando haya nueva entrada
	Mercado save(Mercado mercado);
	@JsonIgnore

	
	@Cacheable
	Optional<Mercado>findById(Long id);
	@Cacheable
	Optional<Mercado>findFirstBy();
}
