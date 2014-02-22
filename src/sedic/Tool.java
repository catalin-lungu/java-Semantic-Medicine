package sedic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

public class Tool {

	private final static String service="http://localhost:3030/dataset/sparql";
	
	public Tool() {
		
	}
		
	public static String execQuery(String queryString) {
		
		return execQuery(queryString, "xml");
	}
	
	public static String execQuery(String queryString , String format) {

		StringBuilder resultStringBuilder = new StringBuilder();
		try {
			
			URL url = new URI("http", "localhost:3030", "/dataset/query", "query="+queryString+"&format="+format, "").toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
	
			if (conn.getResponseCode() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	 
			String output;
			
			while ((output = br.readLine()) != null) {
				resultStringBuilder.append(output +"\n");
			}
	 
			conn.disconnect();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
	 
		  } catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return resultStringBuilder.toString();
	}
	
	
	public static ArrayList<String> execQueryToArrayList(String queryString){
		
		String rez = execQuery(queryString, "csv");
		String[] array = rez.split("\n");
		ArrayList<String> arrayList = new ArrayList<String>();
		for(int i= 1 ;i<array.length;i++){
			arrayList.add(array[i].substring(array[i].lastIndexOf('/')+1));
		}
		
		return arrayList;
	}
	
	public static ArrayList<String> execQueryToArrayListStrings(String queryString){
		
		String rez = execQuery(queryString, "csv");
		String[] array = rez.split("\n");
		ArrayList<String> arrayList = new ArrayList<String>();
		for(int i= 1 ;i<array.length;i++){
			arrayList.add(array[i]);
		}
		
		return arrayList;
	}
}
