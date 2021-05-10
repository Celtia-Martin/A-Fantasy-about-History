package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjecutarBatallaService {
	
	@Autowired
	private BatallaService controlBatallas;
	
	@Autowired
	private BatallaRepository batallas;
	
	@Autowired
	private UserRepository jugadores;
	/*
	@Autowired
	private InvalidadorDeUser invUser;
	*/
	
	
	public EjecutarBatallaService() {
		// TODO Auto-generated constructor stub
	}
	@Transactional
	public String RealizarBatalla() {
		
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
					
				
				dinero = Math.round(puntosFinales / 2);
					
				jug.setPuntos(jug.getPuntos() + puntosFinales);
				jug.setDinero(jug.getDinero() + dinero);
				jugadores.save(jug);
			}
		}
		
		batallas.delete(bat.get());
		
		controlBatallas.nuevaBatalla();
	
		return "login";
	}
}
