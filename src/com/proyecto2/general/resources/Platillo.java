package com.proyecto2.general.resources;

import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;
import com.ibm.json.java.JSONObject;

/**
 * Clase encargada de almacenar toda la informacion relevante de un platillo.
 * @author alfredo
 *
 */
public class Platillo{
	
	public String nombre;
	public ListaDoble<String,String> receta;
	public ListaDoble<String,Integer> ingredientes;
	public String informacion;
	public int precio;
	public int tiempopreparacion;
	public int categoria;
	
	
	
	/**
	 *Constructor de la clase 
	 */
	public Platillo(){
		
	}
	

	/**
	 * Constructor de la clase
	 * @param nombre nombre del platillo
	 * @param categoria	categoria del platillo
	 * @param ingredientes lista enlazada que contiene los ingredientes que lo componen
	 * @param receta lista enlazada con todos los pasos a seguir para preparar el platillo
	 * @param informacion informacion nutricional del platillo
	 * @param precio numero entero que representa el precio del platillo
	 * @param tiempopreparacion numero entero que representa el tiempo de preparacion del platillo
	 */
	public Platillo(String nombre,int categoria,ListaDoble<String,Integer> ingredientes,ListaDoble<String,String>receta,String informacion,int precio,int tiempopreparacion) {
		this.nombre=nombre;
		this.ingredientes=ingredientes;
		this.informacion=informacion;
		this.precio=precio;
		this.tiempopreparacion=tiempopreparacion;
		this.receta=receta;
		this.categoria=categoria;
	}
	
	
	
	/**
	 * Metodo que ingresa todos los atributos de un objeto de tipo Platillo dentro de un JSONObject
	 * @return JSONObject con toda la informacion de una platillo
	 */
	public JSONObject parseJSONObject(){
		JSONObject objeto= new JSONObject();
		objeto.put("nombre", nombre);
		objeto.put("precio", precio);
		objeto.put("categoria", categoria);
		objeto.put("tiempo", tiempopreparacion);
		objeto.put("informacion", informacion);
		objeto.put("cantidad", ingredientes.size);
		int i=1;
		for(NodoDoble<String,Integer> temp=ingredientes.head;temp!=null;temp=temp.next){
			objeto.put("ingrediente"+i, temp.llave);
			i++;
		}
		objeto.put("pasos", receta.size);
		for(NodoDoble<String,String> tmp=receta.head;tmp!=null;tmp=tmp.next){
			objeto.put(tmp.llave, tmp.valor);
		}
		return objeto;
	}

}
