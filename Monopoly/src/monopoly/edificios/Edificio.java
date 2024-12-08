package monopoly.edificios;
import java.util.ArrayList;
import monopoly.casillas.*;
import partida.*;
import monopoly.*;
import excepcions.ExcepcionMaximoEdificar;

public abstract class Edificio {

    // Atributos
    private Jugador duenho; 
    private int id;
    private PropiedadSolar casilla;
    private Grupo grupo;
    private String tipo;
    private float coste;  // Coste común que será modificado en las subclases
  
    // Constructor vacío
    public Edificio() {
        this.tipo = "";
        this.duenho = null;
        this.id = 0;
        this.casilla = null;
        this.grupo = null;
        this.coste = 0.0f; // Valor predeterminado
    }
  
    // Constructor con parámetros
    public Edificio(Jugador duenho, int id, PropiedadSolar casilla, Grupo grupo, String tipo, float coste) {
        this.duenho = duenho;
        this.id = id;
        this.casilla = casilla;
        this.grupo = grupo;
        this.tipo = tipo;
        this.coste = coste;
    }

    //Métodos para listar edificios construidos
    public void listarEdificio(){
        StringBuilder info = new StringBuilder();

        info.append("{").append("\n");
        info.append("id: ").append(this.tipo).append("-").append(this.id).append("\n");
        info.append("propietario: ").append(this.duenho.getNombre()).append("\n");
        info.append("casilla: ").append(this.casilla.getNombreSinColor()).append("\n");
        info.append("grupo: ").append(this.grupo.getColor()).append("\n");
        info.append("coste: ").append(calcularCoste()).append("\n");
        info.append("}\n");

        System.out.println(info.toString());
    }

    public abstract float calcularCoste();

    public abstract boolean puedeConstruir() throws ExcepcionMaximoEdificar;

    //ESTE METODO USAMOLO CANDO POR EJEMPLO COMPRAMOS UN HOTEL HAI QUE ELIMINAR CATRO CASAS
    public abstract ArrayList<Edificio> accionComprar(ArrayList<Edificio> edificios);
    
    public abstract void identificadorEdificio(StringBuilder info);

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

    public float getCoste(){
        return this.coste;
    }

    public PropiedadSolar getCasilla(){
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

    public void setCasilla(PropiedadSolar casilla){
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
