package es.daw.jakartalogin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AltaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el fichero de texto tecnologias.txt y cargar en un ArrayList
        List<String> tecnologias = leerFichero();


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



    }

    private List<String> leerFichero(String rutaFichero) throws IOException{
        List<String> lista = new ArrayList<>();

        //InputStream is = getServletContext().getResourceAsStream("/WEB-INF/datos/tecnologia.txt");
        InputStream is = getServletContext().getResourceAsStream(rutaFichero);

        if (is == null)
            throw new IOException("No se encuentra el fichero "+rutaFichero);

        // try con recursos...
        // BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
           String linea;
           while( (linea = br.readLine()) != null){
               if (!linea.isBlank())
                   lista.add(linea.trim());
           }
        }
//        catch (IOException e){
//            LOGGER.info(e.getMessage());
//        }
//        finally{
//            // se cerraban recursos.... viejuno!!!!!
//            try {
//                br.close();
//            } catch (IOException e) {
//                LOGGER.info(e.getMessage());
//            }
//        }

        return lista;
    }



}