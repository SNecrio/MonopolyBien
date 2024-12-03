package monopoly;

import java.util.ArrayList;
import java.util.Collections;  // CUANDO SE DESCOMNTEN LAS CARTAS SE NECESITA
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
import monopoly.edificios.*;
import partida.avatares.*;

public class Juego implements Comando{

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean partidainiciada;
    private boolean acabarPartida = false;
    private ConsolaNormal consola;

    public Juego(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.lanzamientos = 0;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.tirado = false;
        this.partidainiciada = false;
        this.consola = new ConsolaNormal();
    }

    @Override
    public void crearJugador(String comando){
        String[] partes = comando.split(" ");

        if(jugadores.size() > 5){
            consola.imprimir("No puede haber más de 6 personajes en el juego");
        }
        else if(partes.length != 4){

            consola.imprimir("\nFormato incorrecto. Usa: crear jugador 'nombre' 'tipo de avatar' "); //EXCEPCION

        }else{
            String nombre = partes[2]; //nombre
            String avatar = partes [3]; //tipo avatar
    
            Jugador nuevoJugador = new Jugador(nombre, avatar, tablero.encontrar_casilla("Salida"), avatares);       
            jugadores.add(nuevoJugador);
            avatares.add(nuevoJugador.getAvatar());
            tablero.getPosiciones().get(3).get(10).anhadirAvatar(nuevoJugador.getAvatar());

            tablero.imprimirTablero();

            consola.imprimir("\nNombre: " + nombre);
            consola.imprimir("Avatar: " + nuevoJugador.getAvatar().getId());
    
        }
    }

    @Override
    public void iniciarPartida(){
        dado1 = new Dado();
        dado2 = new Dado();

        consola.imprimir("\u001B[1m");
        consola.imprimir("&&   &&  &&&&&&  &&   &  &&&&&&  &&&&&&  &&&&&&  &    &   &");
        consola.imprimir("& & & &  &    &  & &  &  &    &  &    &  &    &  &     & &");
        consola.imprimir("&  &  &  &    &  &  & &  &    &  &&&&&   &    &  &      &");
        consola.imprimir("&     &  &    &  &   &&  &    &  &       &    &  &      &");
        consola.imprimir("&     &  &&&&&&  &    &  &&&&&&  &       &&&&&&  &&&&&  &\u001B[0m");

        consola.imprimir("\nBienvenido a una nueva partida de Monopoly!\n");

        //ESTA HAI QUE COMPLETALA, DEIXOA ASI 

    }


    @Override
    public void listarJugadores(String comando){
        if(jugadores.isEmpty()){
            consola.imprimir("\nNo existe ningun jugador."); //EXCEPCIÓN
        }
        
        else{
            consola.imprimir("\nJugadores: ");
            for(Jugador jugador: jugadores){
                jugador.describirJugador(jugador.getNombre());
            }
           
        }

    }

    @Override
    public void describirJugador(String comando){
        String[] partes = comando.split(" "); 
        if(partes.length <= 2){
            consola.imprimir("\nFormato incorrecto. Introduzca describir jugador 'nombre'"); //EXCEPCION
            return;}
        else{

            String nombre = partes[2];

            for(Jugador jugador : jugadores){
                if(jugador.getNombre().equalsIgnoreCase(nombre)){
                    jugador.describirJugador(nombre);
                }
            }
            return;
        }
    }


    @Override
    public void describirAvatar(String comando){
        String[] partes = comando.split(" "); // Dividir por espacios
        String id = partes[2];

        if(avatares.isEmpty()==true){
            consola.imprimir("No se han encontrado avatares"); //EXCEPCION
            return;
        }
        for(Avatar avatar : avatares){
            if(avatar.getId().equalsIgnoreCase(id)){
                avatar.describirAvatar();
            }
        }
        return;
    }

