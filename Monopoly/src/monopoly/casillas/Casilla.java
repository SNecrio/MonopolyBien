package monopoly.casillas;

import java.util.ArrayList;
import java.util.Random;

import monopoly.Carta;
import monopoly.Grupo;
import monopoly.Valor;
import monopoly.edificios.Edificio;
import partida.avatares.*;
import partida.Jugador;

public abstract class Casilla {

    private static final float SUMA_VUELTA = 1301328.584f;
    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private ArrayList<Avatar> avatares;
    private int visitas = 0;
    private ArrayList<Jugador> jugadoresvisitantes; //Array usado para incluir los nombres de todas las personas que caen en la casilla (para calcular si el jugador cae mas de dos veces en esta casilla se puede comprar)

    //Constructores:
    public Casilla() {
        //damoslle valores por defecto
        this.nombre = "";
        this.tipo = "";
        this.posicion= 0;
        this.avatares=new ArrayList<>(); //inicializamos o array vacío
        this.visitas = 0;
        this.jugadoresvisitantes = new ArrayList<>();
        
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion) {
        this.nombre=nombre;
        
        //hay que comprobar que tipo sexa "solar", "servicio" ou "transporte"
        if(tipo.equalsIgnoreCase("Solar")||tipo.equalsIgnoreCase("Servicio")||tipo.equalsIgnoreCase("Transporte")){
            this.tipo=tipo;
        }
        else{
            throw new IllegalArgumentException("O tipo ten que ser solar, servicio ou transporte"); 
        }
        this.posicion=posicion;
        this.avatares=new ArrayList<>(); //inicializamos o array vacío
        this.visitas = 0;
        this.jugadoresvisitantes = new ArrayList<>();

    }

    // Métodos de acceso
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPosicion() { return posicion; }

    //Metodos para avatares
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    public void eliminarAvatar(Avatar av) {
        if(!this.avatares.remove(av)){
            System.err.println("El avatar no esta en la casilla seleccionada");
        }
    }

