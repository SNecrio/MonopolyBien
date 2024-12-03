package partida.avatares;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;
import monopoly.casillas.*;
import partida.Jugador;
import monopoly.ConsolaNormal;

public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private ConsolaNormal consola;

    //Constructor vacío
    public Avatar() {
        this.id= "";
        this.tipo="";
        this.jugador=null;
        this.lugar=null;
        this.consola = new ConsolaNormal();
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados){
        this.tipo= tipo;
        this.jugador=jugador;
        this.lugar= lugar;
        this.id = generarId(avCreados); //usamos o metodo de abaixo para crear ID únicos
        this.consola = new ConsolaNormal();

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

        consola.imprimir(info.toString());
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
                consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);
                consola.imprimir("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel");
                return;
            }
        }

        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);

        ArrayList<Jugador> jugadores = new ArrayList<>(); //ESTO E SIMPLEMENTE PA PASARLLO A CASILLA
        //Comprueba si se puede realizar la acción de la casilla.
        solvente = jugador.getAvatar().getLugar().EvaluarCasilla(jugador, banca, casillasTotal, tablero, jugadores);

        //Atributos estadísticos
        //jugador.getAvatar().getLugar().sumarVisitas(1);
        //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);

        tablero.imprimirTablero();

        consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);

    }


     // Método para movel el avatar en modo avanzado (esfinge y sombrero)
     public void moverEnAvanzado(int dado1, int dado2, Jugador jugador, Tablero tablero, Jugador banca){
        consola.imprimir("Este avatar no tiene modo de avance avanzado. Se procederá en el modo básico");
        moverEnBasico(dado1, dado2, jugador, tablero, banca);
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
        return 0;
    }

    public void setExtras(int extras){} //Función solo implementada para coche

    public int getBloqueado(){
        return 0;
    }

    public void setBloqueado(int block){} //Función solo implementada para coche

    public boolean getSolvente(){
        return this.solvente;
    }
    
    public void setSolvente(boolean expresion){
        this.solvente = expresion;
    }

    public boolean getExtraDobles(){
        return false;
    }
    
    public void setExtraDobles(boolean dobles){} //Función solo implementada para coche

    public int getTiradaInicial(){
        return 0;
    }

    public void setTiradaInicial(int tirada){} //Función solo implementada para pelota

    public int getContinuar(){
        return 0;
    }

    public void setContinuar(int cont){} //Función solo implementada para pelota


}
