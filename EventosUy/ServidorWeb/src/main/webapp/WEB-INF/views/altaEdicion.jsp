
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.Ciudad"%>
<%@ page import="java.util.List" %>
<%



  List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventos");
  List<Ciudad> ciudades   = (List<Ciudad>) request.getAttribute("ciudades");
  
  String error = (String)request.getAttribute("error");

  String evento = ((String)request.getAttribute("old_evento")!=null)?(String)request.getAttribute("old_evento"):"";
  String ciudad = ((String)request.getAttribute("old_ciudad")!=null)?(String)request.getAttribute("old_ciudad"):"";
    
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta de Edicion de Evento</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>

	<div class="container mt-3 mb-3 text-center">
	  <div class="row justify-content-md-center">
	    <div class="col col-lg-6 fondosuave rt rb">
	      <div class="row text-center c2 cLetraRelleno rts">
	        <h5 class="negrita">Alta de Edición de Evento</h5>
	      </div>
         <form id="formEdicion" class="needs-validation" novalidate
	            action="SrvEdicion?destino=ingresarEdicion"
	            method="post" enctype="multipart/form-data">

	        <div class="mb-3 text-start">
			 <label for="evento" class="altaUsuLetra">Evento:</label>
				<select class="form-select" id="evento" name="evento" required>
				  <option disabled value="" <%= evento.isEmpty() ? "selected" : "" %>>Seleccione un evento</option>
				  <%
				    if (eventos != null) {
				      for (DtEvento e : eventos) {
				        String nombreEvento = e.getNombre();
				        String selected = nombreEvento.equals(evento) ? "selected" : "";
				  %>
				        <option value="<%= nombreEvento %>" <%= selected %>><%= nombreEvento %></option>
				  <%
				      }
				    }
				  %>
				</select>

	        
	          <label for="nombreEdicion" class="altaUsuLetra mt-3">Nombre de la edición:</label>
	          <input type="text" class="form-control" id="nombreEdicion" name="nombreEdicion" required>
	          <div class="invalid-feedback">Ingrese un nombre válido.</div>
	          <%if (error != null){ %>
	          <div class="alert alert-danger" role="alert" >
				 <%=error%>
			  </div>
	          <% } %>
	        
	          <label for="sigla" class="altaUsuLetra mt-3">Sigla:</label>
	          <input type="text" class="form-control" id="sigla" name="sigla"  value="${old_sigla}" required>
	          <div class="invalid-feedback">Ingrese una sigla.</div>

			
	          <label for="ciudad" class="altaUsuLetra mt-3">Ciudad:</label>
				<select class="form-select" id="ciudad" name="ciudad" required>
				  <option disabled value="" <%= ciudad.isEmpty() ? "selected" : "" %>>Seleccione una ciudad</option>
				  <%
				    if (ciudades != null) {
				      for (Ciudad c : ciudades) {
				        String selected = c.equals(ciudad)  ? "selected" : "";
				  %>
				        <option value="<%= c.getNombre() %>" <%= selected %>><%= c.getNombre() %></option>
				  <%
				      }
				    }
				  %>
				</select>

	          <label for="fechaInicio" class="altaUsuLetra mt-3">Fecha de inicio:</label>
	          <input type="date" class="form-control" id="fechaInicio" name="fechaInicio"  value="${old_inicio}" required>
	          <div class="invalid-feedback">Ingrese la fecha de inicio.</div>

	          <label for="fechaFin" class="altaUsuLetra mt-3">Fecha de fin:</label>
	          <input type="date" class="form-control" id="fechaFin" name="fechaFin"  value="${old_fin}" required>
	          <div class="invalid-feedback">Ingrese la fecha de fin.</div>
	          <div class="alert alert-danger" role="alert" id="fechaInvalida" style="display: none;">
				 "La fecha de fin no puede ser anterior a la fecha de inicio." 
			  </div>
	          
	          <label for="imagen" class="altaUsuLetra mt-3">Imagen:</label>
	          <input class="form-control form-control-sm" id="imagen" type="file" name="imagen">
	        </div>

	        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3 mb-3">
	          <button type="submit" class="btn btn-primary me-md-2 c2 btn-sm negrita fuente1">Confirmar</button>
	        </div>
	      </form>
	    </div>
	  </div>
	</div>

<script src="../js/bootstrap.bundle.js"></script>
<script>

(function(){
	  const form = document.getElementById('formEdicion');
	  const divError = document.getElementById('fechaInvalida');
	  divError.style.display = 'none';
	  
	  form.addEventListener('submit', function (e) {
	    if (!form.checkValidity()) {
	      e.preventDefault();
	      e.stopPropagation();
	    } else {
	      const finicio = document.getElementById('fechaInicio');
	      const ffin = document.getElementById('fechaFin');
	      const inicio = new Date(finicio.value);
	      const fin = new Date(ffin.value);

	      if (inicio && fin && fin < inicio) {
	        e.preventDefault();
	        const div = document.getElementById('fechaInvalida');
	        divError.style.display = 'block';
	        ffin.value = "";
	      }
	    }

	    form.classList.add('was-validated');
	  });
	})();
</script>
</body>
</html>

