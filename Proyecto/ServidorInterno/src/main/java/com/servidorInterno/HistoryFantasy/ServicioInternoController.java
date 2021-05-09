package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ServicioInternoController {


	@Autowired
	private ComSockets comSockets;
	
	@PostConstruct
	 void started() {
		
	
		
		comSockets.Comunicacion();
	}	

}
