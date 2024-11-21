package monopoly;

import monopoly.casillas.*;
import partida.*;

class Edificio {

    public static final String TIPO_HOTEL = "Hotel";
    public static final String TIPO_CASA = "Casa";
    public static final String TIPO_PISCINA = "Piscina";
    public static final String TIPO_PISTA_DEPORTE = "Pista Deporte";

    //Atributos
    private Jugador duenho; 
    private int id;
    private String tipo; //hotel/casa/piscina/pista deporte
    private Casilla casilla;
    private float coste;
    private Grupo grupo;
    

    //Constructor vacío.
    public Edificio(){
        this.duenho = null;
        this.id = 0;
        this.tipo = "";
        this.coste = 0.0f;
        this.casilla = null;
        this.grupo = null;
    }

    //Constructor
    public Edificio(Jugador duenho, int id, String tipo, float coste, Casilla casilla, Grupo grupo){
        this.duenho = duenho;
        this.id = id;
        if(tipo.equalsIgnoreCase("Hotel")|| tipo.equalsIgnoreCase("Casa")|| tipo.equalsIgnoreCase("Piscina")||tipo.equalsIgnoreCase("Pista de Deporte")){
            this.tipo = tipo;
        }
        else{
            this.tipo = " "; //Inicializamos con un constructor vacío.
        }
        this.coste = coste;
        this.casilla = casilla;
        this.grupo = grupo;
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

   public float venderEdificioBanca(Jugador banca){
        if(this.duenho !=banca){
            float fortuna = this.duenho.getFortuna();
            this.duenho.setFortuna(fortuna + (0.5f * this.coste));
            this.duenho = banca;

            return 0.5f * this.coste;
        }
        else{
            System.out.println("No tiene dueño este edificio");
            return 0;
        }
    
    }
    
    public Jugador getDuenho(){
        return this.duenho;
    }

    public int getId(){
        return this.id;
    }

    public String getTipo(){
        return this.tipo;
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

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setCasilla(Casilla casilla){
        this.casilla = casilla;
    }

    public void setGrupo(Grupo grupo){
        this.grupo =grupo;
    }


}