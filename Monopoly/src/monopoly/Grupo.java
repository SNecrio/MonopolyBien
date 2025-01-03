package monopoly;

import java.util.ArrayList;
import monopoly.casillas.*;
import monopoly.edificios.Edificio;
import partida.*;


public class Grupo {

    //Atributos
    private ArrayList<PropiedadSolar> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.
    private float rentabilidad = 0f;
    private ConsolaNormal consola;

    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<>();
        this.colorGrupo="";
        this.numCasillas= 0;
        consola = new ConsolaNormal();
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(PropiedadSolar cas1, PropiedadSolar cas2, String colorGrupo) {
        this.miembros = new ArrayList<>(); //inicializamos o array de miembros
        this.miembros.add(cas1);
        //cas1.
        this.miembros.add(cas2);
        this.colorGrupo=colorGrupo;

        this.numCasillas=2;
        consola = new ConsolaNormal();
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(PropiedadSolar cas1, PropiedadSolar cas2, PropiedadSolar cas3, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo= colorGrupo;
        this.numCasillas=3;
        consola = new ConsolaNormal();
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(PropiedadSolar miembro){
        if(!miembros.contains(miembro)){
            this.miembros.add(miembro);
            this.numCasillas++;
        }
    }


    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        
        ArrayList<CasillaPropiedad> casillasJugador = jugador.getPropiedades();
        int contador = 0;

        for(CasillaPropiedad casilla: casillasJugador){
            if(casilla instanceof PropiedadSolar){
                PropiedadSolar casillaSolar = (PropiedadSolar)casilla;
                for(PropiedadSolar solar : this.miembros){
                    if(casillaSolar.equals(solar)){
                        contador++;
                    }
                }
            }
        }

        if(contador == numCasillas){
            return true;
        }

        return false;
        
    }


    public void listarEdificiosGrupo(){

        for(PropiedadSolar propiedad : miembros){

            propiedad.ObtenerArrayEdificiosPorTipo("casa");

            ArrayList<Edificio> listaCasas = propiedad.ObtenerArrayEdificiosPorTipo("Casa");
            ArrayList<Edificio> listaHoteles = propiedad.ObtenerArrayEdificiosPorTipo("Hotel");
            ArrayList<Edificio> listaPiscinas = propiedad.ObtenerArrayEdificiosPorTipo("Pista de Deporte");
            ArrayList<Edificio> listaPistas = propiedad.ObtenerArrayEdificiosPorTipo("Piscina");

            StringBuilder info = new StringBuilder();
            
            info.append("{").append("\n");
            info.append("propiedad: ").append(propiedad.getNombreSinColor()).append("\n");
            info.append("casas: ");
            if(listaCasas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaCasas){
                    edificio.identificadorEdificio(info);
                }
            }
            info.append("\n");

            info.append("hoteles: ");
            if(listaHoteles.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaHoteles){
                    edificio.identificadorEdificio(info);
                }
            }
            info.append("\n");

            info.append("piscinas: ");
            if(listaPiscinas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaPiscinas){
                    edificio.identificadorEdificio(info);
                }
            }
            info.append("\n");

            info.append("pistas de deporte: ");
            if(listaPistas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaPistas){
                    edificio.identificadorEdificio(info);
                }
            }
            info.append("\n");

            Jugador jugador = new Jugador();
            int tirada = 0;
            
            info.append("alquiler: ").append(propiedad.calcularAlquiler(jugador, tirada)).append("\n");
            info.append("}");
            consola.imprimir(info.toString());
        }
    }


    public String getColor(){
        return this.colorGrupo;
    }

    public void setColor(String color){
        this.colorGrupo = color;
    }

    public ArrayList<PropiedadSolar> getCasillas(){
        return new ArrayList<>(this.miembros);
    }

    public int getNumeroCasillas(){
        return this.numCasillas;
    }

    public void sumarNumeroCasillas(){
        this.numCasillas ++;
    }
    public void sumarRentabilidad(float valor){
        this.rentabilidad += valor;
    }

    public float getRentabilidad(){
        return this.rentabilidad;
    }
}