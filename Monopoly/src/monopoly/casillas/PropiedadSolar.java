package monopoly.casillas;

import java.util.ArrayList;
import partida.*;
import monopoly.*;
import monopoly.edificios.*;
import monopoly.edificios.Edificio;

public class PropiedadSolar extends CasillaPropiedad {
    //ATRIBUTOS
    private ArrayList<Edificio> edificios;
    private Grupo grupo;
    private float alquilersimple;

    //CONSTRUCTOR VACÍO
    public PropiedadSolar(){
        this.edificios = new ArrayList<>();
        this.grupo = null;
        this.alquilersimple=0.0f;
    }

    public PropiedadSolar(Grupo grupo){
        this.edificios = new ArrayList<>();
        this.grupo = grupo;
        this.alquilersimple = 0.1f*this.getValor();
    }

    //METODOS
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada){

    }

    //Método que calcular el alquiler de las casillas de tipo solar
    public float calcularAlquiler(Jugador actual, int tirada){
        float anhadidoEdificaciones = calculoAlquilerAnhadidoEdificaciones();
        return (this.alquilersimple + anhadidoEdificaciones); 
    }

    float calculoAlquilerAnhadidoEdificaciones(){
        
        int numeroCasas = contarEdificiosPorTipo("Casa");
        int numeroHoteles = contarEdificiosPorTipo("Hotel");
        int numeroPiscinas = contarEdificiosPorTipo("Piscina");
        int numeroPistaDeporte = contarEdificiosPorTipo("Pista de Deporte");
        float anhadidoCasa = 0.0f;
        float anhadidoHotel = 0.0f;
        float anhadidoPiscina = 0.0f;
        float anhadidoPista = 0.0f;

        switch(numeroCasas){
            case 0:
                anhadidoCasa = 0.0f;
                break;
            case 1:
                anhadidoCasa = 5 * this.alquilersimple;
                break;
            case 2:
                anhadidoCasa = 15 * this.alquilersimple;
                break;
            case 3:
                anhadidoCasa = 35 * this.alquilersimple;
                break;
            case 4:
                anhadidoCasa = 50 * this.alquilersimple;
                break;
            default:
                System.out.println("No se pueden tener más de cuatro casas");
        }

        if(numeroHoteles == 1){
            anhadidoHotel = 70 * this.alquilersimple;
        }
        if(numeroPiscinas==1){
            anhadidoPiscina = 25 * this.alquilersimple;
        }
        if(numeroPistaDeporte == 1){
            anhadidoPista = 25*this.alquilersimple;
        }

        return (anhadidoCasa + anhadidoHotel + anhadidoPiscina + anhadidoPista);
    }

    public int contarEdificiosPorTipo(String tipo){
        int contador = 0;
        for (Edificio edificio: edificios){
            if(edificio.getTipo().equalsIgnoreCase(tipo)){
                contador++;
            }
        }
        return contador;
    }
    
}

