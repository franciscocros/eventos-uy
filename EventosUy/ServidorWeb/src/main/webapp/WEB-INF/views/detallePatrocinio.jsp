<%
	String aporte = (String)request.getAttribute("aporte");
	String codigo = (String)request.getAttribute("codigo");
	String fecha  = (String)request.getAttribute("fecha");
	String gratis = (String)request.getAttribute("gratis");
	String nivel  = (String)request.getAttribute("nivel");
	String imagen = (String)request.getAttribute("imagen");
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
						<h5 class="negrita mt-1 ms-1 ">Detalle de patrocinio:</h5>
					</div>
					<div class="col-md-8 mt-2  bordesDesc fondo ">
				  		<div class="row text-start mt-2 mb-2">
				  			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-7 col-5 col ">
				  				<img src="media/ediciones/<%=imagen%>" class="imgorigin rounded float-start img-fluid bordes" alt="Imagen no disponible" width="230" height="220">
				  			</div>
				  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
					  		    <p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Código: </span><span class="ajustarTexto"><%=codigo %></span> </p>
					  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Nivel: </span><span class="ajustarTexto"><%=nivel %></span> </p>
					  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Aporte Económico: </span><span class="ajustarTexto"><%=aporte %></span> </p>
					  			<p class="lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de alta: </span><span class="ajustarTexto"><%=fecha %></span></p>
					  		    <p class="lh-1"><span class="negrita fuente1 ajustarTexto"> Cantidad de registros gratuitos: </span><span class="ajustarTexto"><%=gratis %></span></p>						  		    
					  		</div>
			  			</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-3  "><!-- NAV DERECHO -->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>