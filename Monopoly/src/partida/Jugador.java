package partida;

import java.util.ArrayList;
import monopoly.*;
import static monopoly.Valor.FORTUNA_INICIAL;
import static monopoly.Valor.SUMA_VUELTA;
import monopoly.casillas.*;
import monopoly.edificios.Edificio;
import partida.avatares.*;

public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private float dineroInvertido;
    private float pagoTasasImpuestos;
    private float pagoAlquiler;
    private float cobroAlquiler;
    private float pasoSalida;
    private float premios;
    private int vecesCarcel; 
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private int vueltasTotal;
    private ArrayList<CasillaPropiedad> propiedades; //Propiedades que posee el jugador.
    private int dobles; //Cuenta el número de veces que salen dobles en un turno
    //private int bloqueado; //Cuenta los turnos que un jugador no puede tirar
    private boolean comprado; //Para saber si un jugador ha comprado una propiedad en el mismo turno (usado para, si sacas dobles, no volver a preguntar modo)
    private boolean modo; //false básico, true avanzado
    private int vecesDados;
    private ConsolaNormal consola;

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre="Banca";
        this.avatar=null;
        this.fortuna=0.0f;
        this.gastos=0.0f;
        this.enCarcel=false;
        this.tiradasCarcel=0;
        this.vueltas=0;
        this.vueltasTotal=0;
        this.propiedades= new ArrayList<>();
        this.dineroInvertido = 0.0f;
        this.pagoTasasImpuestos = 0.0f;
        this.pagoAlquiler = 0.0f;
        this.cobroAlquiler = 0.0f;
        this.pasoSalida = 0.0f;
        this.premios = 0.0f;
        this.vecesCarcel = 0;
        consola = new ConsolaNormal();
        //this.bloqueado = 0;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */

    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {

        this.nombre = nombre;
        this.fortuna = (float)FORTUNA_INICIAL; 
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.vueltasTotal=0;
        this.propiedades= new ArrayList<>();
        //Dependiendo del tipo de avatar, se llama al constructor de la subclase
        if(tipoAvatar.equalsIgnoreCase("coche")) this.avatar = new AvatarCoche(tipoAvatar, this, inicio, avCreados);
        else if(tipoAvatar.equalsIgnoreCase("pelota")) this.avatar = new AvatarPelota(tipoAvatar, this, inicio, avCreados);
        else if(tipoAvatar.equalsIgnoreCase("esfinge")) this.avatar = new AvatarEsfinge(tipoAvatar, this, inicio, avCreados);
        else this.avatar = new AvatarSombrero(tipoAvatar, this, inicio, avCreados);
        this.dineroInvertido = 0.0f;
        this.pagoTasasImpuestos = 0.0f;
        this.pagoAlquiler = 0.0f;
        this.pasoSalida = 0.0f;
        this.premios = 0.0f;
        this.vecesCarcel = 0;  
        this.cobroAlquiler = 0.0f;
        this.comprado = false;
        this.modo = false;
        consola = new ConsolaNormal();
    }
    
    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(CasillaPropiedad casilla) {
        this.propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(CasillaPropiedad casilla) {
        if(!this.propiedades.remove(casilla)){
            consola.imprimir("El jugador no tiene esa casilla en propiedad, no se puede eliminar");
        }
    }

    //Método para sumar el numero de vueltas y sumarle la fortuna.
    public void sumarVueltas(){
        this.vueltas ++;
        this.fortuna += SUMA_VUELTA;
        this.pasoSalida += SUMA_VUELTA;
        consola.imprimir("El jugador " + this.nombre + " paso por la salida y recibio " + SUMA_VUELTA);
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    //Método para quitar la fortuna de un jugador al comprar una propiedad
    public void pagar(float valor){
        this.fortuna -= valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
                
        if(!this.enCarcel){
            this.enCarcel = true;
            this.tiradasCarcel = 0;
            Casilla carcel = pos.get(3).get(0);
            getAvatar().moverAvatar(pos, carcel, false);//Lo lleva a la carcel
            //carcel.sumarVisitas(1);
            //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);
            this.vecesCarcel++;

        }else{
            consola.imprimir("El jugador ya esta en la carcel");;
        }
    }

    /*Devuelve true si el jugador está encarcelado o falso si no lo es*/ 
    public boolean getEncarcel(){
        return this.enCarcel;
    }

    /*Setter del atributo enCarcel*/ 
    public void setEncarcel(boolean estado){
        this.enCarcel = estado;
    }


    /*Devuelve un entero que indica el número de casillas de tipo Servicio que tiene el jugador. Servirá a la hora de calcular el alquiler que 
    hay que pagar.*/
    public int getNumCasillasServicio(){
        int contador = 0;
        for(Casilla casilla: propiedades){
            if(casilla.getTipo().equals("Servicio")){
                contador++;
            }
        }
        return contador;
    }

    /*Devuelve un entero que indica el número de casillas de tipo Transporte que tiene el jugador. Servirá a la hora de calcular el alquiler que 
    hay que pagar.*/
    public int getNumCasillasTransporte(){
        int contador = 0;
        for(Casilla casilla: propiedades){
            if(casilla.getTipo().equals("Transporte")){
                contador++;
            }
        }
        return contador;
    }

    
    public void listarPropiedadesenVenta(){
        if(propiedades.isEmpty()){ //comprobamos que no está vacía
            consola.imprimir("No hay propiedades");
        }

        else{
            consola.imprimir("Propiedades en venta: ");
            for(CasillaPropiedad propiedad : propiedades){
                propiedad.casEnVenta();
            }
    }
    }

    public boolean poseeGrupo(Grupo grupo){
        ArrayList<PropiedadSolar> casillasGrupo = grupo.getCasillas();
        for(Casilla casilla: casillasGrupo){
            if(!propiedades.contains(casilla)){
                return false; //si galta alguna el jugador no posee el grupo
            }
        }

        return true;
    }

    public void describirJugador(String nombre) {
        if(this.nombre.equalsIgnoreCase(nombre)){
            StringBuilder info = new StringBuilder();
    
            info.append("Nombre:").append(this.nombre).append("\n");
            info.append("Avatar:").append(this.avatar.getId()).append("\n");
            info.append("Fortuna:").append(this.fortuna).append("\n");
    
            // Agregar las propiedades
            info.append("Propiedades: ");
            for(CasillaPropiedad propiedad : propiedades) {
                info.append("[").append(propiedad.getNombreSinColor()).append("],");
            }
            info.append("\n");
    
            // Agregar las propiedades hipotecadas
            info.append("Hipotecas: ");
            boolean hayHipotecas = false;  // Controlar si hay propiedades hipotecadas
            for(CasillaPropiedad propiedad : propiedades) {
                if(propiedad.estaHipotecada()) {
                    hayHipotecas = true;
                    info.append("[").append(propiedad.getNombreSinColor()).append("],");
                }
            }
            if (!hayHipotecas) {
                info.append("Ninguna propiedad hipotecada.");
            }
            info.append("\n");
    
            int contador =0;
            // Agregar edificios
            info.append("Edificios: ");
            for (CasillaPropiedad propiedad : propiedades) {
                if(propiedad instanceof PropiedadSolar){
                    PropiedadSolar solar = (PropiedadSolar) propiedad;
                    if(solar.getEdificios().isEmpty()==true){
                        contador++;                        
                    }
                    else{
                        for(Edificio edificio: solar.getEdificios()){
                            info.append("[").append(edificio.getTipo()).append("-").append(edificio.getId()).append("] ");
                        }
                    }
                }
                
            }
            if(contador !=0){
                info.append("No hay edificios todavía");
            }
    
            info.append("\n");
            consola.imprimir(info.toString());
            } 
    }

    public void mostrarEstadisticas(){
        StringBuilder info = new StringBuilder();
            
        info.append("{").append("\n");
        info.append("dinero invertido: ").append(this.dineroInvertido).append("\n");
        info.append("pago tasas e impuestos: ").append(pagoTasasImpuestos).append("\n");
        info.append("pago de alquileres: ").append(pagoAlquiler).append("\n");
        info.append("cobro de alquileres: ").append(cobroAlquiler).append("\n");
        info.append("pasar por casilla de salida: ").append(pasoSalida).append("\n");
        info.append("premios inversiones o bote: ").append(premios).append("\n");
        info.append("veces en la carcel: ").append(vecesCarcel).append("\n");
        info.append("}").append("\n");

        consola.imprimir(info.toString());
    }

    //Metodo para transferir todas as propiedades do xogador a banca
    public void transferirPropiedadesBanca(Jugador banca){
        for(CasillaPropiedad propiedad: propiedades){
            propiedad.setDuenho(banca);
        }
        propiedades.clear(); //limpiamos as propidades do xogador eliminado
    }

    public void EstadisticaDineroInvertido(float valor){
        this.dineroInvertido += valor;
    }

    public void EstadisticaTasasImpuesto(float valor){
        this.pagoTasasImpuestos += valor;
    }

    public void EstadisticaPagoAlquiler(float valor){
        this.pagoAlquiler += valor;
    }

    public void EstadisticaCobroAlquiler(float valor){
        this.cobroAlquiler += valor;
    }

    public void EstadisticaPremios(float valor){
        this.premios += valor;
    }
    //Incrementar el contador para saber las veces que salen dobles
    public void incrementarDobles() {
        dobles++;
    }
    
    public void resetearDobles() {
        dobles = 0;
    }
    
    public int getDobles() {
        return dobles;
    }

    public int getTiradasCarcel(){
        return this.tiradasCarcel;
    }

    public void setTiradasCarcel(int num){
        this.tiradasCarcel = num;
    }

    public float getFortuna(){
        return this.fortuna;
    }

    public ArrayList<CasillaPropiedad> getPropiedades(){
        return this.propiedades;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Avatar getAvatar(){
        return this.avatar;
    }

    public void setFortuna(float fortuna){
        this.fortuna = fortuna;
    }

     public int getVueltas(){
        return this.vueltas;
    }

    public void setVueltas(int vueltas){
        this.vueltas = vueltas;
    }
/* 
    public int getBloqueado(){
        return this.bloqueado;
    }
    
    public void setBloqueado(int bloqueado){
        this.bloqueado = bloqueado;
    }*/

    public boolean getComprado(){
        return this.comprado;
    }
    
    public void setComprado(boolean comprado){
        this.comprado = comprado;
    }

    public void setDobles(int dobles){
        this.dobles = dobles;
    }

    public boolean getModo(){
        return this.modo;
    }
    
    public void setModo(boolean modoElegido){
        this.modo = modoElegido;
    }


    @Override
    public boolean equals(Object obj){
    if(this==obj) return true;
    if(obj==null) return false;
    if(getClass() != obj.getClass()) return false;
    final Jugador other = (Jugador) obj;
    if(!this.avatar.equals(other.avatar)){
        return false;
    }
    return true;
    }

    public int getTiradas(){
        return this.vecesDados;
    }

    public void sumarTiradas(int valor){
        this.vecesDados += valor;
    }

    public int getVueltasTotal(){
        return this.vueltasTotal;
    }

    public void sumarVueltasTotal(int valor){
        this.vueltasTotal += valor;
    }

}
