package excepcions;

public class ExcepcionHipotecar extends Excepcion {
    public ExcepcionHipotecar() {
        super("Error al hipotecar la casilla");
    }

    public ExcepcionHipotecar(String mensaje) {
        super(mensaje);
    }
}