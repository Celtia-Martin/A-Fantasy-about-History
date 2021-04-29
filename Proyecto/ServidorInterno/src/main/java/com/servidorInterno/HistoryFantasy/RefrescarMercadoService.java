package com.servidorInterno.HistoryFantasy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefrescarMercadoService {
	@Autowired
	private ControlPuja controlPuja;
	
	@Autowired
	private ControlMercado controlMercado;
	
	@Autowired
	private ControlPersonajes controlPersonajes;

	@Autowired
	UserRepository userRepo;
	@Autowired
	FormacionRepository formRepo;

	public RefrescarMercadoService() {
		// TODO Auto-generated constructor stub
	}
	@Transactional
	public String RefrescarMercado() {
		controlPuja.ReiniciarMercado(controlMercado,userRepo,controlPersonajes,formRepo);
		controlMercado.newMercado(controlPersonajes);
		
		return "login";
	}

}
