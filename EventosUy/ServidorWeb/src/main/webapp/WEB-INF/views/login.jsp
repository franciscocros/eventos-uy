<%

String error = (String)request.getAttribute("error");

%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio de Sesión</title>
</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>
	<div class="container text-center mt-3">
		<div class="row justify-content-center">
			<div class="col col-lg-4 col-8 col-sm-7">
				<form id="formUsuario" class="needs-validation" name="login" method="POST" action="SrvUsuario?formulario=login" novalidate>
				  <div class="text-center c2 cLetraRelleno bordes rts">
					<h5 class="negrita">Inicio de sesión</h5>
				  </div>
				  <div class="fondosuave rb bordes p-3">
					  <div class="mb-3">
					    <input type="text" class="form-control" id="nickname" placeholder="Nickname o Email" name="usuario" required>
					    <div class="invalid-feedback">Ingrese su nickname o email.</div>
					  </div>
					  <div class="mb-3">
					    <input type="password" class="form-control" id="contra" placeholder="Contraseña" name="contrasenia" required>
					    <div class="invalid-feedback">Ingrese su contraseña.</div>
					  </div>
					  <div class="mb-3 text-end">
					    <button type="submit" class="btn btn-primary c2 fuente1">Ingresar</button>
					  </div>
					  <div id="errorDiv" class="alert alert-danger mt-2" style="display:none;">datos incorrectos</div>
				  </div>
				</form>
			</div>
		</div>
	</div>

<script src="../js/bootstrap.bundle.js"></script>
<script>

window.addEventListener("DOMContentLoaded", function() {
    <% if (request.getAttribute("error") != null) { %>
      document.getElementById("errorDiv").style.display = "block";
    <% } %>
  });

(function () {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  var forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.prototype.slice.call(forms)
	    .forEach(function (form) {
	      form.addEventListener('submit', function (event) {
	        if (!form.checkValidity()) {
	          event.preventDefault()
	          event.stopPropagation()
	        }else{
				this.submit();
	        }

	        form.classList.add('was-validated')
	      }, false)
	    })
	})()
	

</script>
</body>
</html>