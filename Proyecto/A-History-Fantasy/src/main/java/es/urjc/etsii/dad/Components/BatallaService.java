package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Transactional
@Service
public class BatallaService {
	@Autowired
	private BatallaRepository batallas;
	
	
	
	private Logger log = LoggerFactory.getLogger(BatallaService.class);
	
	public void RealizarBatalla() {
		
		CommManager cm = new CommManager();//invalidar: para refrescar mercado: usuarios formaciones y personajes// ejecutar batalla usuarios
		cm.Comunicacion("Batalla");
		
		
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
