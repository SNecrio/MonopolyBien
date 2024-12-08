package monopoly.casillas;

import java.util.ArrayList;
import monopoly.Tablero;
import partida.Jugador;
import monopoly.ConsolaNormal;

public class CasillaImpuesto extends Casilla{
    
    //ATRIBUTOS
    private float impuesto;
    private ConsolaNormal consola;

    //CONSTRUCTORES
    public CasillaImpuesto(){
        super();
        this.impuesto = 0.0f;
        consola = new ConsolaNormal();
    }

    public CasillaImpuesto(float impuesto, String nombre, int posicion){
        super(nombre, "Impuesto", posicion);
        this.impuesto = impuesto;
        consola = new ConsolaNormal();

    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> lista){
        if(this.getTipo().equalsIgnoreCase("impuesto")){
            if(actual.getFortuna() >= this.impuesto){
                actual.pagar(this.impuesto);
                actual.sumarGastos(impuesto);
                actual.EstadisticaTasasImpuesto(this.impuesto);

                consola.imprimir("Oh no! El jugador "+actual.getNombre()+" tiene que pagar "+this.impuesto+ " por impuestos.");
                return true;
            }
            return false;
        }
        else{
            consola.imprimir("ERROR, tipo equivocado a la hora de llamar Evaluar Casilla");
            return false;
        }

    }

    @Override
    public void describirEspecifico(StringBuilder info){
        info.append("Impuesto: ").append(this.impuesto).append("\n");
    }
}
