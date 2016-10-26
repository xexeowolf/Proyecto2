package com.proyecto2.general.resources;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proyecto2.general.estructuradatos.ColaPrioridad;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;

public class BaseXML {

	public ListaDoble<String,Platillo> basedatosmenu;
	public ListaDoble<String,String> basedatosinv;
	
	public BaseXML() {
		basedatosmenu=new ListaDoble<String,Platillo>();
		basedatosinv=new ListaDoble<String,String>();
	}
	
	
	
	
	public void escrituraXMLOrdenes(ListaDoble<String,ColaPrioridad<String,String>> matrix){
		
		try{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("asignaciones");
		doc.appendChild(rootElement);
		
		NodoDoble<String,ColaPrioridad<String,String>>temp=matrix.head;
		
		while(temp!=null){
			
			Element raiz = doc.createElement(temp.llave);
			rootElement.appendChild(raiz);
			
			Element generico=doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.size)));
			raiz.appendChild(generico);
			
			NodoDoble<String,String>punt=temp.valor.head;
			
			int i=1;
			while(punt!=null){
				
				generico=doc.createElement("orden"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(punt.llave)));
				raiz.appendChild(generico);
				
				generico=doc.createElement("paso"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(punt.valor)));
				raiz.appendChild(generico);
				
				i++;
				punt=punt.next;
			}
			
			
			temp=temp.next;
		}
		
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
	
	public ListaDoble<String,ColaPrioridad<String,String>> cargarMatriz(ListaDoble<String,String>chefs){
		ListaDoble<String,ColaPrioridad<String,String>> matriz=new ListaDoble<String,ColaPrioridad<String,String>>();
		NodoDoble<String,String>temp=chefs.head;
		while(temp!=null){
			matriz.addLast(temp.llave, cargarOrden(new ColaPrioridad<String,String>(),"/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosOrdenes.xml",temp.llave));
			temp=temp.next;
		}
		return matriz;
	}
	
	
	
	public ColaPrioridad<String,String> cargarOrden(ColaPrioridad<String,String> cola,String direccion,String chefs){
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName(chefs);
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
								String nombre;
								String pasos;
								int cont;
								
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("cantidad");	
								cont=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								while(cont!=0){
									elementos = e.getElementsByTagName("orden"+cont);	
									nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
									
									elementos = e.getElementsByTagName("paso"+cont);	
									pasos=elementos.item(0).getChildNodes().item(0).getNodeValue();
								
									cola.addLast(nombre, pasos);
									cont--;
								}
								
							}
						}
					}
			}else{
					System.exit(1);
				}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cola;
	}
	
	
	public ListaDoble<String,String> cargarChefs(ListaDoble<String,String> lista,String direccion){
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
				NodeList informacion = docEle.getElementsByTagName("chef");
				if (informacion != null && informacion.getLength() > 0) {
					for (int i = 0; i < informacion.getLength(); i++) {
						Node nodo = informacion.item(i);
						if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							String nombre;
							Element e = (Element) nodo;
							NodeList elementos = e.getElementsByTagName("nombre");	
							nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
							lista.addLast(nombre, "");
						}
					}
				}
			}
			else{
				System.exit(1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
		
		
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
								//elementos=e.getElementsByTagName("cantidad"+min);
								//cantidad=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								ingredientes.addFirst(llave, 0);
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
	
	public void escrituraXMLMenu(Menu menu){
		  try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("menu");
			doc.appendChild(rootElement);
			
			
			
			NodoDoble<String,Platillo>temp=menu.recetas.head;
			while(temp!=null){
				Element raiz = doc.createElement("platillo");
				rootElement.appendChild(raiz);
				Element generico = doc.createElement("nombre");
				generico.appendChild(doc.createTextNode(temp.valor.nombre));
				raiz.appendChild(generico);
				
				generico=doc.createElement("categoria");
				generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.categoria)));
				raiz.appendChild(generico);
				
				generico=doc.createElement("informacion");
				generico.appendChild(doc.createTextNode(temp.valor.informacion));
				raiz.appendChild(generico);
				
				generico=doc.createElement("precio");
				generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.precio)));
				raiz.appendChild(generico);
				
				generico=doc.createElement("tiempo");
				generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.tiempopreparacion)));
				raiz.appendChild(generico);
				
				generico=doc.createElement("numero");
				generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.ingredientes.size)));
				raiz.appendChild(generico);
				
				int i=1;
				int max=temp.valor.ingredientes.size;
				NodoDoble<String,Integer>punt=temp.valor.ingredientes.head;
				while(i<=max){
					generico=doc.createElement("ingrediente"+i);
					generico.appendChild(doc.createTextNode(punt.llave));
					raiz.appendChild(generico);
					punt=punt.next;
					i++;
				}
				generico=doc.createElement("pasos");
				generico.appendChild(doc.createTextNode(String.valueOf(temp.valor.receta.size)));
				raiz.appendChild(generico);
				i=1;
				max=temp.valor.receta.size;
				NodoDoble<String,String>pto=temp.valor.receta.head;
				while(i<=max){
					generico=doc.createElement(pto.llave);
					generico.appendChild(doc.createTextNode(pto.valor));
					raiz.appendChild(generico);
					pto=pto.next;
					i++;
				}
				
				temp=temp.next;
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml"));

			transformer.transform(source, resulto);


		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}

	
	
	
	public void escrituraXMLInventario(Inventario inventario){
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("inventario");
			doc.appendChild(rootElement);
			
			NodoDoble<String,String>temp=inventario.frutas.head;
			
			
			Element raiz = doc.createElement("categoria");
			rootElement.appendChild(raiz);
			
			Element generico = doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(inventario.frutas.size)));
			raiz.appendChild(generico);
			
			generico=doc.createElement("nombre");
			generico.appendChild(doc.createTextNode("fruta"));
			raiz.appendChild(generico);
			
			
			int i=1;
			int max=inventario.frutas.size;
			while(i<=max){
				generico = doc.createElement("ingrediente"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(temp.llave)));
				raiz.appendChild(generico);	
				i++;
				temp=temp.next;
			}
			
			temp=inventario.granos.head;
			raiz = doc.createElement("categoria");
			rootElement.appendChild(raiz);
			
			generico = doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(inventario.granos.size)));
			raiz.appendChild(generico);
			
			generico=doc.createElement("nombre");
			generico.appendChild(doc.createTextNode("grano"));
			raiz.appendChild(generico);
			
			i=1;
			max=inventario.granos.size;
			while(i<=max){
				generico = doc.createElement("ingrediente"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(temp.llave)));
				raiz.appendChild(generico);	
				i++;
				temp=temp.next;
			}
			
			
			temp=inventario.carnes.head;
			raiz = doc.createElement("categoria");
			rootElement.appendChild(raiz);
			
			generico = doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(inventario.carnes.size)));
			raiz.appendChild(generico);
			
			
			generico=doc.createElement("nombre");
			generico.appendChild(doc.createTextNode("carne"));
			raiz.appendChild(generico);
			
			i=1;
			max=inventario.carnes.size;
			while(i<=max){
				generico = doc.createElement("ingrediente"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(temp.llave)));
				raiz.appendChild(generico);	
				i++;
				temp=temp.next;
			}
			
			temp=inventario.lacteos.head;
			raiz = doc.createElement("categoria");
			rootElement.appendChild(raiz);
			
			generico = doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(inventario.lacteos.size)));
			raiz.appendChild(generico);
		
			generico=doc.createElement("nombre");
			generico.appendChild(doc.createTextNode("lacteo"));
			raiz.appendChild(generico);
			
			i=1;
			max=inventario.lacteos.size;
			while(i<=max){
				generico = doc.createElement("ingrediente"+i);
				generico.appendChild(doc.createTextNode(String.valueOf(temp.llave)));
				raiz.appendChild(generico);	
				i++;
				temp=temp.next;
			}
			
			temp=inventario.vegetales.head;
			raiz = doc.createElement("categoria");
			rootElement.appendChild(raiz);
			
			generico = doc.createElement("cantidad");
			generico.appendChild(doc.createTextNode(String.valueOf(inventario.vegetales.size)));
			raiz.appendChild(generico);
			
			generico=doc.createElement("nombre");
			generico.appendChild(doc.createTextNode("vegetal"));
			raiz.appendChild(generico);
			
			i=1;
			max=inventario.vegetales.size;
			while(i<=max){
				generico = doc.createElement("ingrediente"+i);
				generico.appendChild(doc.createTextNode(temp.llave));
				raiz.appendChild(generico);	
				i++;
				temp=temp.next;
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosInventario.xml"));

			transformer.transform(source, resulto);
		}catch(Exception r){
			r.printStackTrace();
		}
		
	}

}
