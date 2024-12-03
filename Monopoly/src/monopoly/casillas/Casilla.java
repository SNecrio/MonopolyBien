package monopoly.casillas;

import java.util.ArrayList;
import java.util.Random;

import monopoly.Carta;
import monopoly.Grupo;
import monopoly.Valor;
import monopoly.edificios.Edificio;
import monopoly.Tablero;
import partida.avatares.*;
import partida.Jugador;
import excepcions.*;
import monopoly.Consola;
import monopoly.ConsolaNormal;

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
        this.consola = new ConsolaNormal(); //No se si es mejor esto o pasar como argumento, asumo que esto
        
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion) {
        this.nombre=nombre;
        
        //hay que comprobar que tipo sexa "solar", "servicio" ou "transporte"
        if(tipo.equalsIgnoreCase("Solar")||tipo.equalsIgnoreCase("Servicio")||tipo.equalsIgnoreCase("Transporte")){
            this.tipo=tipo;
        }
        else{
            throw new IllegalArgumentException("O tipo ten que ser solar, servicio ou transporte"); 
        }
        this.posicion=posicion;
        this.avatares=new ArrayList<>(); //inicializamos o array vacío
        this.visitas = 0;
        this.jugadoresvisitantes = new ArrayList<>();

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
    public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero);

    //EVALUAR CASILLAS PARA SUERTE/COMUNIDAD
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

        System.out.println(info.toString());
    }

    // Método para recuperar el dueño de una casilla, implementado por las subclases que lo tengan
    public Jugador getDuenho(){
        consola.imprimir("Esta casilla no tiene dueño");
        //HAY QUE PONER UNA EXCEPCIÓN PARA QUE NO COJA NADA EN CASO DE FALLO
    }

    public float calcularAlquiler(Jugador actual, int tirada){
        return 0;  //Borrar cuando excepción puesta
        //EXCEPCIÓN
    }

    public boolean estaHipotecada(){
        //EXCEPCION
        return false;  //Borrar cuando excepción puesta

    }

    public void venderEdificios(int cantidad, String tipo, Jugador banca, Jugador jugador){
        //Lanzar excepción
        }
    


    ///

public void listarAvatares (StringBuilder info){

        ArrayList<Avatar> avatares = this.getAvatares();

        info.append("Jugadores: ");
        if(this.getAvatares().isEmpty()){
            info.append(" - ");
        }
        else{
            for(Avatar avatar : avatares){
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

 

}
