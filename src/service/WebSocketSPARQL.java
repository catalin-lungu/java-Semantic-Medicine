package service;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sedic.Tool;


@ServerEndpoint("/sparql")
public class WebSocketSPARQL {

	
	@OnMessage
	  public void onMessage(String message, Session session) throws IOException {
		
		String format = "";
		String query = "";
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(message);
			JSONObject jsonObject = (JSONObject) object;
			format = (String) jsonObject.get("format");
			query = (String) jsonObject.get("query");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.getBasicRemote().sendText(Tool.execQuery(query,format));
	    
	  }
	  
	  @OnOpen
	  public void onOpen (Session session) {
	   
	  }

	  @OnClose
	  public void onClose (Session session) {
	  
	  }
}
