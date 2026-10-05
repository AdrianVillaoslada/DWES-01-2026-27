package es.daw.jakartalogin.exception;

public class NoEcontradoException extends Exception{
    public NoEcontradoException(String mensaje){
        super("MAJETE!!! "+mensaje);
    }
}
