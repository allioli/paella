package com.mytesting;

import java.util.HashMap;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class World {

	private HashMap<String,String> loginCredentials = new HashMap<>();

	public String getLoginEmail() {
		
		if(loginCredentials.containsKey("email")) {
			return loginCredentials.get("email");
		}
		
		String email = Utils.generateTestEmail();
		loginCredentials.put("email", email);
		return email;
	}
	
	public String getLoginPassword() {
			
		if(loginCredentials.containsKey("password")) {
			return loginCredentials.get("password");
		}
		
		String pwd = Utils.generateTestPassword(8);
		loginCredentials.put("password", pwd);
		return pwd;
	}

    
}
