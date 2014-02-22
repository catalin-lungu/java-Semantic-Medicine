<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="WEB-INF/head_links.jsp"%>

<script type="text/javascript">
	var webSocket = new WebSocket('ws://localhost:8080/sedic/websocket');

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

		var obj = JSON.parse(event.data);

		switch (obj.type) {
		case "init":
			var select = document.getElementById('inputCondition');

			var list = obj.effects;

			var find = '_';
			var re = new RegExp(find, 'g');
			
			for (var i = 0; i < list.length; i++) {
				if(list[i].substring(0,1) == "?") continue; //
				var opt = document.createElement('option');
				opt.value = list[i];
				opt.innerHTML = list[i].replace(re, ' ');
				select.appendChild(opt);
			}
/*
			var select2 = document.getElementById('inputAlergens');

			list = obj.allergens;
			for (var i = 0; i < list.length; i++) {
				var opt = document.createElement('option');
				opt.value = list[i];
				opt.innerHTML = list[i];
				select2.appendChild(opt);
			}
*/
			break;

		case "message":
		    		var output = document.getElementById('output');
		    		var table = document.createElement('table');
		    		
		    		var listPlants = obj.plants;
		    		
		    		for(var i=0;i<listPlants.length;i++){
		    			var plant = listPlants[i];
		    			table.innerHTML +="<tr><td colspan='2'><i>"+plant.name+"</i></td></tr>"
		    			table.innerHTML +="<tr><td>" + plant.img + "</td><td>"+ plant.otherNames +"</td></tr>";
		    			table.innerHTML +="<tr><td colspan='2'>"+ plant.use+ "</td></tr>";
		    			table.innerHTML +="<tr><td colspan='2'>"+ plant.caution+"</td></tr>";
		    			table.innerHTML +="<tr><td colspan='2'>"+ plant.otherInfo+"</td></tr>";
		    		}
		    		output.appendChild(table);
			break;

		}
	}

	function onOpen(event) {
		document.getElementById('output').innerHTML = '';

	}

	function onError(event) {
		alert('Err ' + event.data);
	}

	    function send() {
	    	document.getElementById('output').innerHTML="";
	      	var txt = document.getElementById('input').value;
	      	webSocket.send(txt);
	      	return false;
	    }

	    function add(){
	    	var selectIn = document.getElementById('inputCondition');
	    	document.getElementById('input').value+=selectIn.options[selectIn.selectedIndex].value+"\n";
	    }
	    
	    function remove(){
	    	document.getElementById('inputCondition').innerHTML="";
	    }
	        
</script>
<script type="text/javascript">
	function loadHead(){
		document.getElementById('inputCondition').innerHTML="Hii!!";
	}

</script>

</head>
<body>

	<%@include file="WEB-INF/menu.jsp"%>

	<div class="center_box"
		style="min-height:600px; ">
		&nbsp; <br />

		<div style="align: center; background-color: white; opacity:0.8; padding: 10px">

	    <div>
			<table>
			<tr>
			    <!--<td>Body</td>-->
				<td>Medical conditions</td>
				<td colspan="2">Your request</td>
			</tr>
			<tr><!--
			    <td width="100" height="190">
			    <div id="myImageBody">
				    <img alt="body" src="imgs/body1.jpg" width="100" height="190" align="top" usemap="#bodymap">
				    <map name="bodymap">
					  <area shape="rect" coords="0,20,100,100" alt="Sun" href="mercur.htm" onclick="loadHead()">
					  <area shape="circle" coords="90,58,3" alt="Mercury" href="mercur.htm">
					  <area shape="circle" coords="124,58,8" alt="Venus" href="venus.htm">
					</map>
				    
				</div>
				</td> -->
				<td><select id="inputCondition" size="11"></select></td>
				<!--  <td><select id="inputAlergens" size="4"></select></td> -->
				<td colspan="2"><textarea id="input" style="height: 189px; width: 300px; " ></textarea></td>
			</tr>
			<tr>
				<td colspan="1"><input type="submit" value="Add" onclick="add()" /></td>
				<td colspan="2"><input type="reset" value="Remove" onclick="remove()" /> &nbsp; &nbsp;
				<input type="submit" value="Send request" onclick="send()" /></td>
			</tr>
			</table>
			<br />
			
			<div id="output">
		    </div>
      	</div>
     </div>
		<%@include file="WEB-INF/footer.jsp"%>


	</div>
</body>
</html>