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

import com.proyecto2.general.busquedaordenamiento.OrdenamientoDLL;
import com.proyecto2.general.estructuradatos.ColaPrioridad;
import com.proyecto2.general.estructuradatos.ListaDoble;
import com.proyecto2.general.estructuradatos.NodoDoble;

/**
 * Clase encargada de escribir informacion de lista enlazadas en archivos XML en un formato especifico
 * @author alfredo
 *
 */
public class BaseXML {

	public ListaDoble<String,Platillo> basedatosmenu;
	public ListaDoble<String,String> basedatosinv;
	
	/**
	 * Constructor de la clase
	 */
	public BaseXML(){
		basedatosmenu=new ListaDoble<String,Platillo>();
		basedatosinv=new ListaDoble<String,String>();
	}
	
	
	/**
	 * Metodo encargado de almacenar en una lista doble los progresos de las ordenes obtenidas
	 * @return lista doble con la informacion requerida
	 */
	public ListaDoble<Integer,ListaDoble<String,String>> cargarProgresos(){
		ListaDoble<Integer,ListaDoble<String,String>> progresos=new ListaDoble<Integer,ListaDoble<String,String>>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/ProgresosOrdenes.xml");
			
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName("orden");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("numero");	
								int cont=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								ListaDoble<String,String> ordenes=new ListaDoble<String,String>();
								elementos = e.getElementsByTagName("pedido");
								String[]todos=elementos.item(0).getChildNodes().item(0).getNodeValue().split("jk");
								for(int k=0;k<todos.length;k++){
									ordenes.addLast(todos[k], "");
								}
								OrdenamientoDLL.InsertionSort(ordenes);
								progresos.addLast(cont, ordenes);
						}
					}
				}
					
			}else{System.exit(1);}		
		} catch (Exception e) {
			e.printStackTrace();
		}
			OrdenamientoDLL.ShellSort(progresos);
			return progresos;
	}
	
	/**
	 * Metodo encargado de almacenar en un archivo XML la informacion de una lista doble
	 * @param progresos lista doble con la informacion que se desea almacenar
	 */
	public void escrituraXMLprogreso(ListaDoble<Integer,ListaDoble<String,String>> progresos){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("mesas");
			doc.appendChild(rootElement);
		
			for(NodoDoble<Integer,ListaDoble<String,String>> puntero=progresos.head;puntero!=null;puntero=puntero.next){
				Element oro = doc.createElement("orden");
				rootElement.appendChild(oro);
				Element Generico=doc.createElement("numero");
				Generico.appendChild(doc.createTextNode(String.valueOf(puntero.llave)));
				oro.appendChild(Generico);
				String todoP=puntero.valor.head.llave;
				for(NodoDoble<String,String> ped=puntero.valor.head.next;ped!=null;ped=ped.next){
					todoP=todoP+"jk"+ped.llave;
				}
				Generico=doc.createElement("pedido");
				Generico.appendChild(doc.createTextNode(todoP));
				oro.appendChild(Generico);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/ProgresosOrdenes.xml"));
			transformer.transform(source, resulto);
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * Metodo que carga en una cola de prioridad la informacion almacenada en un archivo XML de formato especifico.
	 * @param direccion direccion del archivo XML
	 * @return cola de prioridad con la informacion deseada
	 */
	public ColaPrioridad<Integer,String> cargarJerarquia(String direccion){
		ColaPrioridad<Integer,String> cola=new ColaPrioridad<Integer,String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName("oro");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("cantidad");	
								int cont=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								while(cont!=0){
									elementos = e.getElementsByTagName("orden"+cont);
									if(elementos!=null){
									String nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
									cola.addLast(3,nombre);//oro
									cont--;}
								}
							}
						}
					}
					informacion = docEle.getElementsByTagName("plata");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("cantidad");	
								int cont=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								while(cont!=0){
									elementos = e.getElementsByTagName("orden"+cont);
									if(elementos!=null){
									String nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
									cola.addLast(2,nombre);
									cont--;}
								}
							}
						}
					}
					informacion = docEle.getElementsByTagName("bronce");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
							
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("cantidad");	
								int cont=Integer.parseInt(elementos.item(0).getChildNodes().item(0).getNodeValue());
								while(cont!=0){
									elementos = e.getElementsByTagName("orden"+cont);
									if(elementos!=null){
									String nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
									cola.addLast(1,nombre);
									cont--;}
								}
							}
						}
					}
			}else{
					System.exit(1);
				}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cola;
	}
	
	/**
	 * Metodo que escribe en un archivo XML la informacion contenidad en una cola de prioridad
	 * @param cola cola de proridad con la informacion que se desea almacenar
	 */
	public void escrituraXMLJerarquia(ColaPrioridad<Integer,String> cola){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("categorias");
			doc.appendChild(rootElement);
			int contOro=1,contPlata=1,contBronce=1;
			Element oro = doc.createElement("oro");
			rootElement.appendChild(oro);
			Element plata = doc.createElement("plata");
			rootElement.appendChild(plata);
			Element bronce = doc.createElement("bronce");
			rootElement.appendChild(bronce);
			for(NodoDoble<Integer,String> temp=cola.head;temp!=null;temp=temp.next){
				switch(temp.llave){
				case 0:Element genericoW=doc.createElement("nulo");genericoW.appendChild(doc.createTextNode("nulo"));oro.appendChild(genericoW);contOro=contPlata=contBronce=0;break;
				case 3:Element generico=doc.createElement("orden"+contOro);generico.appendChild(doc.createTextNode(String.valueOf(temp.valor)));oro.appendChild(generico);contOro++;break;
				case 2:Element genericoP=doc.createElement("orden"+contPlata);genericoP.appendChild(doc.createTextNode(String.valueOf(temp.valor)));plata.appendChild(genericoP);contPlata++;break;
				case 1:Element genericoB=doc.createElement("orden"+contBronce);genericoB.appendChild(doc.createTextNode(String.valueOf(temp.valor)));bronce.appendChild(genericoB);contBronce++;break;
				}			
			}
			Element Generico=doc.createElement("cantidad");
			Generico.appendChild(doc.createTextNode(String.valueOf(contOro)));
			oro.appendChild(Generico);
			Generico=doc.createElement("cantidad");
			Generico.appendChild(doc.createTextNode(String.valueOf(contPlata)));
			plata.appendChild(Generico);
			Generico=doc.createElement("cantidad");
			Generico.appendChild(doc.createTextNode(String.valueOf(contBronce)));
			bronce.appendChild(Generico);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosCategorias.xml"));
			transformer.transform(source, resulto);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que almacena en una archivo XML la informacion contenida en una matriz
	 * @param matrix matriz con la informacion que se desea almacenar
	 */
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

	/**
	 * Metodo que escribe en un archivo XML la informacion de una lista doble en una formato especifico
	 * @param chefs lista doble con la informacion que se desea almacenar
	 */
	public void escrituraXMLOrdenInicial(ListaDoble<String,String> chefs){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("asignaciones");
			doc.appendChild(rootElement);
			
			
			NodoDoble<String,String> temp=chefs.head;
			while(temp!=null){
				
				Element raiz = doc.createElement(temp.llave);
				rootElement.appendChild(raiz);
				
				Element generico=doc.createElement("cantidad");
				generico.appendChild(doc.createTextNode(String.valueOf(1)));
				raiz.appendChild(generico);
				
				generico=doc.createElement("orden1");
				generico.appendChild(doc.createTextNode("a"));
				raiz.appendChild(generico);
				
				generico=doc.createElement("paso1");
				generico.appendChild(doc.createTextNode("a"));
				raiz.appendChild(generico);
				
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
		
	/**
	 * Metodo que obtiene la informacion de un archivo XML y lo almacena en una matriz
	 * @param chefs matriz en la cual se almacenara la informacion
	 * @return matriz con la informacion deseada.
	 */
	public ListaDoble<String,ColaPrioridad<String,String>> cargarMatriz(ListaDoble<String,String>chefs){
		ListaDoble<String,ColaPrioridad<String,String>> matriz=new ListaDoble<String,ColaPrioridad<String,String>>();
		NodoDoble<String,String>temp=chefs.head;
		while(temp!=null){
			matriz.addLast(temp.llave, cargarOrden(new ColaPrioridad<String,String>(),"/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosOrdenes.xml",temp.llave));
			temp=temp.next;
		}
		return matriz;
	}
		
	/**
	 * Metodo que almacena en una lista enlazada la informacion contenida en una rama de un archivo XML
	 * @param cola lista en la cual se almacenara la informacion
	 * @param direccion direccion del archivo XML
	 * @param chefs nombre de la rama que contiene la informacion deseada
	 * @return lista con la informacion deseada
	 */
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
								int minimal=1;
								while(minimal<=cont){
									elementos = e.getElementsByTagName("orden"+minimal);	
									nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
									
									elementos = e.getElementsByTagName("paso"+minimal);	
									pasos=elementos.item(0).getChildNodes().item(0).getNodeValue();
								
									cola.addLast(nombre, pasos);
									minimal++;
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
	
	/**
	 * Metodo que almacena en una lista la informacion de una archivo XML
	 * @param lista lista enlazada donde se almacenara la informacion deseada
	 * @param direccion direccion del archivo XML
	 * @return lista con la informacion deseada
	 */
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
	
	/**
	 * Metodo que sobreescribe un archivo XML con la informacion contenida en una lista enlazada
	 * @param chefs lista con la informacion que se desea almacenar
	 */
	public void escrituraXMLChefs(ListaDoble<String,String> chefs){
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("chefs");
			doc.appendChild(rootElement);
			
			for(NodoDoble<String,String> head=chefs.head;head!=null;head=head.next){
				Element raiz = doc.createElement("chef");
				rootElement.appendChild(raiz);
				Element generico = doc.createElement("nombre");
				generico.appendChild(doc.createTextNode(head.llave));
				raiz.appendChild(generico);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosChefs.xml"));

			transformer.transform(source, resulto);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	/**Metodo que cargar en una lista la informacion contenida en un archivo XML
	 * @param direccion direccion del archivo XML
	 * @return lista con la informacion deseada
	 */
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
									receta.addLast("paso"+min,elementos.item(0).getChildNodes().item(0).getNodeValue());
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
	
	
	/**Metodo que cargar en una lista la informacion contenida en un archivo XML
	 * @param direccion direccion del archivo XML
	 * @return lista con la informacion deseada
	 */
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
	
	/**Metodo que almacena la informacion contenida en una lista enlazada en una archivo XML
	 * @param menu lista con la informacion que se desea almacenar
	 */
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

	
	/**Metodo que almacena la informacion contenida en una lista enlazada en una archivo XML
	 * @param inventario lista con la informacion que se desea almacenar
	 */
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

	/**Metodo que cargar en una lista la informacion contenida en un archivo XML
	 * @param direccion direccion del archivo XML
	 * @return lista con la informacion deseada
	 */
	public ListaDoble<String,String> cargarChat(String direccion){
		ListaDoble<String,String> mensajes=new ListaDoble<String,String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file=new File(direccion);
			if (file.exists()){
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();
					NodeList informacion = docEle.getElementsByTagName("chat");
					if (informacion != null && informacion.getLength() > 0) {
						for (int i = 0; i < informacion.getLength(); i++) {
							Node nodo = informacion.item(i);
							if (nodo.getNodeType() == Node.ELEMENT_NODE) {
								Element e = (Element) nodo;
								NodeList elementos = e.getElementsByTagName("dialogo");	
								String nombre=elementos.item(0).getChildNodes().item(0).getNodeValue();
								mensajes.addLast(nombre, "");	
								
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
		return mensajes;
	}

	/**Metodo que almacena la informacion contenida en una lista enlazada en una archivo XML
	 * @param chat lista con la informacion que se desea almacenar
	 */
	public void escrituraXMLChat(ListaDoble<String,String> chat){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("mensajes");
			doc.appendChild(rootElement);
			
			Element oro = doc.createElement("chat");
			rootElement.appendChild(oro);
			
			for(NodoDoble<String,String> temp=chat.head;temp!=null;temp=temp.next){
				Element Generico=doc.createElement("dialogo");
				Generico.appendChild(doc.createTextNode(temp.llave));
				oro.appendChild(Generico);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resulto = new StreamResult(new File("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/Mensajes.xml"));
			transformer.transform(source, resulto);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
