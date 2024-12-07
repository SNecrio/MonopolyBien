package excepcions;

public class ExcepcionCasilla extends Exception {
    public ExcepcionCasilla(){
        super("Error al acceder a una casilla");
    }

    public ExcepcionCasilla(String mensaje){
        super(mensaje);
    }
}