    // Método abstracto para evaluar una casilla, debe ser implementado por las subclases
    public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada);

    //Metodo para saber si un avatar está en esta casilla
    public boolean estaAvatar(Avatar av){
        if(av.getLugar().getPosicion() == this.posicion){
            return true;
        }

        return false;
    }


    //Metodo ToString para describir una casilla
    public void DescribirCasilla(){
        StringBuilder info = new StringBuilder();

        info.append("\n");
        //COMPLETAR
    }

    
        
    ////////////////////HASTA AQUI O NOVO
    /*Devuelve la cantidad a pagar por caer en una casilla de otro jugador*/
    public float calcularAlquiler(Jugador actual, int tirada){
        switch(tipo){
            case "Solar":
                float anhadidoEdificaciones = calculoAlquilerAnhadidoEdificaciones();
                return (this.impuesto + anhadidoEdificaciones); //el alquiler para "Solar" es el impuesto
               
            case "Servicio":
                Jugador propietariocasillaservicio = this.duenho;

                //miramos cantas casillas servicio ten o dueño
                int num_servicio = propietariocasillaservicio.getNumCasillasServicio();

                float factor_servicio = SUMA_VUELTA /200; //calculamos o factor servicio

                //Dependiendo del numero de casillas servicio y de la tirada, se pagara un valor o otro
                if (num_servicio == 1){
                    return 4 * tirada * factor_servicio; //alquiler por un servicio
                }
                else if(num_servicio ==2){
                    return 10 * tirada * factor_servicio; //alquiler por dos servicios
                }
                break; //salimos si no se cumple ninguna condicion

            case "Transporte":
                //A casilla pertence a outro xogador
                Jugador propietarioTransporte = this.duenho;

                //Miramos cuantas casillas de transporte tiene
                int num_transporte = propietarioTransporte.getNumCasillasTransporte();

                //El alquiler depende del numero de casillas de tipo transporte que tiene el dueño
                if(num_transporte == 1){
                    return 0.25f * this.valor; //alquiler por un transporte
                }
                else if(num_transporte == 2){
                    return 0.5f * this.valor; //alquiler por dos transportes
                }
                else if(num_transporte == 3){
                    return 0.75f * this.valor; //alquiler por tres transportes
                }
                else if (num_transporte == 4){
                    return this.valor; //alquiler por cuatro transportes
                }

                break;
            
            case "Especial":
                switch(nombre){
                    case "Carcel":
                        //Pagamos el 25% de la cantidad que recibirá el jugador cada vez que completa una vuelta al tablero
                        return  0.25f * SUMA_VUELTA;
                       
                    default:
                        return 0f; //si no hay caso valido
                }  
                
            default:
                return 0f; //si el tipo no esta reconocido, devolvemos 0
        
        }   

        return 0f; //si no se cumple ninguna condicion devuelve 0
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if(solicitante.getFortuna() < this.valor){
            System.out.println("El jugador no tiene dinero suficiente para comprar la casilla");
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
        
        System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + this.nombre + Valor.WHITE + " por " + this.valor + ".");
        System.out.println("Su fortuna actual es " + solicitante.getFortuna());

    }


    public void hipotecarCasilla(Jugador solicitante, Jugador banca){

        estarHipotecada = true;
        solicitante.sumarFortuna(this.hipoteca);

        System.out.println("El jugador " + solicitante.getNombre() + " hipoteca la casilla " + this.nombre + Valor.WHITE + " por " + this.hipoteca + ".");

    }

    public void deshipotecarCasilla(Jugador solicitante, Jugador banca){

        estarHipotecada = false;
        solicitante.sumarFortuna(this.hipoteca * -1.1f);

        System.out.println("El jugador " + solicitante.getNombre() + " deshipoteca la casilla " + this.nombre + Valor.WHITE + " por " + this.hipoteca * 1.1f + 
        ", ahora puede volver a recibir alquiler");

    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        
        StringBuilder info = new StringBuilder();

        info.append(nombre).append("\n");
        info.append("Tipo: ").append(tipo).append("\n");
        info.append("Posicion: ").append(posicion).append("\n");
        
        switch(tipo){
            case "Solar":
                info.append("Valor: ").append(valor).append("\n");
                info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                info.append("Grupo: ").append(grupo.getColor()).append("\n");
                info.append("Alquiler: ").append(this.impuesto).append("\n");
                info.append("Hipoteca: ").append(hipoteca).append("\n");
                info.append("valor casa: ").append(0.6f*valorInicial).append("\n");
                info.append("valor hotel: ").append(0.6f*valorInicial).append("\n");
                info.append("valor piscina: ").append(0.4f*valorInicial).append("\n");
                info.append("valor pista de deporte: ").append(1.25f*valorInicial).append("\n");
                info.append("alquiler una casa: ").append(5* impuesto).append("\n");
                info.append("alquiler dos casas: ").append(15*impuesto).append("\n");
                info.append("alquiler tres casas: ").append(35*impuesto).append("\n");
                info.append("alquiler cuatro casas: ").append(50*impuesto).append("\n");
                info.append("alquiler hotel: ").append(70*impuesto).append("\n");
                info.append("alquiler piscina: ").append(25*impuesto).append("\n");
                info.append("alquiler pista de deporte: ").append(25*impuesto).append("\n");
                
                break;
            
            case "Transporte":
            case "Servicio":
                info.append("Valor: ").append(valor).append("\n");
                info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                info.append("Cantidad a pagar (alquiler): ").append(impuesto).append("\n");
                info.append("Hipoteca: ").append(hipoteca).append("\n");
                
                break;

            case "Comunidad":
            case "Suerte":
                System.out.println("No hay características especiales"); //AQUI CREO QUE NON FARIA FALTA IMPRIMIR NADA
                break;

            case"Impuesto":
                info.append("Impuesto: ").append(impuesto).append("\n");
                break;
            case "Especial":
                switch(this.getNombreSinColor()){
                    case "salida":
                    case "Salida":
                    case "ircarcel":
                    case "irCarcel":
                    case "Ircarcel":
                    case "IrCarcel":
                        listarAvatares(info);
                        break;
                    case "parking":
                    case "Parking":
                        info.append("Bote: ").append(valor).append("\n");
                        listarAvatares(info);
                        break;
                    case "carcel":
                    case "Carcel":
                        info.append("Impuesto: ").append(impuesto).append("\n");
                        listarAvatares(info);
                        break;
                    default:
                        System.out.println("ERROR INFOCASILLA");
                }
                break;
            default:
                System.out.println("ERROR INFOCASILLA");
                break;
        }

        return info.toString();
    }

    //Método para listar los avatares (se usa arriba) para decir el nombre de los jugadores que estan en la casilla
    public void listarAvatares (StringBuilder info){
        if(avatares.isEmpty()){
            info.append("No hay avatares en esta casilla ");
        }
        else{
            info.append("Jugadores: ");
            for(Avatar avatar : avatares){
                info.append(avatar.getJugador().getNombre()).append(",");
                //info.append(avatar.getId()).append(",");
            }
        }
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        StringBuilder info = new StringBuilder();
        
        switch(tipo){
            case "Solar":
                info.append("\n").append(nombre).append("\n");
                info.append("Tipo: ").append(tipo).append("\n");
                info.append("Grupo: ").append(grupo.getColor()).append("\n");
                info.append("Valor: ").append(valor).append("\n");
                break;
            case "Transporte":
            case "Servicios":
                info.append(Valor.WHITE + "\n").append(nombre).append("\n");
                info.append("Tipo: ").append(tipo).append("\n");
                info.append("Valor: ").append(valor).append("\n");
                break;       
        }

        return info.toString();
    }

    public boolean tieneEdificios(){
        if( edificios.size() == 0){
            return false;
        }
        return true;
    }

    //ESTA ES LA FUNCION QUE HAI QUE LLAMAR
    public boolean Edificar(Jugador jugador, String tipo){
        if(!this.tipo.equalsIgnoreCase("Solar")){
            System.out.println("No se pueden comprar edificios en una casilla que no es del tipo Solar");
            return false;
        }

        if(!jugador.equals(this.duenho)){
            System.out.println("El jugador no es dueño de esta casilla.");
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
            
        }

        if(tipo.equalsIgnoreCase("hotel")){
            if((numeroCasillasGrupo == 2 && numeroHotelesGrupo >= 2) || 
               (numeroCasillasGrupo == 3 && numeroHotelesGrupo >= 3)){
                System.out.println("No se puede construir más hoteles, ya se alcanzó el límite del grupo.");
                return false;
            }
        }

        if(tipo.equalsIgnoreCase("piscina")){
            if((numeroCasillasGrupo == 2 && numeroPiscinasGrupo >= 2) || 
               (numeroCasillasGrupo == 3 && numeroPiscinasGrupo >= 3)){
                System.out.println("No se puede construir más piscinas, ya se alcanzó el límite del grupo.");
                return false;
            }
        }

        if(tipo.equalsIgnoreCase("pista de deporte")){
            if((numeroCasillasGrupo == 2 && numeroPistasGrupo >= 2) || 
               (numeroCasillasGrupo == 3 && numeroPistasGrupo >= 3)){
                System.out.println("No se puede construir más pistas de deporte, ya se alcanzó el límite del grupo.");
                return false;
            }
        }

        switch(tipo.toLowerCase()){
            case "casa":
                if(!puedeConstruirCasa(jugador)){
                    return false;
                }
                break;
            
            case "hotel":
                if(!puedeConstruirHotel(jugador)){
                    return false;
                }
                break;

            case "piscina":
                if(!puedeConstruirPiscina(jugador)){
                    return false;
                }
                break;

            case "pista de deporte":
                if(!puedeConstruirPistaDeporte(jugador)){
                    return false;
                }
                break;

            default:
                System.out.println("Tipo de construcción no reconocido");
                return false;
        }

        jugador.pagar(costoConstruccion);
        jugador.sumarGastos(costoConstruccion);
        jugador.EstadisticaDineroInvertido(costoConstruccion);

        Edificio nuevoEdificio = new Edificio(jugador, edificios.size()+1, tipo, costoConstruccion, this, this.grupo);
        comprarEdificio(tipo);
        edificios.add(nuevoEdificio);
        System.out.println("Se ha comprado por el valor de "+nuevoEdificio.getCoste()+ " : " + tipo);
        return true ;
    }

 
    public void comprarEdificio(String tipo) {
        int contadorCasa = 0;
        int eliminadas = 0;
    
        if (tipo.equalsIgnoreCase("Hotel")) {
            // Contamos cuántas casas hay
            for (Edificio edificio : edificios) {
                if (edificio.getTipo().equalsIgnoreCase("Casa")) {
                    contadorCasa++;
                }
            }
    
            // Verificamos si hay al menos 4 casas para poder construir un hotel
            if (contadorCasa >= 4) {
                // Eliminamos las casas
                for (int i = edificios.size() - 1; i >= 0 && eliminadas < 4; i--) {
                    Edificio edificio = edificios.get(i);
                    if (edificio.getTipo().equalsIgnoreCase("Casa")) {
                        edificios.remove(i);  // Eliminamos la casa
                        eliminadas++;
                    }
                }
            } else {
                System.out.println("No se puede construir el hotel porque no hay cuatro casas");
            }
        }
    }

    public void ListarEdificios(){
        if(edificios.isEmpty() == true){
            System.out.println("La casilla no contiene edificios");
            return;
        }
        for(Edificio edificio: edificios){
            edificio.listarEdificio();
        }
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

    public int contarEdificiosPorTipoGrupo(String tipo){
        int contador = 0;

        ArrayList<Casilla> casillasgrupo = new ArrayList<Casilla>();
        casillasgrupo = grupo.getCasillas();

        for(Casilla casilla : casillasgrupo){

            for (Edificio edificio: casilla.edificios){
                if(edificio.getTipo().equalsIgnoreCase(tipo)){
                        contador++;
                }
            }
        }
        
        return contador;
    }

    public boolean puedeConstruirCasa(Jugador jugadoractual){
        int contador = 0;
        if(!this.duenho.equals(jugadoractual)){
            System.out.println("El jugador no es el dueño del solar");
            return false;
        }
        
        for(Jugador jugador: jugadoresvisitantes){
            if(jugador.getNombre().equalsIgnoreCase(jugadoractual.getNombre()))
                contador++;
        }
        
       if((!jugadoractual.poseeGrupo(grupo)) && (contador < 2)){ 
            System.out.println("No se puede construír la casa porque el jugador no tiene todo el grupo de solares ni ha caído dos veces en la casilla");
            return false;
        }
        return true;
    }

    public boolean puedeConstruirHotel(Jugador jugador){
        if(contarEdificiosPorTipo("Casa") < 4){
            System.out.println("No se puede construir un hotel: se requieren 4 casas");
            return false;
        }
        if(!this.duenho.equals(jugador)){
            System.out.println("El jugador no es el dueño del solar");
            return false;
        }
        return true;
    }

    public boolean puedeConstruirPiscina(Jugador jugador){
        if (contarEdificiosPorTipo("Hotel") < 1 || contarEdificiosPorTipo("Casa") < 2) {
            System.out.println("No se puede construir piscina: se requieren al menos 1 hotel y 2 casas.");
            return false;
        }
        if(!this.duenho.equals(jugador)){
            System.out.println("El jugador no es el dueño del solar");
            return false;
        }
        return true;
    }

    public boolean puedeConstruirPistaDeporte(Jugador jugador){
        if (contarEdificiosPorTipo("Hotel") < 2) {
            System.out.println("No se puede construir pista de deporte: se requieren al menos 2 hoteles.");
            return false;
        }
        if(!this.duenho.equals(jugador)){
            System.out.println("El jugador no es el dueño del solar");
            return false;
        }

        return true;
    }

    public boolean puedeConstruirEdificio(String tipo) {
        int numeroCasillasGrupo = grupo.getNumeroCasillas(); // número de casillas en el grupo
        switch(numeroCasillasGrupo){
            case 2:
                if(contarEdificiosPorTipoGrupo("Casa") > 2){
                    System.out.println("No se pueden construír más casas en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Hotel") > 2){
                    System.out.println("No se pueden construír más hoteles en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Pista de Deporte") > 2){
                    System.out.println("No se pueden construír más pistas de deporte en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Piscina") > 2){
                    System.out.println("No se pueden construír más piscinas en el grupo, hay un máximo de 2.");
                    return false;
                }
                break;

            case 3:
                if(contarEdificiosPorTipoGrupo("Casa") > 3){
                    System.out.println("No se pueden construír más casas en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Hotel") > 3){
                    System.out.println("No se pueden construír más hoteles en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Pista de Deporte") > 3){
                    System.out.println("No se pueden construír más pistas de deporte en el grupo, hay un máximo de 2.");
                    return false;
                }
                if(contarEdificiosPorTipoGrupo("Piscina") > 3){
                    System.out.println("No se pueden construír más piscinas en el grupo, hay un máximo de 2.");
                    return false;
                }
                break;
            
            default:
                System.out.println("Hay un error contando el número de casillas del grupo");
  
        }
     
        return true; // Puede construir
    }
    
    /*Método que devuelve el coste de la edificación que se quiere construir */
    public float obtenerCosteConstruccion(String tipo){
        switch(tipo.toLowerCase()){
            case "casa":
            case "hotel":
                return 0.6f * this.valorInicial;
                
            case "piscina":
                return 0.4f * this.valorInicial;
                
            case "pista de deporte":
                return 1.25f * this.valorInicial;
            
            default:
                return 0.0f;  
        }
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
                anhadidoCasa = 5 * this.impuesto;
                break;
            case 2:
                anhadidoCasa = 15 * this.impuesto;
                break;
            case 3:
                anhadidoCasa = 35 * this.impuesto;
                break;
            case 4:
                anhadidoCasa = 50 * this.impuesto;
                break;
            default:
                System.out.println("No se pueden tener más de cuatro casas");
        }

        if(numeroHoteles == 1){
            anhadidoHotel = 70 * this.impuesto;
        }
        if(numeroPiscinas==1){
            anhadidoPiscina = 25 * this.impuesto;
        }
        if(numeroPistaDeporte == 1){
            anhadidoPista = 25*this.impuesto;
        }

        return (anhadidoCasa + anhadidoHotel + anhadidoPiscina + anhadidoPista);
    }
    
    public void venderEdificios(int cantidad, String tipo, Jugador banca, Jugador jugador){
        if(!tipo.equalsIgnoreCase("casa") && !tipo.equalsIgnoreCase("hotel") && !tipo.equalsIgnoreCase("piscina") && !tipo.equalsIgnoreCase("pista de deporte")){
            System.out.println("El tipo de edificio indicado no existe\n");
            return;
        }

        if(!this.tipo.equalsIgnoreCase("Solar")){
            System.out.println("No se pueden vender edificios en una casilla que no es del tipo Solar");
            return;
        }

        if(!this.duenho.equals(jugador)){
            System.out.println("El jugador no es el dueño del solar");
            return;
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
            System.out.println("No se encuentran estos edificios para vender");
            return;
        }

        if (contador < cantidad){
            System.out.println("La casilla no tiene tantos edificios de ese tipo, se han vendido los máximos posibles");
        }

        StringBuilder info = new StringBuilder();

        info.append("Se han vendido ").append(contador).append(" edificios del tipo ").append(tipo).append("\n");
         
        System.out.println(info.toString());
        
    }


    // Barajar las cartas
    public void barajar(ArrayList<Carta> cartas){
        Random ran = new Random();
        int s = cartas.size();
        
        for(int i=s-1;i>0;i--){
            // Genera un número aleatorio entre 0 y i
            int j = ran.nextInt(i + 1);

            // Intercambia las cartas en las posiciones i y j
            Carta temp = cartas.get(i);
            cartas.set(i, cartas.get(j));
            cartas.set(j, temp);}
    }


    public ArrayList<Edificio> ObtenerArrayEdificiosPorTipo(String tipo){
        ArrayList<Edificio> listaEdificios = new ArrayList<Edificio>();
        for(Edificio edificio:edificios){
            if(edificio.getTipo().equalsIgnoreCase(tipo)){
                listaEdificios.add(edificio);
            }
        }
        return listaEdificios;        
    }
 

}
