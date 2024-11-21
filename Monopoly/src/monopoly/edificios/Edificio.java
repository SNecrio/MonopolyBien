package monopoly.edificios;

import monopoly.casillas.*;
import partida.*;
import monopoly.*;

public class Edificio {

    //Atributos
    private Jugador duenho; 
    private int id;
    private Casilla casilla;
    private float coste;
    private Grupo grupo;
    

    //Constructor vacío.
    public Edificio(){
        this.duenho = null;
        this.id = 0;
        this.coste = 0.0f;
        this.casilla = null;
        this.grupo = null;
    }

    //Constructor
    public Edificio(Jugador duenho, int id, float coste, Casilla casilla, Grupo grupo){
        this.duenho = duenho;
        this.id = id;
        this.coste = coste;
        this.casilla = casilla;
        this.grupo = grupo;
    }
    
    public Jugador getDuenho(){
        return this.duenho;
    }

    public int getId(){
        return this.id;
    }

    public float getCoste(){
        return this.coste;
    }

    public Casilla getCasilla(){
        return this.casilla;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setDuenho(Jugador duenho){
        this.duenho = duenho;
    }

    public void setId(int id){
        this.id= id;
    }

    public void setCasilla(Casilla casilla){
        this.casilla = casilla;
    }

    public void setGrupo(Grupo grupo){
        this.grupo =grupo;
    }


}
