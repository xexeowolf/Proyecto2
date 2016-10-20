package com.proyecto2.general.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/menu")
public class MenuService {
	
	
	public MenuService() {
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String algo(){
		return "Estoy en el menu";
	}

}
