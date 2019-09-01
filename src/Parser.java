import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
//import java.text.SimpleDateFormat;
import org.w3c.dom.Node;
import java.sql.*;
import java.lang.*;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;
//import java.sql.Timestamp;
//import java.sql.Date;
//import java.txt.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Parser{

	Connection conn = null;
	Document doc = null;
	int event_id;
	public	ArrayList <String> cvar_s = new  ArrayList<String>();
	public    ArrayList <String> cquery_s = new ArrayList<String>();
	public    ArrayList <String> faquery_s = new  ArrayList<String>();
	public     ArrayList <String> aquery_s = new  ArrayList<String>();
	public   ArrayList<String> avar_s = new ArrayList<String>();
	public   ArrayList<String> favar_s = new ArrayList<String>();


    Parser (int eid,Connection c,Document d){
    	event_id = eid;
    	conn = c;
    	doc=d;
    }


	void parse_xml(String event_name){

	try{
		doc.getDocumentElement().normalize();
	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			//String attributeValue = ((Element)yourNode).getAttribute("yourAttribute");

	    // NodeList nList = doc.getElementsByTagName("add_to_wishlist");
			// NodeList nList = doc.getElementsByTagName("recommendation");
			// NodeList nList = doc.getElementsByTagName("bus_insert");
			// NodeList nList = doc.getElementsByTagName("apply_for_ticket");
			NodeList nList = doc.getElementsByTagName(event_name);







	    for (int temp = 0; temp < nList.getLength(); temp++) {

	        Node nNode = nList.item(temp);

	        System.out.println("\nCurrent Element :" + nNode.getNodeName());

	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {


	            // Conditions -------------------------------------------------------------------------------------------------------

	            Element eElement = (Element) nNode;
	            Node condition =eElement.getElementsByTagName("condition").item(0);
	            Element condition_e = (Element) condition;


	            // conditions variables------------------------------------------------------------------
              System.out.println("condition variables\n");
              NodeList cvar_list = condition_e.getElementsByTagName("cvar");
	            cvar_s = new  ArrayList<String>(cvar_list.getLength());
	            for(int cv_item = 0; cv_item<cvar_list.getLength();cv_item++){

	                Node cvar = cvar_list.item(cv_item);
	                Element cvar_e = (Element) cvar;
	                cvar_s.add(cvar_e.getTextContent());
	                System.out.println(cvar_e.getTextContent());
	            }
	             // condition queries------------------------------------------------------------------
              System.out.println("condition queries\n");
              NodeList cquery_list = condition_e.getElementsByTagName("cquery");
	            cquery_s = new  ArrayList<String>(cquery_list.getLength());

	            for(int cq_item = 0; cq_item<cquery_list.getLength();cq_item++){

	                Node cquery = cquery_list.item(cq_item);
	                Element cquery_e = (Element) cquery;
	                cquery_s.add(cquery_e.getTextContent());
	            }

	              Iterator itr=cquery_s.iterator();
	                while(itr.hasNext()){
	               System.out.println(itr.next());
	              }

	            // Action ------------------------------------------------------------------

	              Node action =eElement.getElementsByTagName("action").item(0);
	              Element action_e = (Element) action;

	            // action variables------------------------------------------------------------------
                System.out.println("action  variables\n");

	              NodeList avar_list = action_e.getElementsByTagName("avar");
	            	avar_s = new  ArrayList<String>(avar_list.getLength());
	            	for(int av_item = 0; av_item<avar_list.getLength();av_item++){

	                Node avar = avar_list.item(av_item);
	                Element avar_e = (Element) avar;
	                avar_s.add(avar_e.getTextContent());
	                System.out.println(avar_e.getTextContent());
	            }
	           // action queries------------------------------------------------------------------
              System.out.println("action queries\n");
	            NodeList aquery_list = action_e.getElementsByTagName("aquery");

	            aquery_s = new  ArrayList<String>(aquery_list.getLength());
	            for(int aq_item = 0; aq_item <aquery_list.getLength();aq_item++){
	                Node aquery = aquery_list.item(aq_item);
	                Element aquery_e = (Element)aquery;

	                 aquery_s.add(aquery_e.getTextContent());
	              System.out.println(aquery_e.getTextContent());
	            }
	            // fail action variables------------------------------------------------------------------
                System.out.println("fail action variables\n");
	              NodeList favar_list = action_e.getElementsByTagName("favar");
	            	favar_s = new  ArrayList<String>(favar_list.getLength());
	            	for(int fav_item = 0; fav_item<favar_list.getLength();fav_item++){

	                Node favar = favar_list.item(fav_item);
	                Element favar_e = (Element) favar;
	                favar_s.add(favar_e.getTextContent());
	                System.out.println(favar_e.getTextContent());
	            }



	          // fail  action queries------------------------------------------------------------------
              System.out.println("fail action queries\n");
	            NodeList faquery_list = action_e.getElementsByTagName("faquery");
	            faquery_s = new  ArrayList<String>(faquery_list.getLength());
	            for(int faq_item = 0; faq_item<faquery_list.getLength();faq_item++){
	                Node faquery = faquery_list.item(faq_item);
	                Element faquery_e = (Element)faquery;
	                 faquery_s.add(faquery_e.getTextContent());
	              System.out.println(faquery_e.getTextContent());
	            }



	            }// end of if
	          }  ///end of for loop




	   }catch(Exception e)
	   {
	    e.printStackTrace();
	   }

  }

}
