package monopoly.casillas;

import java.util.ArrayList;
import partida.*;
import monopoly.*;
import monopoly.edificios.*;

public class CasillaPropiedad extends Casilla {
    private float valor;
    private float valorInicial;
    private Jugador duenho;
    private Grupo grupo;
    private float hipoteca;
    private boolean estarHipotecada;
    private float rentabilidad;
    private ArrayList<Edificio> edificios; 


     //Constructores:
     public CasillaPropiedad() {
        
        
    }
}
