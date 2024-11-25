package monopoly.casillas;

import java.util.ArrayList;
import partida.avatares.*;
import partida.Jugador;

public class CasillaEspecial extends Casilla{
    
    //ATRIBUTOS
    private float impuesto; //Almacena os gastos que se acumulan en 'Parking'

    //CONSTRUCTOR
    public CasillaEspecial(){
        this.impuesto = 0.0f;
    }

    public CasillaEspecial(float acumuladoParking){
        this.impuesto=acumuladoParking;
    }

    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada){
        
        if(this.getNombreSinColor().equalsIgnoreCase("parking")){
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

        return true;
    }

    public void infoCasilla(StringBuilder info){

        if(this.getNombreSinColor().equalsIgnoreCase("salida") || this.getNombreSinColor().equalsIgnoreCase("ircarcel")){
            listarAvatares(info);
        }

        else if(this.getNombreSinColor().equalsIgnoreCase("parking")){
            info.append("BOTE: ").append(this.impuesto).append("\n");
            listarAvatares(info);
        }

        else if(this.getNombreSinColor().equalsIgnoreCase("carcel")){
            info.append("IMPUESTO: ").append(this.impuesto).append("\n");
            listarAvatares(info);
        }
    }

    public void listarAvatares (StringBuilder info){

        ArrayList<Avatar> avatares = this.getAvatares();

        info.append("Jugadores: ");
        if(this.getAvatares().isEmpty()){
            info.append(" - ");
        }
        else{
            for(Avatar avatar : avatares){
                info.append(avatar.getJugador().getNombre()).append(",");
            }
        }
    }
}
