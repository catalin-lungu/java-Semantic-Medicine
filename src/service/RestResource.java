package service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import sedic.Tool;

@Path("/")
public class RestResource {

	ArrayList<String> formatsArrayList = new ArrayList<String>(
			Arrays.asList("xml","text","json","csv","tsv","rdf","n-triple","turtle","ttl","n3"));
	
	// http://localhost:8080/sedic/rest/rdf?input_query=SELECT%20?p%20?o%20WHERE%20{%20%3Chttp://dbpedia.org/resource/Viola_tricolor%3E%20?p%20?o%20.%20}
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{format}")
	public String runQueryPath(@PathParam("format")String format, @QueryParam("input_query") String queryParam){
		if(queryParam == null)
			return "" ;
		String query = queryParam;
		try {
			query = URLDecoder.decode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (format==null) {
			return Tool.execQuery(query);
		} else if (!formatsArrayList.contains(format.toLowerCase())) {
			return "Incorect format!";
		}
		
		return Tool.execQuery(query,format);
	}
	
	//http://localhost:8080/sedic/rest?format=json&input_query=SELECT%20?p%20?o%20WHERE%20{%20%3Chttp://dbpedia.org/resource/Viola_tricolor%3E%20?p%20?o%20.%20}
	//http://localhost:8080/sedic/rest?input_query=SELECT%20?p%20?o%20WHERE%20{%20%3Chttp://dbpedia.org/resource/Viola_tricolor%3E%20?p%20?o%20.%20}
	@GET
	@Path("/rest")
	@Produces(MediaType.APPLICATION_XML)
	public String runQueryParam(@QueryParam(value = "input_query")final String queryParam, @QueryParam("format") final String format){
		if(queryParam == null)
			return "" ;
		String query = queryParam;
		try {
			query = URLDecoder.decode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (format==null) {
			return Tool.execQuery(query);
		} else if (!formatsArrayList.contains(format.toLowerCase())) {
			return "Incorect format!";
		}
		
		return Tool.execQuery(query,format);
	}
	
}
