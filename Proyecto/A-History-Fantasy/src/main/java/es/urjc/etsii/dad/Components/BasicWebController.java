package es.urjc.etsii.dad.Components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
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
	public String GetMenuPrincipal(Model model,HttpServletRequest request, HttpSession session) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		
		model.addAttribute("batalla", controlBatalla.getBatalla());
		model.addAttribute("esAdmin", request.isUserInRole("ADMIN"));
		
		if(ActualizarEncabezado(model,request,false, session)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}
	/*
	@GetMapping("/")
	public String Inicio (Model model, HttpSession session) {

		return "index";
	}*/
	
	@GetMapping("/error")
	public String ErrorGeneral() {
		return "error";
	}
	

}
