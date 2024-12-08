package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
import partida.avatares.*;
import monopoly.edificios.*;
import excepcions.*;

public class Menu {

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

    public Menu(){
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
    
    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {
        
        String nome;

        dado1 = new Dado();
        dado2 = new Dado();
    
        Scanner scan = new Scanner(System.in);
        consola.imprimir("\u001B[1m");
        consola.imprimir("&&   &&  &&&&&&  &&   &  &&&&&&  &&&&&&  &&&&&&  &    &   &");
        consola.imprimir("& & & &  &    &  & &  &  &    &  &    &  &    &  &     & &");
        consola.imprimir("&  &  &  &    &  &  & &  &    &  &&&&&   &    &  &      &");
        consola.imprimir("&     &  &    &  &   &&  &    &  &       &    &  &      &");
        consola.imprimir("&     &  &&&&&&  &    &  &&&&&&  &       &&&&&&  &&&&&  &\u001B[0m");

        consola.imprimir("\nBienvenido a una nueva partida de Monopoly!\n");
        consola.imprimir("Para comenzar, cree de 2 a 6 jugadores. Recuerde que una vez empezada, no se podran crear nuevos jugadores.\n");
        try{
            mostrarMenu();
        }catch(GeneralException e){
            System.err.println("Excepción capturada: " + e.getMessage() + "\n");
        }

    }
    
    private void mostrarMenu(){
        
        String comando;
        Scanner scan = new Scanner(System.in);
        
        while(true){
            
            if(acabarPartida == true){
                if(jugadores.size() == 1){
                    consola.imprimir("Felicidades " + jugadores.get(0).getNombre() + ", ganaste el Monopoly!");    
                }
                consola.imprimir("Cerrando juego...");
                scan.close();
                return;
            }

            if(partidainiciada == false){

                consola.imprimir(Valor.WHITE + "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");
                consola.imprimir("-------------------------------------------");
                consola.imprimir("Crear jugador <nombreJugador> <tipoAvatar>");
                consola.imprimir("Listar jugadores");
                consola.imprimir("Consultar tipos avatar");
                consola.imprimir("Iniciar partida");
                consola.imprimir("-------------------------------------------");
            }
    
            else{
        
            consola.imprimir(Valor.WHITE + "\nEs tu turno " + jugadores.get(turno).getNombre() + "!");        
                consola.imprimir( "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");

                consola.imprimir("-------------------------------------------------------------------------------");
                System.out.printf("%-45s%-45s%n", "Ver tablero", "Describir jugador <nombreJugador>");
                System.out.printf("%-45s%-45s%n", "Lanzar dados", "Describir avatar <idAvatar>");
                System.out.printf("%-45s%-45s%n", "Comprar <nombre casilla>", "Describir <nombreCasilla>");
                System.out.printf("%-45s%-45s%n", "Edificar <tipo>", "Listar jugadores");
                System.out.printf("%-45s%-45s%n", "Hipotecar <nombre casilla>", "Listar avatares");
                System.out.printf("%-45s%-45s%n", "Deshipotecar <nombre casilla>", "Listar en venta");
                System.out.printf("%-45s%-45s%n", "Vender <tipo> <nombre casilla> <cantidad>", "Estadisticas <nombre jugador>");
                System.out.printf("%-45s%-45s%n", "listar edificios", "Listar edificios <color grupo>");
                System.out.printf("%-45s%-45s%n", "Estadisticas", "Estadisticas <nombreJugador>");


                if(jugadores.get(turno).getEncarcel()==true){
                    consola.imprimir("Pagar fianza");}
                System.out.printf("%-45s%-45s%n", "Acabar turno", "Salir");
                consola.imprimir("-------------------------------------------------------------------------------");
            }
    
            comando = scan.nextLine();

            if(comando.equalsIgnoreCase("salir")){
                consola.imprimir("Cerrando juego...");
                scan.close();
                return;
            }
            
           
        }
    }
    




    public void trato(String jugadorNombre){
        

        for(Jugador jugador : jugadores){
            if(jugador.getNombre().equalsIgnoreCase(jugadorNombre)){
                
            }
        }
    }
}
