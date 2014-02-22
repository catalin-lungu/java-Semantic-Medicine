<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div id="global-nav" class="no-js  gn-signed-out">

	<div id="gn-wrap" class="gn-wrap">

		<div class="need">
			<a href="/" title="home"> <span class="needed"> home </span>

			</a>
		</div>

		<div class="nav small-logo">
			<ul class="top-nav">
				<li class="toplink"><a href="/sedic/index.jsp"><img
						style="height: 37px; width: 37px; margin-top: 7px; margin-left: 17px; padding: 0px;"
						src="style/images/green-leaf.png" /></a></li>

				<div class="nav small-logo">
					<ul class="top-nav">
						<li class="toplink"><a
							<%if (!request.getRequestURL().toString().contains("index.jsp")) {
				out.println("class=\"gn-link explore\"");
			} else {
				out.println("class=\"gn-link gn-signup sign-up-pixel-trigger\"");
			}%>
							href="/sedic/index.jsp"><span class="logo">SEDIC Home</span></a></li>


						<li class="gn-menu-parent toplink"><a
							<%if (!request.getRequestURL().toString().contains("index.jsp")) {
				out.println("class=\"gn-link explore\"");
			} else {
				out.println("class=\"gn-link gn-signup sign-up-pixel-trigger\"");
			}%>
							href="/sedic/rest.jsp"><span>Rest service</span></a></li>

						<li class="gn-menu-parent toplink"><a
							<%if (!request.getRequestURL().toString().contains("sqvizler.jsp")) {
				out.println("class=\"gn-link explore\"");
			} else {
				out.println("class=\"gn-link gn-signup sign-up-pixel-trigger\"");
			}%>
							href="/sedic/sgvizler.jsp" ><span> Visualization Data</span></a></li>

						<li class="gn-menu-parent toplink"><a
							<%if (!request.getRequestURL().toString().contains("sparql.jsp")) {
				out.println("class=\"gn-link explore\"");
			} else {
				out.println("class=\"gn-link gn-signup sign-up-pixel-trigger\"");
			}%>
							href="/sedic/sparql.jsp"><span>SPARQL</span></a></li>
					</ul>
				</div>
		</div>

	</div>

</div>

<div
	style="height: 49px; width: 100%; color: red; border: 0px solid green;">&nbsp;</div>
