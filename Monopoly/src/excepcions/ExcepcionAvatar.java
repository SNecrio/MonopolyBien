package excepcions;

public class ExcepcionAvatar extends Exception {
    public ExcepcionAvatar(){
        super("Error al acceder a un avatar");
    }

    public ExcepcionAvatar(String mensaje){
        super(mensaje);
    }
}