package com.proyecto2.general.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.json.java.JSONObject;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.resources.Menu;
import com.proyecto2.general.resources.Platillo;


@Path("/cliente")
public class ClienteService {
	public ListaDoble<String,Integer> ingred=new ListaDoble<String,Integer>();
	public Menu menu=new Menu();
	
	@GET
	@Path("/menu")
	@Produces(MediaType.TEXT_PLAIN)
	public String enviar(){
		return "Exito perro!";
	}
	
	
	
	/*@GET
	@Path("/algo")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public String verMenu(){
		/*ingred.addFirst("tomate",3);
		ingred.addFirst("aguacate",1);
		ingred.addFirst("tortillas",10);
		//Platillo plato=new Platillo("Sopa Azteca",ingred,new ListaDoble<String,String>(),"50 kcal",13000,30);
		menu.agregarReceta("",plato);
		return menu.parseJSONArray().toString();
	}*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String agregarMenu(String algo){
		ingred.addFirst("banano", 2);
		//Platillo plato=new Platillo(algo,ingred,new ListaDoble<String,String>(),"50 kcal",13000,30);
		//menu.agregarReceta("",plato);
		return algo;
	}
	

}
