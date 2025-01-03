package monopoly.casillas;

import java.util.ArrayList;
import monopoly.*;
import monopoly.edificios.Edificio;
import partida.*;
import excepcions.*;
import monopoly.ConsolaNormal;

public class PropiedadSolar extends CasillaPropiedad {
    //ATRIBUTOS
    private ArrayList<Edificio> edificios;
    private Grupo grupo;
    private float alquilersimple;
    private ConsolaNormal consola;

    //CONSTRUCTOR VACÍO
    public PropiedadSolar(){
        super();
        this.edificios = new ArrayList<>();
        this.grupo = null;
        this.alquilersimple=0.0f;
        this.consola = new ConsolaNormal();
    }

    public PropiedadSolar(Grupo grupo,float valor, Jugador duenho, String nombre, int posicion){
        super(valor, duenho, nombre, "Solar", posicion);
        this.edificios = new ArrayList<>();
        this.grupo = grupo;
        this.alquilersimple = 0.1f*this.getValor();
        this.consola = new ConsolaNormal();

    }

    //Método que calcular el alquiler de las casillas de tipo solar
    @Override
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
                consola.imprimir("No se pueden tener más de cuatro casas");
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

    public int contarEdificiosPorTipoGrupo(String tipo, Grupo grupo) {
        int contador = 0;
        for (Edificio edificio : edificios) {
            if (edificio.getTipo().equalsIgnoreCase(tipo) && edificio.getGrupo().equals(grupo)) {
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
        consola.imprimir(info.toString());
    }

    public boolean tieneEdificios(){
        return !edificios.isEmpty();    //Si la lista está vacía devuelve false
    }

    public boolean Edificar(Jugador jugador, Edificio edificio, String tipo){
        
        float coste = edificio.calcularCoste();

        try{
            if(verificarPrecondiciones(jugador, tipo, edificio)){
            
                try{
                    if(verificarLimiteEdificaciones(tipo)){
                        try{
                            if(edificio.puedeConstruir()){
                            this.edificios.add(edificio);

                            jugador.EstadisticaDineroInvertido(coste);
                            jugador.pagar(coste);
                            jugador.sumarGastos(coste);
                            
                    
                            this.edificios = edificio.accionComprar(this.edificios); 
                            return true;
                            }
                        }
                        catch(ExcepcionMaximoEdificar e){
                            consola.imprimir("Error: "+e.getMessage());
                        }
                    }
                }
                catch (ExcepcionEdificar e){
                    consola.imprimir("Error: " + e.getMessage());
                }
        }
      
        }
        catch (ExcepcionEdificar e){
            consola.imprimir("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean verificarPrecondiciones(Jugador jugador, String tipo, Edificio edificio) throws ExcepcionEdificar{

        if(!jugador.equals(this.getDuenho())){
            throw new ExcepcionEdificar("El jugador no es el dueño de la casilla");
        }

        if(this.getGrupo().esDuenhoGrupo(jugador) == false){
            throw new ExcepcionEdificar("El jugador no es el dueño del grupo entero, no puede comprarla");
        }
        
        if (!tipo.equalsIgnoreCase("casa") && !tipo.equalsIgnoreCase("hotel") && !tipo.equalsIgnoreCase("piscina") && !tipo.equalsIgnoreCase("pista de deporte")) {
            throw new ExcepcionEdificar("TIPO CONSTRUCCION NON VALIDO");
        }

        float coste = edificio.calcularCoste();

        if(jugador.getFortuna() < coste){
            throw new ExcepcionEdificar("El jugador no tiene suficiente dinero para construir"); //EXCEPCION
        }

        return true;
    }

    public boolean verificarLimiteEdificaciones(String tipo) throws ExcepcionEdificar {
        int numeroCasillasGrupo = this.grupo.getNumeroCasillas();
        int numeroEdificacionesGrupo = contarEdificiosPorTipoGrupo(tipo, this.grupo);
        int numerohoteles = contarEdificiosPorTipo("Hotel");
    
        if(numerohoteles >2){
            if (numeroCasillasGrupo == 2 && numeroEdificacionesGrupo >= 2) {
                throw new ExcepcionEdificar("No se pueden construir más " + tipo + " en el grupo, ya se alcanzó el límite.");
            }
        
            if (numeroCasillasGrupo == 3 && numeroEdificacionesGrupo >= 3) {
                throw new ExcepcionEdificar("No se pueden construir más " + tipo + " en el grupo, ya se alcanzó el límite.");
            }
        }
       
    
        return true;
    }

    public void venderEdificios(int cantidad, String tipo, Jugador banca, Jugador jugador) throws ExcepcionEdificar{
        if(!tipo.equalsIgnoreCase("casa") && !tipo.equalsIgnoreCase("hotel") && !tipo.equalsIgnoreCase("piscina") && !tipo.equalsIgnoreCase("pista de deporte")){
            throw new ExcepcionEdificar("El tipo de edificio indicado no existe\n");
        }

        if(!this.getDuenho().equals(jugador)){
            throw new ExcepcionEdificar("El jugador no es el dueño del solar");
        }

        int contador = 0;
        for(int i=0; (i<edificios.size()) && (contador < cantidad); i++){
            if(edificios.get(i).getTipo().equalsIgnoreCase(tipo)){
                contador++;
                edificios.get(i).venderEdificioBanca(banca);  
                edificios.remove(edificios.get(i));
            }
        }

        if(contador == 0){
            throw new ExcepcionEdificar("No se encuentran estos edificios para vender");
        }

        if (contador < cantidad){
            consola.imprimir("La casilla no tiene tantos edificios de ese tipo, se han vendido los máximos posibles");
        }

        StringBuilder info = new StringBuilder();

        info.append("Se han vendido ").append(contador).append(" edificios del tipo ").append(tipo).append("\n");
         
        consola.imprimir(info.toString());
    }


    public ArrayList<Edificio> ObtenerArrayEdificiosPorTipo(String tipo){
        
        ArrayList<Edificio> listaEdificios = new ArrayList<>();
        for(Edificio edificio:edificios){
            if(edificio.getTipo().equalsIgnoreCase(tipo)){
                listaEdificios.add(edificio);
            }
        }
        return listaEdificios;        
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public ArrayList<Edificio> getEdificios(){
        return this.edificios;
    }
}

