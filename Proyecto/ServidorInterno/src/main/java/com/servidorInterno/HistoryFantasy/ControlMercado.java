package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class ControlMercado  implements CommandLineRunner {

	@Autowired
	private MercadoRepository repository;
	public ControlMercado() {
	
	}
	
	public void newMercado(ControlPersonajes controlpersonaje) {
		Optional<Mercado> current= repository.findFirstBy();
		if(current.isPresent()) {
			repository.delete(current.get());
		}
		Mercado nuevo= new Mercado();
		List<Personaje> mercado= controlpersonaje.getRandomMercado();
		nuevo.addPersonajes(mercado);
		repository.save(nuevo);
		
	}
	public List<Personaje> findAllPersonajes(){
		Optional<Mercado> mercado= repository.findFirstBy();
		if(mercado.isPresent()) {
			return mercado.get().getOferta();
		}
		return null;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}
	

}
