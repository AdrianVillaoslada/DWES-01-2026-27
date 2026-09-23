package es.daw.initdemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Servlet para comprobar que el método init se ejecuta una solo vez porque el Servlet está en memoria
 * https://github.com/profeMelola/DWES-01-2026-27/blob/main/ejercicios/init.md
 */
@WebServlet("/init-demo")
public class InitDemoServlet extends HttpServlet {

    private String horaInicializacion;
    private int contadorPeticiones = 0;

    private final AtomicInteger contadorPeticionesAtomic =
            new AtomicInteger(0);

    private static final Logger logger = Logger.getLogger(InitDemoServlet.class.getName());

    @Override
    public void init() throws ServletException {
        horaInicializacion = LocalDateTime.now().toString();
        System.out.println(">>> init() ejecutado, instancia " + this.hashCode());
        logger.info(">>> init() ejecutado, instancia " + this.hashCode());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        contadorPeticiones++;
        System.out.println(">>> doGet() num " + contadorPeticiones + ", instancia " + this.hashCode());
        logger.info(">>> doGet() num " + contadorPeticiones + ", instancia " + this.hashCode());


        request.setAttribute("horaInit", horaInicializacion);
        request.setAttribute("contador", contadorPeticiones);
        request.setAttribute("instancia", this.hashCode());

        int numeroPeticion = contadorPeticionesAtomic.incrementAndGet();
        request.setAttribute("contadorAtomic", numeroPeticion);

        request.getRequestDispatcher("/resultado.jsp").forward(request, response);
    }

//    @Override
//    public void destroy() {
//        super.destroy();
//    }
}