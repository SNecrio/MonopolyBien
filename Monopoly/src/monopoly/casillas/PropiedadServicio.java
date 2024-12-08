package monopoly.casillas;

import java.util.ArrayList;
import partida.avatares.*;
import partida.Jugador;
import monopoly.Grupo;
import monopoly.Valor;

public class PropiedadServicio extends CasillaPropiedad {

    
    public PropiedadServicio(){
        super();
    };

    public PropiedadServicio(float valor, Jugador duenho, String nombre, int posicion){
        super(valor, duenho, nombre,"Servicio",  posicion);
    };

    @Override
    public float calcularAlquiler(Jugador actual, int tirada){
        Jugador propietariocasillaservicio = this.getDuenho();

        //miramos cantas casillas servicio ten o dueño
        int num_servicio = propietariocasillaservicio.getNumCasillasServicio();

        float factor_servicio =  Valor.SUMA_VUELTA/200; //calculamos o factor servicio

        //Dependiendo del numero de casillas servicio y de la tirada, se pagara un valor o otro
        if (num_servicio == 1){
            return 4 * tirada * factor_servicio; //alquiler por un servicio
        }
        else if(num_servicio ==2){
            return 10 * tirada * factor_servicio; //alquiler por dos servicios
        }
        else{
            return 0; 
        }
        
    }

    @Override
    public void infoCasilla(StringBuilder info){
        info.append("Alquiler: ").append(0.1*getValor()).append("\n");
    }

    @Override
    public void casEnVenta(){
        StringBuilder info = new StringBuilder();
        info.append(Valor.WHITE + "\n").append(this.getNombre()).append("\n");
        info.append("Tipo: ").append(this.getTipo()).append("\n");
        info.append("Valor: ").append(this.getValor()).append("\n");

        System.out.println(info.toString());
    }


}
