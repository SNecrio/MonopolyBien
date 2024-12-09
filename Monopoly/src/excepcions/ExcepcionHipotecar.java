package excepcions;

public class ExcepcionHipotecar extends Exception {
    public ExcepcionHipotecar() {
        super("Error al hipotecar la casilla");
    }

    public ExcepcionHipotecar(String mensaje) {
        super(mensaje);
    }
}