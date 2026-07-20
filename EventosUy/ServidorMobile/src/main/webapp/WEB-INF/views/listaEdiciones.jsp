<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.*"%>

<%
	List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("ediciones");
   
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
							<%for(DtEdicion edi : ediciones){ %>
								<div class="row text-start mt-4 mx-auto bordesDesc">
						   		<div class="col-xl-3 col-lg-3 col-md-4 col-12 mb-1 mx-auto">
									<a class="link" 
									
									href="SrvEdicion?destino=detalleEdiciono&edicion=<%=edi.getNombre()%>">
										<%String rutaImagen =(edi.getImagen().equals(""))?"defaultEdicion.png":edi.getImagen();  %>
						   				<img src="media/ediciones/<%=rutaImagen%>" class="rounded float-center img-fluid align-middle" alt="Imagen no disponible" width="400" height="400">
									</a>
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

