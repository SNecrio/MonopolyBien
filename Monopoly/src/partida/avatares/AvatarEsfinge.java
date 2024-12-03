package partida.avatares;

import java.util.ArrayList;
import monopoly.casillas.Casilla;
import partida.Jugador;

public class AvatarEsfinge extends Avatar {

    //Constructor
    public AvatarEsfinge(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados){
    setTipo(tipo);
    setJugador(jugador);
    setLugar(lugar);
    setId(generarId(avCreados)); //usamos o metodo de abaixo para crear ID únicos
    }

}