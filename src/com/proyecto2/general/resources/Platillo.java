package com.proyecto2.general.resources;





import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;
import com.ibm.json.java.JSONObject;



public class Platillo{
	
	public String nombre;
	public ListaDoble<String,String> receta;
	public ListaDoble<String,Integer> ingredientes;
	public String informacion;
	public int precio;
	public int tiempopreparacion;
	int categoria;
	
	public Platillo(){
		
	}
	

	public Platillo(String nombre,int categoria,ListaDoble<String,Integer> ingredientes,ListaDoble<String,String>receta,String informacion,int precio,int tiempopreparacion) {
		this.nombre=nombre;
		this.ingredientes=ingredientes;
		this.informacion=informacion;
		this.precio=precio;
		this.tiempopreparacion=tiempopreparacion;
		this.receta=receta;
		this.categoria=categoria;
	}
	
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
