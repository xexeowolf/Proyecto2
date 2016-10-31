package com.proyecto2.general.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
 * Clase encargada de enviar datos que se requieren del servidor
 * @author alfredo
 *
 */
@Path("/chef")
public class ChefService {
	
	BaseXML base=new BaseXML();
	ListaDoble<String,String> nombres=base.cargarChefs(new ListaDoble<String,String>(),"/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosChefs.xml");
	ListaDoble<String,String> chat=base.cargarChat("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/Mensajes.xml");
	ListaDoble<String,ColaPrioridad<String,String>> ordenes=base.cargarMatriz(nombres);
	Inventario inventario=new Inventario(base.cargarInventario("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml"));
	Menu menu=new Menu(base.cargarMenu("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml"));
	
	ListaDoble<Integer,ListaDoble<String,String>>progresos=base.cargarProgresos();
	
	/**
	 * Constructor de la clase
	 */
	public ChefService() {
		 
	}
	
	/**
	 * Metodo que escribe la informacion del inventario en un archivo XML
	 */
	public void escrituraXMLInventario(){
		base.escrituraXMLInventario(inventario);
	}
	
	/**
	 * Metodo que escribe la informacion del menu en un archivo XML
	 */
	public void escrituraXMLMenu(){
		base.escrituraXMLMenu(menu);
	}
	
