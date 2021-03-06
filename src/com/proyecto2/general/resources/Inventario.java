package com.proyecto2.general.resources;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;



/**
 * Clase encargada de almacenar informacion de alimentos  por categorias definidas
 * @author alfredo
 *
 */
public class Inventario {
	
	public ListaDoble<String,String> frutas;
	public ListaDoble<String,String> granos;
	public ListaDoble<String,String> carnes;
	public ListaDoble<String,String> vegetales;
	public ListaDoble<String,String> lacteos;

	/**
	 * Constructor de la clase
	 * @param inventario lista enlazada que contiene los elementos que se desea clasificar.
	 */
	public Inventario(ListaDoble<String,String> inventario) {
		frutas=new ListaDoble<String,String>();
		granos=new ListaDoble<String,String>();
		carnes=new ListaDoble<String,String>();
		vegetales=new ListaDoble<String,String>();
		lacteos=new ListaDoble<String,String>();
		rellenar(inventario);
		ordenar();
	}
	
	/**
	 * Metodo que agrega un elementos a una categoria especifica
	 * @param categoria cadena de texto que representa el nombre de la categoria
	 * @param ingrediente cadena de texto que contiene el nombre del ingrediente que se desea agregar.
	 */
	public void agregarIngrediente(String categoria,String ingrediente){
		switch(categoria){
		case "fruta": frutas.addFirst(ingrediente, "");break;
		case "grano": granos.addFirst(ingrediente, "");break;
		case "carne": carnes.addFirst(ingrediente, "");break;
		case "vegetal": vegetales.addFirst(ingrediente, "");break;
		case "lacteo": lacteos.addFirst(ingrediente, "");break;
		}
	}
	
	/**
	 * Metodo que clasifica los elementos de una lista enlazada en categorias
	 * @param inventario lista enlazada con los elementos a clasificar
	 */
	public void rellenar(ListaDoble<String,String> inventario){
		NodoDoble<String,String> tmp=inventario.head;
		while(tmp!=null){
			agregarIngrediente(tmp.llave,tmp.valor);
			tmp=tmp.next;
		}
	}
	
	/**
	 * Metodo que ordena los elementos de cada categoria segun un algoritmo diferente,
	 */
	public void ordenar(){
		OrdenamientoDLL.QuickSort(frutas);
		granos=OrdenamientoDLL.RadixSort(granos,new String[granos.size]);
		OrdenamientoDLL.ShellSort(vegetales);
		OrdenamientoDLL.BubbleSort(lacteos);
		OrdenamientoDLL.InsertionSort(carnes);
	}
	
	
	/**
	 * Metodo que coloca la informacion de una categoria dentro de un JSONArray.
	 * @param atributo numeto entero que representa la categoria que se desea obtener
	 * @return JSONArray con la informacion de una categoria.
	 */
	public JSONArray parseEspecifico(int atributo){
		JSONArray arreglo=new JSONArray();
		if(atributo==1){
			for(NodoDoble<String,String>tmp=frutas.head;tmp!=null;tmp=tmp.next){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo", tmp.llave);
				arreglo.add(objeto);
			}	
		}else if(atributo==2){
			for(NodoDoble<String,String>tmp=granos.head;tmp!=null;tmp=tmp.next){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo", tmp.llave);
				arreglo.add(objeto);
			}
		}else if(atributo==3){
			for(NodoDoble<String,String>tmp=carnes.head;tmp!=null;tmp=tmp.next){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo", tmp.llave);
				arreglo.add(objeto);
			}
		}else if(atributo==4){
			for(NodoDoble<String,String>tmp=lacteos.head;tmp!=null;tmp=tmp.next){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo", tmp.llave);
				arreglo.add(objeto);
			}
		}else if(atributo==5){
			for(NodoDoble<String,String>tmp=vegetales.head;tmp!=null;tmp=tmp.next){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo", tmp.llave);
				arreglo.add(objeto);
			}
		}
		return arreglo;
	}
	

}
