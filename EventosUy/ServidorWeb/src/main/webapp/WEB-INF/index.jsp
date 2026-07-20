<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.DtEdicion"%>

<%
	List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventos");
    String mensajeExito = (String) request.getAttribute("mensajeExito"); // mensaje para popup
    boolean paraFinalizar = (boolean) request.getAttribute("finalizar");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eventos Uy</title>
</head>
<body>
	<jsp:include page="part/nav.jsp"></jsp:include>
	<div class="container-fluid text-center mt-3 mx-auto ">    
		<div class="row">
		    <jsp:include page="part/leftNav.jsp"></jsp:include>
			<div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
				<div class="row">
					<div class="row rt c2 cLetraRelleno text-start mx-auto ">
						<h5 class="negrita mt-1 ms-1 ">Eventos:</h5>
					</div>
						<div class="col-md-9">
						<% if(eventos != null){ %>
							<%for(DtEvento eve : eventos){ %>
								<%if (!eve.isFinalizado()) { %>
									<div class="row text-start mt-4 mx-auto bordesDesc">
							   		<div class="col-xl-3 col-lg-3 col-md-4 col-12 mb-1 mx-auto">
										<a class="link" href="SrvEvento?destino=detalleEvento&nomEvento=<%=eve.getNombre()%>">
											<%String rutaImagen =(eve.getImagen().equals(""))?"defaultEvento.png":eve.getImagen();  %>
							   				<img src="media/eventos/<%=rutaImagen%>" class="rounded float-center img-fluid align-middle" alt="Imagen no disponible" width="400" height="400">
										</a>
									</div>
							   		<div class="col-xl-9 col-lg-9 col-md-8 bordesDesc fondo rounded itembox ">
							   			<h4 class="subt"><%=eve.getNombre() %></h4>
							   			<p class="desc">  <%=eve.getDescipcion() %>
							   			<a class="link" href="SrvEvento?destino=detalleEvento&nomEvento=<%=eve.getNombre()%>">Ver información</a>
							   			<% if(paraFinalizar){ %>
							   				<a class="link" href="SrvEvento?destino=finEvento&nomEvento=<%=eve.getNombre()%>">Finalizar evento</a>
							   			<% } %>
							   		</div>
									</div>
								 <%} %>													
							<%} %>			
						<%} %>			
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

