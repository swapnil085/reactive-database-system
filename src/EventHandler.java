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


public class EventHandler extends Thread{
	public static int i=0;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/RDBS";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "123456";
   public Connection conn = null;
	public Document doc = null;
   public EventHandler(Connection conn,Document doc) {
	   this.conn = conn;
		this.doc =doc;
   }

  public static int get_eventid(JSONObject jsonobj){

        // String  event_id = (String) jsonobj.get("event_id");
        int  event_id = (int)(long) jsonobj.get("event_id");

        return event_id;
    }


   public static JSONObject call_event(Connection conn){

    String jsonstring=null;
    JSONObject j = new JSONObject();
    int eid=0;

      try{

        PreparedStatement stmt = null;
        String sql = "select e_values,eid from event where status=? order by time_stamp desc limit 1";
        // String sql = "select e_values from event_table";

        stmt = conn.prepareStatement(sql);
        stmt.setString(1,"WAITING");
        ResultSet rs = stmt.executeQuery();

			System.out.println("in call event");
        // int i=0
        while(rs.next()){
          jsonstring = rs.getString("e_values");
									System.out.println(eid);
          eid =  rs.getInt("eid");
					System.out.print("eid:");
					System.out.println(eid);

        }
        if(jsonstring !=null) {

      	   j = to_jsonobject(jsonstring);
      	  j.put("e_id",eid);

        }


        }catch(Exception e){
        	System.out.println("sfjfjghjgtrad"+e);
        }

    return j;

   }
   public void update_event(Connection conn,int eventid){

	    String jsonstring=null;

	      try{

	        PreparedStatement stmt = null;
	        String sql = "update  event set status=? where eid =?";
	        // String sql = "select e_values from event_table";

	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1,"PROCESSED");
	        stmt.setInt(2,eventid);
	         stmt.executeUpdate();




	        }catch(Exception e){
	        	e.printStackTrace();
	        }


	   }




   public static JSONObject to_jsonobject(String jsonstring){
      JSONObject newJObject = null;
      JSONParser parser = new JSONParser();
      try {
          newJObject = (JSONObject) parser.parse(jsonstring);
      } catch (Exception e) {
    	  e.printStackTrace();
      }

      System.out.println(newJObject.get("event_id"));
      // System.out.println(newJObject.get("destination"));
      // System.out.println(newJObject.get("event_id"));
      // System.out.println(newJObject.get("name"));

      return newJObject;


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

    public static String get_event_name(int event_id,Connection conn){
      String event_name = "";
      try{
      PreparedStatement stmt = null;
      String sql = "select event_name from event_mapping where event_id = ?";
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1,event_id);

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
					Thread.sleep(7000);
      JSONObject jsonobj = call_event(conn);
			String jsontext = jsonobj.toJSONString();
			System.out.println(jsontext);
			if(jsontext.equals("{}")){
				continue ;
			}



//      JSONObject jsonobj = to_jsonobject(jsonstring);
      int event_id  = get_eventid(jsonobj);
      int eid=(int)jsonobj.get("e_id");
      update_event(conn,eid);
      String event_name = get_event_name(event_id,conn);
      Parser parser = new Parser(event_id,conn,doc);
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

  public static void main(String args[]) throws SQLException{
     	try{
        //STEP 2b: Register JDBC driver
     	  Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Document doc =read_file();
        System.out.println("blablabla...");

//        get_event(conn,doc);
        EventHandler get_event= new EventHandler(conn,doc);
				EventHandler1 get_event1= new EventHandler1(conn,doc);
        get_event.start();
				get_event1.start();

     }catch(Exception e){
    	 e.printStackTrace();
     }

  }


}
