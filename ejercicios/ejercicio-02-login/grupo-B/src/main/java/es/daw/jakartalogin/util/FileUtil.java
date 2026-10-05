package es.daw.jakartalogin.util;

import es.daw.jakartalogin.exception.NoEcontradoException;
import jakarta.servlet.ServletContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * Lee un fichero de texto pasado como argumento.
     * El fichero tiene diferentes líneas
     * @param rutaFichero ruta absoluta del fichero. Condición, debe ser accesible y estar en el .war
     * @return ArrayList con el contenido, línea a línea
     * @throws IOException Si no se encuentra el fichero...
     */
    public static List<String> leerFichero(ServletContext sc, String rutaFichero) throws NoEcontradoException, IOException{
        List<String> lista = new ArrayList<>();

        // kk!!! no pongo a fuego la ruta del fichero... quiero reutilizar!!!
        //InputStream is = getServletContext().getResourceAsStream("/WEB-INF/datos/tecnologia.txt");

        // getResourceAsStream abrir un flujo de bytes (inputStream)
        InputStream is = sc.getResourceAsStream(rutaFichero);

        if (is == null) {
            // Trabajar con excepciones propias. Crea una excepción checked llamada RutaNoEncontradaException!!!!
            throw new NoEcontradoException("No se encuentra el fichero " + rutaFichero);
        }

        // try con recursos...
        // BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        // InputStream: flujo de bytes
        // InputStreamReader: convierte esos bytes en caracteres, según el charset
        // BufferedReader: añade un buffer para leer línea a lína
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String linea;
            while( (linea = br.readLine()) != null){
                if (!linea.isBlank())
                    lista.add(linea.trim());
            }
        }
        return lista;
    }

}
