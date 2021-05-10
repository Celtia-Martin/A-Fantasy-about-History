package es.urjc.etsii.dad.Components;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class WebController {

	@Autowired
	protected ControlUsuarios controlUsuarios;
	
	@Autowired
	private BatallaService controlBatalla;
	
	public boolean ActualizarEncabezado(Model model,HttpServletRequest request,boolean formulario, HttpSession session) {
		
			if(formulario) {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				model.addAttribute("token", token.getToken()); 
			}
			
			if(request.getUserPrincipal().getName()!=null) {

				Optional<User> current= controlUsuarios.findByNombre((String) request.getUserPrincipal().getName());
				
				if(current.isPresent()) {
					long dinero = 0;
					long puntos = 0;
					
					if(current.isPresent()) {
						dinero= current.get().getDinero();
						puntos= current.get().getPuntos();
					}
					
					model.addAttribute("name",(String) request.getUserPrincipal().getName());
					model.addAttribute("dinero",dinero);
					model.addAttribute("puntos",puntos);
					return true;
				}
				else {
				
					return false;
				}
			}
		
		return false;
	}
	
	public String GetMenuPrincipal(Model model,HttpServletRequest request, HttpSession session) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		
		model.addAttribute("batalla", controlBatalla.getBatalla());
		model.addAttribute("esAdmin",request.isUserInRole("ADMIN"));
		
		if(ActualizarEncabezado(model,request,false, session)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}

	public String Inicio (Model model) {

		return "index";
	}
	
	

	public String ErrorGeneral() {
		return "error";
	}
	
}
