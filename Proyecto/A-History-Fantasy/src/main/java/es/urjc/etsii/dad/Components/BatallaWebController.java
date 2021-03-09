package es.urjc.etsii.dad.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BatallaWebController extends WebController{

	public BatallaWebController() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	private BatallaService controlBatalla;
	
	@PostMapping("/ejecutarBatalla")
	public String FormularioPersonajes(Model model) {
		controlBatalla.RealizarBatalla();
		
		return GetMenuPrincipal(model);
	}
	

}
