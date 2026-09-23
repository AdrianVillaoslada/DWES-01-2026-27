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

        try {
            List<String> tecnologias = leerFichero("/WEB-INF/datos/tecnologiassss.txt");
            LOGGER.info(tecnologias.toString());

            //String opcional = request.getParameter("opcional").trim();

            request.setAttribute("tecnologias",tecnologias);

            request.getRequestDispatcher("/formulario.jsp").forward(request,response);

        }catch (IOException e){
            // PENDIENTE!!!! enviar a una paǵina error.jsp de error el mensaje de error...
            LOGGER.severe(e.getMessage());

            // Añadir como atributo el mensaje de error...
            request.setAttribute("mensajeError",e.getMessage());

            request.getRequestDispatcher("/error.jsp").forward(request,response);

        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Leer todos los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String tecnologia = request.getParameter("tecnologia");
        String nivel = request.getParameter("nivel");

        LOGGER.info("nombre: "+nombre);
        LOGGER.info(String.format("email: %s",email));
        LOGGER.info(String.format("tecnologia: %s",tecnologia));
        LOGGER.info(String.format("nivel: %s",nivel));

        // Validar los parámetros!!!
        nombre = nombre.trim();
        email = email == null ? null : email.trim();
        tecnologia = tecnologia == null ? null : tecnologia.trim();
        nivel = nivel == null ? null : nivel.trim();

        // -----------------
        // EN ESTE PUNTO SE COMPROBARÍA EN BD SI EXISTE UN USUARIO CON ESE NOMBRE... ETC...
        // CONSIDERAMOS QUE TODO OK!!! LA LÓGICA DE NEGOCIO ES MUY SENCILLITA!!!!!
        // ------------------------

        // PENDIENTE!!! si el nombre viene vacío que vuelva a la página del formulario indicando que
        // el nombre no puede estar vacío...


        // Pendiente enviar a la jsp como atributos los parámetros..
        request.setAttribute("nombre",nombre);
        request.setAttribute("email",email);
        request.setAttribute("tecnologia",tecnologia);
        request.setAttribute("nivel",nivel);

        // Pendiente llamar a la página confirmacion.jsp

        request.getRequestDispatcher("/confirmacion.jsp").forward(request,response);

    }

    /**
     * Lee un fichero de texto
     * @param pathFile ruta al fichero. Debe ser absoluta y encontrarse protegida en WEB-INF
     * @return List de cadena de texto de cada linea
     * @throws IOException si no existe la ruta
     */
    private List<String> leerFichero(String pathFile) throws IOException{
        List<String> lista = new ArrayList<>();

        InputStream is = getServletContext().getResourceAsStream(pathFile);

        // PENDIENTE!!! En vez de propagar IOException, implementar una excepción propia de tipo checked
        // llamada FicheroTxtNoEncontradoException...
        if ( is == null)
            throw new IOException("No se encuentra el fichero de texto: "+pathFile);


        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String linea;
            while( (linea = br.readLine()) != null){
                if (!linea.isBlank())
                    lista.add(linea.trim());

            }
        }
        return lista;
    }

}