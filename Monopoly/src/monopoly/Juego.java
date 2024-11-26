package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;  // CUANDO SE DESCOMNTEN LAS CARTAS SE NECESITA
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
import partida.avatares.*;

public class Juego {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private boolean partidainiciada;
    private boolean acabarPartida = false;
    private Baraja baraja;
    private ConsolaNormal consola;

    public Juego(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.lanzamientos = 0;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.tirado = false;
        this.solvente = true;
        this.partidainiciada = false;
        this.baraja = new Baraja();
        this.consola = new ConsolaNormal();
    }

    public void lanzarDados(Jugador jugador){
        //Se lanzan los dados
        dado1.hacerTirada();
        dado2.hacerTirada();

        if(tirado==true){
            consola.imprimir("El jugador ya ha tirado este turno.");
            return;
        }

        //Avance en modo simple
        if(jugador.getModo()==false){
            jugador.getAvatar().moverEnBasico(dado1.getValor(), dado2.getValor(), jugador, tablero, banca);
            if(dado1.getValor()!=dado2.getValor() || jugador.getEncarcel()==true) tirado = true;
        }
        //Avance en modo avanzado
        else ;
        return;
    }

    



}
