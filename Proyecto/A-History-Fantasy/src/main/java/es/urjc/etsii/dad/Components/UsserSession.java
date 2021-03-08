package es.urjc.etsii.dad.Components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UsserSession {
	private String currentName;

	public UsserSession() {
		
	}
	public UsserSession(String name) {
		currentName=name;
	}
	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

}
