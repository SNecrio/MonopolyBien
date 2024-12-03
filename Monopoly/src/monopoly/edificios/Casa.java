package monopoly.edificios;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.casillas.*;
import partida.Jugador;

public class Casa extends Edificio {

    public Casa(Jugador duenho, int id, float coste, PropiedadSolar casilla, Grupo grupo) {
        super(duenho, id, casilla, grupo, "Casa", 0.6f * coste); // Calculamos el coste aquí
    }

    public Casa(){
        super();
    }

    @Override
    public float calcularCoste() {
        return getCoste(); // Retorna el coste calculado en el constructor
    }

    @Override
    public  boolean puedeConstruir(){

        int numeroCasillasGrupo = this.getGrupo().getNumeroCasillas();
        int numeroHotelesGrupo = this.getCasilla().contarEdificiosPorTipoGrupo("hotel", this.getGrupo());
        int numCasas = this.getCasilla().contarEdificiosPorTipo("Casa");

        if(numeroCasillasGrupo == 2){
            if(numeroHotelesGrupo==2){
                if(numCasas >= 2){
                    System.out.println("No se puede construir mas de dos casas"); //EXCEPCION
                    return false;
                }
            }
            else{
                if(numCasas >=4){
                    System.out.println("No se pueden construír mas de cuatro casas"); //EXCEPCION
                    return false;
                }
            }
        }
        else if(numeroCasillasGrupo==3){
            if(numeroHotelesGrupo == 3){
                if(numCasas >= 2){
                    System.out.println("No se puede construir mas de tres casas"); //EXCEPCION
                    return false;
                }
            }
            else{
                if(numCasas >=4){
                    System.out.println("No se pueden construír mas de cuatro casas"); //EXCEPCION
                    return false;
                }
            }
        }
        
        return true;
    }

    @Override
    public ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios){
        System.out.println("Se ha comprado una casa por el precio de " + getCoste());
        return edificios;
    }

    @Override
    public void identificadorEdificio(StringBuilder info){
        info.append("[").append("casa-").append(this.getId()).append("] ");
    }



}

