package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ControlPuja implements CommandLineRunner {

	@Autowired
	private PujaRepository repository;
	
	@Override
	public void run(String... args) throws Exception {

		
	}

	public Optional<Puja> findMejorPujaByPersonajeId(Long id){
		return repository.findFirstByPersonajePujado_IdOrderByValorDesc(id);
	}
	public boolean Pujar(User user, Personaje personaje, int valor) {
		if(valor> user.getDinero()) {
			return false;
		}
		else if( valor<personaje.getPrecio()) {
			return false;
		}
		else {
			Puja nueva= new Puja(personaje,user,valor);
			repository.save(nueva);
			return true;
		}
		
	}
	public void ReiniciarMercado(ControlMercado controlMercado,UserRepository userepo, ControlPersonajes controlPersonajes, FormacionRepository formRepository) {
		List<Personaje> oferta= controlMercado.findAllPersonajes();
		boolean listo;
		for( Personaje p: oferta) {
			listo=false;
			while(!listo) {
				Optional<Puja> ganadora= repository.findFirstByPersonajePujado_IdOrderByValorDesc(p.getId());
				
				if(ganadora.isPresent()) {
					
					User ganador= ganadora.get().getUser();
					
					if(ganador.getDinero()>=ganadora.get().getValor()) {
						
						Formacion formacion= ganador.getFormacion();
						
						if(formacion.contPersonajes()<6) {
							
							p.setTieneFormacion(true);
							formacion.addPersonaje(p);
							ganador.setDinero(ganador.getDinero()-ganadora.get().getValor());
							listo=true;
							//invalidar caches
							userepo.save(ganador);
							controlPersonajes.UpdatePersonaje(p);
							formRepository.save(formacion);
						}
						else {
							repository.delete(ganadora.get());
							listo= false;
						}
						
					}else {
						repository.delete(ganadora.get());
					}
				}
				else {
					listo=true;
				}
			}
			
		}
		
		repository.deleteAll();
	}
}
