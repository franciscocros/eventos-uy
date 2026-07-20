<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Error</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .error-container {
            text-align: center;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            color: #dc3545; /* Bootstrap danger color */
        }
    </style>
</head>
<body>
    <div class="container error-container">
        <h1 class="error-code">404</h1>
        <h2>Página no encontrada</h2>
        <p class="lead">Lo sentimos, la página que buscas no existe o ha sido eliminada.</p>
        <a href="SrvEvento?destino=index" class="btn btn-primary btn-lg mt-3">Volver a la Página de Inicio</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>