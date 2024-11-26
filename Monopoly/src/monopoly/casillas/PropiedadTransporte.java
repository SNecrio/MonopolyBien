package monopoly.casillas;
import java.util.ArrayList;

import monopoly.Valor;
import partida.*;

public class PropiedadTransporte extends CasillaPropiedad {

    public PropiedadTransporte(float valor, Jugador duenho, String nombre, int posicion){
        super(valor, duenho, nombre, "Transporte", posicion);
    }

    @Override
    public void infoCasilla(StringBuilder info){
        info.append("Alquiler: ").append(0.1*getValor()).append("\n");
    }
    
    @Override
    public float calcularAlquiler(Jugador actual, int tirada){
        Jugador propietarioTransporte = this.getDuenho();

        //Miramos cuantas casillas de transporte tiene
        int num_transporte = propietarioTransporte.getNumCasillasTransporte();

        //El alquiler depende del numero de casillas de tipo transporte que tiene el dueño
        if(num_transporte == 1){
            return 0.25f * this.getValor(); //alquiler por un transporte
        }
        else if(num_transporte == 2){
            return 0.5f * this.getValor(); //alquiler por dos transportes
        }
        else if(num_transporte == 3){
            return 0.75f * this.getValor(); //alquiler por tres transportes
        }
        else if (num_transporte == 4){
            return this.getValor(); //alquiler por cuatro transportes
        }
        else{
            return 0.0f;
        }
    }

    @Override
    public void casEnVenta(){
        StringBuilder info = new StringBuilder();
        info.append(Valor.WHITE + "\n").append(this.getNombre()).append("\n");
        info.append("Tipo: ").append(this.getTipo()).append("\n");
        info.append("Valor: ").append(this.getValor()).append("\n");
    }
}

