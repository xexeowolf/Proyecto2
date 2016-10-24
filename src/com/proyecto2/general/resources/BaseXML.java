package com.proyecto2.general.resources;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proyecto2.general.estructuradatos.ListaDoble;

public class BaseXML {

	public ListaDoble<String,Platillo> basedatosmenu;
	public ListaDoble<String,String> basedatosinv;
	
	public BaseXML() {
		basedatosmenu=new ListaDoble<String,Platillo>();
		basedatosinv=new ListaDoble<String,String>();
	}
	public ListaDoble<String,Platillo> cargarMenu(String direccion){
		
		
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
				NodeList informacion = docEle.getElementsByTagName("platillo");
				
				if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
								String nombre;
								ListaDoble<String,Integer> ingredientes=new ListaDoble<String,Integer>();
								ListaDoble<String,String>receta= new ListaDoble<String,String>();
								String info;
								int precio;
								int tiempo;
								int categoria;
								
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("nombre");	
								nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
								
								elementos=e.getElementsByTagName("categoria");
								categoria=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								
								elementos=e.getElementsByTagName("informacion");
								info=elementos.item(0).getChildNodes().item(0).getNodeValue();
								
								elementos=e.getElementsByTagName("precio");
								precio=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								
								elementos=e.getElementsByTagName("tiempo");
								tiempo=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								
								elementos=e.getElementsByTagName("numero");
								int max=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								int min=1;
								while(min<=max){
								String llave;
								int cantidad;
								elementos=e.getElementsByTagName("ingrediente"+min);
								llave=elementos.item(0).getChildNodes().item(0).getNodeValue();
								elementos=e.getElementsByTagName("cantidad"+min);
								cantidad=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								ingredientes.addFirst(llave, cantidad);
								min++;
								}
								
								elementos=e.getElementsByTagName("pasos");
								int pasos=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								min=1;
								while(min<=pasos){
									elementos=e.getElementsByTagName("paso"+min);
									receta.addFirst("paso"+min,elementos.item(0).getChildNodes().item(0).getNodeValue());
									min++;
								}
								Platillo plato=new Platillo(nombre,categoria,ingredientes,receta,info,precio,tiempo);
								basedatosmenu.addFirst(nombre, plato);
							}
						}
						//basedatosmenu.imprimir();
					}
			}else{System.exit(1);}
		}catch(Exception e){e.printStackTrace();}
		return basedatosmenu;
	}
	
	
	public ListaDoble<String,String> cargarInventario(String direccion){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
				NodeList informacion = docEle.getElementsByTagName("categoria");
				
				if (informacion != null && informacion.getLength() > 0) {
					
					for (int i = 0; i < informacion.getLength(); i++) {
						Node nodo = informacion.item(i);
						if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							Element e = (Element) nodo;
							NodeList elementos = e.getElementsByTagName("cantidad");	
							int max=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
							elementos = e.getElementsByTagName("nombre");
							String categoria=elementos.item(0).getChildNodes().item(0).getNodeValue();
							int min=1;
							while(min<=max){
								elementos = e.getElementsByTagName("ingrediente"+min);
								basedatosinv.addFirst(categoria, elementos.item(0).getChildNodes().item(0).getNodeValue());
								min++;
							}
						}
					}
					//basedatosinv.imprimir();
				}
			}else{System.exit(1);}
		}catch(Exception e){e.printStackTrace();}
		return basedatosinv;
	}
	
	public static void main(String[] args){
		BaseXML xml=new BaseXML();
		ListaDoble<String,Platillo> m=xml.cargarMenu("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml");
		ListaDoble<String,String> n=xml.cargarInventario("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml");
	}

}
