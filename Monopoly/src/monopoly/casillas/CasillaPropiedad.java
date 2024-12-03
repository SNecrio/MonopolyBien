package monopoly.casillas;

import java.util.ArrayList;
import partida.*;
import monopoly.*;
import monopoly.edificios.*;
import monopoly.edificios.Edificio;
import excepcions.*;

public abstract class CasillaPropiedad extends Casilla {
    private float valor;
    private float valorInicial;
    private Jugador duenho;
    private float hipoteca;
    private boolean estarHipotecada;
    private float rentabilidad;
    private float alquiler;
    private int visitas = 0;
    private ArrayList<Jugador> jugadoresvisitantes; //Array usado para incluir los nombres de todas las personas que caen en la casilla (para calcular si el jugador cae mas de dos veces en esta casilla se puede comprar)



    //Constructores:
    public CasillaPropiedad() {
        super();
        this.valor = 0.0f;
        this.valorInicial = 0.0f;
        this.duenho = null;
        this.hipoteca = 0.0f;
        this.estarHipotecada = false;
        this.rentabilidad = 0.0f;
        this.alquiler = 0.0f;
        this.jugadoresvisitantes = new ArrayList<>();

    }

    public CasillaPropiedad(float valor, Jugador duenho, String nombre, String tipo, int posicion){
        super(nombre, tipo, posicion);
        this.valor = valor;
        this.valorInicial = valor;
        this.duenho = duenho;
        this.hipoteca = 0.5f * valor;
        this.estarHipotecada = false;
        this.rentabilidad = 0.0f;
        this.jugadoresvisitantes = new ArrayList<>();

    }

    @Override  //Declarado por primera vez en casilla
    public float calcularAlquiler(Jugador actual, int tirada){
        return 0;  
        //EXCEPCIÓN
    }

    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero){
        if(perteneceAJugador(actual)){
            return true;
        }

        boolean espropiedadBanca = perteneceAJugador(banca);

        if(espropiedadBanca== true){
            if(actual.getFortuna() >= this.valor){
                return true;
            }
            else{
                System.out.println("No te puedes permitir comprar esta casilla"); //EXECEPCION!!!!!!!!!!!!
                GeneralException("No te puedes permitir comprar esta casilla");
                return false;
            }
        }

        //La casillla pertenece a otro jugador y calculamos el alquiler
        float alquiler = calcularAlquiler(actual, tirada);

        if(this.estarHipotecada == true){
            return true;
        }

        if(actual.getFortuna() >= alquiler){ //se puede permitir pagar la propiedad
            Jugador duenhocasilla = this.duenho;

            actual.pagar(alquiler);
            actual.sumarGastos(alquiler);
            actual.EstadisticaPagoAlquiler(alquiler);

            duenhocasilla.EstadisticaCobroAlquiler(alquiler);
            duenhocasilla.sumarFortuna(alquiler);

            this.sumarRentabilidad(alquiler);
            /*
            if(this.tipo.equalsIgnoreCase("Solar")){
                this.grupo.sumarRentabilidad(alquiler);
            }*/
            return true;
        }
        else{
            return false; 
        }       
    }

    public void describirEspecifico(StringBuilder info){
        info.append("Valor: ").append(valor).append("\n");
        info.append("Dueño: ").append(duenho.getNombre()).append("\n");
        info.append("Hipoteca: ").append(hipoteca).append("\n");

        infoCasilla(info);
    }

    public abstract void infoCasilla(StringBuilder info);

    boolean perteneceAJugador(Jugador jugador){
        if(this.duenho.getNombre().equalsIgnoreCase(jugador.getNombre())){
            return true;
        }

        return false;
    }

    public void comprarCasilla(Jugador solicitante, Jugador banca){
        if(perteneceAJugador(solicitante)==true){
            System.out.println("NO PUEDES COMPRAR UNA PROPIEDAD QUE YA ES TUYA"); //EXCEPCIÓN
            return;
        }

        if(perteneceAJugador(banca)==false){
            System.out.println("No puedes comprar una casilla que ya esta comprada"); //EXCEPCIÓN
            return;
        }

        else if(solicitante.getFortuna() < this.valor){
            System.out.println("El jugador no tiene dinero suficiente para comprar la casilla"); //EXCEPCIÓN
            return;
        }

        banca.eliminarPropiedad(this);
        solicitante.anhadirPropiedad(this);
        solicitante.setComprado(true);
        solicitante.pagar(this.valor); //reducimos a fortuna
        solicitante.sumarGastos(this.valor); //aumentamos os gastos
        solicitante.EstadisticaDineroInvertido(this.valor);

        //establecemos dueño da casilla
        this.duenho = solicitante;
        
        System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + this.getNombreSinColor() + Valor.WHITE + " por " + this.valor + ".");
    }

    public void hipotecarCasilla(Jugador solicitante, Jugador banca){

        estarHipotecada = true;
        solicitante.sumarFortuna(this.hipoteca);

        System.out.println("El jugador " + solicitante.getNombre() + " hipoteca la casilla " + this.getNombreSinColor() + Valor.WHITE + " por " + this.hipoteca + ".");
    }

    public void deshipotecarCasilla(Jugador solicitante, Jugador banca){
        estarHipotecada = false;
        solicitante.sumarFortuna(this.hipoteca * -1.1f);

        System.out.println("El jugador " + solicitante.getNombre() + " deshipoteca la casilla " + this.nombre + Valor.WHITE + " por " + this.hipoteca * 1.1f + 
        ", ahora puede volver a recibir alquiler");
    }

    public abstract void casEnVenta();

    

    
    public float getValor(){
        return this.valor;
    }
    public void setValor(float valor){
        this.valor = valor;
    }

    public void sumarValor(float valor){
        this.valor += valor;
    }

    public float getValorInicial(){
        return this.valorInicial;
    }
   
    @Override
    public Jugador getDuenho(){
        return this.duenho;
    }
    public void setDuenho(Jugador duenho){
        this.duenho = duenho;
    }

    public float getHipoteca(){
        return this.hipoteca;
    }

    @Override
    public boolean estaHipotecada(){
        return this.estarHipotecada;
    }

     public float getRentabilidad(){
        return rentabilidad;
    }

    public void sumarRentabilidad(float valor){
        this.rentabilidad += valor;
    }

    public int getVisitas(){
        return visitas;
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
            System.out.println("Jugador no puede ser nulo");
        }   
    }

}
