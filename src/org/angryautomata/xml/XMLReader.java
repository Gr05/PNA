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

			File f = new File("essai.xml");

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				DocumentBuilder builder = factory.newDocumentBuilder();

				Document doc = builder.parse(f);

		    /*
	        
	         for (int i=0; i < nodes.getLength(); i++) 
	         { 
	            Node node = nodes.item(i); 
	            if (node.getNodeType() == Node.ELEMENT_NODE) {   
	               Element child = (Element) node; 
	               //process child element 
	            } 
	         } * */
	                        //NodeList sym = doc.getElementsByTagName("nb_symbole_max");/////////////////////////
				
				//get root element 
		         Element rootElement = doc.getDocumentElement(); 
		         //traverse child elements 
		         NodeList nodes = rootElement.getChildNodes(); 
		         Node nbauto = nodes.item(0);
		         Node maxetat = nodes.item(1);
		         Node maxsym = nodes.item(2);
		         int nb_automate = Integer.valueOf(nbauto.getNodeValue());
		         int nb_etat_max = Integer.valueOf(maxetat.getNodeValue());
		         int nb_symbole_max = Integer.valueOf(maxsym.getNodeValue());
				NodeList list = doc.getElementsByTagName("automate");

				//ici, on attend des Attributes dans AUTOMATE.遍历该集合，显示结合中的元素及其子元素的名字
				for(int i = 0; i < list.getLength(); i++)
				{
					Element element = (Element) list.item(i);
					int autono = Integer.valueOf(element.getAttribute("numero"));
					String joueur = element.getElementsByTagName("nom").item(0).getFirstChild().getNodeValue();
					int etat = Integer.valueOf(element.getElementsByTagName("nb_etat").item(0).getFirstChild().getNodeValue());
					int[][] transition = new int[etat*nb_symbole_max][4];
					//String transition = element.getElementsByTagName("transition").item(0).getFirstChild().getNodeValue();
					NodeList transitionList = element.getElementsByTagName("transition");  
	                                //if (transitionList == null) continue;
	                                for (int j = 0; j < transitionList.getLength(); j++) {  
	                                	transition[j] = transitionList.item(j);////////////////////////////??????!!!
	                                }  
				}
		}
		catch(Exception e)
		{

			e.printStackTrace();

		}
	}

}
