package partida.avatares;

import java.util.ArrayList;
import monopoly.casillas.Casilla;
import partida.Jugador;

public class AvatarEsfinge extends Avatar {

    //Constructor
    public AvatarEsfinge(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados){
    super(tipo, jugador, lugar, avCreados);  //Como no tiene atributos propios, usa el constructor de avatar genérico
    }

}