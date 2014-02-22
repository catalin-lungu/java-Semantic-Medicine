<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



<link rel="stylesheet" type="text/css" href="style/sparqlStyle.css">

<%@include file="WEB-INF/head_links.jsp"%>
<title>SPARQL Endpoint</title>

<script type="text/javascript" src="scripts/util.js"></script>
<script type="text/javascript" src="scripts/json2.js"></script>
<script type="text/javascript">
	var webSocket = new WebSocket('ws://localhost:8080/sedic/sparql');

	webSocket.onerror = function(event) {
		onError(event)
	};

	webSocket.onopen = function(event) {
		onOpen(event)
	};

	webSocket.onmessage = function(event) {
		onMessage(event)
	};

	function onMessage(event) {
		document.getElementById('output').innerHTML = event.data;
	}

	function onOpen(event) {
		document.getElementById('output').innerHTML = '';
	}

	function onError(event) {
		document.getElementById('output').innerHTML = 'Error!';
	}

	function sendQuery(elementId) {
		document.getElementById('output').innerHTML = '';

		var query = document.getElementById('inputQuery').value;
		var select = document.getElementById('select');
		var format = select.options[select.selectedIndex].value;
		var myJSONObject = {
			"format" : format,
			"query" : query
		};

		webSocket.send(JSON.stringify(myJSONObject));
	}

	function clearContent() {
		document.getElementById('inputQuery').innerHTML = '';
		document.getElementById('output').innerHTML = '';
	}

	function loadExample(exampleId) {
		document.getElementById('inputQuery').innerHTML = document
				.getElementById(exampleId).innerHTML;
		document.getElementById('output').innerHTML = '';
	}
</script>
</head>
<body>

	<%@include file="WEB-INF/menu.jsp"%>


	<div class="center_box"
		style="min-height:600px; ">
		&nbsp; <br />

		<div style="align: center; background-color: white; opacity:0.8; padding: 10px">

			<div id="left_content">
				<div id="query">
					<textarea id="inputQuery"></textarea>
					<br /> <label>Output format: </label> <select id="select">
						<option value="text" selected>Text</option>
						<option value="xml">XML</option>
						<option value="json">Json</option>
						<option value="csv">CSV</option>
						<option value="tsv">TSV</option>
						<option value="rdf">RDF</option>
						<option value="n-triple">N-Triple</option>
						<option value="turtle">Turtle</option>
						<option value="n3">N3</option>
					</select> <input type="submit" value="Run query" onclick="sendQuery()"></input> <input
						type="reset" value="Reset" id="reset" onclick="clearContent()" />
				</div>

				<div id="result">
					<textarea id='output'></textarea>
				</div>
			</div>

			<div id="sample">
				<h2>Examples:</h2>
				<ul>
					<li><a onclick="loadExample('ex1')">Get all information about a plant: </a><br /> 
						<span id="ex1">select * where { &lt;http://sedic.org/resource/Abies_balsamea&gt; ?p ?o.}</span>
					</li>
					<li ><a onclick="loadExample('ex2')">Get all effects of a plant : </a><br /> 
						<span id="ex2">Select ?o Where {&lt;http://sedic.org/resource/Viola_tricolor&gt; &lt;http://sedic.org/hasEffect&gt; ?o .}</span>
					</li>
					<li ><a onclick="loadExample('ex3')">Get plants that effectively treats a medical condition  : </a><br /> 
						<span id="ex3">Select Distinct ?s Where {?s &lt;http://sedic.org/cure/Effective&gt; &lt;http://sedic.org/MedicalCondition/High_cholesterol&gt;.}</span>
					</li>
					<li ><a onclick="loadExample('ex4')">Get plants that might treats a medical condition : </a><br /> 
						<span id="ex4">Select Distinct ?s Where {?s &lt;http://sedic.org/cure/LikelyEffective&gt; &lt;http://sedic.org/MedicalCondition/Absence_of_menstrual_periods&gt;.}</span>
					</li>
					<li ><a onclick="loadExample('ex5')">Get plants that may treat a medical condition : </a><br /> 
						<span id="ex5">Select Distinct ?s Where {?s &lt;http://sedic.org/cure/PossiblyEffective&gt; &lt;http://sedic.org/MedicalCondition/Headaches&gt; .}</span>
					</li>
				</ul>
			</div>

		</div>
		
		 <%@include file="WEB-INF/footer.jsp" %>   

	</div>


</body>
</html>