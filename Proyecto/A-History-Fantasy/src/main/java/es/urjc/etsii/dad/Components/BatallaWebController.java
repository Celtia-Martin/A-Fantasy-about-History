package es.urjc.etsii.dad.Components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BatallaWebController extends WebController{

	public BatallaWebController() {
		
	}
	@Autowired
	private BatallaService controlBatalla;
	
	@PostMapping("/ejecutarBatalla")
	public String FormularioPersonajes(Model model,HttpServletRequest request, HttpSession session) {
		controlBatalla.RealizarBatalla();
		
		return GetMenuPrincipal(model,request, session);
	}
	

}
