<%@ page import="webservice.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>

<%
    List<DtCompraWeb> compras = (List<DtCompraWeb>) request.getAttribute("compras");
	List<DtCompraWeb> comprasAConfirmar = (List<DtCompraWeb>) request.getAttribute("comprasAConfirmar");
    String nickname = (String) request.getAttribute("nickname");
    String soloConf = (String) request.getAttribute("soloNoConfirmadas");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis Registros</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/estilos.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../part/nav.jsp"></jsp:include>
	<%if (soloConf.equals("false")) { %>
		   <div class="container-fluid text-center mt-3 mx-auto">
		       <div class="row">
		           <jsp:include page="../part/leftNav.jsp"></jsp:include>
		
		           <div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
		               <div class="row">
		                   <div class="row rt c2 cLetraRelleno text-start mx-auto">
		                       <h5 class="negrita mt-1 ms-1">Registros a ediciones</h5>
		                   </div>
		
		                   <div class="col-md-12 mt-3">
		                       <% if (compras != null && !compras.isEmpty()) { %>
		                           <div class="row">
		                               <% for (DtCompraWeb c : compras) { 
		                                   String imgCompra = (c.getImagenEdicion().equals("")) ? "defaultCompra.png" : c.getImagenEdicion();
		                               %>
		                                   <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 mb-4">
		                                       <div class="card shadow-sm bordes">
	                                            <a href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=nickname%>" class="sinSubrayado">
	                                                <img src="media/ediciones/<%=imgCompra%>" class="card-img-top rounded-top img-fluid" alt="Imagen no disponible">
	                                            </a>
	                                            <div class="card-body">
	                                                <h6 class="card-title negrita"><%=c.getEdicion()%></h6>
	                                                <p class="card-text mb-1"><span class="negrita">Evento:</span> <%=c.getEvento()%></p>
	                                                <p class="card-text mb-1">
													    <span class="negrita">Asistencia: </span>
													    <%if(c.isAceptada()){%>
													        <span style="color: green;">CONFIRMADA</span>
													    <%} else{%>
													        <span style="color: red;">NO CONFIRMADA</span>
													    <%}%>
													</p>
	                                                <a href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=nickname%>" class="btn btn-primary btn-sm mt-2">Ver detalle</a>
	                                            </div>
	                                        </div>
	                                    </div>
	                                <% } %>
	                            </div>
	                        <% } else { %>
	                            <div class="alert alert-secondary mt-4" role="alert">
	                                No tiene registros a ediciones aún.
	                            </div>
	                        <% } %>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	 <%} else {%>
	 	<div class="container-fluid text-center mt-3 mx-auto">
		       <div class="row">
		           <jsp:include page="../part/leftNav.jsp"></jsp:include>
		
		           <div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
		               <div class="row">
		                   <div class="row rt c2 cLetraRelleno text-start mx-auto">
		                       <h5 class="negrita mt-1 ms-1">Registros a ediciones comenzadas con asistencia sin confirmar</h5>
		                   </div>
		
		                   <div class="col-md-12 mt-3">
		                       <%
		                        if (comprasAConfirmar != null && !comprasAConfirmar.isEmpty()) { %>
		                           <div class="row">
		                               <% for (DtCompraWeb c : comprasAConfirmar) {
				                                   String imgCompra = (c.getImagenEdicion().equals("")) ? "defaultCompra.png" : c.getImagenEdicion(); %>				                               		
				                                   <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 mb-4">
				                                       <div class="card shadow-sm bordes">
			                                            <a href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=nickname%>" class="sinSubrayado">
			                                                <img src="media/ediciones/<%=imgCompra%>" class="card-img-top rounded-top img-fluid" alt="Imagen no disponible">
			                                            </a>
			                                            <div class="card-body">
			                                                <h6 class="card-title negrita"><%=c.getEdicion()%></h6>
			                                                <p class="card-text mb-1"><span class="negrita">Evento:</span> <%=c.getEvento()%></p>
			                                                <p class="card-text mb-1"><span class="negrita">Asistencia:</span> <span style="color: red;">NO CONFIRMADA</span></p>                                               			                                                                                          
			                                                <a href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=nickname%>" class="btn btn-primary btn-sm mt-2">Ver detalle</a>		                                           	
			                                            </div>
			                                            </div>
			                                       </div>
	                                   <% } %>
	                            </div>
	                        <% } else {%>
	                            <div class="alert alert-secondary mt-4" role="alert">
	                                No tiene registros a ediciones comenzadas con asistencia sin confirmar.
	                            </div>
	                        <% } %>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	 <%} %>
</body>
</html>
