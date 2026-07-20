<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.*" %>

<%
    String error = (String) request.getAttribute("error");

List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventos");
List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("ediciones");
List<String> instituciones = (List<String>) request.getAttribute("instituciones");

    String evento_old = (String) request.getAttribute("evento_old");
    String edicion_old = (String) request.getAttribute("edicion_old");
    String tiporeg_old = (String) request.getAttribute("tiporeg_old");
    String inst_old = (String) request.getAttribute("inst_old");
    String nivel_old = (String) request.getAttribute("nivel_old");
    String cantreg_old = (String) request.getAttribute("cantreg_old");
    String aporte_old = (String) request.getAttribute("aporte_old");
    String codigo_old = (String) request.getAttribute("codigo_old");
    
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta de patrocinio</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
<jsp:include page="../part/nav.jsp"></jsp:include>

<div class="container mt-3 text-center ">
    <div class="row justify-content-md-center">
        <div class="col col-lg-5 fondosuave rt rb">
            <div class="row text-center c2 cLetraRelleno rts">
                <h5 class="negrita">Alta de patrocinio:</h5>
            </div> 
			
            <form id="formInst" class="needs-validation" name="altaPtr" method="POST" action="SrvPatrocinio?formulario=altaPatrocinio" novalidate>
			
                <!-- Evento -->
                <div class="mb-3 text-start">
                <% if (error!=null && error.equals("El numero ingresado es mas grande de lo permitido")){ %><div id="errorDiv" class="alert alert-danger mt-2" > <%= error.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") %></div><%} %>
                    <label for="evento" class="altaUsuLetra">Evento:</label>
                    <select class="form-select" id="evento" name="evento" required>
                        <option disabled value="">Seleccione un evento</option>
                        <% if (eventos != null) { 
                            for (DtEvento eve : eventos) { 
                                String selected = (evento_old != null && evento_old.equals(eve.getNombre())) ? "selected" : "";
                        %>
                            <option value="<%= eve.getNombre() %>" <%= selected %>><%= eve.getNombre() %></option>
                        <% } } %>
                    </select>
                </div>

                <!-- Edición -->
                <div class="mb-3 text-start">
                    <label for="edicion" class="altaUsuLetra">Edicion:</label>
                    <select class="form-select" id="edicion" name="edicion" required>
                        <option disabled value="">Seleccione una edición</option>
                        <% if (ediciones != null) {
                            for (DtEdicion edi : ediciones) {
                                String selected = (edicion_old != null && edicion_old.equals(edi.getNombre())) ? "selected" : "";
                        %>
                            <option value="<%= edi.getNombre() %>" <%= selected %>><%= edi.getNombre() %></option>
                        <% } } %>
                    </select>
                </div>

                <!-- Tipo de registro -->
                <div class="mb-3 text-start">
                    <label for="tiporeg" class="altaUsuLetra">Tipo de registro:</label>
                    <select class="form-select" id="tiporeg" name="tiporeg" required>
                        <option disabled value="">Seleccione el tipo de registro</option>
                        <option value="Completo" <%= "Completo".equals(tiporeg_old) ? "selected" : "" %>>Completo</option>
                        <option value="Básico" <%= "Básico".equals(tiporeg_old) ? "selected" : "" %>>Básico</option>
                    </select>
                </div>

                <!-- Institución -->
                <div class="mb-3 text-start">
                    <label for="inst" class="altaUsuLetra">Institucion:</label>
                    <select class="form-select" id="inst" name="inst" required>
                        <option disabled value="">Seleccione institución patrocinadora</option>
                        <% if (instituciones != null) {
                            for (String ins : instituciones) {
                                String selected = (inst_old != null && inst_old.equals(ins)) ? "selected" : "";
                        %>
                            <option value="<%= ins %>" <%= selected %>><%= ins %></option>
                        <% } } %>
                    </select>
                </div>

                <% if (error != null && error.equals("Ya tiene este patrocinio, modificar datos? ")) { %>
                    <div class="alert alert-danger" role="alert">
                        <%= error %>
                    </div>
                <% } %>

                <!-- Nivel -->
                <div class="mb-3 text-start">
                    <label for="nivel" class="altaUsuLetra">Nivel:</label>
                    <select class="form-select" id="nivel" name="nivel" required>
                        <option disabled value="">Seleccione nivel del patrocinio</option>
                        <option value="Platino" <%= "Platino".equals(nivel_old) ? "selected" : "" %>>Platino</option>
                        <option value="Oro" <%= "Oro".equals(nivel_old) ? "selected" : "" %>>Oro</option>
                        <option value="Plata" <%= "Plata".equals(nivel_old) ? "selected" : "" %>>Plata</option>
                        <option value="Bronce" <%= "Bronce".equals(nivel_old) ? "selected" : "" %>>Bronce</option>
                    </select>
                </div>

                <!-- Cantidad de registros -->
                <div class="mb-3 text-start">
                    <label for="cantreg" class="altaUsuLetra">Cantidad de registros gratuitos:</label>
                    <input type="number" class="form-control" id="cantreg" name="cantreg" min="0" required 
                           value="<%= (cantreg_old != null) ? cantreg_old : "" %>">
                    <div class="invalid-feedback">Ingrese la cantidad de registros a otorgar</div>
                </div>
                <% if (error != null && error.equals("Los registros gratuitos a otorgar supera el 20 % del aporte economico, modificar? ")) { %>
                    <div class="alert alert-danger" role="alert">
                        <%= error %>
                    </div>
                <% } %>

                <!-- Aporte económico -->
                <div class="mb-3 text-start">
                    <label for="aporte" class="altaUsuLetra">Ingrese el aporte economico:</label>
                    <input type="number" class="form-control" id="aporte" name="aporte" required 
                           value="<%= (aporte_old != null) ? aporte_old : "" %>">
                    <div class="invalid-feedback">Ingrese el aporte</div>
                </div>

                <!-- Código -->
                <div class="mb-3 text-start">
                    <label for="codigo" class="altaUsuLetra">Ingrese el codigo de patrocinio:</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" required 
                           value="<%= (codigo_old != null) ? codigo_old : "" %>">
                    <div class="invalid-feedback">Ingrese el codigo</div>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
                    <button type="submit" class="btn btn-primary me-md-2 mb-2 c2 btn-sm negrita fuente1">Confirmar</button>
                </div>

            </form>
		</div>
	</div>
</div>
<script>
// Activar validación de Bootstrap 5
(() => {
  'use strict'
  const forms = document.querySelectorAll('.needs-validation')
  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }
      form.classList.add('was-validated')
    }, false)
  })
})()

