package partida;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private int extras; //Cuenta el numero de tiradas extra correspondiente a un tipo de avatar

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
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
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
