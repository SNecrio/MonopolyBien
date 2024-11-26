package excepcions;

public class GeneralException extends Exception {
    // Constructor por defecto
    public GeneralException() {
        super("Ha ocurrido un error no identificado.");
    }

    // Constructor con mensaje
    public GeneralException(String mensaje) {
        super(mensaje);
    }
}