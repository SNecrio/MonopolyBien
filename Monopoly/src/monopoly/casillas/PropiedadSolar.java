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
        super();
        this.edificios = new ArrayList<>();
        this.grupo = null;
        this.alquilersimple=0.0f;
    }

    public PropiedadSolar(Grupo grupo,float valor, Jugador duenho, String nombre, int posicion){
        super(valor, duenho, nombre, "Solar", posicion);
        this.edificios = new ArrayList<>();
        this.grupo = grupo;
        this.alquilersimple = 0.1f*this.getValor();
    }

    //METODOS

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

    @Override
    public void infoCasilla(StringBuilder info){
        info.append("Grupo: ").append(grupo.getColor()).append("\n");
        info.append("Alquiler: ").append(this.alquilersimple).append("\n");
        info.append("valor casa: ").append(0.6f*getValorInicial()).append("\n");
        info.append("valor hotel: ").append(0.6f*getValorInicial()).append("\n");
        info.append("valor piscina: ").append(0.4f*getValorInicial()).append("\n");
        info.append("valor pista de deporte: ").append(1.25f*getValorInicial()).append("\n");
        info.append("alquiler una casa: ").append(5* alquilersimple).append("\n");
        info.append("alquiler dos casas: ").append(15*alquilersimple).append("\n");
        info.append("alquiler tres casas: ").append(35*alquilersimple).append("\n");
        info.append("alquiler cuatro casas: ").append(50*alquilersimple).append("\n");
        info.append("alquiler hotel: ").append(70*alquilersimple).append("\n");
        info.append("alquiler piscina: ").append(25*alquilersimple).append("\n");
        info.append("alquiler pista de deporte: ").append(25*alquilersimple).append("\n");
    }

    @Override
    public void casEnVenta(){
        StringBuilder info = new StringBuilder();
        info.append(Valor.WHITE + "\n").append(this.getNombre()).append("\n");
        info.append("Tipo: ").append(this.getTipo()).append("\n");
        info.append("Grupo: ").append(grupo.getColor()).append("\n");
        info.append("Valor: ").append(this.getValor()).append("\n");
    }

    public boolean tieneEdificios(){
        if( edificios.size() == 0){
            return false;
        }
        return true;
    }

    /*public boolean Edificar(Jugador jugador, String tipo){
        if(this.perteneceAJugador(jugador) == false){
            System.out.println("El jugador no es dueño de esta casilla."); ///EXCEPCIÓN
            return false;
        }
            
        
        float costoConstruccion = obtenerCosteConstruccion(tipo); 
        if(costoConstruccion <= 0.0){
            System.out.println("Tipo de construccion no valido");
            return false;
        }

        if(jugador.getFortuna() < costoConstruccion){
            System.out.println("El jugador no tiene suficiente dinero para construir");
            return false;
        }

        int numeroCasillasGrupo = grupo.getNumeroCasillas();
        int numeroHotelesGrupo = contarEdificiosPorTipoGrupo("hotel");
        int numeroPiscinasGrupo = contarEdificiosPorTipoGrupo("piscina");
        int numeroPistasGrupo = contarEdificiosPorTipoGrupo("pistas de deporte");


        if(tipo.equalsIgnoreCase("casa")){
            if(numeroCasillasGrupo==2){
                if(numeroHotelesGrupo == 2){
                    if(contarEdificiosPorTipo("Casa") >= 2){
                        System.out.println("No se pueden construír más de 2 casas en este solar. Construye un hotel");
                        return false;
                    }  
                }
                else{
                    if(contarEdificiosPorTipo("Casa") >= 4){
                        System.out.println("No se pueden construír más de 4 casas en este solar. Construye un hotel");
                        return false;
                    }
                }
            }
            else if(numeroCasillasGrupo == 3){
                if(numeroHotelesGrupo == 3){
                    if(contarEdificiosPorTipo("Casa") >= 3){
                        System.out.println("No se pueden construír más de 3 casas en este solar. Construye un hotel");
                        return false;
                    }  
                }
                else{
                    if(contarEdificiosPorTipo("Casa") >= 4){
                        System.out.println("No se pueden construír más de 4 casas en este solar. Construye un hotel");
                        return false;
                    }
                }
            }
            
    }*/

    
    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }
}

