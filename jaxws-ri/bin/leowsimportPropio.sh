#!/bin/bash

ubiweb="/ens/home01/l/leonardo.gonzalez/aCompartido/tpgr32/EventosUy/ServidorWeb/src/main/java/webservice"
ubimobil="/ens/home01/l/leonardo.gonzalez/aCompartido/tpgr32/EventosUy/ServidorMobile/src/main/java/webservice"
# Generar clases a partir de los WSDL
#./wsimport.sh -keep -p webservice -verbose http://127.0.0.1:28099/usuario?wsdl
./wsimport.sh -keep -verbose http://127.0.0.1:9999/usuario?wsdl
./wsimport.sh -keep -verbose http://127.0.0.1:9999/evento?wsdl


#./wsimport.sh -keep -p -verbose http://127.0.0.1:28099/evento?wsdl

# Ajustar los paquetes generados
for f in main/java/webservice/*; do
  [ -f "$f" ] && sed -i 's/main.java.webservice/webservice/g' "$f"
  [ -f "$f" ] && sed -i 's/net.java.dev.jaxb.array.StringArray/com.sun.xml.fastinfoset.util.StringArray/g' "$f"
  [ -f "$f" ] && sed -i 's/net.java.dev.jaxb.array.ObjectFactory.class//g' "$f"
done
# Borro los archivos que tenga
rm -rf "$ubiweb"
rm -rf "$ubimobil"


# Copiar los archivos generados al destino

cp  -rv main/java/webservice "$ubiweb"
mv  -v main/java/webservice "$ubimobil"




