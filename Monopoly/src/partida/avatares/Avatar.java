package partida.avatares;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;
import monopoly.casillas.*;
import partida.Jugador;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private int extras; //Cuenta el numero de tiradas extra correspondiente a un tipo de avatar
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    

    //Constructor vacío
    public Avatar() {
        this.id= "";
        this.tipo="";
        this.jugador=null;
        this.lugar=null;
        this.extras = 0;
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados){
        this.tipo= tipo;
        this.jugador=jugador;
        this.lugar= lugar;
        this.extras = 0;

        this.id = generarId(avCreados); //usamos o metodo de abaixo para crear ID únicos
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        //Los bucles iteran por todas las casillas hasta que encuentra la que tiene la misma posicion que la deseada
        for (int i = 0; i < casillas.size(); i++) {
            for(int u = 0; u < (casillas.get(i)).size(); u++){
                //Debemos asegurarnos de que la casilla sea válida (identificador positivo) aunque el valor de la tirada sea negtivo (retroceso)
                if(valorTirada<0){
                    valorTirada = 40+valorTirada;
                }
                //Si la encuentra, inercambia la casilla del jugador con la encontrada
                if(((casillas.get(i)).get(u)).getPosicion() == (this.lugar.getPosicion() + valorTirada)%40){ 
                    if(valorTirada>12) valorTirada=valorTirada-40;
                    if(this.lugar.getPosicion()+valorTirada > 39){
                        jugador.sumarVueltas();
                        jugador.sumarVueltasTotal(1);
                    }
                    this.lugar.eliminarAvatar(this);
                    this.lugar = (casillas.get(i)).get(u);
                    this.lugar.anhadirAvatar(this);
                    return;
                }
            }
        }
    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, Casilla nuevaCasilla, boolean pasaSalida) {

        if((nuevaCasilla.getPosicion() < this.lugar.getPosicion()) && pasaSalida){
            jugador.sumarVueltas();
            jugador.sumarVueltasTotal(1);
        }
        this.lugar.eliminarAvatar(this);
        this.lugar = nuevaCasilla;
        this.lugar.anhadirAvatar(this);
    }

    public void describirAvatar(){
        StringBuilder info = new StringBuilder();
        info.append(Valor.WHITE + "Id: ").append(this.id).append("\n");
        info.append("Tipo: ").append(this.tipo).append("\n");
        info.append("Jugador: ").append(this.jugador.getNombre()).append("\n");
        info.append("Casilla: ").append(this.lugar.getNombreSinColor()).append("\n");

        System.out.println(info.toString());
        return;
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private String generarId(ArrayList<Avatar> avCreados) {
        
        Random ran = new Random();
        String id;
        
        //generar unha letra maiuscula aleatoria
        id = String.valueOf((char) (ran.nextInt(26) + 'A'));

        for (Avatar avatar : avCreados) {
            if(avatar.getId().equals(id)){
                 id = String.valueOf((char)(ran.nextInt(26) + 'A'));
            }
        }
        avCreados.add(this);
        return id;
        
    }

    // Método para movel el avatar en modo básico (jugador es jugador actual)
    public void moverEnBasico(int dado1, int dado2, Jugador jugador, Tablero tablero, Jugador banca){
        int casillasTotal = dado1 + dado2;

        //COMPROBAR EN CARCEL

        //Comprueba si son dobles y cuantos dobles lleva
        if(dado1==dado2){
            jugador.incrementarDobles();
            if(jugador.getDobles()==3){
                jugador.encarcelar(tablero.getPosiciones());
                tablero.imprimirTablero();
                System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);
                System.out.println("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel");
                //acabarTurno(false);
                return;
            }
        }

        String casillaAnterior = jugador.getAvatar().getLugar().getNombre(); //Nombre de la casilla anterior para prints
        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);

        //Comprueba si se puede realizar la acción de la casilla.
        solvente = jugador.getAvatar().getLugar().evaluarCasilla(jugador, banca, casillasTotal);

        //Atributos estadísticos
        //jugador.getAvatar().getLugar().sumarVisitas(1);
        //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);

        tablero.imprimirTablero();

        System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);

        //Se vuelve a mirar los dobles para imprimirlo debajo del tablero
        if(dado1==dado2) System.out.println("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");

        //Si es una casilla de su propiedad
        if(jugador == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de su propiedad.");
            }
        else if(banca == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de la banca.");   
            }

        //Si es de otro jugador
        else{
            float alquiler = jugador.getAvatar().getLugar().calcularAlquiler(jugador, casillasTotal);
            System.out.println( "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);
            //Si le puedes pagar, se paga automáticamente
            if(solvente == true){
                System.out.println( "Se han pagado " + alquiler + " euros de alquiler. ");
            }
            //Si no puede pagar pero tiene propiedades
            else{
                bancarrota(jugador,casillasTotal);
                }
            }

        if(comprobarVueltas()==true){
            tablero.incrementarPrecios();
            resetearVueltas();
        }
    return;
    }


    























    //Getter do id
    public String getId(){
        return this.id;
    }

    public String getTipo(){
        return this.tipo;
    }

    public Casilla getLugar(){
        return this.lugar;
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    public void setLugar(Casilla pos){
        this.lugar = pos;
    }

    public int getExtras(){
        return this.extras;
    }
    
    public void setExtras(int extras){
        this.extras = extras;
    }

}
