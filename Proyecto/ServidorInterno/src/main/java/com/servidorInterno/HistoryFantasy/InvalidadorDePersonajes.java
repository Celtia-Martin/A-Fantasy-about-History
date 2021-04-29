package com.servidorInterno.HistoryFantasy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
@CacheConfig(cacheNames="personajes")
@Component
public class InvalidadorDePersonajes {
	@CacheEvict(allEntries=true)
	public void Invalidar() {
		
	}

}
