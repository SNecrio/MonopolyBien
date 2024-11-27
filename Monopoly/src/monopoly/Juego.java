package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;  // CUANDO SE DESCOMNTEN LAS CARTAS SE NECESITA
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
import partida.avatares.*;

public class Juego implements Comando{

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

    @Override
    public void crearJugador(String comando){
        String[] partes = comando.split(" ");

        if(jugadores.size() > 5){
            consola.imprimir("No puede haber más de 6 personajes en el juego");
        }
        else if(partes.length != 4){

            System.out.println("\nFormato incorrecto. Usa: crear jugador 'nombre' 'tipo de avatar' "); //EXCEPCION

        }else{
            String nombre = partes[2]; //nombre
            String avatar = partes [3]; //tipo avatar
    
            Jugador nuevoJugador = new Jugador(nombre, avatar, tablero.encontrar_casilla("Salida"), avatares);       
            jugadores.add(nuevoJugador);
            avatares.add(nuevoJugador.getAvatar());
            tablero.getPosiciones().get(3).get(10).anhadirAvatar(nuevoJugador.getAvatar());

            tablero.imprimirTablero();

            consola.imprimir("\nNombre: " + nombre);
            consola.imprimir("Avatar: " + nuevoJugador.getAvatar().getId());
    
        }
        return;
    }

    @Override
    public void iniciarPartida(){
        dado1 = new Dado();
        dado2 = new Dado();

        System.out.println("\u001B[1m");
        System.out.println("&&   &&  &&&&&&  &&   &  &&&&&&  &&&&&&  &&&&&&  &    &   &");
        System.out.println("& & & &  &    &  & &  &  &    &  &    &  &    &  &     & &");
        System.out.println("&  &  &  &    &  &  & &  &    &  &&&&&   &    &  &      &");
        System.out.println("&     &  &    &  &   &&  &    &  &       &    &  &      &");
        System.out.println("&     &  &&&&&&  &    &  &&&&&&  &       &&&&&&  &&&&&  &\u001B[0m");

        System.out.println("\nBienvenido a una nueva partida de Monopoly!\n");

        //ESTA HAI QUE COMPLETALA, DEIXOA ASI 

    }


    @Override
    public void listarJugadores(String comando){
        if(jugadores.isEmpty()){
            System.out.println("\nNo existe ningun jugador.");
        }
        
        else{
            boolean listar = true;
            System.out.println("\nJugadores: ");
            for(short i = 0; i< jugadores.size(); i++){

                Jugador jugador = jugadores.get(i);
                
                //creamos un arreglo para aprovechar la funcion descjugador que pide un string
                String[] partes = new String[3];
                partes[0] = "listar"; //valdria cualquier otro valor
                partes[1] = "jugador"; //valdria cualquier otro valor
                partes[2]= jugador.getNombre(); //nombre del jugador
    
                descJugador(partes, listar);
    
            }
        }

        return;
    }

    @Override
    public void describirJugador(String comando){
        String[] partes = comando.split(" "); // Dividir por espacios
        if(partes.length <= 2){
            System.out.println("\nFormato incorrecto. Introduzca describir jugador 'nombre'");
            return;}
        else{
            boolean listar = false;
            
        }
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
