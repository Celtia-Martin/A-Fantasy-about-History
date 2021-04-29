package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FormacionRepository extends JpaRepository<Formacion, Long> {


	Optional<Formacion> findByPropietario(User user);
	
}
