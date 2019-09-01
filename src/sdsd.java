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


public class sdsd {

   //  public static int datediff(String cur_date){
   //      System.out.println("hel");
   //      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
   //   String dateBeforeString = cur_date;
   //   String dateAfterString = "2019-03-30";
   //   float daysBetween=0;

   //   try {
   //         Date dateBefore = myFormat.parse(dateBeforeString);
   //         Date dateAfter = myFormat.parse(dateAfterString);
   //         long difference = dateAfter.getTime() - dateBefore.getTime();
   //          daysBetween = (difference / (1000*60*60*24));

   //         System.out.println("Number of Days between dates: "+daysBetween);
   //   } catch (Exception e) {
   //         e.printStackTrace();
   //   }
   //   return (int) daysBetween;
   // }

   //  public static int thresholdd(String s,int d){
   //      int fare=0;
   //      s=s+"="+"?";
   //      try{
   //          // Class.forName("com.mysql.jdbc.Driver");
   //      // Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RDBS?autoReconnect=true&useSSL=false","root","123456");
   //      PreparedStatement prestmt = null;
   //      prestmt = con.prepareStatement(s);
   //      prestmt.setInt(1,7);
   //      ResultSet rs=prestmt.executeQuery();
   //     // System.out.println("hel");
   //      while(rs.next())
   //      {

   //      fare = rs.getInt("fare");
   //      fare=(int)fare;
   //     if(d < 10){
   //      fare=fare+fare*2;
   //     }

   //      }
   //       con.close();
   //      } catch (Exception e) {
   //          e.printStackTrace();
   //      }

   //  return fare ;

   //  }
//     public static int reactor(String s1,String s2)
//     {   int d=0;
//         try{
//         Class.forName("com.mysql.jdbc.Driver");



//         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RDBS?autoReconnect=true&useSSL=false","root","123456");

//         //Statement stmt=con.createStatement();
// // prepared statement taking from xml concated with with bus id
//         //String doj;
//         PreparedStatement prestmt = null;
//         String sql;
//         sql = s1+"="+"?";

//         prestmt = con.prepareStatement(sql);
//         prestmt.setInt(1,7);
//         ResultSet rs=prestmt.executeQuery();
//        // System.out.println("hel");
//         while(rs.next())
//         {

//         String doj = rs.getString("doj");
//         String [] ex= doj.split(" ");
//         d=datediff(ex[0]);
//        //System.out.println( datediff(ex[0]));
//         //

//         }con.close();

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         System.out.println("dynamic fare:=> " + thresholdd(s2,d));

//     return 1;

// }



