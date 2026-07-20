<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>

<%
	String error = (String)request.getAttribute("error");
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
					<h5 class="negrita">Alta de institucion:</h5>
				</div> 
					<form id="formInst"  enctype="multipart/form-data" class="needs-validation" name="altaInst" method="POST" action="SrvUsuario?formulario=altaInst" novalidate>
						<div class="row text-start" id="institucion">
						  <div class="mb-3">
						    <label for="nombre" class="altaUsuLetra  text-start">Nombre:</label>
						    <input type="text" class="form-control" id="nombre" aria-describedby="emailHelp" name="nombre" required>
						    <div class="invalid-feedback">
						        Por favor, ingrese un nombre
						    </div>
				          <%if (error != null){ %>
				          <div class="alert alert-danger" role="alert" >
							 <%=error%>
						  </div>
				          <% } %>
						    <label for="desc" class="altaUsuLetra text-start">Descripción:</label>
						    <input type="text" class="form-control" id="desc" aria-describedby="emailHelp" name="desc" value="${old_descripcion}" required>
						    <div class="invalid-feedback">
						        Por favor, ingrese una descripción
						    </div>
						    <label for="sitio" class="altaUsuLetra">Sitio web:</label>
						    <input type="text" class="form-control" id="sitio" aria-describedby="emailHelp" name="sitio" value="${old_sitio}" required>
						    <div class="invalid-feedback">
						        Por favor, ingrese una dirección www
						    </div>
						 	
						    <label for="formFileSm" class="altaUsuLetra">Logo de la institucion:</label>
							<input class="form-control form-control-sm" id="formFileSm" type="file" name="imagen" >
						  </div>
						
					      	
						  <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
						 	 <button type="submit" class="btn btn-primary me-md-2 c2 btn-sm negrita fuente1" value="ingresarDatosUsuario">confirmar datos</button> 
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

	  // Selecciona todos los formularios que requieren validación de Bootstrap
	  const forms = document.querySelectorAll('.needs-validation');

	  Array.prototype.slice.call(forms).forEach(function (form) {
	    form.addEventListener('submit', function (event) {
	      if (!form.checkValidity()) {
	        // Si el formulario NO es válido, bloquea el envío
	        event.preventDefault();
	        event.stopPropagation();
	      }
	      // Agrega la clase de Bootstrap para mostrar los estilos de validación
	      form.classList.add('was-validated');
	    }, false);
	  });
	})();

</script>

</body>
</html>
