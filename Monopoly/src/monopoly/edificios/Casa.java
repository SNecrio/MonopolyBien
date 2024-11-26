package monopoly.edificios;

import monopoly.Grupo;
import monopoly.casillas.Casilla;
import partida.Jugador;

public class Casa extends Edificio{
    
    public Casa(Jugador duenho,int id, float coste, Casilla casilla, Grupo grupo){
        super(duenho, id, coste, casilla, grupo, "Casa");
    }
}
