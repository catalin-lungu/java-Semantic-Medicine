package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import sedic.Tool;



@ServerEndpoint("/websocket")
public class WebSocket {
	
	  @SuppressWarnings("unchecked")
	  @OnMessage
	  public void onMessage(String message, Session session) throws IOException {
		  
		  String tokens[] = message.split("\n");
		  if(tokens.length<1)
			  return;
		  
		 
		  StringBuffer whereString = new StringBuffer();
		  
		  for (int i=0; i<tokens.length;i++) {
			  whereString.append("{?s <http://sedic.org/hasEffect> <http://sedic.org/effect/"+tokens[i]+"> .} UNION");
			  whereString.append("{?s <http://sedic.org/cure/Effective> <http://sedic.org/MedicalCondition/"+tokens[i]+"> .} UNION");
			  whereString.append("{?s <http://sedic.org/cure/LikelyEffective> <http://sedic.org/MedicalCondition/"+tokens[i]+"> .} ");
			  if(i+1<tokens.length){
				  whereString.append(" UNION ");
			  }
		  }
		  
		  String query="SELECT Distinct ?s WHERE {"+whereString.toString()+"} LIMIT 10 ";
		  
		  JSONObject obj = new JSONObject();
		  JSONArray plants = new JSONArray();
		  obj.put("type", "message");
		  
		  ArrayList<String> listPlants = Tool.execQueryToArrayList(query);
		  
		  // in case that no sure treatment was found
		  String observation="";
		  if(listPlants.size()<1){
			  whereString.delete(0, whereString.length());
			  for (int i=0; i<tokens.length;i++) {
				  whereString.append("{?s <http://sedic.org/cure/PossiblyEffective> <http://sedic.org/MedicalCondition/"+tokens[i]+"> .} ");
				  if(i+1<tokens.length){
					  whereString.append(" UNION ");
				  }
			  }
			  query = "SELECT Distinct ?s WHERE {"+whereString.toString()+"} LIMIT 10 ";
			  listPlants = Tool.execQueryToArrayList(query);
			  observation="<b>IT IS POSSIBLE TO HAVE EFFECT BUT IS NOT CERTAIN</b><br/>";
		  }
		  
		  
		  for (String plantString : listPlants) {
			  JSONObject plantJSON = new JSONObject();
			  plantJSON.put("name", plantString.replaceAll("_", " "));
			  plantJSON.put("img", "<img src=\""+ getImage(plantString) +"\" alt=\""+plantString+"\">");
			  plantJSON.put("otherNames", observation + getOtherNames(plantString));
			  plantJSON.put("use", getUse(plantString));
			  plantJSON.put("caution", getCautions(plantString));
			  plantJSON.put("otherInfo", "");
			  plants.add(plantJSON);
		  }
		  obj.put("plants", plants); 
		  
		  session.getBasicRemote().sendText(obj.toJSONString());
	    
	  }
	  
	  @SuppressWarnings("unchecked")
	  @OnOpen
	  public void onOpen (Session session) throws IOException {
		  
		  String query ="SELECT DISTINCT ?o WHERE { {?s <http://sedic.org/hasEffect> ?o .} "
		  		+ "UNION {?s <http://sedic.org/cure/Effective> ?o. }"
		  		+ "UNION {?s <http://sedic.org/cure/LikelyEffective> ?o. }"
		  		+ "UNION {?s <http://sedic.org/cure/PossiblyEffective> ?o. } } ORDER By ?o ";
		  
		  ArrayList<String> listActions = Tool.execQueryToArrayList(query);
		  
		  JSONObject obj = new JSONObject();
		  JSONArray list = new JSONArray();
		  list.addAll(listActions);
		  
		  String query2 ="SELECT DISTINCT ?o WHERE { ?s <http://sedic.org/notRecommended> ?o .} ORDER By ?o ";
		  ArrayList<String> listNotRecommended = Tool.execQueryToArrayList(query2);
		  JSONArray listNotRecom = new JSONArray();
		  listNotRecom.add(listNotRecommended);
		  
		  obj.put("type", "init");
		  obj.put("effects", list);
		  obj.put("allergens", listNotRecom);
		  
		  session.getBasicRemote().sendText(obj.toJSONString());
		  
	  }

	  @OnClose
	  public void onClose (Session session) {
	  
	  }

	  
	  private String getImage(String plantName){
		  
		  String plantImg = "";
		  File folder = new File("D:\\Dropbox\\workspace\\sedic\\WebContent\\imgs"); 
		  File[] listOfFiles = folder.listFiles(); 
		  if(listOfFiles==null) return "http://localhost:8080/sedic/imgs/notAvailable.jpg";
		  
		  for (int i = 0; i < listOfFiles.length; i++) {
			  plantImg = listOfFiles[i].getName();
			  plantImg = plantImg.replaceAll("-", "");
			  plantImg = plantImg.replaceAll("_", "");
			  plantName = plantName.replaceAll("-", "");
			  plantName = plantName.replaceAll("_", "");
			  if (plantImg.toLowerCase().contains(plantName.toLowerCase())) {
				  plantImg = listOfFiles[i].getName();
				  break;
			  }
			  plantImg="notAvailable.jpg";
		  }
		  
		  return "http://localhost:8080/sedic/imgs/"+plantImg;
	  }

	  private String getOtherNames(String plantName){
		  plantName = plantName.replaceAll("\n", "");
		  plantName = plantName.replaceAll("\r", "");
		  String query="SELECT Distinct ?p WHERE { <http://sedic.org/resource/"+plantName.replaceAll("\n", "")+"> <http://www.w3.org/2000/01/rdf-schema#label> ?p . }";
	      String rez = "<b>Ohter names: </b>" + Tool.execQuery(query, "csv");
	      return rez;
	  }

	  private String getUse(String plantString){
		  plantString = plantString.replaceAll("\n", "");
		  plantString = plantString.replaceAll("\r", "");
		  String query="SELECT Distinct ?o WHERE { { <http://sedic.org/resource/"+plantString+"> <http://sedic.org/cure/Effective> ?o .} UNION  "
		  		        + " { <http://sedic.org/resource/"+plantString+"> <http://sedic.org/cure/LikelyEffective> ?o .} UNION "
		  				//+ " { <http://sedic.org/resource/"+plantString+"> <http://sedic.org/cure/PossiblyEffective> ?o .} UNION "
		  				+ "{ <http://sedic.org/resource/"+plantString+"> <http://sedic.org/hasEffect> ?o .} } LIMIT 20 ";
	      ArrayList<String> list = Tool.execQueryToArrayList(query);
	      String rez = "<b>Usage: </b>"; 
	      for (String string : list) {
	    	  rez+= string +" ,";
		  }
	      return rez;
	  }

	  private String getCautions(String plantString) {
		  plantString = plantString.replaceAll("\n", "");
		  plantString = plantString.replaceAll("\r", "");
		  String query="SELECT Distinct ?o WHERE { { <http://sedic.org/resource/"+plantString+"> <http://sedic.org/Caution> ?o .} } LIMIT 10";
		  ArrayList<String> list = Tool.execQueryToArrayListStrings(query);
		  String rez = "<b>Cautions: </b>";
		  for (String string : list) {
	    	  rez+="<br>"+ string.replaceAll("_", " ");
		  }
		  if(list.size()<1)
			  rez += " Not known at this time ";
		  return rez;
	}
}
