<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.DtEdicion"%>
<%@ page import="webservice.EEstadoEdicion"%>

<%
	List<webservice.DtEdicion> ediciones = (List<webservice.DtEdicion>) request.getAttribute("ediciones");
   
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eventos Uy</title>
</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>
	<div class="container-fluid text-center mt-3 mx-auto ">    
		<div class="row">
		    <jsp:include page="../part/leftNav.jsp"></jsp:include>
			<div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
				<div class="row">
					<div class="row rt c2 cLetraRelleno text-start mx-auto ">
						<h5 class="negrita mt-1 ms-1 ">Mis ediciones:</h5>
					</div>
						<div class="col-md-9">
						<% if(ediciones != null){ %>
							<%for(webservice.DtEdicion edi : ediciones){ %>
								<div class="row text-start mt-4 mx-auto bordesDesc">
						   		<div class="col-xl-3 col-lg-3 col-md-4 col-12 mb-1 mx-auto">
									<a class="link" 
									
									href="SrvEdicion?destino=detalleEdiciono&edicion=<%=edi.getNombre()%>">
										<%String rutaImagen =(edi.getImagen().equals(""))?"defaultEdicion.png":edi.getImagen();  %>
						   				<img src="media/ediciones/<%=rutaImagen%>" class="rounded float-center img-fluid align-middle" alt="Imagen no disponible" width="400" height="400">
									</a>
								</div>
						   		<div class="col-xl-9 col-lg-9 col-md-8 bordesDesc fondo rounded itembox ">
						   			<h4 class="subt"><%=edi.getNombre() %></h4>						   			 
						   			<%if (edi.getEstado().equals(EEstadoEdicion.CONFIRMADA)) { %>
						   				<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Estado:</span><span class="ajustarTexto" style="color: green;"> Confirmada</span></p>
						   			<%} else if (edi.getEstado().equals(EEstadoEdicion.INGRESADA)) { %>
						   				<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Estado:</span><span class="ajustarTexto"> Ingresada</span></p>
						   			<%} else { %>	
						   				<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Estado:</span><span class="ajustarTexto" style="color: red;"> Rechazada</span></p>	<%} %>	   								   			
						   			<a class="link" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=edi.getNombre()%>">Ver información</a>
						   		</div>
								</div>												
							<%} %>			
						<%} %>			
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