	/**
	 * Metodo que obtiene un JSONObject y lo descompone en la informacion que se desea almacenar
	 * @param objeto JSONObject que contiene la informacion a almacenar
	 */
	public void agregarAlMenu(JSONObject objeto){
		try {
			String nombre=(String)objeto.get("nombre");
			String info=(String)objeto.get("informacion");
			int precio=(int)objeto.get("precio");
			int tiempo=(int)objeto.get("tiempo");
			int categoria=(int)objeto.get("categoria");
			int cantidad=(int)objeto.get("cantidad");
			int pasos=(int)objeto.get("pasos");
			ListaDoble<String,Integer> ingredientes=new ListaDoble<String,Integer>();
			ListaDoble<String,String> receta=new ListaDoble<String,String>();
			while(cantidad!=0){
				ingredientes.addFirst((String)objeto.get("ingrediente"+cantidad), 5);
				cantidad--;
			}
			while(pasos!=0){
				receta.addFirst("paso"+pasos,(String)objeto.get("paso"+pasos));
				pasos--;
			}
			Platillo plato=new Platillo(nombre,categoria,ingredientes,receta,info,precio,tiempo);
			menu.agregarReceta(nombre, plato);
			escrituraXMLMenu();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que agregar un nombre de usuario en un archivo XML
	 * @param chef nombre del usuario
	 */
	@POST
	@Path("/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregarChef(String chef){
		System.out.println("llego nombre");
		nombres.addFirst(chef, "");
		base.escrituraXMLChefs(nombres);
		base.escrituraXMLOrdenInicial(nombres);
	}
	
	/**
	 * Metodo que eliminar un nombre de usuario de un archivo XML
	 * @param chef nombre de usuario a eliminar
	 */
	@POST
	@Path("/eliminar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void eliminarChef(String chef){
		NodoDoble<String,String> puntero=Busqueda.busquedaBinariaDL(nombres, chef, 0, nombres.size);
		if(puntero!=null){
			if(puntero==nombres.head){
				nombres.deleteFirst();
			}else if(puntero==nombres.tail){
				nombres.deleteLast();
			}else{
				nombres.delete(puntero);
			}
			base.escrituraXMLChefs(nombres);
		}
	}

	/**
	 * Metodo que devuelve la informacion del chat
	 * @return JSONArray con la informacion del chat
	 */
	@GET
	@Path("/chat/obtener")
	@Produces(MediaType.APPLICATION_JSON)
	public String obtenerChat(){
		JSONObject objeto=new JSONObject();
		try {
			objeto.put("atributo", chat.head.llave);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray arr=new JSONArray();
		arr.put(objeto);
		return arr.toString();
	}
	
	/**Metodo que agregar un mensaje a la base de datos del chat
	 * @param mensaje mensaje que se desea almacenar
	 */
	@POST
	@Path("/chat/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregarChat(String mensaje){
		chat.head.llave=chat.head.llave+"jk"+mensaje;
		base.escrituraXMLChat(chat);
		
	}
	
	/**
	 * Metodo que devuelve una orden relacionada a un nombre de usuario
	 * @param nombre nombre de usuario
	 * @return JSONArray con la orden relacionada
	 */
	@POST
	@Path("/orden")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String obtenerOrden(String nombre){
		NodoDoble<String,ColaPrioridad<String,String>> temp;
		temp=Busqueda.busquedaBinariaDL(ordenes, nombre, 0, ordenes.size);
		JSONArray arrT=new JSONArray();
		
		if(temp!=null){
			try {
				if(temp.valor.head.valor.length()!=1){
				JSONObject objeto=new JSONObject();
				objeto.put("atributo",temp.valor.head.llave);
				arrT.put(objeto);
				objeto=new JSONObject();
				objeto.put("atributo",temp.valor.head.valor);
				arrT.put(objeto);
				if(temp.valor.head.next==null){
					temp.valor.head.valor="a";
					temp.valor.head.llave="a";
				}else{
					temp.valor.head=temp.valor.head.next;
					temp.valor.size--;
				}
				base.escrituraXMLOrdenes(ordenes);
				}
				else{
					JSONObject warning=new JSONObject();
					warning.put("atributo", "vacio");
					arrT.put(warning);
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return arrT.toString();
		
	}
	
	/**
	 * Metodo que indica el fin de una orden
	 * @param nombre nombre de usuario relacionada a una orden
	 */
	@POST
	@Path("/orden/fin")
	@Consumes(MediaType.APPLICATION_JSON)
	public void terminarOrden(String nombre){
		NodoDoble<Integer,ListaDoble<String,String>> temp=progresos.head;
		char[]minus=nombre.toCharArray();
		while(temp!=null){
			for(NodoDoble<String,String>punt=temp.valor.head;punt!=null;punt=punt.next){
				char[]mayus=punt.llave.toCharArray();
				int max;
				int cont=0;
				if(minus.length<mayus.length){
					max=minus.length;
				}else{
					max=mayus.length;
				}
				for(int r=0;r<max;r++){
					if(minus[r]==mayus[r]){
						cont++;
					}
				}
				if(cont==nombre.length()){
					if(punt==temp.valor.head){
						temp.valor.deleteFirst();
					}else if(punt==temp.valor.tail){
						temp.valor.deleteLast();
					}else{
						temp.valor.delete(punt);
					}
					if(temp.valor.head==null && temp.valor.size==0){
						temp.valor.addFirst("a", "");
					}
					break;
				}
			}
			
			temp=temp.next;
		}
		base.escrituraXMLprogreso(progresos);
	}
	
	/**
	 * Metodo que agrega un elemento al menu
	 * @param algo elemento que se desea almacenar
	 */
	@POST
	@Path("/menu/agregar")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregarMenu(String algo){
		try {
			JSONArray arr=new JSONArray(algo);
			int max=arr.length();
			int min=0;
			while(min<max){
				JSONObject objeto=arr.getJSONObject(min);
				agregarAlMenu(objeto);
				min++;
			}
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que almacena un elemento en el inventario
	 * @param name elemento que se desea almacenar
	 */
	@POST
	@Path("/inventario/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregarAlInv(String name){
		try {
			JSONObject objeto=new JSONObject(name);
			inventario.agregarIngrediente(objeto.getString("categoria"), objeto.getString("nombre"));
			escrituraXMLInventario();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que retorna el menu
	 * @return JSONArray que contiene la informacion del menu
	 */
	@GET
	@Path("/menu")
	@Produces(MediaType.TEXT_PLAIN)
	public String enviar(){
		return menu.parseJSONArray().toString();
	}
	
	/**
	 * Metodo que envia la informacion de ensaladas almacenada en el menu
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/menu/ensaladas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarEnsaladas(){
		return menu.parseEspecifico(1).toString();
	}
	
	/**
	 * Metodo que envia la informacion de sopas almacenada en el menu
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/menu/sopas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarSopas(){
		return menu.parseEspecifico(2).toString();
	}
	
	/**
	 * Metodo que envia la informacion de platos fuertes almacenada en el menu
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/menu/platos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarPlatos(){
		return menu.parseEspecifico(3).toString();
	}
	
	/**
	 * Metodo que envia la informacion de bebidas almacenada en el menu
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/menu/bebidas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarBebidas(){
		return menu.parseEspecifico(4).toString();
	}
	
	/**
	 * Metodo que envia la informacion de postres almacenada en el menu
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/menu/postres")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarPostres(){
		return menu.parseEspecifico(5).toString();
	}
	
	/**
	 * Metodo que envia la informacion de frutas almacenada en el inventario
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/inventario/frutas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarFrutas(){
		return inventario.parseEspecifico(1).toString();
	}
	
	/**
	 * Metodo que envia la informacion de granos almacenada en el inventario
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/inventario/granos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarGranos(){
		return inventario.parseEspecifico(2).toString();
	}
	
	/**
	 * Metodo que envia la informacion de carnes almacenada en el inventario
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/inventario/carnes")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarCarnes(){
		return inventario.parseEspecifico(3).toString();
	}
	
	/**
	 * Metodo que envia la informacion de lacteos almacenada en el inventario
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/inventario/lacteos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarLacteos(){
		return inventario.parseEspecifico(4).toString();
	}
	
	/**
	 * Metodo que envia la informacion de vegetales almacenada en el inventario
	 * @return JSONArray con la informacion requerida
	 */
	@GET
	@Path("/inventario/vegetales")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarVegetales(){
		return inventario.parseEspecifico(5).toString();
	}
	
	
	
	

}
