package monopoly.casillas;
import java.util.ArrayList;
import partida.avatares.*;
import partida.Jugador;

public class CasillaImpuesto extends Casilla{
    
    //ATRIBUTOS
    private float impuesto;

    //CONSTRUCTORES
    public CasillaImpuesto(){
        this.impuesto = 0.0f;
    }

    public CasillaImpuesto(float impuesto){
        this.impuesto = impuesto;
    }

    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada){
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

    public void infoCasilla(StringBuilder info){

        info.append("IMPUESTO: ").append(this.impuesto).append("\n");
    }
}
