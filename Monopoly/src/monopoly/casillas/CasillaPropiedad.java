package monopoly.casillas;

import excepcions.ExcepcionPropiedad;
import excepcions.ExcepcionPropiedadComprar;
import java.util.ArrayList;
import monopoly.*;
import partida.*;

public abstract class CasillaPropiedad extends Casilla {
    private float valor;
    private float valorInicial;
    private Jugador duenho;
    private float hipoteca;
    private boolean estarHipotecada;
    private float rentabilidad;
    private int visitas = 0;
    private ArrayList<Jugador> jugadoresvisitantes; //Array usado para incluir los nombres de todas las personas que caen en la casilla (para calcular si el jugador cae mas de dos veces en esta casilla se puede comprar)
    private ConsolaNormal consola;



    //Constructores:
    public CasillaPropiedad() {
        super();
        this.valor = 0.0f;
        this.valorInicial = 0.0f;
        this.duenho = null;
        this.hipoteca = 0.0f;
        this.estarHipotecada = false;
        this.rentabilidad = 0.0f;
        this.jugadoresvisitantes = new ArrayList<>();
        this.consola = new ConsolaNormal();
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
        this.consola = new ConsolaNormal();

    }

    public abstract float calcularAlquiler(Jugador actual, int tirada);

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> lista){
        if(perteneceAJugador(actual)){
            return true;
        }

        boolean espropiedadBanca = perteneceAJugador(banca);

        if(espropiedadBanca== true){
            if(actual.getFortuna() >= this.valor){
                return true;
            }
            else{
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

    @Override
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

    public void comprarCasilla(Jugador solicitante, Jugador banca) throws ExcepcionPropiedad{
 
        if (perteneceAJugador(banca)) { 
            if(solicitante.getAvatar().getLugar().getPosicion()!=this.getPosicion()){
                throw new ExcepcionPropiedadComprar("No puedes comprar la propiedad porque no estás situado sobre ella");
            }
            if (solicitante.getFortuna() >= this.valor) {
                solicitante.pagar(this.valor);
                solicitante.EstadisticaDineroInvertido(this.valor);
                banca.eliminarPropiedad(this);
                solicitante.setComprado(true);
                solicitante.anhadirPropiedad(this);
                this.duenho = solicitante;
        
                consola.imprimir("El jugador " + solicitante.getNombre() + " compra la casilla " + this.getNombreSinColor() + Valor.WHITE + " por " + this.valor + ".");
            } else {
                throw new ExcepcionPropiedadComprar("\nEl jugador " + solicitante.getNombre() + " no tiene suficiente dinero para comprar esta casilla.");
            }
        } else {
            throw new ExcepcionPropiedadComprar("\nLa casilla " + this.getNombreSinColor() + " no está en venta o ya tiene dueño.");
        }
    }

    public void hipotecarCasilla(Jugador solicitante, Jugador banca){

        estarHipotecada = true;
        solicitante.sumarFortuna(this.hipoteca);

        consola.imprimir("El jugador " + solicitante.getNombre() + " hipoteca la casilla " + this.getNombreSinColor() + Valor.WHITE + " por " + this.hipoteca + ".");
    }

    public void deshipotecarCasilla(Jugador solicitante, Jugador banca){
        estarHipotecada = false;
        solicitante.sumarFortuna(this.hipoteca * -1.1f);

        consola.imprimir("El jugador " + solicitante.getNombre() + " deshipoteca la casilla " + this.getNombre() + Valor.WHITE + " por " + this.hipoteca * 1.1f + 
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
   
    public Jugador getDuenho(){
        return this.duenho;
    }
    public void setDuenho(Jugador duenho){
        this.duenho = duenho;
    }

    public float getHipoteca(){
        return this.hipoteca;
    }

    public boolean estaHipotecada(){
        return this.estarHipotecada;
    }

     public float getRentabilidad(){
        return rentabilidad;
    }

    public void sumarRentabilidad(float valor){
        this.rentabilidad += valor;
    }
    
}
