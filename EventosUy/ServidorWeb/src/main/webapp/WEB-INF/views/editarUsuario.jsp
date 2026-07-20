
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Usuario</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>

<%
    
	DtAsistente asist = null;
	DtOrganizador organiza = null;

	DtUsuario usu = (DtUsuario)request.getAttribute("datosUsuario");
	String rutaImagen =(usu.getImagen().equals(""))?"defaultUser.png":usu.getImagen();
		
	String tipo = (String)request.getAttribute("tipoUsuario"); // "Asistente" o "Organizador"
	Set<String> instituciones = null;
	
	if(tipo.equals("Asistente")){
		asist = (DtAsistente)request.getAttribute("datosUsuario");
        instituciones = (Set<String>) request.getAttribute("instituciones");
	}else{
		organiza = (DtOrganizador)request.getAttribute("datosUsuario");
	}
	
    String error = (String) request.getAttribute("error");
    
    %>

	<jsp:include page="../part/nav.jsp"></jsp:include>
		<div class="container mt-3 text-center">
		  <div class="row justify-content-md-center">
		    <div class="col col-lg-5 fondosuave rt rb">
		      <div class="row text-center c2 cLetraRelleno rts">
		        <h5 class="negrita">Modificar Datos</h5>
		      </div>
				<% if (error != null) { %>
				    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
				        <%= error %>
				        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				    </div>
				<% } %>
		      <form id="formUsuario" class="needs-validation" novalidate method="post" action="SrvUsuario?destino=modificarUsuario" enctype="multipart/form-data">
		        <div class="row text-start" id="usuario">
		          <div class="mb-3">
		          	<label for="nickname" class="altaUsuLetra">Nickname:</label>
					<input type="text" class="form-control" id="nickname" name="nickname"
					       value="<%= usu.getNickname() %>" readonly>
					
					<label for="email" class="altaUsuLetra">Correo electrónico:</label>
					<input type="email" class="form-control" id="email" name="email"
					       value="<%= usu.getCorreo() %>" readonly>

		                     
		            <label for="nombre" class="altaUsuLetra">Nombre:</label>
		            <input type="text" class="form-control" id="nombre" name="nombre"
		                   value="<%= usu.getNombre() %>" required>
		            <div class="invalid-feedback">Por favor, ingrese un nombre.</div>
		
		            <% if (tipo.equals("Asistente")) { %>
		              <label for="apellido" class="altaUsuLetra">Apellido:</label>
		              <input type="text" class="form-control" id="apellido" name="apellido"
		                     value="<%= asist.getApellido() %>">
		            <% } %>
		
		            <label for="contra" class="altaUsuLetra">Contraseña:</label>
		            <input type="password" class="form-control" id="contra" name="contra" required>
		            <div class="invalid-feedback">Por favor, ingrese una contraseña.</div>
		
		            <label for="contrabis" class="altaUsuLetra">Repetir contraseña:</label>
		            <input type="password" class="form-control" id="contrabis" name="contrabis" required>
		            <div class="invalid-feedback">Por favor, repita la contraseña.</div>
		
		            <label for="formFileSm" class="altaUsuLetra">Imagen de perfil:</label>
		            <input class="form-control form-control-sm" id="formFileSm" type="file" name="imagen">
		          </div>
		
		          <% if ("Asistente".equals(tipo)) { %>
		          <div id="formularioAsistente">
		          <%
					    java.time.LocalDate hoy = java.time.LocalDate.now();
				  %>
		            <label for="fechaNacimiento" class="altaUsuLetra">Fecha de nacimiento:</label>
		            <input type="date" class="form-control" id="fechaNacimiento" aria-describedby="emailHelp" name="fechaNacimiento"
		                   value="<%= asist.getFechaNacimiento() %>"  max="<%= hoy %>">
		
		           <label for="institucion" class="altaUsuLetra">Institución:</label>
					<select class="form-select" id="institucion" name="institucion">
					    <option value="">Ninguna</option>
					    <%
					        String instActual = asist.getInstitucion();
					
					        if (instituciones != null) {
					            for (String inst : instituciones) {
					                String selected = (inst.equals(instActual)) ? "selected" : "";
					    %>
					                <option value="<%= inst %>" <%= selected %>><%= inst %></option>
					    <%
					            }
					        }
					    %>
					</select>

		          <% } else { %>
		          <div id="formularioOrganizador">
		            <label for="descripcion" class="altaUsuLetra">Descripción:</label>
		            <input type="text" class="form-control" id="descripcion" name="descripcion"
		                   value="<%= organiza.getDescripcion() %>">
		            <label for="paginaWeb" class="altaUsuLetra">Página web:</label>
		            <input type="text" class="form-control" id="paginaWeb" name="paginaWeb"
		                   value="<%= organiza.getPaginaWeb() %>">
		          </div>
		          <% } %>
		
		          <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
		            <button type="submit" class="btn btn-primary me-md-2 c2 btn-sm negrita fuente1">Confirmar datos</button>
		          </div>
		          </div>
		        </div>
		      </form>
		    </div>
		  </div>
		</div>
<script src="js/bootstrap.bundle.js"></script>
</body>
</html>
