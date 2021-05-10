package com.servidorInterno.HistoryFantasy;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatallaService {
	@Autowired
	private BatallaRepository batallas;
	
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
