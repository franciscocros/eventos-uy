<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="webservice.*" %>
<%

	Map<String,DtUsuario> seguidos = new HashMap<String,DtUsuario>();
	Map<String,DtUsuario> seguidores = new HashMap<String,DtUsuario>();
	seguidos = (Map<String,DtUsuario>)request.getAttribute("mapseguidos");
    seguidores = (Map<String,DtUsuario>)request.getAttribute("mapseguidores");
    boolean losigo = (boolean) request.getAttribute("losigo");
	HttpSession sesionLog = request.getSession(false);
	boolean logeado = false;
	String nicklog  = null;
	if (sesionLog != null) {
	    String estadoSesion = (String) sesionLog.getAttribute("estadoSesion");
	    if ("logeado".equals(estadoSesion)) {
	        logeado = true;
	        DtUsuario usuario = (DtUsuario)session.getAttribute("datosUsuario");
	        nicklog = usuario.getNickname();	        
	    }
	}

	List<DtCompraWeb>  compras = null;
	DtAsistente asist = null;
	DtOrganizador organiza = null;
	List<DtEdicion> ediciones = null;
	boolean miperfil = false;

	DtUsuario usu = (DtUsuario)request.getAttribute("detalleUsuario");
	String rutaImagen =(usu.getImagen().equals(""))?"defaultUser.png":usu.getImagen();
	
	String sesion = (String)session.getAttribute("estadoSesion");
    if(sesion != null &&  sesion.equals("logeado")){
        DtUsuario usuario = (DtUsuario) session.getAttribute("datosUsuario");
        if(usuario.getNickname().equals(usu.getNickname())){
        	miperfil = true;
        }
    }

	String tipo = (String)request.getAttribute("tipo");
	
	if(tipo.equals("asistente")){
		asist = (DtAsistente)request.getAttribute("detalleUsuario");
		compras = (List<DtCompraWeb>)request.getAttribute("comprasUsuario");
	}else{
		organiza = (DtOrganizador)request.getAttribute("detalleUsuario");
		ediciones =  organiza.getEdiciones();
	}
	
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
						<h5 class="negrita mt-1 ms-1 ">Perfil del usuario:</h5>
					</div>
					<div class="col-md-9 mt-2">
				  		<div class="row text-start">
				  			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-7 col-5 col ">
				  				<img src="media/users/<%=rutaImagen%>"  class="rounded bordes" alt="Imagen no disponible" style="width: 250px; height: 250px; object-fit: cover;">
				  			</div>
				  			<div class="col-xl-8 col-lg-8 col-md-6 col-sm-5 col-7">
				  				<h3 class="fuente1 resptext negrita "><%=usu.getNombre()%></h3>
				  			</div>
				  		</div>
				  		<div class="row mt-4 text-start ">
						  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Nickname:</span><span class="ajustarTexto"> <%=usu.getNickname()%></span> </p>
						  			<p class=" lh-1"><span class="negrita fuente1 ajustarTexto"> Email:</span><span class="ajustarTexto"> <%=usu.getCorreo()%></span> </p>
				  			<% if(tipo.equals("asistente")){ %>
						  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Apellido:</span><span class="ajustarTexto"><%=asist.getApellido()%></span></p>
						  			<p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Fecha de nacimiento:</span><span class="ajustarTexto"><%=asist.getFechaNacimiento()%></span></p>
						  		    <p  class="lh-1"><span class="negrita fuente1 ajustarTexto"> Institución:</span><span class="ajustarTexto"><%=asist.getInstitucion()%></span></p>	
				  		    <% }else{ %>
				  		          	<p class="lh-1"><span class="negrita fuente1 ajustarTexto">Descripción: </span><span class="ajustarTexto"><%=organiza.getDescripcion()%></span></p>
      								<p class="lh-1"><span class="negrita fuente1 ajustarTexto">Sitio Web: </span><span class="ajustarTexto"><%=organiza.getPaginaWeb()%></span></p>	
				  		    <% } %>
			  		    	<% if(logeado){ %>
			  		    	  	<% String seguidor = usu.getNickname(); %>
			  		    	  	 <% if (!seguidor.equals(nicklog)){ %>
					   			 <% if(losigo){ %>
						   			<p><a class="link desc btn btn-sm btn-outline-warning" style="width:300px;" href="SrvUsuario?destino=dejarDeSeguirUsuario&nick=<%=usu.getNickname()%>&origen=detalleUsuario&nickusu=<%=usu.getNickname()%>">dejar de seguir usuario</a></p>
					   			 <% } if (!losigo){ %>
					   				<p><a class="link desc btn btn-sm btn-outline-success" style="width:300px;" href="SrvUsuario?destino=comenzarASeguirUsuario&nick=<%=usu.getNickname()%>&origen=detalleUsuario&nickusu=<%=usu.getNickname()%>">comenzar a seguir usuario</a></p>
					   			<% } %>
					   		<% } %>
					   		<% } %>
				  		</div>
					</div>
					<div class="col-md-3"><!-- NAV DERECHO -->
						<% if(tipo.equals("asistente") && compras != null){ %>
							<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
							   	<h6 class="negrita">Registros a ediciones:</h6>
							</div>
							<div class="rounded mx-auto rb">
								<% if(compras != null){ %>
									<% for(DtCompraWeb c : compras){ %>
										<% String imgCompra =(c.getImagenEdicion().equals(""))?"defaultCompra.png":c.getImagenEdicion();  %>
										<div class="row fondo">
										   	<a class="mid sinSubrayado" href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=usu.getNickname()%>">
										   		<img src="media/ediciones/<%=imgCompra%>" class="imgorigin rounded img-fluid mt-1" alt="Imagen no disponible" width="150" height="150" style="display: block; margin: 0 auto;">  
										   	</a>
										   	<a class="sinSubrayado" href="detalleCompra?edicion=<%=c.getEdicion()%>&nickname=<%=usu.getNickname()%>" style="text-align: center;"> <%=c.getEdicion()%></a>
										</div>
									<% } %>
								<% } %>
							</div>
						<% }else if(tipo.equals("organizador")){ %>
							<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
							   	<h6 class="negrita">Ediciones de eventos:</h6>
							</div>
							<div class="rounded mx-auto rb">
								<% if(ediciones != null){ %>
									<% for(DtEdicion e : ediciones){ %>
										<% if(e.getEstado().equals(EEstadoEdicion.CONFIRMADA)){ %>
											<% String imgCompra =(e.getImagen().equals(""))?"defaultCompra.png":e.getImagen();  %>
											<div class="row fondo">
											   	<a class="mid sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>">
											   		<img src="media/ediciones/<%=imgCompra%>" class="imgorigin rounded img-fluid mt-1" alt="Imagen no disponible" width="150" height="150" style="display: block; margin: 0 auto;">  
											   	</a>
											   	<a class="sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>&evento=noEsNecesario" style="text-align: center;"> <%=e.getNombre()%></a>
											</div>
										<% } %>
									<% } %>
								<% } %>
							</div>
							<% if(miperfil){ %>
								<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
								   	<h6 class="negrita">Ediciones de eventos Ingresadas:</h6>
								</div>
								<div class="rounded mx-auto rb">
									<% if(ediciones != null){ %>
										<% for(DtEdicion e : ediciones){ %>
											<% if(e.getEstado().equals(EEstadoEdicion.INGRESADA)){ %>
												<% String imgCompra =(e.getImagen().equals(""))?"defaultCompra.png":e.getImagen();  %>
												<div class="row fondo">
												   	<a class="mid sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>&evento=noEsNecesario">
												   		<img src="media/ediciones/<%=imgCompra%>" class="imgorigin rounded img-fluid mt-1" alt="Imagen no disponible" width="150" height="150" style="display: block; margin: 0 auto;">  
												   	</a>
												   	<a class="sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>&evento=noEsNecesario" style="text-align: center;"> <%=e.getNombre()%></a>
												</div>
											<% } %>
										<% } %>
									<% } %>
								</div>
								<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
								   	<h6 class="negrita">Ediciones de eventos Rechazadas:</h6>
								</div>
								<div class="rounded mx-auto rb">
									<% if(ediciones != null){ %>
										<% for(DtEdicion e : ediciones){ %>
											<% if(e.getEstado().equals(EEstadoEdicion.RECHAZADA)){ %>
												<% String imgCompra =(e.getImagen().equals(""))?"defaultCompra.png":e.getImagen();  %>
												<div class="row fondo">
												   	<a class="mid sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>&evento=noEsNecesario">
												   		<img src="media/ediciones/<%=imgCompra%>" class="imgorigin rounded img-fluid mt-1" alt="Imagen no disponible" width="150" height="150" style="display: block; margin: 0 auto;">  
												   	</a>
												   	<a class="sinSubrayado" href="SrvEdicion?destino=detalleEdiciono&edicion=<%=e.getNombre()%>&evento=noEsNecesario" style="text-align: center;"> <%=e.getNombre()%></a>
												</div>
											<% } %>
										<% } %>
									<% } %>
								</div>
							<% } %>
						<% } %>

						<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
						   	<h6 class="negrita">Seguidos:</h6>
						</div>
						<div class="rounded mx-auto rb">
							<% if(seguidos != null){ %>
								<% for(DtUsuario u : seguidos.values()){ %>
									<% String imgu =(u.getImagen().equals(""))?"defaultCompra.png":u.getImagen();  %>
									<div class="row fondo text-center">
									  <div class="col">
									      <img src="media/users/<%=imgu%>" class="rounded bordes" alt="Imagen no disponible" style="width: 100px; height: 100px; object-fit: cover;"> <p>Nombre: <%=u.getNombre()%></p><p>Nickname: <%=u.getNickname()%></p>
									       <a class="sinSubrayado d-block"    href="SrvUsuario?destino=detalleUsuario&nick=<%=u.getNickname()%>">Ver detalle</a>
                                       </div>
                                   </div>
								<% } %>
							<% } %>
						</div>
						<div class="row  c5 cLetraRelleno text-center bordes mt-4 ">
						   	<h6 class="negrita">Seguidores:</h6>
						</div>
						<div class="rounded mx-auto rb">
							<% if(seguidores != null){ %>
								<% for(DtUsuario us : seguidores.values()){ %>
									<% String imgu =(us.getImagen().equals(""))?"defaultCompra.png":us.getImagen();  %>
									<div class="row fondo">
									   	<img src="media/users/<%=imgu%>" class="rounded bordes" alt="Imagen no disponible" style="width: 100px; height: 100px; object-fit: cover;">  
										<p>Nombre: <%=us.getNombre()%></p>
										<p>Nickname: <%=us.getNickname()%>></p>
									   	<a class="sinSubrayado" href="SrvUsuario?destino=detalleUsuario&nick=<%=us.getNickname()%>" style="text-align: center;">Ver detalle</a>
									</div>
								<% } %>
							<% } %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>