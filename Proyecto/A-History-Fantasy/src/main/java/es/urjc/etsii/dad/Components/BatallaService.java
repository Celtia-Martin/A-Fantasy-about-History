package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatallaService {
	@Autowired
	private BatallaRepository batallas;
	
	@Autowired
	private UserRepository jugadores;
	
	private Logger log = LoggerFactory.getLogger(BatallaService.class);


	
	public void RealizarBatalla() {
		Optional<Batalla> bat = batallas.findFirstBy();
		
		if(bat.isPresent()) {
			
			Enums.TipoBatalla tipo = bat.get().getTipo();
			
			int limit = (int) jugadores.count();
			
			List<User> listJug = jugadores.findAll();
			
			for(int i = 0; i < limit;i++) {
				
				int dinero = 0;
				int puntos = 0;
				
				int vMilitar = 0;
				int vDiplomatico = 0;
				int vCultural = 0;
				
				User jug = listJug.get(i);
				
				Formacion form = jug.getFormacion();
					
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
						
					puntosFinales += puntosTotales;
				}
					
				puntos = puntosFinales;
				dinero = puntosFinales * 2;
					
				jug.setPuntos(jug.getPuntos() + puntosFinales);
				jug.setDinero(jug.getDinero() + dinero);
			}
		}
		
		batallas.delete(bat.get());
		nuevaBatalla();
	}

	public void nuevaBatalla() {
		Batalla batalla = new Batalla();
		batallas.save(batalla);
	}
	
	public String getBatalla() {
		Optional<Batalla> bat = batallas.findFirstBy();
		
		if(bat.isPresent()) {
			return bat.get().getTipo().toString();
		}
		return "";
	}

}
