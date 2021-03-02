package es.urjc.etsii.dad.Components;

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
		// TODO Auto-generated method stub
		
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
	public void ReiniciarMercado(ControlMercado controlMercado) {
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
						}
						else {
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
