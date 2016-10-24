package com.proyecto2.general.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ibm.json.java.JSONArray;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.resources.BaseXML;
import com.proyecto2.general.resources.Inventario;
import com.proyecto2.general.resources.Menu;
import com.proyecto2.general.resources.Platillo;


@Path("/chef")
public class ChefService {
	
	BaseXML base=new BaseXML();
	Inventario inventario=new Inventario(base.cargarInventario("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml"));
	Menu menu=new Menu(base.cargarMenu("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml"));
	
	public ChefService() {
		 
	}
	
	
	@POST
	@Path("/menu/agregar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregar(String namae){
		try {
			JSONObject objeto=new JSONObject(namae);
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
				receta.addFirst("paso"+cantidad,(String)objeto.get("paso"+pasos));
				pasos--;
			}
			Platillo plato=new Platillo(nombre,categoria,ingredientes,receta,info,precio,tiempo);
			menu.agregarReceta(nombre, plato);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*JSONObject objeto=(JSONObject)arreglo.get(0);
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
			receta.addFirst("paso"+cantidad,(String)objeto.get("paso"+pasos));
			pasos--;
		}
		Platillo plato=new Platillo(nombre,categoria,ingredientes,receta,info,precio,tiempo);
		menu.agregarReceta(nombre, plato);*/
		
		/*ListaDoble<String,Integer>ingred=new ListaDoble<String,Integer>();
		ingred.addFirst("tomate",3);
		ingred.addFirst("aguacate",1);
		ingred.addFirst("tortillas",10);
		Platillo plato=new Platillo("Sopa Algas",2,ingred,new ListaDoble<String,String>(),"50 kcal",13000,30);
		menu.agregarReceta("",plato);*/
	}
	
	@GET
	@Path("/menu/ensaladas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarSopas(){
		return menu.parseEspecifico(1).toString();
	}
	@GET
	@Path("/menu/sopas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarEnsalada(){
		return menu.parseEspecifico(2).toString();
	}
	@GET
	@Path("/menu/platos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarPlatos(){
		return menu.parseEspecifico(3).toString();
	}
	@GET
	@Path("/menu/bebidas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarBebidas(){
		return menu.parseEspecifico(4).toString();
	}
	@GET
	@Path("/menu/postres")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarPostres(){
		return menu.parseEspecifico(5).toString();
	}
	@GET
	@Path("/inventario/frutas")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarFrutas(){
		return inventario.parseEspecifico(1).toString();
	}
	@GET
	@Path("/inventario/granos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarGranos(){
		return inventario.parseEspecifico(2).toString();
	}
	@GET
	@Path("/inventario/carnes")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarCarnes(){
		return inventario.parseEspecifico(3).toString();
	}
	@GET
	@Path("/inventario/lacteos")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarLacteos(){
		return inventario.parseEspecifico(4).toString();
	}
	@GET
	@Path("/inventario/vegetales")
	@Produces(MediaType.APPLICATION_JSON)
	public String enviarVegetales(){
		return inventario.parseEspecifico(5).toString();
	}
	
	
	
	

}
