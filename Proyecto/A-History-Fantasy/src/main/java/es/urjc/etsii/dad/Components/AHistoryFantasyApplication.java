package es.urjc.etsii.dad.Components;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@SpringBootApplication
@EnableCaching
//@EnableHazelcastHttpSession
public class AHistoryFantasyApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(AHistoryFantasyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
	}
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("user","personajes","formacion","mercado");
	
	}


}
