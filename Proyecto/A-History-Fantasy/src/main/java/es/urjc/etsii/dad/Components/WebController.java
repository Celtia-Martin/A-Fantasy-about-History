package es.urjc.etsii.dad.Components;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@Autowired
	protected ControlUsuarios controlUsuarios;
	
	@Autowired
	private BatallaService controlBatalla;
	
	@Autowired
	protected UsserSession currentUser;
	
	
	public boolean ActualizarEncabezado(Model model,HttpServletRequest request,boolean formulario) {
		if(currentUser!=null) {
			if(formulario) {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				 model.addAttribute("token", token.getToken()); 
			}
			
			if(currentUser.getCurrentName()!=null) {
				Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
				
				if(current.isPresent()) {
					long dinero = 0;
					long puntos = 0;
					
					if(current.isPresent()) {
						dinero= current.get().getDinero();
						puntos= current.get().getPuntos();
					}
					
					model.addAttribute("name",currentUser.getCurrentName());
					model.addAttribute("dinero",dinero);
					model.addAttribute("puntos",puntos);
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	public String GetMenuPrincipal(Model model,HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		
		model.addAttribute("batalla", controlBatalla.getBatalla());
		model.addAttribute("esAdmin",request.isUserInRole("ADMIN"));
		
		if(ActualizarEncabezado(model,request,false)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}

	public String Inicio (Model model) {
		currentUser.setCurrentName(null);
		return "index";
	}
	
	

	public String ErrorGeneral() {
		return "error";
	}
	
}
