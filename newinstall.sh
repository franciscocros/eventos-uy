#!/usr/bin/env bash

# ==== Colores para la salida ====
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
NC='\033[0m' # Sin color

# ==== Pedir ubicación del servidor Apache Tomcat ====
read -rp "Ingrese la ubicación raíz de Apache Tomcat (por ejemplo /opt/tomcat): " apache


# ==== Empaquetar la aplicación de escritorio ====
echo -e "${BLUE}Empaquetando la aplicación de escritorio...${NC}"
if cd EventosUy/ServidorCentral && mvn clean package -DskipTests; then
    mv target/EventosUy-jar-with-dependencies.jar ../.. || exit 1
    cd ../.. || exit 1
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}Error al empaquetar la aplicación de escritorio.${NC}"
    exit 1
fi

# ==== Empaquetar el sitio web ====
echo -e "${BLUE}Empaquetando el sitio web...${NC}"
if cd EventosUy/ServidorWeb && mvn clean package -DskipTests; then
    mv target/EventosUy.war "$apache/webapps/" || exit 1
    cd ../.. || exit 1
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}Error al empaquetar el sitio web.${NC}"
    exit 1
fi

# ==== Empaquetar el sitio móvil ====
echo -e "${BLUE}Empaquetando el sitio móvil...${NC}"
if cd EventosUy/ServidorMobile && mvn clean package -DskipTests; then
    mv target/MovilEventosUy.war "$apache/webapps/" || exit 1
    cd ../.. || exit 1
    echo -e "${GREEN}[OK]${NC}"
else
    echo -e "${RED}Error al empaquetar el sitio móvil.${NC}"
    exit 1
fi

# ==== Ejecutar aplicación de escritorio ====
echo -e "${BLUE}Iniciando la aplicación de escritorio...${NC}"
java -jar EventosUy-jar-with-dependencies.jar &
echo -e "${GREEN}Aplicación iniciada.${NC}"

