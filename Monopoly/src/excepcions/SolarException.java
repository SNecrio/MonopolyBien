package excepcions;

public class SolarException extends CasillaException {
    // Constructor por defecto
    public SolarException() {
        super("Ha ocurrido un error no identificado.");
    }

    // Constructor con mensaje
    public SolarException(String mensaje) {
        super(mensaje);
    }
}