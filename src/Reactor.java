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

public class Reactor{

	public Connection conn = null;
	public ArrayList<String> aquery = new ArrayList<String>();
	public ArrayList<JSONObject> resultset  = new ArrayList<JSONObject>();
	public ArrayList<String> avar = new ArrayList<String>();
	public JSONObject jsonobj =new JSONObject();


	Reactor(ArrayList<String> aquery,ArrayList<JSONObject> rs, JSONObject jsonobj, Connection conn,ArrayList<String> avar){
			this.aquery = aquery;
			this.resultset = rs;
			this.conn = conn;
			this.avar = avar;
			this.jsonobj = jsonobj;
	}

	int checktype(String value){
		char c = value.charAt(0);
        int ascii = c;

      	if ((ascii >=97 && ascii<=122 ) || (ascii >=65 &&  ascii <= 90 )){

        	return 0;
      	}

      	return 1;
	}
	JSONObject toJsonObject(String s){

		String[] arr = s.split(";");
		JSONObject javar = new JSONObject();
		for (int i=0;i<arr.length;i++){
			javar.put(arr[i],null);

		}
		return javar;

	}

	 void executeAction(){
		for(int i=0;i<aquery.size();i++){

			String sql = aquery.get(i);
			String[] variables = avar.get(i).split(",");

			for( int k=0;k<resultset.size();k++){
						try{
			            PreparedStatement stmt = null;
			            stmt = conn.prepareStatement(sql);

			            for(int j=0;j<variables.length;j++){

											System.out.println("Variable Name");
											System.out.println(variables[j]);

											if(variables[j].indexOf(';') >= 0){  //json string

													JSONObject javar = new JSONObject();
													javar = toJsonObject(variables[j]);

													for(Iterator iterator = javar.keySet().iterator(); iterator.hasNext();) {

															System.out.println("JSON Variable Name");
															String key = (String) iterator.next();
															System.out.println(key);
//inside resultset....................
																	JSONObject robj = resultset.get(k);
																	Object ovalue =robj.get(key);
																	 Object ovalue1  =  jsonobj.get(key);

																	if(ovalue!=null){
																			System.out.println("robj");

																			if(ovalue instanceof String){
																				String  value =  (String) ovalue;
																				System.out.println(value);
																				javar.put(key,value);
																			 }
																			 else if(ovalue instanceof Integer ){
																				 int value = (int) ovalue;
																				 System.out.println(value);
																				 javar.put(key,value);
																			 }
																			 else{
																				 int value = (int) (long) ovalue;
																				 System.out.println(value);
																				 javar.put(key,value);
																			 }
																	 }
																	 else if(ovalue1!=null){
																		 System.out.println("jsonobj");


																					 if(ovalue1 instanceof String){
																						 String  value =  (String) ovalue1;
																						 System.out.println(value);
																						 javar.put(key,value);
																						}
																						else if(ovalue1 instanceof Integer ){
																							int value = (int) ovalue1;
																							System.out.println(value);
																							javar.put(key,value);
																						}
																						else{
																							int value = (int) (long)ovalue1;
																							System.out.println(value);
																							javar.put(key,value);
																						}
																			}

																}
																	String jsonText = javar.toJSONString();
																	System.out.println(jsonText);
																	stmt.setString(j+1,jsonText);

													 	}  //closing of json string column

			                //if present inside json object
														else{
															// System.out.println("Variable Name");

																JSONObject robj = resultset.get(k);
																String key = variables[j];
																Object ovalue =robj.get(key);
															 	Object ovalue1  =  jsonobj.get(key);

															if(ovalue!=null){
																// System.out.println("Variable Name");


																	if(ovalue instanceof String){
																		String  value =  (String) ovalue;
																		System.out.println(value);
																		 stmt.setString(j+1,value);
																	 }
																	 else if(ovalue instanceof Integer ){
																		 // System.out.println("Variable Name");

																		 int value = (int) ovalue;
																		 System.out.println(value);
																		  stmt.setInt(j+1,value);
																	 }
																	 else{
																		 int value = (int) (long) ovalue;
																		 System.out.println(value);
																		  stmt.setInt(j+1,value);
																	 }
															 }
															 else if(ovalue1!=null){
																 			// System.out.println("Variable Name");

																			 if(ovalue1 instanceof String){
																				 String  value =  (String) ovalue1;
																				 System.out.println(value);
																				 stmt.setString(j+1,value);
																				}
																				else if(ovalue1 instanceof Integer ){


																					int value = (int) ovalue1;
																					System.out.println(value);
																					stmt.setInt(j+1,value);
																				}
																				else{
																					int value = (int) (long) ovalue1;
																					System.out.println(value);
																					stmt.setInt(j+1,value);
																				}
																}
														}

			            			}
			            stmt.executeUpdate();

			          }catch(Exception e){
			            e.printStackTrace();
			          }
							}
					}
		}

	}
