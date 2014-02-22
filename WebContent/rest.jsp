<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@include file="WEB-INF/head_links.jsp"%>
<title>Rest service</title>
</head>
<body>
    <%@include file="WEB-INF/menu.jsp"%>
    
    <div class="center_box"
		style="min-height:600px; ">
		&nbsp; <br />
       
        <div style="align: center; background-color: white; opacity:0.8; padding: 10px">
        
		<div>
			<h2>In this page is described how rest service can be used</h2><br>
			<div>A REST service endpoint is available at <a>http://localhost:8080/sedic/rest </a><br/>
			   An request can have one or two parameters as follows:<br/>
			   <ul>
			   		<li>input_query : that is a required parameter containing a SPARQL query </li>
			   		<li>format: that is an optional parameter that specifies the format of the result<br>
			   		     and can be one of the following: text, xml, json, csv or tsv</li> 
			   </ul>
			   Default return type is XML
			   <br><br>
			   
			   <h2>Example of request:</h2><br/>
			   <b>Sent request via a link using a web browser </b> <br/>
			   <a href="http://localhost:8080/sedic/rest?input_query=SELECT%20?p%20?o%20WHERE%20{%20%3Chttp://dbpedia.org/resource/Viola_tricolor%3E%20?p%20?o%20.%20}">
			   http://localhost:8080/sedic/rest?input_query=SELECT ?p ?o <br/> WHERE{ &lt;http://dbpedia.org/resource/Viola_tricolor&gt; ?p ?o .}
			   </a><br>
			   <b>Another example this time specifying the format</b><br>
			   <a href="http://localhost:8080/sedic/rest?format=xml&input_query=Select%20Distinct%20?s%20Where%20{?s%20%3Chttp://sedic.org/cure/PossiblyEffective%3E%20%3Chttp://sedic.org/MedicalCondition/Headaches%3E%20.}">
			   http://localhost:8080/sedic/rest?format=xml&amp;input_query=Select Distinct ?s <br/>Where {?s &lt;http://sedic.org/cure/PossiblyEffective&gt; &lt;http://sedic.org/MedicalCondition/Headaches&gt; .}
			   </a>
			   <br><br>
			   <b>Or service can be called by a web service client using:</b><br>
			   URL: http://localhost:8080/sedic/rest <br>
			   input_query="Select ?o Where {&lt;http://sedic.org/resource/Viola_tricolor&gt; &lt;http://sedic.org/hasEffect&gt; ?o .} Limit 50" <br>
			   format=json
			</div>
			   
		
		
		</div>
		
		
		</div>
		<%@include file="WEB-INF/footer.jsp"%>
	</div>
</body>
</html>