    @Override
    public void describirCasilla(String comando){
        String[] partes = comando.split(" "); //Dividir por espacios
        String nombreCasilla = partes[1];
        consola.imprimir("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);

        if(casilla == null){
            consola.imprimir("No se encuentra la casilla"); //EXCEPCIÓN
        }

        casilla.DescribirCasilla();
        return;
    }

    @Override
    public void comprarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");

        Casilla casillacomprada = tablero.encontrar_casilla(nombreCasilla);
        if(casillacomprada==null){
            consola.imprimir("No se encuentra la casilla"); //EXCEPCIÓN
            return;
        } 

        
        int lanzamiento = dado1.getValor() + dado2.getValor();
        Jugador jugadoractual = jugadores.get(turno);

        if((tirado == false)&&(jugadoractual.getDobles()<1)){
            consola.imprimir("El jugador aun no ha tirado los dados, por lo que no puede comprarla");
            return;
        } 

        if(casillacomprada instanceof CasillaPropiedad){
            CasillaPropiedad casillaPropiedad = (CasillaPropiedad) casillacomprada;
            
            if(casillaPropiedad.EvaluarCasilla(jugadoractual, banca, lanzamiento, tablero) ==true){
                casillaPropiedad.comprarCasilla(jugadoractual, banca);
            }
        }

        return;
    }

    @Override
    public void hipotecarCasilla(String comando){

        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla==null){
            consola.imprimir("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno); //obter xogador actual

        if(casilla instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar) casilla;
            
            if(!solar.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                consola.imprimir("No eres el dueño de la casilla para hipotecarla"); //EXCEPCION
                return;
            }

            else if(solar.tieneEdificios()==true){
                consola.imprimir("Debes vender antes los edificios de esta casilla antes de hipotecar"); //EXCEPCION
                return;
            }

            solar.hipotecarCasilla(jugadoractual, banca);
        }
       
    }

