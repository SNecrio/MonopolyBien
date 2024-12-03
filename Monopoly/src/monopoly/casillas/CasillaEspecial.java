package monopoly.casillas;

import java.util.ArrayList;
import monopoly.Tablero;
import partida.Jugador;

public class CasillaEspecial extends Casilla{
    
    //ATRIBUTOS
    private float impuesto; //Almacena os gastos que se acumulan en 'Parking'

    //CONSTRUCTOR
    public CasillaEspecial(){
        super();
        this.impuesto = 0.0f;
    }

    public CasillaEspecial(float impuesto,String nombre, int posicion){
        super(nombre, "Especial", posicion);
        this.impuesto=impuesto;
    }
    
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> lista){
        
        if(this.getNombre().equalsIgnoreCase("parking")){
            if(this.impuesto<0.0001f){
                System.out.println("OOOPSSS... El bote del Parking está vacío");
            }
            else{
                System.out.println("El jugador " + actual.getNombre()+ " recibe " + this.impuesto);
                System.out.println("Su fortuna actual es: "+ actual.getFortuna());

                actual.sumarFortuna(this.impuesto);
                actual.EstadisticaPremios(this.impuesto);
                this.impuesto = 0.0f;
                
            }
        }

        /*if(this.getNombre().equalsIgnoreCase("ircarcel")){  // pegado desde lanzar dado, hay que modificar
        
            actual.encarcelar(tablero.getPosiciones());
            tablero.imprimirTablero();
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + "IrCárcel y es llevado a la cárcel, perdiendo su turno. Oh no!");
            tirado=true;
            acabarTurno(false);
            return;
        
        }*/

        return true;
    }

    public void describirEspecifico(StringBuilder info){

        if(this.getNombreSinColor().equalsIgnoreCase("salida") || this.getNombreSinColor().equalsIgnoreCase("ircarcel")){
            listarAvatares(info);
        }

        else if(this.getNombreSinColor().equalsIgnoreCase("parking")){
            info.append("Bote: ").append(this.impuesto).append("\n");
            listarAvatares(info);
        }

        else if(this.getNombreSinColor().equalsIgnoreCase("carcel")){
            info.append("Fianza: ").append(this.impuesto).append("\n");
            listarAvatares(info);
        }
    }

}
