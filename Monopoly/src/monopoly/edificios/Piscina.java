package monopoly.edificios;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.casillas.*;
import partida.Jugador;

public class Piscina extends Edificio{

    public Piscina(Jugador duenho, int id, float coste, PropiedadSolar casilla, Grupo grupo) {
        super(duenho, id, casilla, grupo, "Piscina", 0.4f * coste); // Calculamos el coste aquí
    }

    @Override
    public float calcularCoste() {
        return getCoste(); // Retorna el coste calculado en el constructor
    }

    @Override
    public  boolean puedeConstruir(){

        int numHoteles = this.getCasilla().contarEdificiosPorTipo("hotel");
        int numCasas = this.getCasilla().contarEdificiosPorTipo("casa");

        if(numHoteles < 1 || numCasas <2){
            System.out.println("No se puede construír una piscina: se requiere un hotel y dos casas"); //EXCEPCION
            return false;
        }

        return true;

    }

    public ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios){
        System.out.println("Se ha comprado una piscina por el precio de " + getCoste());
        return edificios;
    }


    
    @Override
    public void identificadorEdificio(StringBuilder info){
        info.append("[").append("piscina-").append(this.getId()).append("] ");
    }

    
    
}
