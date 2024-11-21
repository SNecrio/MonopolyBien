package monopoly;

import partida.*;
import java.util.ArrayList;

public class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.
    private float rentabilidad = 0f;

    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<Casilla>();
        this.colorGrupo="";
        this.numCasillas= 0;
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>(); //inicializamos o array de miembros
        this.miembros.add(cas1);
        //cas1.
        this.miembros.add(cas2);
        this.colorGrupo=colorGrupo;

        this.numCasillas=2; //entendo que como esta formado por duas casillas xa poñemos directamente esto
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo= colorGrupo;
        this.numCasillas=3;
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro){
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
        
        int tamano = miembros.size();
        ArrayList<Casilla> casillasJugador = jugador.getPropiedades();;
        
        //recorremos o array de miembros 
        for(int i=0; i< tamano; i++){
            if(!casillasJugador.contains(miembros.get(i))){  //se o xogador non ten todas as propiedades do grupo, xa devolvemos falso
                return false;
            }
        }
        return true;
    }

    public void listarEdificiosGrupo(){

        for(Casilla casilla : miembros){
            ArrayList<Edificio> listaCasas = casilla.ObtenerArrayEdificiosPorTipo("Casa");
            ArrayList<Edificio> listaHoteles = casilla.ObtenerArrayEdificiosPorTipo("Hotel");
            ArrayList<Edificio> listaPiscinas = casilla.ObtenerArrayEdificiosPorTipo("Pista de Deporte");
            ArrayList<Edificio> listaPistas = casilla.ObtenerArrayEdificiosPorTipo("Piscina");

            StringBuilder info = new StringBuilder();
            
            info.append("{").append("\n");
            info.append("propiedad: ").append(casilla.getNombreSinColor()).append("\n");
            info.append("casas: ");
            if(listaCasas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaCasas){
                    info.append("[").append("casa-").append(edificio.getId()).append("] ");
                }
            }
            info.append("\n");

            info.append("hoteles: ");
            if(listaHoteles.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaHoteles){
                    info.append("[hotel-").append(edificio.getId()).append("] ");
                }
            }
            info.append("\n");

            info.append("piscinas: ");
            if(listaPiscinas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaPiscinas){
                    info.append("[piscina-").append(edificio.getId()).append("] ");
                }
            }
            info.append("\n");

            info.append("pistas de deporte: ");
            if(listaPistas.isEmpty()==true){
                info.append("-vacío-");
            }
            else{
                for(Edificio edificio : listaPistas){
                    info.append("[pista deporte-").append(edificio.getId()).append("] ");
                }
            }
            info.append("\n");
            
            info.append("alquiler: ").append(casilla.calculoAlquilerAnhadidoEdificaciones()+casilla.getImpuesto()).append("\n");
            info.append("}");
            System.out.println(info.toString());
        }
    }

    public String getColor(){
        return this.colorGrupo;
    }

    public ArrayList<Casilla> getCasillas(){
        return new ArrayList<>(this.miembros);
    }

    public int getNumeroCasillas(){
        return this.numCasillas;
    }

    public void sumarRentabilidad(float valor){
        this.rentabilidad += valor;
    }

    public float getRentabilidad(){
        return this.rentabilidad;
    }
}