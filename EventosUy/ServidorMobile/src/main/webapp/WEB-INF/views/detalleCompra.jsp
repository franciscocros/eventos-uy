

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
String fechaInicioEdicion = (String) request.getAttribute("fechaInicioEdicion");
String ediCompra = (String) request.getAttribute("ediCompra");
boolean comenzada = (boolean) request.getAttribute("comenzada");
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
			<div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto text-center">
				<div class="rt c2 cLetraRelleno text-start bordes">
	       		 	<h5 class="negrita mt-1 ms-1">Detalle del registro</h5>
       		  	</div> 
				<div class="row text-start mt-3 mx-auto">
					<div class="col-xl-8 col-lg-7 col-md-6 col-sm-12 bordes fondosuave p-3">					
			    		<img src="media/ediciones/<%=rutaImagenEdi %>" class="imgorigin rounded img-fluid" alt="Imagen no disponible" width="280" height="280"> 			    			    	 	

						<div style="margin-top: 30px">
							<p><span class="negrita fuente1">Edicion:</span> <%=edicion %> </p>
							<p><span class="negrita fuente1">Fecha de registro:</span> <%=fecha  %></p>
							<p><span class="negrita fuente1">Valor del registro:</span> $<%=costo  %>
							<p><span class="negrita fuente1">Código de patrocinio:</span> <%= esPatrocinado %></p>
							<p><span class="negrita fuente1">Tipo de registro:</span> <%= TRregistro %></p>
							<p class="card-text mb-1">
							    <span class="negrita fuente1">Asistencia: </span>
							    <%if(confirmada){%>
							        <span style="color: green;">CONFIRMADA</span>
							    <%} else{%>
							        <span style="color: red;">NO CONFIRMADA</span>
							    <%}%>
						    </p>
                            <%if(!confirmada && comenzada){ %>
                                <a href="asistenciaCompra?ediCompra=<%=ediCompra%>" class="btn btn-primary btn-sm mt-2"> Confirmar asistencia</a>
                           <%} else if(!confirmada && !comenzada) { %>
                           		<span class="fuente1">(Podrá confirmar su asistencia luego de comenzada la edición del evento el día <%=fechaInicioEdicion %>) </span>  
                           <%} %>                                                       
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>