package monopoly;

import excepcions.*;
import java.util.ArrayList;
import static monopoly.Valor.SUMA_VUELTA;
import monopoly.casillas.*;
import monopoly.edificios.*;
import partida.*;
import partida.avatares.*;

public class Juego implements Comando{

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    //private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean permitir; //Booleano para comprobar si se permite hacer ciertas acciones, como cambiar de modo.
    private boolean partidainiciada;
    private boolean acabarPartida = false;
    private ConsolaNormal consola;

    public Juego(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        //this.lanzamientos = 0;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.tirado = false;
        this.permitir = true;
        this.partidainiciada = false;
        this.consola = new ConsolaNormal();
    }

    @Override
    public void crearJugador(String comando){
        String[] partes = comando.split(" ");
        if(partidainiciada == true){
            consola.imprimir("No se puede crear un jugador una vez la partida haya iniciado"); //EXCEPCION
        }
        if(jugadores.size() > 6){
            consola.imprimir("No puede haber más de 6 personajes en el juego");
        }
        else if(partes.length != 4){

            consola.imprimir("\nFormato incorrecto. Usa: crear jugador 'nombre' 'tipo de avatar' "); //EXCEPCION

        }else{
            String nombre = partes[2]; //nombre
            String avatar = partes [3]; //tipo avatar
            
            //Comprobar que el tipo de avatar dado es correcto

            try{
                comprobarAvatar(avatar);
                try {
                    Jugador nuevoJugador = new Jugador(nombre, avatar, tablero.encontrar_casilla("Salida"), avatares);
                        
                    jugadores.add(nuevoJugador);
                    //avatares.add(nuevoJugador.getAvatar());
                    tablero.getPosiciones().get(3).get(10).anhadirAvatar(nuevoJugador.getAvatar());

                    tablero.imprimirTablero();

                    consola.imprimir("\nNombre: " + nombre);
                    consola.imprimir("Avatar: " + nuevoJugador.getAvatar().getId());
                } catch (ExcepcionCasilla e){ 
                    consola.imprimir(e.getMessage());}
            } catch(ExcepcionTipoAvatar e) {
                consola.imprimir(e.getMessage());
            }
            
        }
    }

