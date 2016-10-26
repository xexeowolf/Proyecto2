package com.proyecto2.general.services;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.proyecto2.general.busquedaordenamiento.Busqueda;
import com.proyecto2.general.estructuradatos.ColaPrioridad;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;
import com.proyecto2.general.resources.BaseXML;
import com.proyecto2.general.resources.Inventario;
import com.proyecto2.general.resources.Menu;
import com.proyecto2.general.resources.Platillo;




@Path("/cliente")
public class ClienteService {
	
	BaseXML base=new BaseXML();
	ListaDoble<String,String> nombres=base.cargarChefs(new ListaDoble<String,String>(),"/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosChefs.xml" );
	ListaDoble<String,ColaPrioridad<String,String>> ordenes=base.cargarMatriz(nombres);
	Inventario inventario=new Inventario(base.cargarInventario("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml"));
	Menu menu=new Menu(base.cargarMenu("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml"));
	
	@POST
	@Path("/orden")
	@Consumes(MediaType.APPLICATION_JSON)
	public void agregarOrden(String algo){
		
		ListaDoble<String,String>orden=new ListaDoble<String,String>();
		try {
			JSONArray arr=new JSONArray(algo);
			int max=arr.length();
			int min=0;
			while(min<max){
				JSONObject objeto=arr.getJSONObject(min);
				String msj=objeto.getString("nombre");
				orden.addFirst(msj, "");
				min++;
			}
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
				while(puntR!=null){
					pasos=pasos+"jk"+puntR.llave+": "+puntR.valor;
					puntR=puntR.next;
				}
				
				ordenes.head.valor.addFirst(nomLlave, pasos);}
				temp=temp.next;
			}
			base.escrituraXMLOrdenes(ordenes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void escrituraXMLMenu(String nom){
		  try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("bigorder");
			doc.appendChild(rootElement);
			
			Element raiz = doc.createElement("orden");
			rootElement.appendChild(raiz);
			Element generico = doc.createElement("nombre");
			generico.appendChild(doc.createTextNode(nom));
			raiz.appendChild(generico);
				
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosOrdenes.xml"));

			transformer.transform(source, resulto);


		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	

}
