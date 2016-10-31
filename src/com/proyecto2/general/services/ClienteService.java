package com.proyecto2.general.services;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.proyecto2.general.busquedaordenamiento.Busqueda;
import com.proyecto2.general.estructuradatos.ColaPrioridad;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;
import com.proyecto2.general.resources.BaseXML;
import com.proyecto2.general.resources.Inventario;
import com.proyecto2.general.resources.Menu;
import com.proyecto2.general.resources.Platillo;



/**
 * Clase encargada de brindar la informacion requerida del servidor por un usuario
 * @author alfredo
 */
@Path("/cliente")
public class ClienteService {
	
	BaseXML base=new BaseXML();
	ListaDoble<String,String> nombres=base.cargarChefs(new ListaDoble<String,String>(),"/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosChefs.xml" );
	ListaDoble<String,ColaPrioridad<String,String>> ordenes=base.cargarMatriz(nombres);
	Inventario inventario=new Inventario(base.cargarInventario("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml"));
	Menu menu=new Menu(base.cargarMenu("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml"));
	ColaPrioridad<Integer,String> jerarquia=base.cargarJerarquia("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosCategorias.xml");
	
	ListaDoble<Integer,ListaDoble<String,String>>progresos=base.cargarProgresos();
	
	
	
	/**
	 * Metodo qeu agrega un nuevo progreso relacionado a una orden
	 * @param numero numero de mesa que identifica a un cliente
	 * @return estado de la orden
	 */
	@POST 
	@Path("/progreso")
	@Produces(MediaType.APPLICATION_JSON)
	public String obtenerProgreso(String numero){
		int num=Integer.parseInt(numero);
		JSONArray ar=new JSONArray();
		JSONObject ob=new JSONObject();
		NodoDoble<Integer,ListaDoble<String,String>> lugar=progresos.head;
		while(lugar!=null){
			if(lugar.llave==num){
				break;
			}
			lugar=lugar.next;
		}
		if(lugar!=null){
		String cantidad;
		if(lugar.valor.head.llave.length()==1 && lugar.valor.size==1){
			cantidad="0";
		}else{
			cantidad=String.valueOf(lugar.valor.size);
		}
		try {
			ob.put("atributo", cantidad);
			ar.put(ob);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}else{
			try {
				ob.put("atributo", "500");
				ar.put(ob);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return ar.toString();
		
	}
	
	/**
	 * Metodo que recibe una orden y la distribuye en los chef activos
	 * @param orden nombre de los platillos que se ordenaron
	 */
	@POST
	@Path("/orden")
	@Consumes(MediaType.APPLICATION_JSON)
	public void jerarquiaOrden(String orden){
		System.out.print("Llego");
		try {
			JSONArray cat=new JSONArray(orden);
			int priori=Integer.parseInt(cat.getString(0));
			jerarquia.insertar(priori,cat.getString(1));
			int numeroMesa=Integer.parseInt(cat.getString(2));
			NodoDoble<Integer,ListaDoble<String,String>> lugar=progresos.head;
			while(lugar!=null){
				if(lugar.llave==numeroMesa){
					break;
				}
				lugar=lugar.next;
			}
			if(lugar!=null){
				JSONArray totalOrden=new JSONArray(cat.getString(1));
				for(int r=0;r<totalOrden.length();r++){
					lugar.valor.addLast(totalOrden.getJSONObject(r).getString("nombre"), "");
				}
			}else{
				ListaDoble<String,String> ordenesT=new ListaDoble<String,String>();
				JSONArray totalOrden=new JSONArray(cat.getString(1));
				for(int r=0;r<totalOrden.length();r++){
					ordenesT.addLast(totalOrden.getJSONObject(r).getString("nombre"), "");
				}
				ordenesT.addFirst("a", "");
				progresos.addLast(numeroMesa, ordenesT);
			}
			
			base.escrituraXMLJerarquia(jerarquia);
			base.escrituraXMLprogreso(progresos);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		cargarOrden();
		
	}
	
	/**
	 * Metodo que distribuye una orden segun el numero de chefs disponibles
	 */
	public void cargarOrden(){
		if(jerarquia.head.llave!=0){
			distribuirOrden(jerarquia.head.valor);
			if(jerarquia.size==1){
				jerarquia.head.llave=0;
			}else{
				jerarquia.head=jerarquia.head.next;
				jerarquia.size--;
			}
			base.escrituraXMLJerarquia(jerarquia);
		}else{
			
		}
	}
	
	
	/**
	 * Metodo qeu distribuye una serie de platillos obtenidos de un JSONArray
	 * @param order nombres de los platillos ordenados
	 */
	public void distribuirOrden(String order){
		try {
			JSONArray arr=new JSONArray(order);
			NodoDoble<String,String>puntero=nombres.head;
			for(int i=0;i<arr.length();i++){
				agregarOrden(arr.getJSONObject(i).getString("nombre"),puntero.llave);
				if(puntero.next==null){
					puntero=nombres.head;
				}else{
					puntero=puntero.next;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo que agrega una orden a la lista de tareas de un chef
	 * @param recipe platillo que debe preparar
	 * @param usuario nombre de usuario del chef
	 */
	public void agregarOrden(String recipe,String usuario){
		
		ListaDoble<String,String>orden=new ListaDoble<String,String>();
		try {
			orden.addFirst(recipe, "");
			NodoDoble<String,String>temp=orden.head;
			while(temp!=null){
				NodoDoble<String,Platillo> punt=Busqueda.busquedaBinariaDL(menu.recetas, temp.llave, 0,menu.recetas.size);
				if(punt!=null){
				NodoDoble<String,Integer>ing=punt.valor.ingredientes.head;
				String nomLlave=temp.llave;
				while(ing!=null){
					nomLlave=nomLlave+"\n"+ing.llave;
					ing=ing.next;
				}
				
				NodoDoble<String,String>puntR=punt.valor.receta.head;
				String pasos=puntR.llave+": "+puntR.valor;
				puntR=puntR.next;
				while(puntR!=null){
					pasos=pasos+"jk"+puntR.llave+": "+puntR.valor;
					puntR=puntR.next;
				}
				NodoDoble<String,ColaPrioridad<String,String>>unPuntero=Busqueda.busquedaBinariaDL(ordenes, usuario, 0, ordenes.size);
				unPuntero.valor.addFirst(nomLlave, pasos);}
				temp=temp.next;
			}
			base.escrituraXMLOrdenes(ordenes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