    @Override
    public void iniciarPartida(){
        dado1 = new Dado();
        dado2 = new Dado();

        consola.imprimir("\u001B[1;33m"); // Negrita y color amarillo
        consola.imprimir("███╗   ███╗   ████╗   ███╗   ██╗   ████╗    ████╗    ██████╗  ██║   ██╗   ██╗");
        consola.imprimir("████╗ ████║ ██╔═══██╗ ████╗  ██║ ██╔═══██╗ ██╔══██╗ ██╔═══██╗ ██║   ╚██╗ ██╔╝");
        consola.imprimir("██╔████╔██║ ██║   ██║ ██╔██╗ ██║ ██║   ██║ ██████╔╝ ██║   ██║ ██║    ╚████╔╝");
        consola.imprimir("██║╚██╔╝██║ ██║   ██║ ██║╚██╗██║ ██║   ██║ ██╔═══╝  ██║   ██║ ██║     ╚██╔╝");
        consola.imprimir("██║ ╚═╝ ██║ ╚██████╔╝ ██║ ╚████║ ╚██████╔╝ ██║      ╚██████╔╝ ██████╗  ██║");
        consola.imprimir("╚═╝     ╚═╝  ╚═════╝  ╚═╝  ╚═══╝  ╚═════╝  ╚═╝       ╚═════╝  ╚═════╝  ╚═╝");
        consola.imprimir("\u001B[0m"); // Reset de estilos




        consola.imprimir("\nBienvenido a una nueva partida de Monopoly!\n");
        consola.imprimir("Para comenzar, cree de 2 a 6 jugadores. Recuerde que una vez empezada, no se podran crear nuevos jugadores.\n");
        
        mostrarMenu();

    }

    
    private void mostrarMenu(){
        
        String comando;        
        
        while(true){
            
            if(acabarPartida == true){
                if(jugadores.size() == 1){
                    consola.imprimir("Felicidades " + jugadores.get(0).getNombre() + ", ganaste el Monopoly!");    
                }
                consola.imprimir("Cerrando juego...");
                return;
            }

            if(partidainiciada == false){

                consola.imprimir(Valor.WHITE + "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");
                consola.imprimir("-------------------------------------------");
                consola.imprimir("Crear jugador <nombreJugador> <tipoAvatar>");
                consola.imprimir("Listar jugadores");
                consola.imprimir("Consultar tipos avatar");
                consola.imprimir("Iniciar partida");
                consola.imprimir("-------------------------------------------");
            }
    
            else{
        
                consola.imprimir(Valor.WHITE + "\nEs tu turno " + jugadores.get(turno).getNombre() + "!");        
                consola.imprimir( "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");
                

                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf("%-45s%-45s%n", "Ver tablero", "Describir jugador <nombreJugador>");
                System.out.printf("%-45s%-45s%n", "Lanzar dados", "Describir avatar <idAvatar>");
                System.out.printf("%-45s%-45s%n", "Comprar <nombre casilla>", "Describir <nombreCasilla>");
                System.out.printf("%-45s%-45s%n", "Edificar <tipo>", "Listar jugadores");
                System.out.printf("%-45s%-45s%n", "Hipotecar <nombre casilla>", "Listar avatares");
                System.out.printf("%-45s%-45s%n", "Deshipotecar <nombre casilla>", "Listar en venta");
                System.out.printf("%-45s%-45s%n", "Vender <tipo> <nombre casilla> <cantidad>", "Estadisticas <nombre jugador>");
                System.out.printf("%-45s%-45s%n", "Listar edificios", "Listar edificios <color grupo>");
                System.out.printf("%-45s%-45s%n", "Estadisticas", "Estadisticas <nombreJugador>"); 
                System.out.printf("%-45s%-45s%n", "Cambiar modo", "Acabar turno");       
                if(jugadores.get(turno).getEncarcel()==true){
                    consola.imprimir("Pagar fianza");
                }
                consola.imprimir("Salir");
                consola.imprimir("-------------------------------------------------------------------------------");
            }
    
            comando = consola.leer();

            if(comando.equalsIgnoreCase("salir")){
                consola.imprimir("Cerrando juego...");
                consola.cerrarScanner();
                return;
            }
            try{
                AnalizarComando(comando);

            }
            catch(ExcepcionComando e){
                consola.imprimir("Error: " + e.getMessage());
            }
        }
    }


    @Override
    public void listarJugadores(String comando) throws ExcepcionComando, ExcepcionJugador{
        if(!comando.equalsIgnoreCase("listar jugadores")){
            throw new ExcepcionComando("El comando introducido no es correcto");
        }

        if(jugadores.isEmpty()){
            throw new ExcepcionJugador("No hay jugadores para listar");
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
    }

    @Override
    public void describirCasilla(String comando){
        String[] partes = comando.split(" "); 
        String nombreCasilla = partes[1];
        consola.imprimir("");
        try{
            Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
            casilla.DescribirCasilla();
        } catch(ExcepcionCasilla e){
            consola.imprimir(e.getMessage());
        }
    }

    @Override
    public void comprarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");
        Casilla casillacomprada = null;

        try{
            casillacomprada = tablero.encontrar_casilla(nombreCasilla);
        } catch(ExcepcionCasilla e){
            consola.imprimir(e.getMessage());
        }
        
        /*if(casillacomprada==null){
            return;
        } */

        
        int lanzamiento = dado1.getValor() + dado2.getValor();
        Jugador jugadoractual = jugadores.get(turno);

        //if(((tirado == false)&&(jugadoractual.getDobles()<1)&&(jugadoractual.getModo()==false))||((jugadoractual.getModo()==true))&&((jugadoractual.getAvatar().getExtras()==3)||(jugadoractual.getAvatar().getTiradaInicial()==0))){
        if(permitir==true){    
        consola.imprimir("El jugador aún no ha tirado los dados, por lo que no puede comprarla");
        } 
        else if(jugadoractual.getAvatar().getBloqueado()!=0){
            consola.imprimir("El jugador está bloqueado, por lo que no puede realizar compras");
        }
        else if(jugadoractual.getModo()==true && jugadoractual.getComprado()==true && jugadoractual.getAvatar() instanceof AvatarCoche){
            consola.imprimir("El jugador ya ha comprado una propiedad este turno");
        }

        else if(casillacomprada instanceof CasillaPropiedad){
            CasillaPropiedad casillaPropiedad = (CasillaPropiedad) casillacomprada;
            
            if(casillaPropiedad.EvaluarCasilla(jugadoractual, banca, lanzamiento, tablero, jugadores) ==true){
                try{
                    casillaPropiedad.comprarCasilla(jugadoractual, banca);
                    jugadoractual.setComprado(true);

                }catch(ExcepcionPropiedad e){
                    consola.imprimir("Error: " + e.getMessage());
                }
                
            }
        }

    }

