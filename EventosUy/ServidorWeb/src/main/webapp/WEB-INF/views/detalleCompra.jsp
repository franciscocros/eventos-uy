<%
String evento = (String) request.getAttribute("evento");
String edicion = (String) request.getAttribute("ediciones");
String nickname = (String) request.getAttribute("nickname");
String nombre = (String) request.getAttribute("nombre");
float costo = (float) request.getAttribute("costo");
String fecha = (String)  request.getAttribute("fecha");
String TRregistro = (String) request.getAttribute("TRregistro");
String esPatrocinado = (String) request.getAttribute("esPatrocinado");
String rutaImagenUsu =(String) request.getAttribute("imgusu");
String rutaImagenEdi =(String) request.getAttribute("imgedi");
boolean confirmada = (boolean) request.getAttribute("confirmada");
boolean finalizada = (boolean) request.getAttribute("finalizada");
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
				<div class="row rt c2 cLetraRelleno text-start bordes mx-auto">
	       		 	<h5 class="negrita mt-1 ms-1">Detalle del registro</h5>
       		  	</div> 
				<div class="row text-start mt-3 mx-auto">
					<div class="col-xl-8 col-lg-7 col-md-6 col-sm-12 bordes fondosuave p-3">
						<p><span class="negrita fuente1">Fecha de registro:</span> <%=fecha  %></p>
						<p><span class="negrita fuente1">Valor del registro:</span> $ <%=costo  %>
						<p><span class="negrita fuente1">Código de patrocinio:</span><%=  esPatrocinado %></p>
						<p class="card-text mb-1">
						    <span class="negrita fuente1">Asistencia: </span>
						    <%if(confirmada){%>
						        <span style="color: green;">CONFIRMADA</span>
						    <%} else{%>
						        <span style="color: red;">NO CONFIRMADA</span>
						    <%}%>
						</p>
					</div>
					<div class="col-xl-4 col-lg-5 col-md-6 col-sm-6 mx-auto">
					<div class="row  c5 cLetraRelleno text-center bordes mx-auto ">
	       		 	<h6 class="negrita mt-1">Usuario:</h6>
	       		  </div>
	       		  <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
			    	<div class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    		<a class="mid sinSubrayado" href="SrvUsuario?destino=detalleUsuario&nick=<%= nickname%>">
			    		<img src="media/users/<%=rutaImagenUsu %>" class="imgorigin rounded img-fluid" alt="Imagen no disponible" width="120" height="120" style="display: block; margin: 0 auto;">  
			    		</a>
			    		<a class="sinSubrayado" href="SrvUsuario?destino=detalleUsuario&nick=<%= nickname%>" style="text-align: center;"> <%=nombre %> </a>
					</div>
	       		  </div>
	 			  <div class="row  c5 cLetraRelleno text-center bordes mx-auto ">
	       		 	<h6 class="negrita mt-1">Edicion del registro:</h6>
	       		  </div>
	       		  <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
			    	<div class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    		<a class="mid sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&evento=<%=evento %>&edicion=<%=edicion %>">
			    		<img src="media/ediciones/<%=rutaImagenEdi %>" class="imgorigin rounded img-fluid" alt="Imagen no disponible" width="120" height="120" style="display: block; margin: 0 auto;">  
			    		</a>
			    	 	<a class="sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&evento=<%=evento%>&edicion=<%=edicion%>" style="text-align: center;"> <%=edicion %> </a>
					</div>
	       		  </div>
	       		  <div class="row  c5 cLetraRelleno text-center bordes mx-auto ">
	       		 	<h6 class="negrita mt-1">Tipo de registro:</h6>
	       		  </div>
	       		  <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
			    	<div class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    		<a class="sinSubrayado" href="SrvTipoRegistro?destino=detalleTipoRegistro&treg=<%=TRregistro %>&edi=<%=edicion %>&imagen=<%=rutaImagenEdi %>" style="text-align: center;"> <%=TRregistro %> </a>
					</div>
	       		  </div>
			  	</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>