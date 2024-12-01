package partida.avatares;

import monopoly.*;
import partida.Jugador;

public class AvatarCoche extends Avatar {

    //Atributos
    private int extras; //Cuenta el numero de tiradas extra correspondiente a un tipo de avatar
    private int bloqueado; //Cuenta los turnos que un jugador no puede tirar
    private final ConsolaNormal consola;
    private boolean extraDobles; //Almacena si se obtuvieron dobles en el último turno extra

    //Constructor
    public AvatarCoche(){
    this.extras = 3;
    this.bloqueado = 0;
    this.consola = new ConsolaNormal();
    this.extraDobles = false;
    }

    @Override
    public void moverEnAvanzado(int dado1, int dado2, Jugador jugador, Tablero tablero, Jugador banca){
        int casillasTotal = dado1 + dado2;

        if(casillasTotal<=4){

            //Se mueve el valor obtenido para atrás
            casillasTotal = -1*casillasTotal;
            jugador.setBloqueado(2);
            //Se mueve el avatar del jugador el numero de casillas
            jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
            tablero.imprimirTablero();
            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2 + "\nComo es un 4 o menos, retrocede el valor obtenido y pierde sus 2 proximos turnos.");
        }
        else{

            if(extras==0){
                if(dado1==dado2 && jugador.getDobles()==0){
                    extras+=1;
                    jugador.incrementarDobles();}
            }

            jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
            //Comprueba si se puede realizar la acción de la casilla.
            jugador.getAvatar().setSolvente(jugador.getAvatar().getLugar().EvaluarCasilla(jugador, banca, casillasTotal, tablero));

            //Atributos estadísticos
            //jugador.getAvatar().getLugar().sumarVisitas(1);
            //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);

            tablero.imprimirTablero();

            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);

            extras-=1;

        }

    }

    @Override
    public int getExtras(){
        return this.extras;
    }

    @Override
    public void setExtras(int extras){
        this.extras=extras;
    }

    @Override
    public int getBloqueado(){
        return this.bloqueado;
    }

    @Override
    public void setBloqueado(int block){
        this.bloqueado=block;
    }

    
    @Override
    public boolean getExtraDobles(){
        return this.extraDobles;
    }

    @Override
    public void setExtraDobles(boolean dobles){
        this.extraDobles=dobles;
    }
        


}