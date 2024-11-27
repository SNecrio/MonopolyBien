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

        consola.imprimir("\u001B[1m");
        consola.imprimir("&&   &&  &&&&&&  &&   &  &&&&&&  &&&&&&  &&&&&&  &    &   &");
        consola.imprimir("& & & &  &    &  & &  &  &    &  &    &  &    &  &     & &");
        consola.imprimir("&  &  &  &    &  &  & &  &    &  &&&&&   &    &  &      &");
        consola.imprimir("&     &  &    &  &   &&  &    &  &       &    &  &      &");
        consola.imprimir("&     &  &&&&&&  &    &  &&&&&&  &       &&&&&&  &&&&&  &\u001B[0m");

        consola.imprimir("\nBienvenido a una nueva partida de Monopoly!\n");

        //ESTA HAI QUE COMPLETALA, DEIXOA ASI 

    }


    @Override
    public void listarJugadores(String comando){
        if(jugadores.isEmpty()){
            consola.imprimir("\nNo existe ningun jugador."); //EXCEPCIÓN
        }
        
        else{
            consola.imprimir("\nJugadores: ");
            for(Jugador jugador: jugadores){
                jugador.describirJugador(jugador.getNombre());
            }
           
        }

        return;
    }

    @Override
    public void describirJugador(String comando){
        String[] partes = comando.split(" "); 
        if(partes.length <= 2){
            consola.imprimir("\nFormato incorrecto. Introduzca describir jugador 'nombre'"); //EXCEPCION
            return;}
        else{

            String nombre = partes[2];

            for(Jugador jugador : jugadores){
                if(jugador.getNombre().equalsIgnoreCase(nombre)){
                    jugador.describirJugador(nombre);
                }
            }
            return;
        }
    }


    @Override
    public void describirAvatar(String comando){
        String[] partes = comando.split(" "); // Dividir por espacios
        String id = partes[2];

        if(avatares.isEmpty()==true){
            consola.imprimir("No se han encontrado avatares"); //EXCEPCION
            return;
        }
        for(Avatar avatar : avatares){
            if(avatar.getId().equalsIgnoreCase(id)){
                avatar.describirAvatar();
            }
        }
        return;
    }

    @Override
    public void describirCasilla(String comando){
        String[] partes = comando.split(" "); //Dividir por espacios
        String nombreCasilla = partes[1];
        System.out.println("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);

        if(casilla == null){
            consola.imprimir("No se encuentra la casilla"); //EXCEPCIÓN
        }

        casilla.DescribirCasilla();
        return;
    }

    @Override
    public void comprarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        System.out.println("");

        Casilla casillacomprada = tablero.encontrar_casilla(nombreCasilla);
        if(casillacomprada==null){
            consola.imprimir("No se encuentra la casilla"); //EXCEPCIÓN
            return;
        } 

        
        int lanzamiento = dado1.getValor() + dado2.getValor();
        Jugador jugadoractual = jugadores.get(turno);

        if((tirado == false)&&(jugadoractual.getDobles()<1)){
            consola.imprimir("El jugador aun no ha tirado los dados, por lo que no puede comprarla");
            return;
        } 

        if(casillacomprada instanceof CasillaPropiedad){
            CasillaPropiedad casillaPropiedad = (CasillaPropiedad) casillacomprada;
            if(casillaPropiedad.EvaluarCasilla(jugadoractual, banca, lanzamiento, tablero) ==true){
                casillaPropiedad.comprarCasilla(jugadoractual, banca);
            }
        }

        return;
    }

    @Override
    public void hipotecarCasilla(String comando){

        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        System.out.println("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla==null){
            consola.imprimir("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno); //obter xogador actual

        if(casilla instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar) casilla;
            
            if(!solar.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                consola.imprimir("No eres el dueño de la casilla para hipotecarla"); //EXCEPCION
                return;
            }

            else if(solar.tieneEdificios()==true){
                consola.imprimir("Debes vender antes los edificios de esta casilla antes de hipotecar"); //EXCEPCION
                return;
            }

            solar.hipotecarCasilla(jugadoractual, banca);
        }
       
    }

    @Override
    public void deshipotecarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        System.out.println("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla==null){
            System.out.println("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno);

        if(casilla instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar) casilla;
            
            if(!solar.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                consola.imprimir("No eres el dueño de la casilla para deshipotecarla"); //EXCEPCION
                return;
            }

            else if(solar.estaHipotecada()==true){
                consola.imprimir("No puedes hipotecar la casilla si no está hipotecada"); //EXCEPCION
                return;
            }

            solar.deshipotecarCasilla(jugadoractual, jugadoractual);
        }
    }

    @Override
    public void listarEnVenta(){
        banca.listarPropiedadesenVenta();
        return;
    }

    @Override
    public void edificar(String comando){
        String[] partes = comando.split(" ");
        String tipo = partes[1];
        System.out.println("");

        Jugador jugadoractual = jugadores.get(turno);
        Casilla casillaActual = jugadoractual.getAvatar().getLugar();

        if(!tipo.equalsIgnoreCase("Casa") && !tipo.equalsIgnoreCase("Hotel")&&!tipo.equalsIgnoreCase("Pista")&&!tipo.equalsIgnoreCase("Piscina")){
            consola.imprimir("Las edificaciones pueden ser del tipo: 'casa' 'hotel' 'pista de deporte' 'piscina'"); //EXCEPCION
        }
        if(tipo.equalsIgnoreCase("pista")){
            tipo = "pista de deporte";
        }

        if(casillaActual instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)casillaActual;
            solar.Edificar(jugadoractual, null, tipo); //MIRAR AQUI COMO HAI QUE FACER MANDANDOLLE O EDIFICIO
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
