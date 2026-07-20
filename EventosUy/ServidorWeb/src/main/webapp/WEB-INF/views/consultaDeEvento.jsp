<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.DtEdicion"%>

<%
	DtEvento evento = (DtEvento) request.getAttribute("datosEvento");
	List<DtEdicion> edsEvento = (List<DtEdicion>) request.getAttribute("edsConfiEvento");
	List<String> cats = (List<String>) evento.getCategorias();
	String rutaImagen =(evento.getImagen().equals(""))?"defaultEvento.png":evento.getImagen();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta de evento</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">

</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>
		<div class="container-fluid text-center mt-3 mx-auto ">    
		<div class="row">
			<jsp:include page="../part/leftNav.jsp"></jsp:include>
			<div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
 			  <div class="row rt c2 cLetraRelleno text-start bordes mx-auto ">
       		 	<h5 class="negrita mt-1 ms-1">Evento:</h5>
       		  </div> 
			  <div class="row text-start mt-1 mx-auto mt-4 ">
			  	<div class="col-xl-8 col-lg-7 col-md-6 col-sm-6 bordes fondosuave">
			  		<div class="row">
			  			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-7 col-5 col ">
			  				<img src="media/eventos/<%=rutaImagen %>" class="imgorigin rounded float-start img-fluid bordes" alt="Imagen no disponible" width="230" height="220">
			  			</div>
			  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
			  				<h3 class="fuente1 resptext negrita "><%=evento.getNombre() %></h3>
			  			</div>
			  			<%if (evento.isFinalizado()) { %>
				  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
				  				<h3 class="fuente1 resptext negrita ">El evento esta finalizado</h3>
				  			</div>
			  			<%} %>
			  		</div>
			  		<div class="row mt-4">
			  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Sigla:</span><span class="ajustarTexto"> <%=evento.getSigla() %></span> </p>
			  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Descripción:</span><span class="ajustarTexto"> <%=evento.getDescipcion() %></span> </p>
			  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Categorías:</span><span class="ajustarTexto">
			  			 <%
			  			 int count = 0;
			  			 for(String c : cats) { %>
			  			 	<%=c%>
			  			 	<%
			  			 	count++;
			  			 	if (count < cats.size()) { %> 
			  			        -
			  			    <%} %>			  			 	
			  			 <%}%>
			  			</span></p>
			  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de alta:</span><span class="ajustarTexto"> <%=evento.getFechaAlta() %></span></p>			  			
				  	</div>
			  	</div>
			  	<div class="col-xl-4 col-lg-5 col-md-6 col-sm-6 mx-auto">
	 			  <div class="row  c5 cLetraRelleno text-center bordes mx-auto ">
	       		 	<h6 class="negrita mt-1">Ediciones:</h6>
	       		  </div>
	       		  <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">			    	
			    	<%for(DtEdicion edi : edsEvento){ %>
			    		<div class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    			<a class="mid sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&evento=<%=evento.getNombre()%>&edicion=<%=edi.getNombre()%>">
			    			<%	String imagen =(edi.getImagen().equals(""))?"defaultEdicion.png":edi.getImagen(); %>
			    			<img src="media/ediciones/<%=imagen%>" class="imgorigin rounded img-fluid" alt="Imagen no disponible" width="120" height="120" style="display: block; margin: 0 auto;">  
			    			</a>
			    			<a class="sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=edi.getNombre()%>" style="text-align: center;"><%=edi.getNombre()%> </a>
						</div>					
			    	<%} %>
			  	</div>
			  </div>
			</div>
			</div>
		</div>
	</div>

	<script src="../js/bootstrap.bundle.js"></script>
</body>
</html>

