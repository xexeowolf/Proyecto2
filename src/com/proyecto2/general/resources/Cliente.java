package com.proyecto2.general.resources;

public class Cliente {

	public String categoria;
	public int mesa;
	public Orden orden;
	
	public Cliente(String grado,int numerodemesa) {
		categoria=grado;
		mesa=numerodemesa;
	}
	public void agregarOrden(Orden orden){
		this.orden=orden;
	}

}
