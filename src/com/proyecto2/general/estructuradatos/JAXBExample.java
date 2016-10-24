package com.proyecto2.general.estructuradatos;



import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class JAXBExample {
  
public static void main(){
	try{
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	Document doc = docBuilder.parse("/home/alfredo/Inicio/Documentos/Eclipse_Keppler/Proyecto2/WebContent/WEB-INF/BaseDatosMenu.xml");
	Node earth = doc.getFirstChild();
	NamedNodeMap earthAttributes = earth.getAttributes();
	Attr galaxy = doc.createAttribute("galaxy");
	galaxy.setValue("milky way");
	earthAttributes.setNamedItem(galaxy);

	// nodes
	Node canada = doc.createElement("country");
	canada.setTextContent("ca");
	earth.appendChild(canada);
	
	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	//initialize StreamResult with File object to save to file
	StreamResult result = new StreamResult(new StringWriter());
	DOMSource source = new DOMSource(doc);
	transformer.transform(source, result);

	String xmlString = result.getWriter().toString();
	System.out.println(xmlString);
	}catch(Exception e){
		e.printStackTrace();
	}
}


}