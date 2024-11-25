package monopoly.casillas;

import java.util.ArrayList;
import partida.*;
import monopoly.*;
import monopoly.edificios.*;
import monopoly.edificios.Edificio;

public abstract class CasillaPropiedad extends Casilla {
    private float valor;
    private float valorInicial;
    private Jugador duenho;
    private float hipoteca;
    private boolean estarHipotecada;
    private float rentabilidad;

     //Constructores:
    public CasillaPropiedad() {
        super();
        this.valor = 0.0f;
        this.valorInicial = 0.0f;
        this.duenho = null;
        this.hipoteca = 0.0f;
        this.estarHipotecada = false;
        this.rentabilidad = 0.0f;

    }

    public CasillaPropiedad(float valor, Jugador duenho, Grupo grupo,String nombre, String tipo, int posicion){
        super(nombre, tipo, posicion);
        this.valor = valor;
        this.valorInicial = valor;
        this.duenho = duenho;
        this.hipoteca = 0.5f * valor;
        this.estarHipotecada = false;
        this.rentabilidad = 0.0f;

    }

    public abstract float calcularAlquiler(Jugador actual, int tirada);



    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada){
        if(this.duenho == actual){
            return true;
        }

        boolean espropiedadBanca = (this.duenho == banca);

        if(espropiedadBanca== true){
            if(actual.getFortuna() >= this.valor){
                return true;
            }
            else{
                System.out.println("No te puedes permitir comprar esta casilla");
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

    public float getValor(){
        return this.valor;
    }
    public void setValor(float valor){
        this.valor = valor;
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

}
