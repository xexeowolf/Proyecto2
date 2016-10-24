package com.proyecto2.general.resources;

import com.proyecto2.general.estructuradatos.ListaDoble;


public class Orden {

	public ListaDoble<String,Integer> platillos;
	
	public Orden() {
		platillos=new ListaDoble<String,Integer>();
	}
	public void agregarPlatillo(String platillo,int cantidad){
		platillos.addFirst(platillo, cantidad);
	}

}
