package excepcions;

public class CasillaException extends GeneralException{

    public CasillaException(){
        super("Ha ocurrido un error de casilla no identificado");
    }

    public CasillaException(String mensaje){
        super(mensaje);
    }
}