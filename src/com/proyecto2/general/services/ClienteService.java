package com.proyecto2.general.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.estructurasdedatos.ListaDoble;
import com.proyecto2.general.resources.Menu;
import com.proyecto2.general.resources.Receta;


@Path("/cliente")
public class ClienteService {
	
	public Menu menu;
	
	public ClienteService() {
		
	}
	
	@GET
	@Path("/menu")
	@Produces(MediaType.APPLICATION_XML)
	public Menu verMenu(){
		menu=new Menu();
		ListaDoble<String,Integer> l=new ListaDoble<String,Integer>();
		l.addFirst("arroz", 2);
		l.addFirst("tomate", 3);
		Receta platillo=new Receta("sopaazteca",l,"estacaliente",2350,2);
		menu.agregarReceta(platillo);
		return menu;
	}
	
	public static void main(String[] args){
		ClienteService j=new ClienteService();
		j.menu=new Menu();
		ListaDoble<String,Integer> l=new ListaDoble<String,Integer>();
		l.addFirst("arroz", 2);
		l.addFirst("tomate", 3);
		Receta platillo=new Receta("sopaazteca",l,"estacaliente",2350,2);
		j.menu.agregarReceta(platillo);
		
	}

}
