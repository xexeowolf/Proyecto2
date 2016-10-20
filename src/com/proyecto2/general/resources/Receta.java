package com.proyecto2.general.resources;

import com.estructurasdedatos.ListaDoble;

public class Receta {
	
	public String nombre;
	public ListaDoble<String,Integer> ingredientes;
	public String informacion;
	public int precio;
	public int tiempopreparacion;
	

	public Receta(String nombre,ListaDoble<String,Integer> ingredientes,String informacion,int precio,int tiempopreparacion) {
		this.nombre=nombre;
		this.ingredientes=ingredientes;
		this.informacion=informacion;
		this.precio=precio;
		this.tiempopreparacion=tiempopreparacion;
	}

}
