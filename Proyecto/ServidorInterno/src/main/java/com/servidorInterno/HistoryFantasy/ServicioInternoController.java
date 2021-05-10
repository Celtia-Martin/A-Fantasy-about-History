package com.servidorInterno.HistoryFantasy;



import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class ServicioInternoController {


	@Autowired
	private ComSockets comSockets;
	
	@PostConstruct
	 void started() {
		
	
		
		comSockets.Comunicacion();
	}	

}
