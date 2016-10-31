package com.proyecto2.general.resources;


import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



/**
 * Clase encargada de almacenar toda la informacion del menu de un restaurante
 * @author alfredo
 */
public class Menu {
	
	public ListaDoble<String,Platillo> recetas;

	
	/**
	 * Constructor de la clase
	 */
	public Menu() {
		recetas=new ListaDoble<String,Platillo>();
	}
	/**
	 * Constructor de la clase
	 * @param menu lista enlazada que contienen la informacion de todos los platillos de un menu
	 */
	public Menu(ListaDoble<String,Platillo> menu){
		recetas=menu;
		OrdenamientoDLL.ShellSort(recetas);
	}
	/**
	 * Metodo que agrega una receta al menu
	 * @param categoria categoria a la que pertenece el platillo
	 * @param platillo	objeto de la clase platillo que contiene toda la informacion que se desea almacenar sobre una receta
	 */
	public void agregarReceta(String categoria,Platillo platillo){
		recetas.addFirst(categoria,platillo);
		OrdenamientoDLL.ShellSort(recetas);
	}
	/**
	 * Metodo que coloca toda la informacion contenida en el menu en JSONObject y luego en un JSONArray para posteriormente ser transmitida
	 * @return un JSONArray con toda la informacion contenida en el menu
	 */
	public JSONArray parseJSONArray(){
		JSONArray arreglo=new JSONArray();
		for(NodoDoble<String,Platillo>temp=recetas.head;temp!=null;temp=temp.next){
			arreglo.add(temp.valor.parseJSONObject());
		}
		return arreglo;
	}
	
	/**
	 * Metodo que coloca solo una categoria del menu en un JSONArray para luego ser transmitida
	 * @param atributo numero entero que representa la categoria que se desea obtener
	 * @return JSONArray con la informacion de una categoria del menu
	 */
	public JSONArray parseEspecifico(int atributo){
		JSONArray arreglo=new JSONArray();
		for(NodoDoble<String,Platillo>temp=recetas.head;temp!=null;temp=temp.next){
			JSONObject objeto=new JSONObject();
			if(temp.valor.categoria==atributo){
				objeto.put("atributo", temp.valor.nombre+"jkPrecio: "+temp.valor.precio+" colonesjkTiempo de preparacion: "+temp.valor.tiempopreparacion+"jkInformacion nutricional: "+temp.valor.informacion);
				arreglo.add(objeto);
			}
		}
		return arreglo;
		
		
	}
	
}
