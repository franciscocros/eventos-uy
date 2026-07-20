<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>

<%
	Set<String> instituciones = (Set<String>)request.getAttribute("instituciones");
	String existeUsuario = (request.getAttribute("error")!=null)? request.getAttribute("error").toString():"false";
    String oldUsuario = (String)request.getAttribute("old_usuario");
    if(oldUsuario == null || oldUsuario.isEmpty()) oldUsuario = "asistente";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Principal</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
	 <jsp:include page="../part/nav.jsp"></jsp:include>
     <div class="container mt-3 text-center ">
      <div class="row justify-content-md-center">
      	<div class="col col-lg-5 fondosuave rt rb">
			<div class="row text-center c2 cLetraRelleno  rts">
				<h5 class="negrita">Registro de nuevo usuario</h5>
			</div> 
			<form id="formUsuario" enctype="multipart/form-data" class="needs-validation" name="altaUsu" method="POST" action="SrvUsuario?destino=ingresarUsuario" novalidate>
				<div class="form-check form-check-inline text-center ">
				  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="asistente" checked onclick="mostrarAsistente()">
				  <label class="form-check-label altaUsuLetrabis" for="inlineRadio1">Asistente</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="organizador" onclick="mostrarOrganizador()">
				  <label class="form-check-label altaUsuLetrabis" for="inlineRadio2">Organizador</label>
				</div>
				<div class="row text-start" id="usuario">
				  <div class="mb-3">
				    <label for="nickname" class="altaUsuLetra text-start">nickname:</label>
				    <input type="text" class="form-control" id="nickname" aria-describedby="emailHelp" name="nickname" value="${old_nickname}" required>
				      <div class="invalid-feedback">
				        Por favor, ingrese un nickname.
				      </div>
					  <div class="valid-feedback">
				        
				      </div>
				    <label for="nombre" class="altaUsuLetra  text-start">Nombre:</label>
				    <input type="text" class="form-control" id="nombre" aria-describedby="emailHelp" name="nombre"  value="${old_nombre}" required>
				    <div class="invalid-feedback">
				        Por favor, ingrese un nombre.
				      </div>
				    <label for="email" class="altaUsuLetra text-start">Correo electrónico:</label>
				    <input type="email" class="form-control" id="email" aria-describedby="emailHelp" name="email"  value="${old_email}" required>
				    <div class="invalid-feedback">
				        Por favor, ingrese un email válido.
				    </div>
				    <label for="contra" class="altaUsuLetra">Contraseña:</label>
				    <input type="password" class="form-control" id="contra" aria-describedby="emailHelp" name="contra" required>
				    <div class="invalid-feedback">
				        Por favor, ingrese una contraseña.
				    </div>
				 	<label for="contrabis" class="altaUsuLetra">Repetir contraseña:</label>
				    <input type="password" class="form-control" id="contrabis" aria-describedby="emailHelp" name="contrabis" required>   
				    <div class="invalid-feedback">
				        Por favor,repita la contraseña.
				    </div>
					<div class="nocoinciden invalid-feedback">
				        Las contraseñas nocoinciden
				    </div>
					<p id="err" class="text-danger"> </p>
				    <label for="formFileSm" class="altaUsuLetra">Imagen de perfil:</label>
					<input class="form-control form-control-sm" id="formFileSm" type="file" name="imagen" >
				  </div>
				  <div class="formularioCliente" id="formularioAsistente">
				  	  <label for="apellido" class="altaUsuLetra">Apellido:</label>
						<div class="invalid-feedback">
							Por favor, ingrese un apellido.
						</div>
				  	  <input type="text" class="form-control" id="apellido" aria-describedby="emailHelp" name="apellido"  value="${old_apellido}">   
					  <label for="fechaNacimiento" class="altaUsuLetra">Fecha de nacimiento:</label>
					  <input type="date" class="form-control" id="fechaNacimiento" aria-describedby="emailHelp" name="fechaNacimiento"  value="${old_fechaNacimiento}">   
					  <label for="institucion" class="altaUsuLetra">Institucion:</label>
					  <select class="form-select" aria-label="Default select example" name="institucion">
						  <option value="3">ninguna</option>
						  <option selected>Seleccione una institucion</option>
						  <% if(instituciones != null){ %>
						   <% for(String inst : instituciones){ %>
						  		<option value="<%=inst%>"><%=inst%></option>
						   	<% } %>
						   <% } %>
					  </select>
				  </div>
			      <div class="formularioAerolinea" id="formularioOrganizador">
			      	  <label for="descripcion" class="altaUsuLetra">Descripcion:</label>
					  <input type="text" class="form-control" id="descripcion" aria-describedby="emailHelp" name="descripcion"  value="${old_descripcion}">   
					  <label for="paginaWeb" class="altaUsuLetra">pagina web:</label>
				  	  <input type="text" class="form-control" id="paginaWeb" aria-describedby="emailHelp" name="paginaWeb"  value="${old_paginaWeb}" formnovalidate>
			      </div>		
				  <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
				 	 <button type="submit" class="btn btn-primary me-md-2 c2 btn-sm negrita fuente1" value="ingresarDatosUsuario">confirmar datos</button> 
				  </div>
				  <div class="alert alert-danger" role="alert" id="errorDiv">
					</div>
				  <div class="col-6 align-self-center">
						&nbsp
			      </div>        
     			</div>
			</form>
			</div>
     	  </div>
     </div>
