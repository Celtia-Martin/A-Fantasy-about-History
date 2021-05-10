package com.servidorInterno.HistoryFantasy;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PujaRepository extends JpaRepository<Puja, Long> {
	

	Optional<Puja> findFirstByPersonajePujado_IdOrderByValorDesc(Long id);

}
