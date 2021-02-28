

package es.urjc.etsii.dad.Components;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
//@Component
//@SessionScope

public class BatallaService {
	@Autowired
	private BatallaRepository batallas;
	
	@Autowired
	private UserRepository jugadores;
	
	
	//Hacer consultas
	
	public void RealizarBatalla() {
		Optional<Batalla> bat = batallas.findFirstBy();
		
		if(bat.isPresent()) {
		
			Enums.TipoBatalla tipo = bat.get().getTipo();
			
			for(int i = 0; i < jugadores.count();i++) {
				int dinero = 0;
				int puntos = 0;
				
				int vMilitar = 0;
				int vDiplomatico = 0;
				int vCultural = 0;
				
				Long l = (long) i;
				
				Optional<User> jug = jugadores.findById(l);
				
				if(jug.isPresent()) {
					Formacion form = jug.get().getFormacion();
					
					List <Personaje> per = form.getPersonajes();
					
					int puntosTotales = 0;
					int puntosFinales = 0;
					
					for (int j = 0; j < per.size();j++) {
						
						puntosTotales = 0;
						
						vMilitar = per.get(j).getVMilitar();
						vDiplomatico = per.get(j).getVDiplomatico();
						vCultural = per.get(j).getVCultural();
						
						switch(tipo) {
						
							case MILITAR: 
								if(per.get(j).getTipo() == "Militar") {
									puntosTotales += vMilitar * 2;
								} else {
									puntosTotales += vMilitar;
								}
							
								puntosTotales += vDiplomatico;
								puntosTotales += vCultural;
							
								break;
				
							case DIPLOMATICO:
								if(per.get(j).getTipo() == "Diplomatico") {
									puntosTotales += vDiplomatico * 2;
								} else {
									puntosTotales += vDiplomatico;
								}
							
								puntosTotales += vCultural;
								puntosTotales += vMilitar;
							
								break;
					
							case CULTURAL:
								if(per.get(j).getTipo() == "Cultural") {
									puntosTotales += vCultural * 2;
								} else {
									puntosTotales += vCultural;
								}
							
								puntosTotales += vDiplomatico;
								puntosTotales += vMilitar;
							
								break;
						}
						
						puntosFinales = puntosTotales;
						
					}
					
					puntos = puntosFinales;
					dinero = puntosFinales * 3;
					
					jug.get().setPuntos(jug.get().getPuntos() + puntosFinales);
					jug.get().setDinero(jug.get().getDinero() + dinero);
				}
			}
			batallas.delete(bat.get());
		}
	}
	
	
	public Collection<Batalla> findAll() {
		return batallas.findAll();
	}
	
	public Page<Batalla> findAll(Pageable pageable) {
		return batallas.findAll(pageable);
	}

	public Optional<Batalla> findById(long id) {
		return batallas.findById(id);
	}

	public void save(Batalla batalla) {

		batallas.save(batalla);
	}

	public void deleteById(long id) {
		this.batallas.deleteById(id);
	}

}
