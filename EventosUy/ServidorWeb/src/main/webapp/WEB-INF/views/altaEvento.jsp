<%@ page import="java.util.List" %>
<%
	List<String> categorias = (List<String>) request.getAttribute("listaCategorias");

    String errorNombre = (request.getAttribute("error") != null) ? request.getAttribute("error").toString() : "";
    String errorCategoria = (request.getAttribute("errorCategoria") != null) ? request.getAttribute("errorCategoria").toString() : "";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta de evento</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
    <jsp:include page="../part/nav.jsp"></jsp:include>      

    <div class="container mt-1 text-center">
        <div class="row justify-content-md-center">
            <div class="col col-lg-5 fondosuave rt rb">
                <div class="row text-center c2 cLetraRelleno rts">
                    <h2>Alta de evento</h2>
                </div>

                <form id="formUsuario" class="needs-validation" novalidate 
                      action="SrvEvento?destino=ingresarEvento" method="post" enctype="multipart/form-data">
                    <div class="row text-start">
                        <div class="mb-3">

                            <!-- Nombre -->
                            <label class="altaUsuLetra">Nombre:</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required
                                   value="<%= (errorNombre.isEmpty() && request.getAttribute("old_nombre") != null) 
									          ? request.getAttribute("old_nombre").toString() 
									          : "" %>">
                            <p class="invalid-feedback" style="display: <%= !errorNombre.isEmpty() ? "inline" : "none" %>;">
                                <%= errorNombre %>
                            </p>

                            <!-- Descripción -->
                            <label class="altaUsuLetra">Descripción:</label>
                            <input type="text" class="form-control" id="desc" name="desc" required
                                   value="<%= request.getAttribute("old_desc") != null ? request.getAttribute("old_desc") : "" %>">

                            <!-- Sigla -->
                            <label class="altaUsuLetra">Sigla:</label>
                            <input type="text" class="form-control" id="sigla" name="sigla" required
                                   value="<%= request.getAttribute("old_sigla") != null ? request.getAttribute("old_sigla") : "" %>">
						</div>
						
						<!-- Categorías -->
						<div class="mb-3 text-start">
						    <label class="altaUsuLetra mt-2">Categorías:</label>
						    <div class="border rounded p-2" id="categorias-container">
						        <% 
						        if (categorias != null) {
						            for(String cat : categorias){ 
						        %>
						            <div class="form-check text-start">
						                <input class="form-check-input" type="checkbox" name="categorias" id="cat_<%=cat%>" value="<%=cat%>"
						                    <%= (request.getAttribute("old_categorias") != null && 
						                         java.util.Arrays.asList((String[])request.getAttribute("old_categorias")).contains(cat)) 
						                         ? "checked" : "" %>>
						                <label class="form-check-label" for="cat_<%=cat%>"><%=cat%></label>
						            </div>
						        <% 
						            }
						        } 
						        %>
						    </div>
						    <p class="invalid-feedback" id="errorCategoria" 
						       style="display: <%= !errorCategoria.isEmpty() ? "inline" : "none" %>;">
						       <%= !errorCategoria.isEmpty() ? errorCategoria : "Debe seleccionar al menos una categoría." %>
						    </p>
						</div>
												
                            <!-- Imagen -->
                            <label class="altaUsuLetra mt-2">Imagen de perfil:</label>
                            <input class="form-control form-control-sm" id="formFileSm" type="file" name="imagen">

                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
                            <button type="submit" class="btn btn-primary me-md-2 btn-sm">Confirmar datos</button> 
                        </div>
                        <div class="col-6 align-self-center">&nbsp;</div>    
                        </form>    
                    </div>
            </div>
        </div>

<script type="text/javascript"> 
(function () {
  'use strict'
  var forms = document.querySelectorAll('.needs-validation')
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        // Validación estándar de Bootstrap
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        } else {
          // Validar que al menos una categoría esté seleccionada
          const checkboxes = document.querySelectorAll('input[name="categorias"]:checked');
          if (checkboxes.length === 0) {
            event.preventDefault();
            document.getElementById("errorCategoria").style.display = "inline";
          }
        }
        form.classList.add('was-validated')
      }, false)
    })
})()
</script>

<script src="../js/bootstrap.bundle.js"></script>

</body>
</html>