    @Override
    public void hipotecarCasilla(String comando){

        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");

        try{
            Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
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
       
        } catch (ExcepcionCasilla e){
            consola.imprimir(e.getMessage());
        }
        
        }

    @Override
    public void deshipotecarCasilla(String comando){
        String[] partes = comando.split(" ");
        String nombreCasilla = partes[1];
        consola.imprimir("");

        try{
            Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
            
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
        } catch (ExcepcionCasilla e){
            consola.imprimir(e.getMessage());
        }

    }

    @Override
    public void listarEnVenta(){
        banca.listarPropiedadesenVenta();
        return;
    }

    @Override
    public void edificar(String comando) throws ExcepcionComando{
        String[] partes = comando.split(" ");
        String tipo = partes[1];
        consola.imprimir("");

        if(partes.length != 2){
            throw new ExcepcionComando("Comando incorrecto, el formato correcto es 'edificar' 'tipo edificio'");
        }

        boolean tipoValido = false;

        while(!tipoValido){

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
                        consola.imprimir("Las edificaciones pueden ser del tipo: 'casa' 'hotel' 'pista de deporte' 'piscina'. Vuelve a introducir uno de estos tipos");
                        tipo =  consola.leer();
                        break; 
                }

            }
        }
    }

    @Override
    public void venderEdificio(String comando) throws ExcepcionEdificar{
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
        try{
            Casilla casillaActual = tablero.encontrar_casilla(casilla);
            if(casillaActual instanceof PropiedadSolar){
                PropiedadSolar solar = (PropiedadSolar)casillaActual;
                solar.venderEdificios(entero, tipo, banca, jugador);
            }
        } catch (ExcepcionCasilla e){
            consola.imprimir(e.getMessage());
        }
    }

    @Override
    public void listarEdificiosCasilla() throws ExcepcionTipoSolar{
        Jugador jugador = jugadores.get(turno);
        Casilla casillaActual = jugador.getAvatar().getLugar();

        if(casillaActual instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)casillaActual;
            
            ArrayList<Edificio> edificios = solar.getEdificios();
            for(Edificio edificio:edificios){
                edificio.listarEdificio();
            }
        }
        else{
            throw new ExcepcionTipoSolar("La casilla introducida no puede tener edificios");
        }
    }

    @Override
    public void listarEdificiosGrupo(String comando) throws ExcepcionComando{
        String[] partes = comando.split(" ");
        if(partes.length == 3){
            String colorgrupo = partes[2];
            
            Grupo grupo = tablero.obtenerGrupoPorNombre(colorgrupo);
            grupo.listarEdificiosGrupo();
        }
        else{
            throw new ExcepcionComando("Comando introducido erroneo");
        }
    }

    @Override
    public void listarAvatares() throws ExcepcionJugador{

        if(jugadores.isEmpty()){ 
            throw new ExcepcionJugador("No existen avatares");
        }
        else{
            consola.imprimir("\nAvatares: ");
            for(Avatar avatar:avatares){
                avatar.describirAvatar();
            }
        }
    }

    @Override
    public void estadisticasPartida(String mensaje){

        
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
    public void estadisticasJugador(String comando) throws ExcepcionComando, ExcepcionJugadorIncorrecto {
        String[] partes = comando.split(" ");

        if(partes.length > 2){
            throw new ExcepcionComando("El comando introducido es incorrecto");
        }
        String nombreJugador = partes[1];

        int contador = 0;

        for(Jugador jugador: jugadores){
            if(jugador.getNombre().equalsIgnoreCase(nombreJugador)){
                jugador.mostrarEstadisticas();
                contador++;
            }
        }

        if(contador==0){
            throw new ExcepcionJugadorIncorrecto("No se encuentra el jugador introducido");
        }
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
    //Lanzar dados random
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
            consola.imprimir("El jugador está bloqueado por " + (jugador.getAvatar().getBloqueado()+1) + " turno(s). No podrá tirar hasta entonces.");
            tirado=true;
            return;
        }

        // Comprueba si, estando en la cárcel, puede salir de ella
        if(jugador.getEncarcel()==true){
            lanzarCarcel(dado1.getValor(), dado2.getValor());
        }

        permitir=false; //Ya no se puede cambiar de modo

        //Avance en modo simple
        if(jugador.getModo()==false){
            jugador.getAvatar().moverEnBasico(dado1.getValor(), dado2.getValor(), jugador, tablero, banca, jugadores);
            if(dado1.getValor()!=dado2.getValor() || jugador.getEncarcel()==true) tirado = true;
            else consola.imprimir("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
        }
        //Avance en modo avanzado
        else {
            jugador.getAvatar().moverEnAvanzado(dado1.getValor(), dado2.getValor(), jugador, tablero, banca, jugadores);
            if(jugador.getEncarcel()==true || jugador.getAvatar().getExtras()<0 || (jugador.getAvatar().getContinuar()<=0 && jugador.getAvatar().getTiradaInicial()!=0) || jugador.getAvatar().getBloqueado()!=0) tirado = true;
            
            //Si se tira en modo avanzado desde un avatar que no lo tiene, se cambia para hacer comprobaciones y luego se vuelve a poner
            else if (jugador.getModo()==false && (dado1.getValor()!=dado2.getValor())){
                jugador.setModo(true);
                tirado = true;
            }
        }

        //Determina que se imprimirá en cada casilla

        if(jugador.getAvatar().getBloqueado()==0){

            Casilla casilla = jugador.getAvatar().getLugar();
            if(casilla instanceof CasillaPropiedad){
                CasillaPropiedad propiedad = (CasillaPropiedad)casilla;

                 //Si es una casilla de su propiedad
                if(jugador.getNombre().equalsIgnoreCase(propiedad.getDuenho().getNombre())){
                    consola.imprimir(
                    "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                    casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
                    ". Es una casilla de su propiedad.");
                }
                else if(banca == propiedad.getDuenho()){
                    consola.imprimir(
                    "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                    casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
                    ". Es una casilla de la banca.");   
                }
                 //Si es de otro jugador
                else{

                    float alquiler = propiedad.calcularAlquiler(jugador, (dado1.getValor()+dado2.getValor()));
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
            else {//Si no es una propiedad (para no imprimir el dueño)
                consola.imprimir(
                "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre());   
            }

        }
        if(comprobarVueltas()==true){
            tablero.incrementarPrecios();
            resetearVueltas();
        }
    }


    @Override
    //Lanzar dados trucados (Sobrecarga)
    public void lanzarDados(Jugador jugador, int dado1, int dado2){
        //Se guarda el nombre de la casilla en la que se encuentra el jugador para los prints
        String casillaAnterior = jugador.getAvatar().getLugar().getNombre(); //Nombre de la casilla anterior para prints

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
            lanzarCarcel(dado1, dado2);
        }

        permitir=false; //Ya no se puede cambiar de modo

        //Avance en modo simple
        if(jugador.getModo()==false){
            jugador.getAvatar().moverEnBasico(dado1, dado2, jugador, tablero, banca, jugadores);
            if(dado1!=dado2 || jugador.getEncarcel()==true) tirado = true;
            else consola.imprimir("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
        }
        //Avance en modo avanzado
        else {
            jugador.getAvatar().moverEnAvanzado(dado1, dado2, jugador, tablero, banca, jugadores);
            if(jugador.getEncarcel()==true || jugador.getAvatar().getExtras()<0 || (jugador.getAvatar().getContinuar()==0 && jugador.getAvatar().getTiradaInicial()!=0) || jugador.getAvatar().getBloqueado()!=0) tirado = true;
            //Si se tira en modo avanzado desde un avatar que no lo tiene, se cambia para hacer comprobaciones y luego se vuelve a poner
            else if (jugador.getModo()==false && (dado1!=dado2)){
                jugador.setModo(true);
                tirado = true;
            }
        }

        //Determina que se imprimirá en cada casilla

        if(jugador.getAvatar().getBloqueado()==0){

            Casilla casilla = jugador.getAvatar().getLugar();
            if(casilla instanceof CasillaPropiedad){
                CasillaPropiedad propiedad = (CasillaPropiedad)casilla;

                 //Si es una casilla de su propiedad
                if(jugador.getNombre().equalsIgnoreCase(propiedad.getDuenho().getNombre())){
                    consola.imprimir(
                    "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                    casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
                    ". Es una casilla de su propiedad.");
                }
                else if(banca == propiedad.getDuenho()){
                    consola.imprimir(
                    "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                    casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
                    ". Es una casilla de la banca.");   
                }
                 //Si es de otro jugador
                else{

                    float alquiler = propiedad.calcularAlquiler(jugador, (dado1+dado2));
                    consola.imprimir( "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                    casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);

                    //Si le puedes pagar, se paga automáticamente
                    if(jugador.getAvatar().getSolvente() == true){
                        consola.imprimir( "Se han pagado " + alquiler + " euros de alquiler al dueño de la casilla.");
                    }
                    //Si no puede pagar pero tiene propiedades
                    else{
                        bancarrota(jugador,(dado1+dado2));
                    }
                }

            }
            else {//Si no es una propiedad (para no imprimir el dueño)
                consola.imprimir(
                "El avatar "+ jugador.getAvatar().getId() + " avanza desde " + 
                casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre());   
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
                if(jugadores.get(turno).getAvatar().getLugar().EvaluarCasilla(jugadores.get(turno), banca, tirada, tablero, jugadores) == true){
                    consola.imprimir(jugadores.get(turno).getNombre() + " paga " + 0.25*SUMA_VUELTA + " y sale de la cárcel. Puede lanzar los dados");
                    jugadores.get(turno).pagar(0.25f*SUMA_VUELTA);
                    jugadores.get(turno).EstadisticaTasasImpuesto(0.25f*SUMA_VUELTA);
                    //lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0);
                }
                else consola.imprimir("El jugador no puede permitirse salir de la carcel");
            }
            else{
                if(jugadores.get(turno).getAvatar().getLugar().EvaluarCasilla(jugadores.get(turno), banca, tirada, tablero, jugadores) == true){
                    //lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0); 
                    consola.imprimir("Por lo tanto, ha sido obligado a pagar la fianza para salir");
                }
                else{
                    consola.imprimir("El jugador no puede permitirse salir de la carcel y ya ha agotado todos sus intentos. Por lo tanto, " + jugadores.get(turno).getNombre() + " se queda en bancarrota y pierde la partida.");
                    eliminarJugador(jugadores.get(turno)); //eliminamos o xogador porque está en bancarrota
                    
                }
            }
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
                                            
                nombreVender = consola.leer("Que propiedad quieres vender? ");
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

                            tipo = consola.leer("Tipo: ");
                            consola.imprimir("\n");
                            cantidad = consola.leerNumero("Cantidad: ");
                            consola.imprimir("\n"); 

                            try{
                                Casilla casilla = tablero.encontrar_casilla(nombreVender);
                                if(casilla instanceof PropiedadSolar){
                                    venderEdificioJuego(tipo, casilla, cantidad);
    
                                    PropiedadSolar solarVender = (PropiedadSolar)casilla;
                                    if(!solarVender.tieneEdificios()){
                                        paraVenderEdificios.remove(solarVender);
                                        paraHipotecar.add(solarVender);
                                    }
                                    solv = (jugador.getFortuna() > dineroPagar);
                                    if(solv){
                                        jugador.sumarFortuna(-dineroPagar);
                                        jugador.sumarGastos(dineroPagar);
                                        consola.imprimir("El jugador pudo pagar sus deudas");
                                    }
                                }  
                            } catch (ExcepcionCasilla e){
                                consola.imprimir(e.getMessage());
                            }
                        }
                    }
                }                
            }
            if(solv == false){
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); 
                eliminarJugador(jugador);
                acabarTurno(true);
            }

        }else{
            consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); 
            eliminarJugador(jugador);
            acabarTurno(true);
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
            consola.imprimir("Aún no has agotado tus tiradas, aprovéchalas!");
            return;
        }
        else if (
    (
        (jugador.getModo() == true) &&
        (jugador.getAvatar().getExtras() >= 0) &&
        (jugador.getAvatar().getBloqueado() <0) &&
        (jugador.getAvatar() instanceof AvatarCoche)
    ) ||
    (
        (jugador.getModo() == true) &&
        (jugador.getAvatar() instanceof AvatarPelota) &&
        (jugador.getAvatar().getContinuar() != 0)
    ) && 
    (
    (jugador.getEncarcel() == false))
    ) {
        consola.imprimir("Aún no has agotado tus movimientos, sigue avanzando lanzando el dado!");
        return;
    }

        //Resetea algunos de los atributos del jugador cuyo turno se ha acabado antes de cambiar
        permitir=true; //En el siguiente turno se puede volver a cambiar el modo
        jugador.resetearDobles();
        jugador.setComprado(false);
        if(jugador.getAvatar().getBloqueado()<0) jugador.getAvatar().setBloqueado(0);
        //jugador.setExtraDobles(false);
        if(jugador.getAvatar().getBloqueado()!=0) jugador.getAvatar().setBloqueado(jugador.getAvatar().getBloqueado() - 1); 
        jugador.getAvatar().setExtras(0);

        turno++;
        if(turno > jugadores.size() -1){
            turno = 0;
        }
        
        //lanzamientos = 0;
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
            try{
                solar.venderEdificios(cantidad, tipo, banca, actual);
            }
            catch(ExcepcionEdificar e){
                consola.imprimir("Error: "+e.getMessage());
            }
        }
    }

    public void empezar(String mensaje) throws ExcepcionComando{
        if(mensaje.equals("iniciar partida")){
            if(jugadores.size()<2){
                throw new ExcepcionComando("Para empezar la partida tiene que haber 2 jugadores");
            }
            else{
                partidainiciada = true;
                consola.imprimir("\n\u001B[1mComienza la partida!\u001B[0m");
            }
        }
        else{
            throw new ExcepcionComando("El comando introducido es incorrecto");
        }
    }

    private void AnalizarComando(String leido) throws ExcepcionComando{
        try{
            String comando = leido.toLowerCase();
            String[] partes = comando.split(" ");
    
            if(comando.startsWith("crear jugador")){
                crearJugador(comando);
            }
    
            else if(comando.equalsIgnoreCase("iniciar partida")){
                empezar(comando);
            }
    
            else if(comando.equalsIgnoreCase("listar jugadores")){
                listarJugadores(comando);
            }
    
            else if(comando.startsWith("estadisticas")){
                if(partes.length == 1){
                    estadisticasPartida(comando);
                }
                else if(partes.length == 2){
                    estadisticasJugador(comando);
                }
                else{
                    throw new ExcepcionComando("El comando introducido es incorrecto");
                }
            }
    
            else if(comando.startsWith("listar edificios")){
                if(partes.length == 2){
                    listarEdificiosCasilla();
                }
                else if(partes.length == 3){
                    listarEdificiosGrupo(comando);
                }
                else{
                    new ExcepcionComando("El comando introducido es incorrecto");
                }
            }
    
            else if(comando.equalsIgnoreCase("listar avatares")){
                listarAvatares();
            } 
    
            else if(comando.equalsIgnoreCase("acabar turno")){
                acabarTurno(true);
            }
    
            else if(comando.equalsIgnoreCase("lanzar dados")){
                lanzarDados(jugadores.get(turno));
            }
    
            else if(comando.startsWith("trucados")){
                String[] argumento = comando.split(" ");
                //Valor de los dados fijado por el comando
                if(argumento.length>=3){
                lanzarDados(jugadores.get(turno), Integer.parseInt(argumento[1]), Integer.parseInt(argumento[2]));
                }
                else{
                    consola.imprimir("Indica el valor de los dados (Trucados 1 2)");
                }
            }
    
            else if(comando.equalsIgnoreCase("Pagar fianza")){ 
                if(tirado==true) consola.imprimir("Solo es posible pagar la fianza antes de tirar los dados");
                else salirCarcel(true);
            }
    
            else if(comando.equalsIgnoreCase("ver tablero")){
                verTablero();
            }
    
            else if(comando.startsWith("describir jugador")){
                if(partes.length <= 2){
                    return; //EXCEPCION ESTA MAL POSTO O FORMATO
                }
                else{
                    describirJugador(comando);
                }
            }
    
            else if(comando.startsWith("describir avatar")){
                describirAvatar(comando);
            }
    
            else if(comando.startsWith("describir")){
                describirCasilla(comando);
            }
    
            else if(comando.startsWith("comprar")){
                comprarCasilla(comando);
            }
    
            else if(comando.startsWith("hipotecar")){
                hipotecarCasilla(comando);
            }
    
            else if(comando.startsWith("deshipotecar")){
                deshipotecarCasilla(comando);
            }
    
            else if(comando.startsWith("edificar")){
                edificar(comando);
            }
    
            else if(comando.startsWith("vender")){
                venderEdificio(comando);
            }
    
            else if(comando.equalsIgnoreCase("listar en venta")){
                listarEnVenta();
            }
    
            else if(comando.equalsIgnoreCase("consultar tipos avatar")){
                consultarAvatar();
            }
    
            else if(comando.equalsIgnoreCase("cambiar modo")){
                if(permitir==false){
                    consola.imprimir("Solo se puede cambiar de modo antes de tirar. Tendrás que esperar al próximo turno para hacerlo");
                }
                else{
                    jugadores.get(turno).setModo(!jugadores.get(turno).getModo());
                }
    
                if(jugadores.get(turno).getModo()==true) consola.imprimir("El modo de juego es avanzado");
                else consola.imprimir("El modo de juego es simple");
            }
    
        
        }
        catch(ExcepcionComando e){
            consola.imprimir("Error: " + e.getMessage());
        }
        catch(ExcepcionEdificar e){
            consola.imprimir("Error: " + e.getMessage());
        }
        catch(ExcepcionJugador e){
            consola.imprimir("Error: " + e.getMessage());
        }
        catch(ExcepcionTipoSolar e){
            consola.imprimir("Error: " + e.getMessage());

        }
    }

    public void comprobarAvatar(String tipo) throws ExcepcionTipoAvatar {
        // Verifica si el tipo de avatar es válido
            if(!tipo.equalsIgnoreCase("pelota")&&!tipo.equalsIgnoreCase("esfinge")&&!tipo.equalsIgnoreCase("sombrero")&&!tipo.equalsIgnoreCase("coche")){
                throw new ExcepcionTipoAvatar("El tipo de avatar dado no es válido");
        }
    }

}
