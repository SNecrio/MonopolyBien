package excepcions;

//Usamolo cando a casilla que se introduce non e de tipo solar
public class ExcepcionTipoSolar extends ExcepcionPropiedad {
    public ExcepcionTipoSolar() {
    }

    public ExcepcionTipoSolar(String mensaje) {
        super(mensaje);
    }
}