package es.urjc.etsii.dad.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicWebController extends WebController {

	@Autowired
	private BatallaService controlBatalla;
	
	public BasicWebController() {
		// TODO Auto-generated constructor stub
	}
	@GetMapping("/menuPrincipal")
	public String GetMenuPrincipal(Model model) {
		model.addAttribute("batalla", controlBatalla.getBatalla());
		
		if(ActualizarEncabezado(model)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/")
	public String Inicio (Model model) {
		currentUser.setCurrentName(null);
		return "index";
	}
	
	
	@GetMapping("/error")
	public String ErrorGeneral() {
		return "error";
	}
	

}
