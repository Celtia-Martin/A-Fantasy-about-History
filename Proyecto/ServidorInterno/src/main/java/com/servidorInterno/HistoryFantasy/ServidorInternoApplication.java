package com.servidorInterno.HistoryFantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class ServidorInternoApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServidorInternoApplication.class, args);
		
	}

}
