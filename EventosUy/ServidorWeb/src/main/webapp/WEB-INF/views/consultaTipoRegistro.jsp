<%@ page import="webservice.DtTipoRegistro"%>
<%@ page import="webservice.DtPatrocinio"%>
<%@ page import="java.util.List" %>
<%

	DtTipoRegistro reg = (DtTipoRegistro)request.getAttribute("dtreg");
	String imagen = (String)request.getAttribute("imagen");
	
	List<DtPatrocinio> patrocinios = (List<DtPatrocinio>)request.getAttribute("patrocinios");
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
						<h5 class="negrita mt-1 ms-1 ">Tipo de registro:</h5>
					</div>
					<div class="col-md-8 mt-2  bordesDesc fondo ">
				  		<div class="row text-start mt-2 mb-2">
				  			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-7 col-5 col ">
				  				<img src="media/ediciones/<%=imagen%>" class="imgorigin rounded float-start img-fluid bordes" alt="Imagen no disponible" width="230" height="220">
				  			</div>
				  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
				  				<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Nombre:</span><span class="ajustarTexto"> <%=reg.getNombre()%></span> </p>
				  				<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Descripcion:</span><span class="ajustarTexto"> <%=reg.getDescripcion()%></span> </p>
				  				<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Costo:</span><span class="ajustarTexto"> <%=reg.getCosto()%></span> </p>
				  				<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Cantidad de cupos:</span><span class="ajustarTexto"> <%=reg.getCantCupos()%></span> </p>
				  			</div>
                
			  			</div>
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-3  "><!-- NAV DERECHO -->
						<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
							<h6 class="negrita mt-1">Patrocinadores con descuento:</h6>
						</div>
						<div class="rounded mx-auto rb">
						<div class="row  c5 cLetraRelleno text-center bordes mt-2 ">
						   	<% if(patrocinios != null){ %>
							   	<% for(DtPatrocinio p: patrocinios){ %>
							   			<div class="row ">
									   		<div><%=p.getCodigo()%></div>
											<div><%=p.getNivel() %></div>
											<div><%=p.getAporteEconomico()%></div>
											<div><a href="SrvPatrocinio?destino=consultaPatrocinio&aporte=<%=p.getAporteEconomico()%>&codigo=<%=p.getCodigo()%>&fecha=<%=p.getFecha()%>&gratis=<%=p.getCantGratis()%>&nivel=<%=p.getNivel()%>&imagen=<%=imagen%>&tipo=<%=reg.getNombre()%>">ver detalle</a>
											</div>
										</div>
								   	<div>
								 </div>
							   	<% } %>
						    <% } %>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>