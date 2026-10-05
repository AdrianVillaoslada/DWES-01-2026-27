package es.daw.jakartalogin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.daw.jakartalogin.exception.NoEcontradoException;
import es.daw.jakartalogin.util.FileUtil;
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
            // cargamos la lista de tecnologías leyendo el fichero de texto una única vez!!!!
            tecnologias = FileUtil.leerFichero(getServletContext(),"/WEB-INF/datos/tecnologias.txt");

            LOGGER.info("Lista de tecnologias: " + tecnologias);

        } catch (NoEcontradoException | IOException e) {
            // Si no existe el fichero, quiero devolver un error.html!!!! (error.jsp con el mensaje dinámico)
//            request.setAttribute("mensajeError", e.getMessage());
//            request.getRequestDispatcher("/error.jsp").forward(request,response);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, e.getMessage(), e);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el fichero de texto tecnologias.txt y cargar en un ArrayList
        // List<String> tecnologias = new ArrayList<>();

        // Prueba para verificar que si leo un parámetro que no se envía vía get, viene a null
        // y si uso un método directamente en un objeto null me da un NullPointerException
        String paramChungo = request.getParameter("chungo");
        paramChungo = paramChungo == null ? "" : paramChungo.strip();


        // Comentado porque hemos pasado la lógica al método init!!!
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
        //String nivel = request.getParameter("nivel");
        String[] niveles = request.getParameterValues("nivel"); // multiselección

        // PENDIENTE!!! hacer todas validaciones...
        //-------------------------------------
        // Si el nombre viene vacío tenemos que redirigir al formulario indicando un mensaje de aviso
        if (nombre.isBlank()) {
            request.setAttribute("mensajeError", "Majete!!! El nombre es obligatorio");
            //request.setAttribute("tecnologias", leerFichero("/WEB-INF/datos/tecnologias.txt"));
            request.setAttribute("tecnologias", tecnologias);
            //request.setAttribute("email", email); // no es necesario mandarlo como atributo porque puedo usar param.email

            request.setAttribute("nivelesLista", List.of(niveles)); // otra opción
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
        request.setAttribute("nivel", Arrays.toString(niveles));

        request.getRequestDispatcher("/confirmacion.jsp").forward(request,response);



    }




}