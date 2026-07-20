<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento" %>
<%@ page import="webservice.DtEdicion" %>
<%@ page import="webservice.DtUsuario" %>
<%@ page import="webservice.DtPatrocinio" %>
<%@ page import="webservice.DtCompra" %>


<%
webservice.DtEdicion dte = (webservice.DtEdicion) request.getAttribute("edicion");
webservice.DtUsuario dtu = (webservice.DtUsuario) request.getAttribute("organizador");
Set<String> treg = (Set<String>) request.getAttribute("tiposReg");
Set<webservice.DtPatrocinio> pats = (Set<webservice.DtPatrocinio>) request.getAttribute("pats");
Set<webservice.DtCompra> compras = (Set<webservice.DtCompra>) request.getAttribute("compras");


String rutaImagen = (dte.getImagen() == null || dte.getImagen().isEmpty()) 
    ? "defaultEdicion.png" 
    : dte.getImagen();

String imgOrganizador = (dtu.getImagen() == null || dtu.getImagen().isEmpty()) ? "defaultUser.png" : dtu.getImagen();

String logueado = (String) request.getAttribute("login");

	boolean soyorg         = Boolean.TRUE.equals((Boolean) request.getAttribute("soyOrganizador"));
	boolean soycomp        = Boolean.TRUE.equals((Boolean) request.getAttribute("tengoCompra"));
	
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta de edicion</title>
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
       		 	<h5 class="negrita mt-1 ms-1">Edicion de evento:</h5>
       		  </div> 
       		 <div class="row text-start mt-1 mx-auto mt-4 ">
			  	<div class="col-xl-8 col-lg-7 col-md-6 col-sm-6 bordes fondosuave">
			  		<div class="row">
			  			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-7 col-5 col ">
			  				<img src="media/ediciones/<%=rutaImagen%>" class="imgorigin rounded float-start img-fluid bordes" alt="Imagen no disponible" width="230" height="220">
			  			</div>
   			  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
			  				<h3 class="fuente1 resptext negrita "> <%=dte.getNombre() %> </h3>
			  			</div>
			  		</div>
			      <div class="row mt-4">
			      		<iframe width="1268" height="713" src="<%= dte.getVideo() %>"  frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
			  		    <p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Ciudad: </span><span class="ajustarTexto"><%=dte.getCiudad()%> </span> </p>
			  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Pais: </span><span class="ajustarTexto"><%=dte.getPais() %></span></p>
			  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de inicio: </span><span class="ajustarTexto"><%=dte.getFechaInicio() %></span> </p>
			  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de fin: </span><span class="ajustarTexto"><%=dte.getFechaFin()%> </span></p>
					  	<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de alta: </span><span class="ajustarTexto"><%=dte.getFechaAlta() %></span></p>
			        </div>
			        <%
			           if (soycomp){
			        %>
			         <div class=" c5 cLetraRelleno text-center bordes mx-auto  mt-4">
						<h5 class="negrita mt-1">Tu compra</h5>
					</div>
					<div class=" c2 cLetra bordes text-center fondo fuente1">
						<div class="bordesDesc fondo rounded mx-auto rb">
							<div class="row text-start negrita" style="Font-size:20px;">
								<a class="sinSubrayado" href="detalleCompra?edicion=<%=dte.getNombre()%>&nickname=<%=logueado%>" style="text-align: center;"> Ver detalles </a>
							</div>
			           </div>
			        </div>
			        <% } %> 
			     </div>
			    <div class="col-xl-4 col-lg-5 col-md-6 col-sm-6 mx-auto">
			  	<div class="row  c5 cLetraRelleno text-center bordes mx-auto ">
	       		 	<h6 class="negrita mt-1">Organizador:</h6>
	       		  </div>
	       		 <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
			    	<div class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    		<a class="mid sinSubrayado" href="SrvUsuario?destino=detalleUsuario&nick=<%=dtu.getNickname()%>">

			    		<img src="media/users/<%=imgOrganizador%>" class="imgorigin rounded img-fluid" alt="Imagen no disponible" width="120" height="120" style="display: block; margin: 0 auto;">  
			    		</a>
			    		<a class="sinSubrayado" href="SrvUsuario?destino=detalleUsuario&nick=<%=dtu.getNickname()%>" style="text-align: center;"> <%=dtu.getNombre() %> </a>
					</div>
	       		  </div>
	       		  <div class="row  c5 cLetraRelleno text-center bordes mx-auto mt-4">
	       		 	<h6 class="negrita mt-1">Tipos de registro:</h6>
	       		  </div>
	       		  <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
			    	<div id="tiposRegistro" class="row text-start negrita mt-3 ms-1" style="Font-size:20px;">
			    	  <%
                        if (treg != null) {
                       for (String t : treg) {
                      %>                      
                      <a class="sinSubrayado" href="SrvTipoRegistro?destino=detalleTipoRegistro&treg=<%= t %>&edi=<%=dte.getNombre()%>&imagen=<%=rutaImagen%>" style="text-align: center;"><%= t %> </a>
                      <%
                         }
                          }
                       %>
			    	</div>
			       </div>
			        <div class="row  c5 cLetraRelleno text-center bordes mx-auto  mt-4 ">
	       		 	<h6 class="negrita mt-1 ">Patrocinios:</h6>
	       		  </div> 
	       		   <div class="bordesDesc fondo rounded mx-auto rb" style="min-height:150px;">
				  	<div id="patrocinios" class="row text-center negrita mt-3 ms-1" style="Font-size:20px;">
				  	  <%
                        if (pats != null) {
                       for (webservice.DtPatrocinio p : pats) {
                      %>
			    		<a href="SrvPatrocinio?destino=consultaPatrocinio&aporte=<%=p.getAporteEconomico()%>&codigo=<%=p.getCodigo()%>&fecha=<%=p.getFecha() %>&gratis=<%=p.getCantGratis()%>&nivel=<%=p.getNivel() %>&imagen=<%=rutaImagen%>"> <%= p.getCodigo() %> </a>
                      <%
                         }
                          }
                       %>
			    	 </div>
			       </div>
			        <%
			           if (soyorg){			       	  
			          %>
			          <div class="row  c5 cLetraRelleno text-center bordes mx-auto mt-4">
	       		 	<h6 class="negrita mt-1">Registros:</h6>
	       		     </div>
	       		     <div class="bordesDesc fondo rounded mx-auto rb " style="min-height:150px;">
	       		      <div class="row text-center negrita mt-3 ms-1" style="Font-size:20px;">
	       		     
	       		     <%  if (compras != null) { %>
	       		    	<% for (webservice.DtCompra comp : compras){ %>   
	       		    	
	       		    	<a class="sinSubrayado" style="margin-bottom: 10px;" href="detalleCompra?edicion=<%=dte.getNombre()%>&nickname=<%=comp.getAsistente()%>" style="text-align: center;"><%=comp.getAsistente()%>		       
	       		   
	       		    	<%if(comp.isAceptada()){%>
						        <span style="color: green;"> (Asistió)</span>
						    <%} else{%>
						        <span style="color: red;"> (Asistencia no registrada)</span>
						    <%}%>
						 </a>
						<%} 
						  }
						  } %>
			         </div>		        
			       </div>
			     </div>
			    </div>
			   </div>
			  </div>
			 </div>
<script src="../js/bootstrap.bundle.js"></script>
</body>
</html>