    @Override
    public void deshipotecarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla==null){
            consola.imprimir("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno);

        if(casilla instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar) casilla;
            
            if(!solar.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                consola.imprimir("No eres el dueño de la casilla para deshipotecarla"); //EXCEPCION
                return;
            }

            else if(solar.estaHipotecada()==true){
                consola.imprimir("No puedes hipotecar la casilla si no está hipotecada"); //EXCEPCION
                return;
            }

            solar.deshipotecarCasilla(jugadoractual, jugadoractual);
        }
    }

    @Override
    public void listarEnVenta(){
        banca.listarPropiedadesenVenta();
        return;
    }

    @Override
    public void edificar(String comando){
        String[] partes = comando.split(" ");
        String tipo = partes[1];
        consola.imprimir("");

        Jugador jugadoractual = jugadores.get(turno);
        Casilla casillaActual = jugadoractual.getAvatar().getLugar();

        if(tipo.equalsIgnoreCase("pista")){
            tipo = "pista de deporte";
        }

        if(casillaActual instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)casillaActual;

            // Crear el edificio correspondiente
            switch (tipo.toLowerCase()) {
                case "casa":
                    Casa casa = new Casa(banca, solar.getEdificios().size(), solar.getValorInicial(), solar, solar.getGrupo());
                    solar.Edificar(jugadoractual, casa, tipo); 

                    break;

                case "hotel":
                    Hotel hotel = new Hotel(banca, solar.getEdificios().size(), solar.getValor(), solar, solar.getGrupo()); 
                    solar.Edificar(jugadoractual, hotel, tipo); 

                    break;

                case "pista de deporte":
                    Pista pista = new Pista(banca, solar.getEdificios().size(), solar.getValor(), solar, solar.getGrupo());
                    solar.Edificar(jugadoractual, pista, tipo); 

                    break;

                case "piscina":
                    Piscina piscina = new Piscina(banca, solar.getEdificios().size(), solar.getValor(), solar, solar.getGrupo()); 
                    solar.Edificar(jugadoractual, piscina, tipo); 

                    break;
                    
                default:
                    consola.imprimir("Las edificaciones pueden ser del tipo: 'casa' 'hotel' 'pista de deporte' 'piscina'"); //EXCEPCION
                    return; 
            }

        }
    }

    @Override
    public void venderEdificio(String comando){
        String[] partes = comando.split(" ");
        String tipo = partes[1];
        String casilla = partes[2];
        String cantidad = partes[3];
        consola.imprimir("");
        int entero = 0;

        try {
            entero = Integer.parseInt(cantidad);  // Convertir la cantidad a int
        }
        catch (NumberFormatException e) {
            consola.imprimir("Error: La cantidad debe ser un número entero.");
            return;
        }

        Jugador jugador = jugadores.get(turno);
        Casilla casillaActual = tablero.encontrar_casilla(casilla);

        if(casillaActual instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)casillaActual;
            solar.venderEdificios(entero, tipo, banca, jugador);
        }
    }

    @Override
    public void listarEdificiosCasilla(){
        Jugador jugador = jugadores.get(turno);
        Casilla casillaActual = jugador.getAvatar().getLugar();

        if(casillaActual instanceof PropiedadSolar){ //SE A CASILA NON E DE TIPO SOLAR ENTENDO QUE HAI QUE LANZAR EXCEPCION
            PropiedadSolar solar = (PropiedadSolar)casillaActual;
            
            ArrayList<Edificio> edificios = solar.getEdificios();
            for(Edificio edificio:edificios){
                edificio.listarEdificio();
            }
        }
    }

    @Override
    public void listarEdificiosGrupo(String comando){
        String[] partes = comando.split(" ");
        if(partes.length == 3){
            String colorgrupo = partes[2];
            
            Grupo grupo = tablero.obtenerGrupoPorNombre(colorgrupo);
            grupo.listarEdificiosGrupo();
        }
    }

    @Override
    public void listarAvatares(){

        if(jugadores.isEmpty()){ //EXCEPCION
            consola.imprimir("\nNo existe ningun jugador: ");
        }
        else{
            consola.imprimir("\nAvatares: ");
            for(Avatar avatar:avatares){
                avatar.describirAvatar();
            }
        }
    }

    @Override
    public void estadisticasPartida(){
          //CASILLA MAS RENTABLE
          String nombre = "";
          String nombreGrupo = "";
          String nombre2 = "";
          float cant = -1f;
          float cantGrupo = -1f;
          int cant2 = -1;
  
          for(ArrayList<Casilla> filas: tablero.getPosiciones()){
              for(Casilla casilla: filas){

                if(casilla instanceof CasillaPropiedad){
                    CasillaPropiedad casillaPropiedad = (CasillaPropiedad) casilla;
                    
                    if(casillaPropiedad.getRentabilidad() > cant){
                        nombre = casillaPropiedad.getNombre();
                        cant = casillaPropiedad.getRentabilidad();
                    }
                    
                    if(casillaPropiedad.getVisitas() > cant2){
                        nombre2 = casillaPropiedad.getNombre();
                        cant2 = casillaPropiedad.getVisitas();
                    }
                    
                    if(casillaPropiedad instanceof PropiedadSolar){
                        PropiedadSolar solar = (PropiedadSolar)casillaPropiedad;

                        if(solar.getGrupo().getRentabilidad() > cantGrupo){
                            nombreGrupo = solar.getGrupo().getColor();
                            cantGrupo = solar.getGrupo().getRentabilidad();
                        }
                    }
                }    
             }
            }
        consola.imprimir("Casilla mas rentable: " + nombre + Valor.WHITE);
        consola.imprimir("Grupo mas rentable: " + nombreGrupo + Valor.WHITE);
        consola.imprimir("Casilla mas visitada: " + nombre2 + Valor.WHITE);
  
        //JUGADOR EN CABEZA
        cant = -1f;
        cant2 = -1;
        int cant3 = -1;
        float fortuna = 0f;
        nombre = "";
        nombre2 = "";
        String nombre3 = "";
        for(Jugador jugador: jugadores){

            fortuna = jugador.getFortuna();
            for(CasillaPropiedad casilla: jugador.getPropiedades()){
                fortuna += casilla.getValor();
            }

            if(fortuna > cant){
                  cant = fortuna;
                  nombre = jugador.getNombre();
            }
            
            fortuna = 0f;
              
            if(jugador.getTiradas() > cant2){
                  cant2 = jugador.getTiradas();
                  nombre2 = jugador.getNombre();
            }
              
            if(jugador.getVueltasTotal() > cant3){
                cant3 = jugador.getVueltasTotal();
                nombre3 = jugador.getNombre();
            }
        }
  
        consola.imprimir("Jugador que mas vueltas al tablero dio: " + nombre3);
        consola.imprimir("Jugador que mas veces tiro los dados: " + nombre2);
        consola.imprimir("Jugador en cabeza: " + nombre);
  
        return;
    }

    @Override
    public void consultarAvatar(){
        consola.imprimir("Si se elige el modo avanzado al lanzar los dados, las piezas se comportarán de la siguiente manera:");
        consola.imprimir("\n\u001B[1mCoche:\u001B[0m si el valor de los dados es superior a 4, se avanzará tantas casillas como indicado y se podrá seguir lanzando los dados");
        consola.imprimir("\thasta 3 veces más mientras se siga obteniendo un 4 como mínimo. Se podrá realizar una compra en cualquiera de los turnos, pero solo en uno de ");
        consola.imprimir("\tellos. Si el valor es menor a 4, se retrocederá el número de casillas correspondiente y se perderán los dos próximos turnos.");
        consola.imprimir("");
        consola.imprimir("\u001B[1mEsfinge:\u001B[0m si el valor de los dados es superior a 4, se realizará el movimiento en zigzag entre los lados norte y sur del tablero.");
        consola.imprimir("\tMientras se siga obteniendo un 4 de mínimo, se podrán realizar hasta 2 tiradas extra. Si se obtiene menos de un 4, se desharán todos los cambios");
        consola.imprimir("\trealizados en el turno anterior.");
        consola.imprimir("");
        consola.imprimir("\u001B[1mSombrero:\u001B[0m se regirá por las mismas normas que la esfinge, pero el movimiento en zigzag se realizará entre los lados este y oeste.");
        consola.imprimir("");
        consola.imprimir("\u001B[1mPelota:\u001B[0m si el valor de los dados es inferior a 4, se retrocederá ese número de casillas. Si es superior se avanzará el valor dado");
        consola.imprimir("\tpero se irá parando en todas las casillas impares hasta el valor obtenido.");
        return;
    }

    @Override
    public void pagarFianza(){
        salirCarcel(true);
    }

    @Override
    public void verTablero(){
        tablero.imprimirTablero();
    }

    @Override
    public void lanzarDados(Jugador jugador){
        //Se guarda el nombre de la casilla en la que se encuentra el jugador para los prints
        String casillaAnterior = jugador.getAvatar().getLugar().getNombre(); //Nombre de la casilla anterior para prints

        //Se lanzan los dados
        dado1.hacerTirada();
        dado2.hacerTirada();

        if(tirado==true){
            consola.imprimir("El jugador ya ha tirado este turno.");
            return;
        }
        else if(jugador.getAvatar().getBloqueado()!=0){
            consola.imprimir("El jugador está bloqueado por " + jugador.getAvatar().getBloqueado() + "turno(s). No podrá tirar hasta entonces.");
            return;
        }

        // Comprueba si, estando en la cárcel, puede salir de ella
        if(jugador.getEncarcel()==true){
            lanzarCarcel(dado1.getValor(), dado2.getValor());
        }

        //Avance en modo simple
        if(jugador.getModo()==false){
            jugador.getAvatar().moverEnBasico(dado1.getValor(), dado2.getValor(), jugador, tablero, banca);
            if(dado1.getValor()!=dado2.getValor() || jugador.getEncarcel()==true) tirado = true;
            else consola.imprimir("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
        }
        //Avance en modo avanzado
        else {
            jugador.getAvatar().moverEnAvanzado(dado1.getValor(), dado2.getValor(), jugador, tablero, banca);
            if(jugador.getEncarcel()==true || jugador.getAvatar().getExtras()<0 || (jugador.getAvatar().getContinuar()==0 && jugador.getAvatar().getTiradaInicial()!=0) || jugador.getBloqueado()!=0) tirado = true;
        }

        //Determina que se imprimirá en cada casilla

        if(jugador.getAvatar().getBloqueado()==0){

         //Si es una casilla de su propiedad
         if(jugador == jugador.getAvatar().getLugar().getDuenho()){
            consola.imprimir(
            "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de su propiedad.");
            }
        else if(banca == jugador.getAvatar().getLugar().getDuenho()){
            consola.imprimir(
            "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de la banca.");   
            }

        else if(jugador.getAvatar().getLugar().getNombreSinColor().equals("Suerte")||jugador.getAvatar().getLugar().getNombreSinColor().equals("Comunidad")){//Si es una casilla de suerte o comunidad
            consola.imprimir(
            "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre());   
            }//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

        //Si es de otro jugador
        else{
            float alquiler = jugador.getAvatar().getLugar().calcularAlquiler(jugador, (dado1.getValor()+dado2.getValor()));
            consola.imprimir( "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);
            //Si le puedes pagar, se paga automáticamente
            if(jugador.getAvatar().getSolvente() == true){
                consola.imprimir( "Se han pagado " + alquiler + " euros de alquiler al dueño de la casilla.");
            }
            //Si no puede pagar pero tiene propiedades
            else{
                bancarrota(jugador,(dado1.getValor()+dado2.getValor()));
                }
            }
        }

        if(comprobarVueltas()==true){
            tablero.incrementarPrecios();
            resetearVueltas();
        }
    }


    private void lanzarCarcel(int d1, int d2){
        Jugador jugador = jugadores.get(turno);

        if(d1 == d2){
            consola.imprimir("Has sacado dobles y por lo tanto sales de la cárcel");
            jugadores.get(turno).setEncarcel(false);
            jugadores.get(turno).setTiradasCarcel(0); 
        }
        else{
            tablero.imprimirTablero();
            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
            if(jugador.getTiradasCarcel()<2){
                consola.imprimir("Como no has sacado dobles, debes permanecer en la cárcel");
                tirado=true;
                jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
                acabarTurno(false);
                return;
                }
            else{
                consola.imprimir("Has agotado tus intentos para salir de la cárcel");}
                salirCarcel(false);
        }
    }

    public void salirCarcel(boolean pagado) {
        int tirada = dado1.getValor() + dado2.getValor();
            if(pagado==true){
                if(jugadores.get(turno).getAvatar().getLugar().evaluarCasilla(jugadores.get(turno), banca, tirada, tablero) == true){
                    consola.imprimir(jugadores.get(turno).getNombre() + " paga " + 0.25*SUMA_VUELTA + " y sale de la cárcel. Puede lanzar los dados");
                    jugadores.get(turno).pagar(0.25f*SUMA_VUELTA);
                    jugadores.get(turno).EstadisticaTasasImpuesto(0.25f*SUMA_VUELTA);
                    lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0);
                    return;
                }
                else consola.imprimir("El jugador no puede permitirse salir de la carcel");
            }
            else{
                if(jugadores.get(turno).getAvatar().getLugar().evaluarCasilla(jugadores.get(turno), banca, tirada, tablero) == true){
                    lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0); 
                    consola.imprimir("Por lo tanto, ha sido obligado a pagar la fianza para salir");
                    return;
                }
                else{
                    consola.imprimir("El jugador no puede permitirse salir de la carcel y ya ha agotado todos sus intentos. Por lo tanto, " + jugadores.get(turno).getNombre() + " se queda en bancarrota y pierde la partida.");
                    eliminarJugador(jugadores.get(turno)); //eliminamos o xogador porque está en bancarrota
                    
                }
            }
        return;
    }



    public boolean comprobarVueltas(){
        
        for(Jugador jugador : jugadores){
            if(jugador.getVueltas() < 4){
                return false;
            }
        }
        return true; //todos los jugadores han dado 4 vueltas
    }
    
    public void resetearVueltas(){
        for(Jugador jugador : jugadores){
            int vueltas = jugador.getVueltas();
            vueltas = vueltas % 4;
            jugador.setVueltas(vueltas);
        }
    }

    private void bancarrota(Jugador jugador, float dineroPagar){
        //Hace arrays y les introduce las propiedades que tienen
        boolean solv = false;
        if(!jugador.getPropiedades().isEmpty()){
        
            ArrayList<CasillaPropiedad> paraHipotecar = new ArrayList<>();
            ArrayList<PropiedadSolar> paraVenderEdificios = new ArrayList<>();

            ArrayList<CasillaPropiedad> propiedadesJugador = jugador.getPropiedades();

            for(CasillaPropiedad propiedad : propiedadesJugador){

                if(propiedad instanceof PropiedadSolar){
                    PropiedadSolar solar = (PropiedadSolar)propiedad;
                    if((!solar.estaHipotecada()) && (!solar.tieneEdificios())){
                        paraHipotecar.add(solar);
                    }
                    else if(solar.tieneEdificios()){
                        paraVenderEdificios.add(solar);
                    }
                }
                else{
                    if(!propiedad.estaHipotecada()){
                        paraHipotecar.add(propiedad);
                    }
                }
                
            }
                   
            //Si tiene propiedades sigue el codigo, si no, se acaba el juego
            if(!paraHipotecar.isEmpty() || !paraVenderEdificios.isEmpty()){
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, pero tiene solares que puede hipotecar o edificios que vender");
            }else{
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota");
                eliminarJugador(jugador);
                acabarTurno(true);
                return;                        
            }

            String nombreVender;
            //Mientras le queden propiedades y no sea solvente, sigue el bucle
            while(solv == false && !paraHipotecar.isEmpty()){
                        
                //Primero imprime todas sus propiedades
                consola.imprimir("Elija propiedad para hipotecar: \n");
                for(Casilla prop :paraHipotecar){
                    consola.imprimir("\t" + prop.getNombre());
                }
                consola.imprimir(Valor.WHITE + "\nO para vender edificios: \n");
                for(Casilla prop :paraVenderEdificios){
                    consola.imprimir("\t" + prop.getNombre());
                }
                            
                //Escanea cual quiere vender
                
                nombreVender = consola.leer();
                for(CasillaPropiedad hipotecar : paraHipotecar){
                    if(hipotecar.getNombreSinColor().equalsIgnoreCase(nombreVender)){
                        hipotecar.hipotecarCasilla(jugador, banca);
                        paraHipotecar.remove(hipotecar);

                        if(solv){
                            jugador.sumarFortuna(-dineroPagar);
                            jugador.sumarGastos(dineroPagar);
                            consola.imprimir("El jugador pudo pagar sus deudas");
                        }
                    }

                    else{
                        consola.imprimir("Casilla no encontrada, vuelva a introducir una");
                    }
                }
                
                if(!paraVenderEdificios.isEmpty()){
                    for(PropiedadSolar vender : paraVenderEdificios){
                        if(vender.getNombreSinColor().equalsIgnoreCase(nombreVender)){
                            int cantidad;
                            String tipo;

                            System.out.print("Tipo: ");
                            tipo = consola.leer();
                            System.out.print("\n");
                            System.out.print("Cantidad: ");
                            cantidad = consola.leerNumero();
                            System.out.print("\n"); //CAMBIAR O DE QUE CANDO LEAS IMPRIME O MENSAJE DE ANTES

                            
                            //HAY QUE SEGUIR CO DE ABAIXO
                        }
                    }
                }
                if(!paraVenderEdificios.isEmpty()){
                    for(int i = 0; i < paraVenderEdificios.size(); i++){
                        Casilla prop = paraVenderEdificios.get(i);
                        if(prop.getNombreSinColor().equalsIgnoreCase(nombreVender)){
    
                            int cantidad;
                            String tipo;

                            System.out.print("Tipo: ");
                            tipo = scan.nextLine();
                            System.out.print("\n");
                            System.out.print("Cantidad: ");
                            cantidad = scan.nextInt();
                            System.out.print("\n");

                            venderEdificioJuego(tipo,nombreVender,cantidad);
    
                            if(!prop.tieneEdificios()){
                                paraVenderEdificios.remove(prop);
                                paraHipotecar.add(prop);
                            }
                            solv = (jugador.getFortuna() > dineroPagar);
                            if(solv){
                                jugador.sumarFortuna(-dineroPagar);
                                jugador.sumarGastos(dineroPagar);
                                consola.imprimir("El jugador pudo pagar sus deudas");
                            }
                        }
                    }
                }
                
            }
            if(solv == false){
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); //AQUI O XOGADOR PERDE NON SEI QUE HAI QUE FACERLLE
                eliminarJugador(jugador);
                acabarTurno(true);
                return;                    
            }

        }else{
            consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); //AQUI O XOGADOR PERDE NON SEI QUE HAI QUE FACERLLE
            eliminarJugador(jugador);
            acabarTurno(true);
            return;
        }
    }

    public void eliminarJugador(Jugador jugadorEliminado){

        //comprobamos si el jugador no esta en la lista de jugadores
        if(!jugadores.contains(jugadorEliminado)){
            consola.imprimir("El jugador no está en partida, no se puede eliminar");
            return;
        }

        //Transferimos as propiedades do xogador eliminado a banca
        jugadorEliminado.transferirPropiedadesBanca(banca);

        //comprobar  si o xogador eliminado e o o que ten o turno, Volvemos ao turno anterior, para o acabar o turno que funcione
        if(jugadores.get(turno).equals(jugadorEliminado)){
            if(turno != 0){
                turno--;
            }else{
                turno = jugadores.size() - 2;
            }
        }
        else{
            if(turno >= jugadores.size()){
                turno = 0;
            }
        }

        //Eliminar o xogador da lista de xogadores
        jugadores.remove(jugadorEliminado);
        avatares.remove(jugadorEliminado.getAvatar());

        if(jugadores.size() == 1){
            acabarPartida = true;
        }else{
            consola.imprimir("El jugador ha sido eliminado y la partida continua.");
        }
    }


    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    @Override
    public void acabarTurno(boolean vertablero) {
        Jugador jugador = jugadores.get(turno);

        if((tirado == false)&&(jugador.getEncarcel()==false)){
            consola.imprimir("Aún tienes una tirada, aprovéchala!");
            return;
        }
        else if((jugador.getAvatar().getExtras()!=0)&&(jugador.getEncarcel()==false)&&(jugador.getBloqueado()!=2)){
            consola.imprimir("Aún no has agotado tus movimientos, sigue avanzando lanzando el dado!");
            return;
        }

        //Resetea algunos de los atributos del jugador cuyo turno se ha acabado antes de cambiar
        jugador.resetearDobles();
        jugador.setComprado(false);
        if(jugador.getBloqueado()<0) jugador.setBloqueado(0);
        //jugador.setExtraDobles(false);
        if(jugador.getAvatar().getBloqueado()!=0) jugador.getAvatar().setBloqueado(jugador.getAvatar().getBloqueado() - 1); 
        jugador.getAvatar().setExtras(0);

        turno++;
        if(turno > jugadores.size() -1){
            turno = 0;
        }
        
        lanzamientos = 0;
        tirado = false;
        jugador.getAvatar().setExtras(3);
        jugador.getAvatar().setSolvente(true);
        if(vertablero==true) tablero.imprimirTablero();
    }

    public ArrayList<Jugador> arrayJugadores(){
        return jugadores;
    }

    public void venderEdificioJuego(String tipo, Casilla nombrevender, int cantidad){
        Jugador actual = jugadores.get(turno);
        if(nombrevender instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)nombrevender;
            solar.venderEdificios(cantidad, tipo, banca, actual);
        }
    }

}
