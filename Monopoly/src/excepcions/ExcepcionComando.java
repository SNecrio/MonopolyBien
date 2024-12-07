package excepcions;

public class ExcepcionComando extends Exception {
    
    public ExcepcionComando(){
        super("El comando introducido es incorrecto");
    }

    public ExcepcionComando(String mensaje){
        super(mensaje);
    }
}
