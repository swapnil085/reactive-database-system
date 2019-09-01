
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
//import org.json.simple.JSONException;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
//import src.Parser.*;

public class json_test {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/RDBS";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "123456";
	public static void main(String[] args) {
		try{
Connection conn = null;
 Class.forName("com.mysql.jdbc.Driver");

//       //STEP 3: Open a connection
//       System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	//	String value = "{\"id\":null,\"dynamic_fare\":null,\"source\":null,\"detsination\":null,\"doj\":null\"}";
    // String value = '"id":null,"dynamic_fare":null,"source":null,"detsination":null,"doj":null"';

//String jsonStr = "[{\"No\":\"1\",\"Name\":\"ABC\"},{\"No\":\"2\",\"Name\":\"PQR\"},{\"No\":\"3\",\"Name\":\"XYZ\"}]";

   // JSONArray array = new JSONArray(jsonStr);
//JSONObject jsonObject = new JSONParser().parse(value).getAsJsonObject();

   //  for(int i=0; i<array.size(); i++){
   //      JSONObject jsonObj  = array.getJSONObject(i);
   //      System.out.println(jsonObj.get("No"));
   //      System.out.println(jsonObj.get("Name"));
   //  }

   // JSONParser parser = new JSONParser();
   // JSONObject obj = new JSONObject();
   //  obj = (JSONObject)parser.parse(value);
		PreparedStatement stmt = null;
        JSONObject jObject = new JSONObject();

    jObject.put("event_id", 1);
		// jObject.put("bid", 4);
    jObject.put("username", "swapnil");
		jObject.put("destination", "bangalore");
		jObject.put("source", "chennai");


		// jObject.put("pnr", "fgh234");


  //   jObject.put("name", "swapnil");
  //   //convert from JSONObject to JSON string
    String jsonText = jObject.toJSONString();
    System.out.println(jsonText);

    // JsonParser parser = new JsonParser();
    // JsonObject o = parser.parse("{\"a\": \"A\"}").getAsJsonObject();

//JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
// String s = "id-dynamic_fare-source-detsination-doj";
// String[] arr = s.split("-");
//     JSONObject javar = new JSONObject();
//     for (int i=0;i<arr.length;i++){
//       javar.put(arr[i],null);
//       System.out.println(arr[i]);

//     }
//     String jsonText = javar.toJSONString();
//     System.out.println(jsonText);



// event_id,bid,username,dynamic_fare,pnr,doj,source,destination-

    //System.out.println(value);
  //   //stmt = conn.createStatement();
    String sql = "insert into event(e_values) values (?)";
       stmt = conn.prepareStatement(sql);
       stmt.setString(1,jsonText);
       stmt.executeUpdate();
     }
     catch(Exception e){
     e.printStackTrace();
     }


	}
}
