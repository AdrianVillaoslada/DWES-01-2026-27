package es.daw.jakartalogin.util;

import es.daw.jakartalogin.exception.TxtNoEncontradoException;
import jakarta.servlet.ServletContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    /**
     * Lee un fichero de texto
     * @param pathFile ruta al fichero. Debe ser absoluta y encontrarse protegida en WEB-INF
     * @return List de cadena de texto de cada linea
     * @throws IOException si no existe la ruta
     */
    public static List<String> leerFichero(ServletContext sc, String pathFile) throws TxtNoEncontradoException, IOException{
        List<String> lista = new ArrayList<>();

        // getResourceAsStream abre un flujo de bytes (InputStream)
        InputStream is = sc.getResourceAsStream(pathFile);

        // PENDIENTE!!! En vez de propagar IOException, implementar una excepción propia de tipo checked
        // llamada FicheroTxtNoEncontradoException...
        if ( is == null)
            throw new TxtNoEncontradoException("No se encuentra el fichero de texto: "+pathFile);


        // try con recursos: todo lo que se declara dentro del paréntesis se cierra automáticamente (close())
        // InputStream -> bytes en crudo
        // InputStreamReader -> convierte esos bytes en caracteres según el charset
        // BufferedReader -> añade un buffer para leer línea a línea
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String linea;
            while( (linea = br.readLine()) != null){
                if (!linea.isBlank())
                    //lista.add(linea.trim());
                    lista.add(linea.strip());

            }
        }
        return lista;
    }

    public static List<String> leerFicheroAPIStream(ServletContext sc, String pathFile) throws TxtNoEncontradoException, IOException{
        List<String> lista = new ArrayList<>();

        // getResourceAsStream abre un flujo de bytes (InputStream)
        InputStream is = sc.getResourceAsStream(pathFile);

        // PENDIENTE!!! En vez de propagar IOException, implementar una excepción propia de tipo checked
        // llamada FicheroTxtNoEncontradoException...
        if ( is == null)
            throw new TxtNoEncontradoException("No se encuentra el fichero de texto: "+pathFile);


        // try con recursos: todo lo que se declara dentro del paréntesis se cierra automáticamente (close())
        // InputStream -> bytes en crudo
        // InputStreamReader -> convierte esos bytes en caracteres según el charset
        // BufferedReader -> añade un buffer para leer línea a línea
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){

//            String linea;
//            while( (linea = br.readLine()) != null){
//                if (!linea.isBlank())
//                    //lista.add(linea.trim());
//                    lista.add(linea.strip());
//
//            }

            // Obtengo un Stream<String>
            lista = br.lines()
                    .filter(line -> !line.isBlank())
                    //.filter(String::isBlank)
                    //.map(String::strip)
                    .map(line -> line.strip())
                    .toList();

        }
        return lista;
    }

    public static List<String> leerFicheroNIO(ServletContext sc, String pathFile) throws TxtNoEncontradoException, IOException{
        List<String> lista = new ArrayList<>();

        String rutaReal = sc.getRealPath(pathFile);

        // PENDIENTE!!! En vez de propagar IOException, implementar una excepción propia de tipo checked
        // llamada FicheroTxtNoEncontradoException...
        if ( rutaReal == null)
            throw new TxtNoEncontradoException("No se encuentra el fichero de texto: "+pathFile);


        try (Stream<String> lineas = Files.lines(Paths.get(rutaReal), StandardCharsets.UTF_8)) {
            lista = lineas.filter(line -> !line.isBlank())
                    .map(String::trim)
                    .toList();
        }
        return lista;
    }


}
