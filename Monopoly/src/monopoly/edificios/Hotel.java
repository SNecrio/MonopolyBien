package monopoly.edificios;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.casillas.*;
import partida.Jugador;

public class Hotel extends Edificio {

    public Hotel(Jugador duenho, int id, float coste, PropiedadSolar casilla, Grupo grupo) {
        super(duenho, id, casilla, grupo, "Hotel", 0.6f * coste); // Calculamos el coste aquí
    }

    @Override
    public float calcularCoste() {
        return getCoste(); // Retorna el coste calculado en el constructor
    }

    @Override
    public  boolean puedeConstruir(){

        int numCasas = this.getCasilla().contarEdificiosPorTipo("casa");
        if(numCasas < 4){
            System.out.println("No se puede construir un hotel sin 4 casas antes"); //EXCEPCION
            return false;
        }

        return true;

    }

    @Override
    public ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios){
        System.out.println("Se ha comprado hotel por el precio de " + getCoste());
        int eliminadas = 0;
        int contador=0;
        for(Edificio edificio :edificios){
            if(edificio.getTipo().equalsIgnoreCase("casa")){
                contador++;
            }
        }

        if(contador >=4){
            for (int i = edificios.size() - 1; i >= 0 && eliminadas < 4; i--) {
                Edificio edificio = edificios.get(i);
                if (edificio.getTipo().equalsIgnoreCase("Casa")) {
                    edificios.remove(i);  // Eliminamos la casa
                    eliminadas++;
                }
            }
        }
        else{
            System.out.println("No se puede construir el hotel porque no hay cuatro casas"); //EXCEPCION!!!! creo q e imposible que se chegue a aqui pero bueno ainda asi
        }

        return edificios;
    }

    @Override
    public void identificadorEdificio(StringBuilder info){
        info.append("[").append("hotel-").append(this.getId()).append("] ");
    }

}