<script type="text/javascript"> 




(function () {
	  'use strict';

	  var forms = document.querySelectorAll('.needs-validation');
	  var contra = document.getElementById("contra");
	  var contrabis = document.getElementById("contrabis");
	  var fecha = document.getElementById("fecha");
	  var usuario = document.getElementById("usuario"); // ejemplo input usuario

	  Array.prototype.slice.call(forms).forEach(function (form) {
	    // Escuchar submit
	    form.addEventListener('submit', function (event) {
	      event.preventDefault();
	      event.stopPropagation();

	      var valido = true;

	      // 🔹 Validación contraseñas
	      var campoContra = document.querySelector('.nocoinciden');
	      if (contra.value !== "" && contrabis.value !== "" && contra.value !== contrabis.value) {
	        if (campoContra) campoContra.style.display = "block";
	        valido = false;
	      } else {
	        if (campoContra) campoContra.style.display = "none";
	      }

	      // 🔹 Validación fecha
	      var campoFecha = document.querySelector('.fechaInvalida');
	      if (fecha && fecha.value) {
	        var fechaIngresada = new Date(fecha.value);
	        var hoy = new Date();
	        hoy.setHours(0,0,0,0);
	        if (fechaIngresada >= hoy) {
	          if (campoFecha) campoFecha.style.display = "block";
	          valido = false;
	        } else {
	          if (campoFecha) campoFecha.style.display = "none";
	        }
	      }

	      // 🔹 Validación HTML5
	      if (!form.checkValidity()) {
	        valido = false;
	      }

	      // 🔹 Validación de usuario existente (mensaje del servidor)
	      var campoUsuario = document.querySelector('.usuarioExiste'); // span que muestra "usuario ya existe"
	      if (campoUsuario && campoUsuario.style.display === "block") {
	        valido = false;
	      }

	      form.classList.add('was-validated');

	      if (valido) {
	        form.submit();
	      }
	    }, false);

	    // 🔹 Ocultar mensajes del servidor al cambiar los campos
	    var campos = form.querySelectorAll('input');
	    campos.forEach(function(input){
	      input.addEventListener('input', function() {
	        var servidor = document.querySelector('.usuarioExiste');
	        if (servidor) servidor.style.display = "none";
	      });
	    });

	  });
	})();
	
	function errores(){
		var existe = "<%=existeUsuario%>";
		var divError = document.getElementById("errorDiv");
		if(existe === "false"){
			divError.style.display = "none";
		}else{
			divError.style.display = "inline";
			divError.textContent = existe;
		}
	}
	
	window.onload = errores;

	//onclick para elegir entre aerolinea y cliente
	function mostrarAsistente(){
    	var asistente = document.getElementById("formularioAsistente");
    	var organizador = document.getElementById("formularioOrganizador");
   		organizador.style.display = "none";
  		asistente.style.display = "inline";
  		
		var apellido = document.getElementById("apellido")
		var fechaDeNacimiento = document.getElementById('fechaNacimiento');
		var descripcion = document.getElementById("descripcion")
		descripcion.required = false;
		apellido.required = true;
		fechaDeNacimiento.required = true;
	}
	
	function mostrarOrganizador(){
    	var asistente = document.getElementById("formularioAsistente");
    	var organizador = document.getElementById("formularioOrganizador");
    	asistente.style.display = "none";
    	organizador.style.display = "inline";
    	
    	
		var descripcion = document.getElementById("descripcion")
		var apellido = document.getElementById("apellido")
		var fechaDeNacimiento = document.getElementById('fechaNacimiento');
		descripcion.required = true
		apellido.required = false;
		fechaDeNacimiento.required = false;
	}
	
	//Cargar por defecto ingreso de asistente
document.addEventListener('DOMContentLoaded', function() {
    var tipoUsuario = "<%= oldUsuario %>"; // viene del servidor: "asistente" o "organizador"

    if(tipoUsuario === "organizador") {
        document.getElementById("inlineRadio2").checked = true;
        mostrarOrganizador();
    } else {
        document.getElementById("inlineRadio1").checked = true;
        mostrarAsistente();
    }

    // Establece la fecha máxima como hoy
    const hoy = new Date().toISOString().split("T")[0];
    document.getElementById("fechaNacimiento").setAttribute("max", hoy);
});

</script>

</body>
</html>