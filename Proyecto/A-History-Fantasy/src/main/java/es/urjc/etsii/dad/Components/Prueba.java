package es.urjc.etsii.dad.Components;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Prueba extends WebController{

	public Prueba() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	private BatallaService controlBatalla;
	
	@PostMapping("/ejecutarBatalla")
	public String FormularioPersonajes(Model model,HttpServletRequest request) {
		controlBatalla.RealizarBatalla();
		
		return GetMenuPrincipal(model,request);
	}
	

}
