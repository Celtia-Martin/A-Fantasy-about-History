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

public interface BatallaRepository extends JpaRepository<Batalla, Long> {
	

	Optional<Batalla> findById( Long Id);

	Optional<Batalla> findFirstBy();
	
	Page<Batalla> findByTipo( Enums.TipoBatalla tipo, Pageable page);
}
