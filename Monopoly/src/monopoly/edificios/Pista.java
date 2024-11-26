package monopoly.edificios;

import monopoly.Grupo;
import monopoly.casillas.Casilla;
import partida.Jugador;

public class Pista extends Edificio {
    
    public Pista(Jugador duenho,int id, float coste, Casilla casilla, Grupo grupo){
        super(duenho, id, coste, casilla, grupo, "Pista de Deporte");
    }
}
