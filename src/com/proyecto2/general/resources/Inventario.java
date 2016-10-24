package com.proyecto2.general.resources;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;


public class Inventario {
	
	public ListaDoble<String,String> frutas;
	public ListaDoble<String,String> granos;
	public ListaDoble<String,String> carnes;
	public ListaDoble<String,String> vegetales;
	public ListaDoble<String,String> lacteos;

	public Inventario(ListaDoble<String,String> inventario) {
		frutas=new ListaDoble<String,String>();
		granos=new ListaDoble<String,String>();
		carnes=new ListaDoble<String,String>();
		vegetales=new ListaDoble<String,String>();
		lacteos=new ListaDoble<String,String>();
		rellenar(inventario);
	}
	
	public void agregarIngrediente(String categoria,String ingrediente){
		switch(categoria){
		case "fruta": frutas.addFirst(ingrediente, "");break;
		case "grano": granos.addFirst(ingrediente, "");break;
		case "carne": carnes.addFirst(ingrediente, "");break;
		case "vegetal": vegetales.addFirst(ingrediente, "");break;
		case "lacteo": lacteos.addFirst(ingrediente, "");break;
		}
	}
	
	public void rellenar(ListaDoble<String,String> inventario){
		NodoDoble<String,String> tmp=inventario.head;
		while(tmp!=null){
			agregarIngrediente(tmp.llave,tmp.valor);
			tmp=tmp.next;
		}
	}
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
