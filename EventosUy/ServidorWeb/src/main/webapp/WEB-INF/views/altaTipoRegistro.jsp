
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.DtEdicion"%>

<%
List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventos");
String eventoSel = (String) request.getAttribute("eventoSel");
List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("ediciones");

String error = (String) request.getAttribute("Error");
String exito = (String) request.getAttribute("alta");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta de Tipo de Registro</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>

	<div class="container mt-3 mb-3 text-center">
	  <div class="row justify-content-md-center">
	    <div class="col col-lg-6 fondosuave rt rb">
	      <div class="row text-center c2 cLetraRelleno rts">
	        <h5 class="negrita">Alta de Tipo de Registro</h5>
	      </div>
	
	      <form id="formUsuario" class="needs-validation" method="post" 
	            action="SrvTipoRegistro?destino=darAltaTipoRegistro" novalidate>
	        
	        <% if (error != null) { %>
	          <div id="errorDiv" class="alert alert-danger mt-2">
	            <%= error.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") %>
	          </div>
	        <% } %>

	        <% if (exito != null) { %>
	          <div id="successDiv" class="alert alert-success mt-2"><%= exito %></div>
	        <% } %>

	        <div class="mb-3 text-start">
			  <label for="evento" class="altaUsuLetra">Evento:</label>
			  <select class="form-select" id="evento" name="evento"
			          onchange="location.href='SrvTipoRegistro?destino=altaTipoRegistro&eventoSelect=' + encodeURIComponent(this.value)" required>
	            <option selected disabled value="">Seleccione un evento</option>
	            <% if (eventos != null) { 
	                 for (DtEvento eve : eventos) { %>	            
	                <option value="<%= eve.getNombre() %>" 
	                  <% if (eventoSel != null && eventoSel.equals(eve.getNombre())) { %>selected <% } %>>
	                  <%= eve.getNombre() %>
	                </option>
	              <% } 
	               } %>          
	          </select>
	        </div>

			<div class="mb-3 text-start">
			  <label for="edicion" class="altaUsuLetra">Edición:</label>
			  <select class="form-select" id="edicion" name="edicion" required>
			    <% if (ediciones != null) { %>
			 		<option selected disabled value="">Seleccione una edición</option>
			 		<% for (DtEdicion edi : ediciones) { %>
			 			<option value="<%= edi.getNombre() %>"><%= edi.getNombre() %></option>
			 		<% } %>
			 	<% } %>	            
	          </select>
	        </div>

	        <div class="mb-3 text-start">	
	          <label for="nombreTipo" class="altaUsuLetra">Nombre del tipo de registro:</label>
	          <input type="text" class="form-control" id="nombreTipo" name="nombreTipo" required>
	          <div class="invalid-feedback">Ingrese un nombre válido.</div>

	          <label for="descripcion" class="altaUsuLetra mt-2">Descripción:</label>
	          <input type="text" class="form-control" id="descripcion" name="descripcion" required>
	          <div class="invalid-feedback">Ingrese una descripción.</div>

	          <label for="costo" class="altaUsuLetra mt-2">Costo:</label>
	          <input type="text" class="form-control" id="costo" name="costo" required>
	          <div class="invalid-feedback">Ingrese un costo válido (número positivo, puede tener decimales).</div>

	          <label for="cupo" class="altaUsuLetra mt-2">Cupo:</label>
	          <input type="text" class="form-control" id="cupo" name="cupo" required>
	          <div class="invalid-feedback">Ingrese un cupo válido (entero positivo).</div>
	        </div>

	        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
	          <button type="submit" name="aceptar" 
	                  class="btn btn-primary me-md-2 mb-2 c2 btn-sm negrita fuente1">Confirmar</button>
	        </div>
	      </form>
	    </div>
	  </div>
	</div>

<script>
(function () {
  'use strict';

  const form = document.getElementById('formUsuario');
  const costoInput = form.elements['costo'];
  const cupoInput = form.elements['cupo'];

  function validateCupo() {
    const val = cupoInput.value.trim();
    // Solo entero positivo (sin decimales)
    if (!/^[1-9]\d*$/.test(val)) {
      cupoInput.setCustomValidity('El cupo debe ser un número entero positivo');
    } else {
      cupoInput.setCustomValidity('');
    }
  }

  function validateCosto() {
    const val = costoInput.value.trim();
    // Flotante positivo (admite 0.5, 1, 1.25, etc.)
    if (!/^(?:\d+)(?:\.\d+)?$/.test(val) || parseFloat(val) <= 0) {
      costoInput.setCustomValidity('El costo debe ser un número positivo (puede tener decimales)');
    } else {
      costoInput.setCustomValidity('');
    }
  }

  // Validar al enviar
  form.addEventListener('submit', function (event) {
    validateCupo();
    validateCosto();

    form.classList.add('was-validated');
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
      const firstInvalid = form.querySelector(':invalid');
      if (firstInvalid) firstInvalid.focus();
    }
  });

})();
</script>

</body>
</html>
