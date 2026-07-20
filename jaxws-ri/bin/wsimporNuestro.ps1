$ubi = "D:\leonardog\TallerProgramacion2025\tpgr32\EventosUy\ServidorWeb"

# Generar clases
wsimport.bat -keep -verbose http://127.0.0.1:28099/usuario?wsdl
wsimport.bat -keep -verbose http://127.0.0.1:28099/evento?wsdl

# Reemplazar en los archivos
Get-ChildItem "main/java/webservice" -File | ForEach-Object {
    (Get-Content $_.FullName) -replace 'main.java.webservice','webservice' |
        Set-Content $_.FullName
}

# Copiar al destino
Copy-Item "main/java/webservice" -Destination $ubi -Recurse -Force
