package org.angryautomata.xml;

import java.io.File;
//import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLReader {

	/*Je suis seulement en train d'essayer utliser EGIT en Eclipse......pas cool*/
	   public static void main(String arge[]){

	   // long lasting =System.currentTimeMillis();

	    try{

	    File f=new File("data.xml");

	    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();

	    DocumentBuilder builder=factory.newDocumentBuilder();

	    Document doc = builder.parse(f);
	    
	    /*//get root element 
         Element rootElement = document.getDocumentElement(); 

         //traverse child elements 
         NodeList nodes = rootElement.getChildNodes(); 
         for (int i=0; i < nodes.getLength(); i++) 
         { 
            Node node = nodes.item(i); 
            if (node.getNodeType() == Node.ELEMENT_NODE) {   
               Element child = (Element) node; 
               //process child element 
            } 
         } * */
	    
	    NodeList list = doc.getElementsByTagName("disk");  
        
        //ici, on attend des Attributes dans AUTOMATE.遍历该集合，显示结合中的元素及其子元素的名字  
        for(int i = 0; i< list.getLength() ; i ++){  
            Element element = (Element)list.item(i);  
            String name=element.getAttribute("name");  
            String condition=element.getElementsByTagName("condition").item(0).getFirstChild().getNodeValue();  
            String action=element.getElementsByTagName("action").item(0).getFirstChild().getNodeValue();  
            String transition=element.getElementsByTagName("transition").item(0).getFirstChild().getNodeValue();  
        }
	    }catch(Exception e){

	    e.printStackTrace();

	    }
	   }
	
}
