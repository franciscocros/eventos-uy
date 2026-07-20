package main.java.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        String fileName = "eventosuyCentral.properties";
        try {

            URL resourceUrl = new URL ("file:/ens/devel01/tpgr32/"+ fileName);

        	// Usa la forma más segura: carga desde la raíz del classpath
            //URL resourceUrl = Config.class.getResource("/" + fileName);
            if (resourceUrl == null) {
                throw new RuntimeException(" No se encontró " + fileName + " en el classpath");
            }

            System.out.println("Cargando propiedades desde: " + resourceUrl);

            try (InputStream is = resourceUrl.openStream()) {
                props.load(is);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error cargando " + fileName + ": " + e.getMessage(), e);
        }
    }

    // Devuelve una propiedad específica
    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            System.err.println("Propiedad no encontrada: " + key);
        }
        return value;
    }

    // Construye la URL completa para publicar un WS
    public static String buildUrl(String port, String path) {
        String host = get("WS_HOST");
        return "http://" + host + ":" + port + "/" + path;
    }
}
