package com.proyecto2.general.resources;


import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;

public class Menu {
	
	public ListaDoble<String,Platillo> recetas;

	public Menu() {
		recetas=new ListaDoble<String,Platillo>();
	}
	public Menu(ListaDoble<String,Platillo> menu){
		recetas=menu;
		OrdenamientoDLL.ShellSort(recetas);
	}
	public void agregarReceta(String categoria,Platillo platillo){
		recetas.addFirst(categoria,platillo);
		OrdenamientoDLL.ShellSort(recetas);
	}
	public JSONArray parseJSONArray(){
		JSONArray arreglo=new JSONArray();
		for(NodoDoble<String,Platillo>temp=recetas.head;temp!=null;temp=temp.next){
			arreglo.add(temp.valor.parseJSONObject());
		}
		return arreglo;
	}
	
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
