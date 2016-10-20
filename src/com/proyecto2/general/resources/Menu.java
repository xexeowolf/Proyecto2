package com.proyecto2.general.resources;

import com.estructurasdedatos.ListaDoble;
import com.estructurasdedatos.NodoDoble;


public class Menu {
	
	public ListaDoble<Receta,Integer> recetas;

	public Menu() {
		recetas=new ListaDoble<Receta,Integer>();
	}
	public void agregarReceta(Receta platillo){
		recetas.addFirst(platillo, 0);
	}
	public String mostrarMenu(){
		String texto="Menu: \n";
		NodoDoble<Receta,Integer> tmp=recetas.head;
		while(tmp!=null){
			texto=texto+" "+tmp.llave.nombre;
			tmp=tmp.next;
		}
		return texto;
	}
	

}
