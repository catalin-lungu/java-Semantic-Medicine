package sedic;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.jena.riot.RDFDataMgr;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP ;

public class Test {

	public static void main(String[] args) throws IOException {
				
		Model model ;//= ModelFactory.createDefaultModel();
		/*
		String personURI    = "http://somewhere/JohnSmith";
	    String fullName     = "John Smith";
	    
	    Resource johnSmith = model.createResource(personURI);
	    
	    johnSmith.addProperty(VCARD.FN, fullName);
	    
	    model.write(System.out , "RDF/XML");
	    */
		model =  RDFDataMgr.loadModel("file:///D:/Dropbox/dataExtracted.nt");

		//BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\Dropbox\\dataExtracted.ttl"));
		//model.write(bufferedWriter ,"TURTLE");
		
		String queryString = "Select * WHERE { <http://dbpedia.org/resource/Xanthium_spinosum> ?p ?o . }";
		Query query = QueryFactory.create(queryString);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		String outString;
		StringWriter stringWriter = new StringWriter();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(stream , results);
		outString = stream.toString();
		
		
		
		//results.getResourceModel().write(stringWriter,"N3");
		//outString = stringWriter.toString();
		
		System.out.println(outString);

		System.out.println("Done!");
		
	}
}
