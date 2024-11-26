package excepcions;

public class HipotecaException extends CasillaException{

    public HipotecaException(){
        super("Ha ocurrido un relacionado con las hipotecas no identificado");
    }

    public HipotecaException(String mensaje){
        super(mensaje);
    }
}