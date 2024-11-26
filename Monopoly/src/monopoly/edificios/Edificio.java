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
    private String tipo;
    

    //Constructor vacío.
    public Edificio(){
        this.tipo = "";
        this.duenho = null;
        this.id = 0;
        this.coste = 0.0f;
        this.casilla = null;
        this.grupo = null;
    }

    //Constructor
    public Edificio(Jugador duenho, int id, float coste, Casilla casilla, Grupo grupo, String tipo){
        this.duenho = duenho;
        this.id = id;
        this.coste = coste;
        this.casilla = casilla;
        this.grupo = grupo;
        this.tipo = tipo;
    }

    //Métodos para listar edificios construidos
    public void listarEdificio(){
        StringBuilder info = new StringBuilder();

        info.append("{").append("\n");
        info.append("id: ").append(this.tipo).append("-").append(this.id).append("\n");
        info.append("propietario: ").append(this.duenho.getNombre()).append("\n");
        info.append("casilla: ").append(this.casilla.getNombreSinColor()).append("\n");
        info.append("grupo: ").append(this.grupo.getColor()).append("\n");
        info.append("coste: ").append(this.coste).append("\n");
        info.append("}\n");

        System.out.println(info.toString());
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

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return this.tipo;
    }


}
