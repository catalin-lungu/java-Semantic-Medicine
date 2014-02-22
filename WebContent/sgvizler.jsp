<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="WEB-INF/head_links.jsp"%>
<title>Visualization Data</title>
<link rel="stylesheet" type="text/css" href="style/main.css">
<link rel="stylesheet" type="text/css" href="style/visualStyle.css">
<link rel="stylesheet" type="text/css" href="scripts/sgvizler_0.6/example.html/examples.css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" src="scripts/sgvizler_0.6/sgvizler.js"></script>
<script type="text/javascript">
	sgvizler.prefix('wd', 'http://sws.ifi.uio.no/d2rq/resource/');
	sgvizler.prefix('w', 'http://sws.ifi.uio.no/ont/world.owl#');
	     
	 $(document).ready(function() { sgvizler.containerDrawAll(); });
</script>
</head>
<body>

	<%@include file="WEB-INF/menu.jsp"%>

	<div class="center_box"
		style="min-height:600px; ">
		&nbsp; <br />
		
		<h2>Explore data</h2><br/>
		
     	<div id="example"
		     data-sgvizler-endpoint="http://localhost:3030/dataset/query"
		     data-sgvizler-query="SELECT ?class (count(&lt;http://dbpedia.org/resource/Viola_tricolor&gt;) AS ?noOfInstances)
		                          WHERE{ &lt;http://dbpedia.org/resource/Viola_tricolor&gt; a ?class }
		                          GROUP BY ?class
		                          ORDER BY ?class"
		     data-sgvizler-chart="sgvizler.visualization.D3ForceGraph"
		     style="width:1000px; height:300px;"></div><br/>
     	<div id="example2"
		     data-sgvizler-endpoint="http://localhost:3030/dataset/query"
		     data-sgvizler-query="SELECT ?class (count(?inst) AS ?noOfInstances)
		                          WHERE{ ?inst a ?class }
		                          GROUP BY ?class
		                          ORDER BY ?class"
		     data-sgvizler-chart="sgvizler.visualization.D3ForceGraph"
		     style="width:1000px; height:600px;"></div>
     	
     	</div>
		<%@include file="WEB-INF/footer.jsp"%>


	</div>
	
</body>
</html>