package excepcions;

public class SolarException extends CasillaException {

    public SolarException() {
        super("Ha ocurrido un error no identificado.");
    }

    public SolarException(String mensaje) {
        super(mensaje);
    }
}