package monopoly.casillas;

import java.util.ArrayList;
import monopoly.ConsolaNormal;
import monopoly.Tablero;
import partida.Jugador;
import partida.avatares.*;

public abstract class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private ArrayList<Avatar> avatares;
    private int visitas = 0;
    private ArrayList<Jugador> jugadoresvisitantes; //Array usado para incluir los nombres de todas las personas que caen en la casilla (para calcular si el jugador cae mas de dos veces en esta casilla se puede comprar)
    private ConsolaNormal consola;

    //Constructores:
    public Casilla() {
        //damoslle valores por defecto
        this.nombre = "";
        this.tipo = "";
        this.posicion= 0;
        this.avatares=new ArrayList<>(); //inicializamos o array vacío
        this.visitas = 0;
        this.jugadoresvisitantes = new ArrayList<>();
        this.consola = new ConsolaNormal();
        
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion) {
        this.nombre=nombre;
        this.tipo = tipo;
        this.posicion=posicion;
        this.avatares=new ArrayList<>(); //inicializamos o array vacío
        this.visitas = 0;
        this.jugadoresvisitantes = new ArrayList<>();
        this.consola = new ConsolaNormal();
    }

    // Métodos de acceso
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPosicion() { return posicion; }
    public String getNombreSinColor(){
        return this.nombre.replaceAll("\\x1B\\[[0-9;]*m", "");}
    
    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    } 
    
    //Metodos para avatares
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    public void eliminarAvatar(Avatar av) {
        if(!this.avatares.remove(av)){
            System.err.println("El avatar no esta en la casilla seleccionada");
        }
    }

    // Método abstracto para evaluar una casilla, debe ser implementado por las subclases
    //public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero);

    public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> lista);

    //Metodo para saber si un avatar está en esta casilla
    public boolean estaAvatar(Avatar av){
        if(av.getLugar().getPosicion() == this.posicion){
            return true;
        }

        return false;
    }

    public abstract void describirEspecifico(StringBuilder info);

    //Metodo ToString para describir una casilla
    public void DescribirCasilla(){
        StringBuilder info = new StringBuilder();

        info.append("\n");
        info.append(nombre).append("\n");
        info.append("Tipo: ").append(tipo).append("\n");
        info.append("Posicion: ").append(posicion).append("\n");
        
        describirEspecifico(info);

        consola.imprimir(info.toString());
    }


public void listarAvatares (StringBuilder info){

        ArrayList<Avatar> avs = this.getAvatares();

        info.append("Jugadores: ");
        if(this.getAvatares().isEmpty()){
            info.append(" - ");
        }
        else{
            for(Avatar avatar : avs){
                info.append(avatar.getJugador().getNombre()).append(",");
            }
        }
    }

    @Override
    public boolean equals(Object casilla){
        if(this==casilla){
            return true;
        }
        if(casilla==null){
            return false;
        }
        if(getClass() != casilla.getClass()){
            return false;
        }
        final Casilla otro = (Casilla) casilla;
        if(!this.getNombreSinColor().equals(otro.getNombreSinColor())){
            return false;
        }
        return true;
    }

    public int getVisitas(){
        return this.visitas;
    }

    public void sumarVisitas(int valor){
        this.visitas += valor;
    }

    public ArrayList<Jugador> getJugadoresVisitantes(){
        return jugadoresvisitantes;
    }


    public void sumarJugadoresVisitantes(Jugador jugador){
        if (jugador != null) {
            this.jugadoresvisitantes.add(jugador);
        } else {
            consola.imprimir("Jugador no puede ser nulo");
        }   
    }


}
