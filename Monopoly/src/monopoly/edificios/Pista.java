package monopoly.edificios;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.casillas.*;
import partida.Jugador;
import excepcions.ExcepcionMaximoEdificar;

public class Pista extends Edificio {

    public Pista(Jugador duenho, int id, float coste, PropiedadSolar casilla, Grupo grupo) {
        super(duenho, id, casilla, grupo, "Pista de Deporte", 1.25f * coste); // Calculamos el coste aquí
    }

    @Override
    public float calcularCoste() {
        return getCoste(); // Retorna el coste calculado en el constructor
    }

    @Override
    public  boolean puedeConstruir() throws ExcepcionMaximoEdificar{

        int numHoteles = this.getCasilla().contarEdificiosPorTipo("hotel");

        if(numHoteles < 2){
            throw new ExcepcionMaximoEdificar("No se puede construír una pista de deporte: se requieren dos hoteles"); //EXCEPCION
        }

        return true;

    }

    @Override
    public ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios){
        System.out.println("Se ha comprado una pista de deporte por el precio de " + getCoste());
        return edificios;
    }

    
    @Override
    public void identificadorEdificio(StringBuilder info){
        info.append("[").append("pista-").append(this.getId()).append("] ");
    }
    
}
