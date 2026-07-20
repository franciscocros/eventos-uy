<%@ page import="webservice.DtUsuario" %>

<% 
	String sesion = (String)session.getAttribute("estadoSesion");
	
    DtUsuario usuario = (DtUsuario) session.getAttribute("datosUsuario");
    String rutaImagen ="defaultUser.png";
    String nombre = "";
    String index = "SrvEvento?destino=index";
    boolean login = false;
    if(usuario != null){
    	rutaImagen = usuario.getImagen();
    	nombre = usuario.getNombre();
    	login = true;
    	index = "SrvEvento?destino=indexLogeado";
    }

%>

<jsp:include page="routes.jsp"></jsp:include>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="padding-top: 70px;">

<nav class="navbar navbar-expand-sm navbar-dark fixed-top" style="background-color: #2196f2;" >
  <div class="container-fluid" >
  <% if(usuario != null){ %>
  	  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
   
    <%} %>
    <a class="navbar-brand" href="<%=index %>">Eventos Uy</a>
	<% if(usuario != null){ %>
			<img src="media/users/<%=rutaImagen%>" class="navbar-brand rounded-circle img-fluid me-2" alt="Imagen no disponible" width="40" height="40">	        
  </div>
   <div class="collapse navbar-collapse" id="collapsibleNavbar">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="SrvEvento?destino=indexLogeado">Consulta edición</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="listaRegistros?soloNoConfirmadas=false">Consulta registro</a>
        </li>
        <li class="nav-item">
          <a class="nav-link"  href="listaRegistros?soloNoConfirmadas=true">Confirmar asistencia</a>
        </li> 
        <li class="nav-item">
          <a class="nav-link" href="SrvUsuario?destino=cerrarSesion">Cerrar sesión</a>
        </li>
      </ul>
    </div>
    <%} %>
</nav>





<script src="js/bootstrap.bundle.js"></script>