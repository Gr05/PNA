package org.angryautomata.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//import java.util.*;

public class XMLReader
{

	/*Je suis seulement en train d'essayer utliser EGIT en Eclipse......pas cool*/
	public static void main(String arge[])
	{

		// long lasting =System.currentTimeMillis();

		try
		{

			File f = new File("data.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

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
                        int sym = doc.getElementsByTagName("nb_symbole_max");
			NodeList list = doc.getElementsByTagName("automate");

			//ici, on attend des Attributes dans AUTOMATE.遍历该集合，显示结合中的元素及其子元素的名字
			for(int i = 0; i < list.getLength(); i++)
			{
				Element element = (Element) list.item(i);
				int autono = element.getAttribute("numero");
				String joueur = element.getElementsByTagName("nom").item(0).getFirstChild().getNodeValue();
				int etat = element.getElementsByTagName("nb_etat").item(0).getFirstChild().getNodeValue();
				int[etat*sym][4] transition = null;
				//String transition = element.getElementsByTagName("transition").item(0).getFirstChild().getNodeValue();
				NodeList transitionList = element.getElementsByTagName("transition");  
                                //if (transitionList == null) continue;
                                for (int j = 0; j < transitionList.getLength(); j++) {  
                                	transition[j] = transitionList.item(j);
                                }  
			}
		}
		catch(Exception e)
		{

			e.printStackTrace();

		}
	}

}
