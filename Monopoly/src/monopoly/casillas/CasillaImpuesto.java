package monopoly.casillas;

import java.util.ArrayList;
import partida.avatares.*;
import partida.Jugador;
import monopoly.Tablero;

public class CasillaImpuesto extends Casilla{
    
    //ATRIBUTOS
    private float impuesto;

    //CONSTRUCTORES
    public CasillaImpuesto(){
        super();
        this.impuesto = 0.0f;
    }

    public CasillaImpuesto(float impuesto, String nombre, int posicion){
        super(nombre, "Impuesto", posicion);
        this.impuesto = impuesto;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero){
        if(this.getTipo().equalsIgnoreCase("impuesto")){
            if(actual.getFortuna() >= this.impuesto){
                actual.pagar(this.impuesto);
                actual.sumarGastos(impuesto);
                actual.EstadisticaTasasImpuesto(this.impuesto);

                System.out.println("Oh no! El jugador "+actual.getNombre()+" tiene que pagar "+this.impuesto+ "por impuestos.");
                return true;
            }
            return false;
        }
        else{
            System.out.println("ERROR, tipo equivocado a la hora de llamar Evaluar Casilla");
            return false;
        }

    }

    public void describirEspecifico(StringBuilder info){
        info.append("Impuesto: ").append(this.impuesto).append("\n");
    }
}
