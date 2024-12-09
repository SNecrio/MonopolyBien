package excepcions;

public class ExcepcionHipotecarNoHipotecada extends ExcepcionHipotecar {
    public ExcepcionHipotecarNoHipotecada() {
    }

    public ExcepcionHipotecarNoHipotecada(String mensaje) {
        super(mensaje);
    }
}