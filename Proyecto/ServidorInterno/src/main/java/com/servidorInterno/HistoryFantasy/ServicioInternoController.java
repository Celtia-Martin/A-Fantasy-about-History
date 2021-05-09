package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.etsii.dad.Components.BatallaService;
import es.urjc.etsii.dad.Components.ControlFormaciones;
import es.urjc.etsii.dad.Components.ControlMercado;
import es.urjc.etsii.dad.Components.ControlPersonajes;

@Controller
public class ServicioInternoController {
	
	@Autowired
	private ControlPersonajes controlPersonajes;
	
	@Autowired
	private ControlFormaciones controlFormacion;
	
	@Autowired
	private ControlMercado controlMercado;
	
	@Autowired
	private BatallaService controlBatalla;

	@Autowired
	private ComSockets comSockets;
	
	@PostConstruct
	 void started() {
		
		controlUsuarios.newUser("Celtia", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla,true);
		
		controlUsuarios.newUser("Daniel", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);	
		controlUsuarios.newUser("AristoGato", "Gato", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Paimon", "EmergencyFood", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Richtofen","hayquequemarlasconfire", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("M.Rajoy", "persianas", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Kala", "ffviii", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Panumo","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Joselito", "joselito", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Japi","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Musa","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Jaimito", "chiste", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Cactus",  "noAgua", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
			
		controlPersonajes.iniciar();
		controlMercado.newMercado(controlPersonajes);
		controlBatalla.nuevaBatalla();
		
		comSockets.Comunicacion();
	}	

}
