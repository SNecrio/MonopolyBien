package monopoly.edificios;
import java.util.ArrayList;
import monopoly.Grupo;
import monopoly.casillas.*;
import partida.Jugador;
import monopoly.ConsolaNormal;
import excepcions.ExcepcionMaximoEdificar;

public class Casa extends Edificio {

    private ConsolaNormal consola;

    public Casa(Jugador duenho, int id, float coste, PropiedadSolar casilla, Grupo grupo) {
        super(duenho, id, casilla, grupo, "Casa", 0.6f * coste); // Calculamos el coste aquí
        this.consola = new ConsolaNormal();
    }

    public Casa(){
        super();
        this.consola = new ConsolaNormal();

    }

    @Override
    public float calcularCoste() {
        return getCoste(); // Retorna el coste calculado en el constructor
    }

    @Override
    public  boolean puedeConstruir() throws ExcepcionMaximoEdificar{

        int numeroCasillasGrupo = this.getGrupo().getNumeroCasillas();
        int numeroHotelesGrupo = this.getCasilla().contarEdificiosPorTipoGrupo("hotel", this.getGrupo());
        int numCasas = this.getCasilla().contarEdificiosPorTipo("Casa");

        if(numeroCasillasGrupo == 2){
            if(numeroHotelesGrupo==2){
                if(numCasas >= 2){
                    throw new ExcepcionMaximoEdificar("No se puede construir mas de dos casas");
                }
            }
            else{
                if(numCasas >=4){
                    throw new ExcepcionMaximoEdificar("No se pueden construír mas de cuatro casas"); 
                }
            }
        }
        else if(numeroCasillasGrupo==3){
            if(numeroHotelesGrupo == 3){
                if(numCasas >= 2){
                    throw new ExcepcionMaximoEdificar("No se puede construir mas de tres casas"); 
                }
            }
            else{
                if(numCasas >=4){
                    throw new ExcepcionMaximoEdificar("No se pueden construír mas de cuatro casas"); 
                }
            }
        }
        
        return true;
    }

    @Override
    public ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios){
        consola.imprimir("Se ha comprado una casa por el precio de " + getCoste());
        return edificios;
    }

    @Override
    public void identificadorEdificio(StringBuilder info){
        info.append("[").append("casa-").append(this.getId()).append("] ");
    }



}

