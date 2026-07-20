	<%@ page import="webservice.DtUsuario" %>

<% 
	String sesion = (String)session.getAttribute("estadoSesion");
	
	DtUsuario usuario = (DtUsuario) session.getAttribute("datosUsuario");
    String rutaImagen ="defaultUser.png";
    String nombre = "";
    if(usuario != null){
    	rutaImagen = usuario.getImagen();
    	nombre = usuario.getNombre();
    }

%>

<jsp:include page="routes.jsp"></jsp:include>

<nav class="navbar border-bottom border-body c2">
	<% if((sesion == null)|| !sesion.equals("logeado") ){ %>
		  <div class="container-fluid">
				<div class="col-4 text-light a">
				</div>
			 	<div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12 text-end me-2">
						<a class="sinSubrayado text-light negrita mx-auto" href="SrvUsuario?destino=inicioSesion"> Iniciar sesión </a>
					|
					<a class="sinSubrayado text-light negrita" href="SrvUsuario?destino=registroUsuario">Registrarse &nbsp  </a>
				</div>
		  </div>
	<% }else{ %>		  
	
	<div class="container-fluid">
		<div class="col-4 text-light">
		</div>
	    <div class="btn-group">
	        <a href="SrvUsuario?destino=detalleUsuario&nick=<%=usuario.getNickname()%>"d-flex align-items-center text-light barra text-decoration-none px-2">
	          <img src="media/users/<%=rutaImagen%>" class="rounded-circle img-fluid me-2" alt="Imagen no disponible" width="60" height="60">
	          <span class="menu"><%=nombre%></span>
	        </a>
	        <button type="button" class="btn btn-sm btn-outline-light dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false"></button>
	        <ul class="dropdown-menu dropdown-menu-end c2">
	          <li><a class="dropdown-item text-light" href="SrvUsuario?destino=editarPerfil">Editar Perfil</a></li>
	          <li><a class="dropdown-item text-light" href="SrvUsuario?destino=cerrarSesion">Cerrar Sesión</a></li>
	        </ul>
    	</div>
  	</div>
	<% } %>	
  <div class="container-fluid">
	<div class="col-sm-3 text-light">
		<h2><a class="sinSubrayado text-center text-light negrita" href="SrvEvento?destino=index">Eventos Uy</a></h2>
	</div>
	<div class="col-6">
	    <form class="d-flex" role="search">
	      <input class="form-control me-2" type="search" placeholder="Buscar evento" aria-label="Search">
	      <button class="btn btn-outline-light btn-sm" type="submit">Búsqueda</button>
	    </form>
	</div>
	<div class="col-3">
	</div>
  </div>
</nav>

<script src="js/bootstrap.bundle.js"></script>