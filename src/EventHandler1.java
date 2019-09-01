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

//import src.Parser.*;

//class get_event extends Threads{
//	public Connection conn = null;
//	public Document doc = null;
//
//	get_event(Connection conn, Document doc){
//		this.conn = conn;
//		this.doc =doc;
//
//
//	}
//	public void run() {
//
//	}
//}


public class EventHandler1 extends Thread{
	public static int i=0;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/RDBS";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "123456";
   public Connection conn = null;
	public Document doc = null;
  public static int flag=0;
   public EventHandler1(Connection conn,Document doc) {
	   this.conn = conn;
		this.doc =doc;
   }

  public static int get_eventid(JSONObject jsonobj){

        // String  event_id = (String) jsonobj.get("event_id");
        int  event_id = (int)(long) jsonobj.get("event_id");

        return event_id;
    }



    public static int checktype(String value){
    char c = value.charAt(0);
        int ascii = c;

        if ((ascii >=97 && ascii<=122 ) || (ascii >=65 &&  ascii <= 90 )){

          return 0;
        }

        return 1;
  }
    public static ArrayList check_condition(ArrayList<String> cvar,ArrayList<String> cquery,JSONObject jsonobj,Connection conn){
       ArrayList <ResultSet> resultset = new ArrayList<ResultSet>();

     for(int i=0;i<cquery.size();i++){

        String sql = cquery.get(i);
        String[] variables = cvar.get(i).split(",");
        try{
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);
            for(int j=0;j<variables.length;j++){
                  // String value = (String) jsonobj.get(variables[i]);
                  Object ovalue = jsonobj.get(variables[i]);
                  if(ovalue!=null){
                    if(ovalue instanceof String){
                      String value = (String) ovalue;
                      System.out.println(value);

                        stmt.setString(j+1,value);

                    }
                    else if(ovalue instanceof Integer || ovalue instanceof Long){
                      int value = (int)(long) ovalue;
                      System.out.println(value);

                      stmt.setInt(j+1,value);
                    }
                  }

              }
            ResultSet rs = stmt.executeQuery();
            if (!rs.next() ) {
              return resultset;
            }
            else{
              rs.beforeFirst();
              resultset.add(rs);
            }
          }catch(Exception e){
        	  System.out.println("tyrtsad"+e);
          }
      }

      return resultset;

    }

    public static String get_event_name(Connection conn){
      String event_name = "";
      int[] eventid = {3,2};

      try{
      PreparedStatement stmt = null;
      String sql = "select event_name from event_mapping where event_id =?";
      stmt = conn.prepareStatement(sql);
      if(flag==0){
        stmt.setInt(1,eventid[0]);
        flag =1;
      }
      else{
        stmt.setInt(1,eventid[1]);
        flag=0;

      }

    //

      ResultSet rs = stmt.executeQuery();
      while(rs.next()){
        event_name = rs.getString("event_name");
      }
        System.out.println(event_name);

    }catch(Exception e){e.printStackTrace();}
        return event_name;

    }


    public  void run(){
    	while(true) {
    		try {

      Thread.sleep(10000);



     JSONObject jsonobj = new JSONObject();
      String event_name = get_event_name(conn);
      Parser parser = new Parser(3,conn,doc);
      parser.parse_xml(event_name);

      ArrayList<ResultSet> result =check_condition(parser.cvar_s ,parser.cquery_s,jsonobj,conn);
      System.out.println("Resultset Array size: ");

      System.out.println(result.size());
      ArrayList <JSONObject> resultset = new  ArrayList<JSONObject>();

      for( int k =0;k<result.size();k++){

          try{
            ResultSet r =  result.get(k);
            ResultSetMetaData meta = r.getMetaData();
            while(r.next()){
              JSONObject j  = new  JSONObject();

              for( int c =1;c<=meta.getColumnCount();c++){
              // System.out.println(r.getString("doj"));
                  int type = meta.getColumnType(c);
                  String key =meta.getColumnName(c);
        //
                  if( type==Types.VARCHAR || type==Types.CHAR){
                    System.out.println(r.getString(key));


                    j.put(key,r.getString(key));
                  }
                  else if( type==Types.INTEGER){
                    System.out.println(r.getInt(key));

                    j.put(key,r.getInt(key));
                  }
                  else{
                    j.put(key,r.getString(key));
                  }
                }
                resultset.add(j);
                String jsonText = j.toJSONString();
                System.out.println(jsonText);
            }


          }
          catch (Exception e){
        	  System.out.println("sdfhfhad"+e);
          }
      }


      if(result.size()==parser.cquery_s.size()){
        System.out.println("Conditions satisfied!!");

          Reactor reactor = new Reactor(parser.aquery_s,resultset,jsonobj,conn,parser.avar_s);
          reactor.executeAction();
        }
      else {
          System.out.println("Conditions failed!!");
          Reactor reactor = new Reactor(parser.faquery_s,resultset,jsonobj,conn,parser.favar_s);

         reactor.executeAction();
        }
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}


   }

  public static Document read_file(){

      Document doc =null;

      try{
          File fXmlFile = new File("/home/swapnil/Desktop/sem6/Data_Modelling/RDBS/xml/bus_reservation.xml");
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          doc = dBuilder.parse(fXmlFile);
        }catch(Exception e) {
          e.printStackTrace();
        }

      return doc;
  }




}
