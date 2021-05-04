package es.urjc.etsii.dad.Components;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Repository

public interface MercadoRepository extends JpaRepository<Mercado, Long> {

	Optional<Mercado>findById(Long id);
	Optional<Mercado>findFirstBy();
}