  public static void main(String argv[]) {

    try {


    File fXmlFile = new File("/home/swapnil/Desktop/sem6/Data_Modelling/RDBS/bus_reservation.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);

    //optional, but recommended
    //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    doc.getDocumentElement().normalize();

    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

    NodeList nList = doc.getElementsByTagName("apply_for_ticket");

    
    System.out.println("----------------------------");
    ArrayList <String> cvar_s = new  ArrayList<String>();
    ArrayList <String> cquery_s = new ArrayList<String>(); 
    ArrayList <String> faquery_s = new  ArrayList<String>();
    ArrayList <String> aquery_s = new  ArrayList<String>();
    
      //            JSONObject obj = new JSONObject();

      // obj.put("name","foo");
      // obj.put("num",new Integer(100));
      // obj.put("balance",new Double(1000.21));
      // obj.put("is_vip",new Boolean(true));

      // StringWriter out = new StringWriter();
      // obj.writeJSONString(out);
      
      // String jsonText = out.toString();
      // System.out.print(jsonText);
    
    for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        System.out.println("\nCurrent Element :" + nNode.getNodeName());
   
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {


            // Conditions -------------------------------------------------------------------------------------------------------

            Element eElement = (Element) nNode;
            Node condition =eElement.getElementsByTagName("condition").item(0);
            Element condition_e = (Element) condition;
         

            // conditions variables------------------------------------------------------------------
            NodeList cvar_list = condition_e.getElementsByTagName("cvar");
            cvar_s = new  ArrayList<String>(cvar_list.getLength());
            for(int cv_item = 0; cv_item<cvar_list.getLength();cv_item++){

                Node cvar = cvar_list.item(cv_item);
                Element cvar_e = (Element) cvar;
                cvar_s.add(cvar_e.getTextContent());
                // System.out.println(cvar_e.getTextContent());
            }
             // condition queries------------------------------------------------------------------
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

           // action queries------------------------------------------------------------------

            NodeList aquery_list = action_e.getElementsByTagName("aquery");

            aquery_s = new  ArrayList<String>(aquery_list.getLength());
            for(int aq_item = 0; aq_item <aquery_list.getLength();aq_item++){
                Node aquery = aquery_list.item(aq_item);
                Element aquery_e = (Element)aquery;

                 aquery_s.add(aquery_e.getTextContent());
              //System.out.println(aquery_e.getTextContent());
            }


            Iterator itr1=aquery_s.iterator();  
              while(itr1.hasNext()){  
               System.out.println(itr1.next());  
              } 

              //System.out.println(aquery_s.size());

          // fail  action queries------------------------------------------------------------------
            
            NodeList faquery_list = action_e.getElementsByTagName("faquery");
            faquery_s = new  ArrayList<String>(faquery_list.getLength());
            for(int faq_item = 0; faq_item<faquery_list.getLength();faq_item++){
                Node faquery = faquery_list.item(faq_item);
                Element faquery_e = (Element)faquery;
                 faquery_s.add(faquery_e.getTextContent());
              //System.out.println(faquery_e.getTextContent());
            }

            Iterator itr2=faquery_s.iterator();  
              while(itr2.hasNext()){  
               System.out.println(itr2.next());  
              } 


                }
          }
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RDBS?autoReconnect=true&useSSL=false","root","123456");
                  
            //reactor -------------------------------------------------------------------------

        



                  String[] var_values = values.split("_");
                  for( int c=0;c < cquery_s.size();c++){ 
                    String sql=cquery_s.get(c);
                    PreparedStatement prestmt = null;
                    prestmt = con.prepareStatement(sql);
                    String[] cvar = cvar_s.get(c).split(",");
                    for(int i=0;i<cvar.length;i++){
                        System.out.println(cvar[i]);
                    }                    
                     prestmt.setString(1,"swapnil");
                       ResultSet rs = prestmt.executeQuery();
                       while(rs.next()){
                          pass_id = rs.getInt("id");
                          System.out.println("PassId :" + pass_id);

                        }
                         prestmt1.setString(1,"bangalore");
                         prestmt1.setString(2,"chennai");
                         ResultSet rs1 = prestmt1.executeQuery();
                         while(rs1.next()){
                            bus_id = rs1.getInt("id");
                            System.out.println("BusId :" + bus_id);

                          }


                }

                 Random r = new Random();
                 int x= r.nextInt(10000);
                 System.out.println(x);

                 PreparedStatement prestmt2 = null;
                 //PreparedStatement prestmt3 = null;

                 String sql2 = aquery_s.get(0);
                 //String sql3 = faquery_s.get(0);
                 prestmt2 = con.prepareStatement(sql2);
                 //prestmt3 = con.prepareStatement(sql3);

                 prestmt2.setInt(1,x);
                 prestmt2.setInt(2,pass_id);
                 prestmt2.setInt(3,bus_id);
                 prestmt2.setInt(4,200);
                 prestmt2.setString(5,"CONFIRM");

                prestmt2.executeUpdate();
                       while(rs.next()){
                          pass_id = rs.getInt("id");
                          System.out.println("PassId :" + pass_id);

                    }

        
  }catch (Exception e) {
    e.printStackTrace();
    }
  }}
