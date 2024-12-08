package excepcions;

public class ExcepcionTrato extends Exception {
    public ExcepcionTrato(){
        super("Error al acceder al trato");
    }

    public ExcepcionTrato(String mensaje){
        super(mensaje);
    }
}
