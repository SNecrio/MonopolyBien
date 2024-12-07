package excepcions;

public class ExcepcionJugador extends Exception {
    public ExcepcionJugador(){
        super("Jugador no encontrado");
    }

    public ExcepcionJugador(String mensaje){
        super(mensaje);
    }
}
