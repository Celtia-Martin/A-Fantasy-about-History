package es.urjc.etsii.dad.Components;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Transactional
@Service
public class BatallaService {
	@Autowired
	private BatallaRepository batallas;
	
	@Autowired
	private CommManager cm;
	
	
	public void RealizarBatalla() {
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