// Se convierte el set de ediciones en un arreglo JS con tipos de registro
const ediciones = [
  <% if(ediciones != null){
       for(DtEdicion ed : ediciones){ %>
         {
           nombre: "<%= ed.getNombre() %>",
           evento: "<%= ed.getEvento() %>",
           tiposRegistros: [
             <% for(String t : ed.getTiposRegistros()) { %>
               "<%= t %>",
             <% } %>
           ]
         },
  <% } } %>
];

const selectEvento = document.getElementById("evento");
const selectEdicion = document.getElementById("edicion");
const selectTipoReg = document.getElementById("tiporeg");

function limpiarEdiciones() {
  selectEdicion.innerHTML = '<option selected disabled value="">Seleccione una edicion</option>';
}
function limpiarTiposReg() {
  selectTipoReg.innerHTML = '<option selected disabled value="">Seleccione el tipo de registro</option>';
}

// Evento → Edición
function cargarEdiciones(eventoSeleccionado) {
  limpiarEdiciones();
  limpiarTiposReg();

  const edFiltradas = ediciones
    .filter(e => e.evento === eventoSeleccionado)
    .sort((a, b) => a.nombre.localeCompare(b.nombre));

  if (edFiltradas.length === 0) {
    const opt = document.createElement("option");
    opt.disabled = true;
    opt.textContent = "No hay ediciones para este evento";
    selectEdicion.appendChild(opt);
    return;
  }

  // Cargar todas las ediciones
  edFiltradas.forEach(e => {
    const opt = document.createElement("option");
    opt.value = e.nombre;
    opt.textContent = e.nombre;
    selectEdicion.appendChild(opt);
  });

  // Seleccionar automáticamente la primera edición
  selectEdicion.options[1].selected = true;
  const primeraEd = edFiltradas[0];

  // Cargar tipos de registro de esa primera edición
  if (primeraEd.tiposRegistros.length > 0) {
    limpiarTiposReg();
    primeraEd.tiposRegistros
      .sort((a, b) => a.localeCompare(b))
      .forEach(t => {
        const opt = document.createElement("option");
        opt.value = t;
        opt.textContent = t;
        selectTipoReg.appendChild(opt);
      });
  }
}

selectEvento.addEventListener("change", function() {
  cargarEdiciones(this.value);
});

// Edición Tipo de registro
selectEdicion.addEventListener("change", function() {
  const edSeleccionada = this.value;
  limpiarTiposReg();

  const ed = ediciones.find(e => e.nombre === edSeleccionada);
  if (!ed || ed.tiposRegistros.length === 0) {
    const opt = document.createElement("option");
    opt.disabled = true;
    opt.textContent = "No hay tipos de registro para esta edición";
    selectTipoReg.appendChild(opt);
  } else {
    ed.tiposRegistros
      .sort((a, b) => a.localeCompare(b))
      .forEach(t => {
        const opt = document.createElement("option");
        opt.value = t;
        opt.textContent = t;
        selectTipoReg.appendChild(opt);
      });
  }
});

// Al cargar la página: seleccionar el primer evento y sus ediciones
window.addEventListener("DOMContentLoaded", () => {
  const primerEvento = selectEvento.options[1]; // salta la opción "Seleccione un evento"
  if (primerEvento) {
    primerEvento.selected = true;
    cargarEdiciones(primerEvento.value);
  }
});
</script>

</body>
</html>