package monopoly.casillas;

import java.util.ArrayList;
import java.util.Random;
import partida.avatares.*;
import partida.Jugador;
import monopoly.Valor;

public class PropiedadServicio extends CasillaPropiedad {
    public PropiedadServicio(){
        super();
    };

    @Override
    public float calcularAlquiler(Jugador actual, int tirada){
        Jugador propietariocasillaservicio = this.getDuenho();

        //miramos cantas casillas servicio ten o dueño
        int num_servicio = propietariocasillaservicio.getNumCasillasServicio();

        float factor_servicio =  SUMA_VUELTA/200; //calculamos o factor servicio

        //Dependiendo del numero de casillas servicio y de la tirada, se pagara un valor o otro
        if (num_servicio == 1){
            return 4 * tirada * factor_servicio; //alquiler por un servicio
        }
        else if(num_servicio ==2){
            return 10 * tirada * factor_servicio; //alquiler por dos servicios
        }
        else{
            return 0; //AQUÍ HABRÍA QUE DAR UN ERROR
        }
        
    }
}
