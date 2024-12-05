package partida.avatares;

import java.util.ArrayList;
import monopoly.*;
import monopoly.casillas.Casilla;
import partida.Jugador;


public class AvatarPelota extends Avatar {

    //Atributos
    private int tiradaInicial; //Cuenta el numero de tiradas extra correspondiente a un tipo de avatar
    private int continuar; //
    private final ConsolaNormal consola;

    //Constructor
    public AvatarPelota(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados){
    setTipo(tipo);
    setJugador(jugador);
    setLugar(lugar);
    setId(generarId(avCreados)); //usamos o metodo de abaixo para crear ID únicos
    this.tiradaInicial=0;
    this.continuar = 0;
    this.consola = new ConsolaNormal();
    }


    @Override
    public void moverEnAvanzado(int dado1, int dado2, Jugador jugador, Tablero tablero, Jugador banca, ArrayList<Jugador> jugadores){
        int casillasTotal = dado1 + dado2;

        if(tiradaInicial!=0) casillasTotal=tiradaInicial; //Nos aseguramos de que vaya por la rama correcta
        if(casillasTotal<=4){

            if(tiradaInicial==0){
                consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2 + ". Como es un 4 o menos, retrocede el valor obtenido parando en todos los movimientos impares.");
                tiradaInicial=casillasTotal; //Guardamos el valor de la primera tirada
                if(dado1==dado2) jugador.setDobles(jugador.getDobles()+1);
                else jugador.setDobles(0);
                if(jugador.getDobles()==3){
                    jugador.encarcelar(tablero.getPosiciones());
                    tablero.imprimirTablero();
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);
                    consola.imprimir("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel");
                    return;
                }

                switch (tiradaInicial) {
                    case 2: case 3: //Para avanzar hasta esa casilla parando en los impares, se necesita 1 turno extra
                        continuar=2;
                        break;
                    case 4: 
                        continuar=3;
                        break;
                    default:
                        throw new AssertionError();
                }
            casillasTotal=-1;
            }

            else{
                if(continuar==1 && (tiradaInicial%2)==0){
                    casillasTotal=-1;
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + "retrocede la casilla final.");
                }
                else{
                    casillasTotal = -2;
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + "retrocede las 2 siguientes casillas.");
                }
            }                            
        }
        else{

            if(tiradaInicial==0){
                consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2 + ". Como es más de un 4, avanza el valor obtenido parando en todos los movimientos impares.");
                tiradaInicial=casillasTotal;  //Guardamos el valor de la primera tirada
                if(dado1==dado2) jugador.setDobles(jugador.getDobles()+1);
                else jugador.setDobles(0);
                if(jugador.getDobles()==3){
                    jugador.encarcelar(tablero.getPosiciones());
                    tablero.imprimirTablero();
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);
                    consola.imprimir("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel");
                    return;
                }

                switch (tiradaInicial) {
                    case 5:
                    continuar=1;
                        break;
                    case 6: case 7: //Para avanzar hasta esa casilla parando en los impares, se necesita 1 turno extra
                        continuar=2;
                        break;
                    case 8: case 9: 
                        continuar=3;
                        break;
                    case 10: case 11: 
                        continuar=4;
                        break;
                    case 12: 
                        continuar=5;
                        break;
                    default:
                        throw new AssertionError();
                }
            casillasTotal=5; //Todos se mueven al 5 como primera casilla la primera vez
            consola.imprimir("El jugador " + jugador.getAvatar().getId() + "avanza 5 casillas.");
            }
            else{
                //El único caso en el que avanzaremos una casilla es en el último movimiento de una tirada par
                if(continuar==1 && (tiradaInicial%2)==0){
                    casillasTotal = 1;
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + "avanza la casilla final.");
                }
                else{
                    casillasTotal = 2;
                    consola.imprimir("El jugador " + jugador.getAvatar().getId() + "avanza las 2 siguientes casillas.");
                }
            }                
        }
        continuar -=1; //Ya pasó un turno de moverse

        //Moverse
        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
        //Comprueba si se puede realizar la acción de la casilla.
        jugador.getAvatar().setSolvente(jugador.getAvatar().getLugar().EvaluarCasilla(jugador, banca, casillasTotal, tablero, jugadores));
        
        tablero.imprimirTablero();

        if(continuar==0 && jugador.getDobles()>0){
            tiradaInicial=0;
        }

    }

    @Override
    public int getContinuar(){
        return this.continuar;
    }

    @Override
    public void setContinuar(int cont){
        this.continuar=cont;
    }

    @Override
    public int getTiradaInicial(){
        return this.tiradaInicial;
    }

    @Override
    public void setTiradaInicial(int tirada){
        this.tiradaInicial=tirada;
    }
}
