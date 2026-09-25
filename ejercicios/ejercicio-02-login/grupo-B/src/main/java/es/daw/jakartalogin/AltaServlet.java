package es.daw.jakartalogin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * https://github.com/profeMelola/DWES-01-2026-27/blob/main/ejercicios/alta-usuario.md
 */
@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AltaServlet.class.getName());

    private List<String> tecnologias = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            tecnologias = leerFichero("/WEB-INF/datos/tecnologias.txt");

            LOGGER.info("Lista de tecnologias: " + tecnologias);

        } catch (IOException e) {
            // Si no existe el fichero, quiero devolver un error.html!!!! (error.jsp con el mensaje dinámico)
//            request.setAttribute("mensajeError", e.getMessage());
//            request.getRequestDispatcher("/error.jsp").forward(request,response);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el fichero de texto tecnologias.txt y cargar en un ArrayList
        // List<String> tecnologias = new ArrayList<>();

        //
        String paramChungo = request.getParameter("chungo");
        paramChungo = paramChungo == null ? "" : paramChungo.strip();


//        try {
//            tecnologias = leerFichero("/WEB-INF/datos/tecnologias.txt");
//
//            LOGGER.info("Lista de tecnologias: " + tecnologias);
//
//        } catch (IOException e) {
//            // Si no existe el fichero, quiero devolver un error.html!!!! (error.jsp con el mensaje dinámico)
//            request.setAttribute("mensajeError", e.getMessage());
//            request.getRequestDispatcher("/error.jsp").forward(request,response);
//
//        }

        request.setAttribute("tecnologias", tecnologias);
        request.getRequestDispatcher("/formulario.jsp").forward(request,response);




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recoger los datos del formulario!!! son parámetros!!!
        String nombre = request.getParameter("nombre");
        LOGGER.info("El nombre: " + nombre);
        String email = request.getParameter("email");
        String tecnologia = request.getParameter("tecnologia");
        String nivel = request.getParameter("nivel");

        // PENDIENTE!!! hacer todas validaciones...
        //-------------------------------------
        // Si el nombre viene vacío tenemos que redirigir al formulario indicando un mensaje de aviso
        if (nombre.isBlank()) {
            request.setAttribute("mensajeError", "Majete!!! El nombre es obligatorio");
            //request.setAttribute("tecnologias", leerFichero("/WEB-INF/datos/tecnologias.txt"));
            request.setAttribute("tecnologias", tecnologias);
            request.getRequestDispatcher("/formulario.jsp").forward(request,response);
            return;
        }

        //--------------------------------------------
        // ---------------
        // Aquí estaría toda la lógica para comprobar que el usuario no exista en Bd, si no existe darlo alta por
        // tanto hacer un insert...
        // BD Relacional -> primero con JDBC, luego con JPA y los Repositories de Spring...
        //-----------




        request.setAttribute("tecnologia", tecnologia);
        request.setAttribute("nombre", nombre);
        request.setAttribute("email", email);
        request.setAttribute("nivel", nivel);

        request.getRequestDispatcher("/confirmacion.jsp").forward(request,response);



    }

    /**
     * Lee un fichero de texto pasado como argumento.
     * El fichero tiene diferentes líneas
     * @param rutaFichero ruta absoluta del fichero. Condición, debe ser accesible y estar en el .war
     * @return ArrayList con el contenido, línea a línea
     * @throws IOException Si no se encuentra el fichero...
     */
    private List<String> leerFichero(String rutaFichero) throws IOException{
        List<String> lista = new ArrayList<>();

        // kk!!! no pongo a fuego la ruta del fichero... quiero reutilizar!!!
        //InputStream is = getServletContext().getResourceAsStream("/WEB-INF/datos/tecnologia.txt");

        // getResourceAsStream abrir un flujo de bytes (inputStream)
        InputStream is = getServletContext().getResourceAsStream(rutaFichero);

        if (is == null) {
            // PENDIENTE!!! trabajar con excepciones propias. Crea una excepción checked llamada RutaNoEncontradaException!!!!
            throw new IOException("No se encuentra el fichero " + rutaFichero);
        }

        // try con recursos...
        // BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        // InputStream: flujo de bytes
        // InputStreamReader: convierte esos bytes en caracteres, según el charset
        // BufferedReader: añade un buffer para leer línea a lína
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
           String linea;
           while( (linea = br.readLine()) != null){
               LOGGER.info(linea);
               if (!linea.isBlank())
                   lista.add(linea.trim());
           }
        }
        // kk!!!! no finally!!!! siempre que podamos try..catch con recursos!!!
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