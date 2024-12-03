package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;  // CUANDO SE DESCOMNTEN LAS CARTAS SE NECESITA
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
import partida.avatares.*;
import monopoly.edificios.*;
import excepcions.*;

public class Menu {

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
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private boolean partidainiciada;
    private boolean acabarPartida = false;
    private Baraja baraja;

    public Menu(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.lanzamientos = 0;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.tirado = false;
        this.solvente = true;
        this.partidainiciada = false;
        this.baraja = new Baraja();
    }
    
    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {
        
        String nome;

        dado1 = new Dado();
        dado2 = new Dado();
    
        Scanner scan = new Scanner(System.in);
        System.out.println("\u001B[1m");
        System.out.println("&&   &&  &&&&&&  &&   &  &&&&&&  &&&&&&  &&&&&&  &    &   &");
        System.out.println("& & & &  &    &  & &  &  &    &  &    &  &    &  &     & &");
        System.out.println("&  &  &  &    &  &  & &  &    &  &&&&&   &    &  &      &");
        System.out.println("&     &  &    &  &   &&  &    &  &       &    &  &      &");
        System.out.println("&     &  &&&&&&  &    &  &&&&&&  &       &&&&&&  &&&&&  &\u001B[0m");

        System.out.println("\nBienvenido a una nueva partida de Monopoly!\n");
        System.out.println("Para comenzar, cree de 2 a 6 jugadores. Recuerde que una vez empezada, no se podran crear nuevos jugadores.\n");
        try{
            mostrarMenu();
        }catch(GeneralException e){
            System.err.println("Excepción capturada: " + e.getMessage() + "\n");
        }

    }
    
    private void mostrarMenu(){
        
        String comando;
        Scanner scan = new Scanner(System.in);
        
        while(true){
            
            if(acabarPartida == true){
                if(jugadores.size() == 1){
                    System.out.println("Felicidades " + jugadores.get(0).getNombre() + ", ganaste el Monopoly!");    
                }
                System.out.println("Cerrando juego...");
                scan.close();
                return;
            }

            if(partidainiciada == false){

                System.out.println(Valor.WHITE + "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");
                System.out.println("-------------------------------------------");
                System.out.println("Crear jugador <nombreJugador> <tipoAvatar>");
                System.out.println("Listar jugadores");
                System.out.println("Consultar tipos avatar");
                System.out.println("Iniciar partida");
                System.out.println("-------------------------------------------");
            }
    
            else{
        
            System.out.println(Valor.WHITE + "\nEs tu turno " + jugadores.get(turno).getNombre() + "!");        
                System.out.println( "\n\u001B[1mOPCIONES DISPONIBLES:\u001B[0m");

                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf("%-45s%-45s%n", "Ver tablero", "Describir jugador <nombreJugador>");
                System.out.printf("%-45s%-45s%n", "Lanzar dados", "Describir avatar <idAvatar>");
                System.out.printf("%-45s%-45s%n", "Comprar <nombre casilla>", "Describir <nombreCasilla>");
                System.out.printf("%-45s%-45s%n", "Edificar <tipo>", "Listar jugadores");
                System.out.printf("%-45s%-45s%n", "Hipotecar <nombre casilla>", "Listar avatares");
                System.out.printf("%-45s%-45s%n", "Deshipotecar <nombre casilla>", "Listar en venta");
                System.out.printf("%-45s%-45s%n", "Vender <tipo> <nombre casilla> <cantidad>", "Estadisticas <nombre jugador>");
                System.out.printf("%-45s%-45s%n", "listar edificios", "Listar edificios <color grupo>");

                System.out.printf("%-45s%-45s%n", "Estadisticas", "Estadisticas <nombreJugador>");


                if(jugadores.get(turno).getEncarcel()==true){
                    System.out.println("Pagar fianza");}
                System.out.printf("%-45s%-45s%n", "Acabar turno", "Salir");
                System.out.println("-------------------------------------------------------------------------------");
            }
    
            comando = scan.nextLine();

            if(comando.equalsIgnoreCase("salir")){
                System.out.println("Cerrando juego...");
                scan.close();
                return;
            }
            
            analizarComando(comando);
        }
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {

        //DAR DE ALTA A UN JUGADOR DE MONOPOLY
        if(comando.startsWith("crear jugador") || comando.startsWith("Crear jugador")){
            if(partidainiciada == true){
                System.out.println("\nNo se puede crear un jugador una vez la partida haya iniciado");
            }
            else{
                System.out.println("");
                String[] partes = comando.split(" "); // Dividir por espacios
                if(!partes[3].equalsIgnoreCase("esfinge")&&(!partes[3].equalsIgnoreCase("sombrero"))&&(!partes[3].equalsIgnoreCase("pelota"))&&(!partes[3].equalsIgnoreCase("coche"))){
                    System.out.println("Error al crear el jugador. Por favor, introduzca un tipo de avatar valido");
                    return;
                }
                crearJugador(comando); 
            }
        }

        // Dar comienzo a la partida (cambia el menu de juego)
        else if(comando.equalsIgnoreCase("iniciar partida")){
            if(jugadores.size()<2){
                System.out.println("No se puede empezar la partida hasta que haya por lo menos dos jugadores");
            }
            else{
                partidainiciada = true;
                System.out.println("\n\u001B[1mComienza la partida!\u001B[0m");
            }
        }

        /*INDICAR JUGADOR QUE TIENE EL TURNO        NUNCA LA USAMOS PERO LA COMENTO POR SI ACASO
        else if(comando.equalsIgnoreCase("jugador actual")){
            System.out.println("");
            mostrarJugadorTurno();
        }*/

        //LISTAR JUGADORES
        else if(comando.equalsIgnoreCase("listar jugadores")){
            System.out.println("");
            listarJugadores();
        }else if (comando.equalsIgnoreCase("estadisticas")) {
            estadisticasPartida();
        }

        //MOSTRAR ESTADISTICAS DE UN JUGADOR Y ESTADISTICAS (ESTADISTICAS A SECAS NO ESTÁ HECHO AUN)
        else if(comando.startsWith("estadisticas")){
            String[] partes = comando.split(" ");
            if(partes.length == 1){ //FALTA CODIGO ESTADISTICAS GLOBALES

            }
            else if(partes.length == 2){
                String nombreJugador = partes[1];
                estadisticasJugador(nombreJugador);
            }
        }

        //MOSTRAR ESTADISTICAS DE UN JUGADOR Y ESTADISTICAS (ESTADISTICAS A SECAS NO ESTÁ HECHO AUN)
        else if(comando.startsWith("estadisticas")){
            String[] partes = comando.split(" ");
            if(partes.length == 1){ //FALTA CODIGO ESTADISTICAS GLOBALES

            }
            else if(partes.length == 2){
                String nombreJugador = partes[1];
                estadisticasJugador(nombreJugador);
            }
        }

        //LISTAR EDIFICIOS Y EDIFICIOS CONSTRUIDOS POR UN GRUPO
        else if(comando.startsWith("listar edificios")){
            String[] partes = comando.split(" ");
            if(partes.length == 2){
                listarEdificios();
            }
            else if(partes.length == 3){
                String colorGrupo = partes[2];
                listarEdificiosPorGrupo(colorGrupo);
            }

        }

        //LISTAR AVATARES
        else if(comando.equalsIgnoreCase("listar avatares")){
            System.out.println("");
            listarAvatares();
        } 

        //ACABAR TURNO
        else if(comando.equalsIgnoreCase("acabar turno")){
                System.out.println("");
                acabarTurno(true);
            }

        //LANZAR DADOS
        else if(comando.equalsIgnoreCase("lanzar dados")){
            modoAvanzar(false);
        }

        //SALIR CARCEL 
        else if(comando.equalsIgnoreCase("Pagar fianza")){
            
            salirCarcel(true);

        }

        else if(comando.equalsIgnoreCase("ver tablero")){
            tablero.imprimirTablero();
        }

        //DESCRIBIR JUGADOR ---
        else if(comando.startsWith("describir jugador") || comando.startsWith("Describir jugador")){
            String[] partes = comando.split(" "); // Dividir por espacios
            if(partes.length <= 2){
                System.out.println("\nFormato incorrecto. Introduzca describir jugador 'nombre'");
                return;}
            else{
                boolean listar = false;

                descJugador(partes, listar);}
        }

        //DESCRIBIR AVATAR --
        else if(comando.startsWith("describir avatar") || comando.startsWith("Describir avatar") ){
            String[] partes = comando.split(" "); // Dividir por espacios
            String id = partes[2];

            descAvatar(id);
        }

        //DESCRIBIR CASILLA
        else if(comando.startsWith("describir") || comando.startsWith("Describir")){
            String[] partes = comando.split(" "); //Dividir por espacios
            String nombreCasilla = partes[1];
            System.out.println("");
            descCasilla(nombreCasilla);
        }

        //COMPRAR CASILLA
        else if(comando.startsWith("comprar") || comando.startsWith("Comprar")){;
            String[] partes = comando.split(" ");
            String nombreCasilla = partes[1];
            System.out.println("");
            comprar(nombreCasilla);
        }

        //HIPOTECAR CASILLA
        else if(comando.startsWith("hipotecar") || comando.startsWith("Hipotecar")){;
            String[] partes = comando.split(" ");
            String nombreCasilla = partes[1];
            System.out.println("");
            hipotecar(nombreCasilla);
        }

        //DESHIPOTECAR CASILLA
        else if(comando.startsWith("deshipotecar") || comando.startsWith("Deshipotecar")){;
            String[] partes = comando.split(" ");
            String nombreCasilla = partes[1];
            System.out.println("");
            deshipotecar(nombreCasilla);
        }

        //EDIFICAR EDIFICIO
        else if(comando.startsWith("edificar")||comando.startsWith("Edificar")){
            String[] partes = comando.split(" ");
            String tipo = partes[1];
            System.out.println("");
            Edificar(tipo);
        }

         //VENDER EDIFICIO
         else if(comando.startsWith("vender")){
            String[] partes = comando.split(" ");
            String tipo = partes[1];
            String casilla = partes[2];
            String cantidad = partes[3];
            System.out.println("");

            int entero = 0;
            try {
                entero = Integer.parseInt(cantidad);  // Convertir la cantidad a int
            }
            catch (NumberFormatException e) {
                System.out.println("Error: La cantidad debe ser un número entero.");
                return;
            }

            venderEdificio(tipo, casilla, entero);

        }

        //LISTAR EDIFICIOS Y LISTAR EDIFICIOS GRUPO
        else if(comando.startsWith(("listar edificios")) || comando.startsWith("Listar edificio")){
            String[] partes = comando.split(" ");
            
            if(partes.length == 2){
                listarEdificios();
            }
            else if(partes.length == 3){
                String grupocolor = partes[2];
                listarEdificiosPorGrupo(grupocolor);

            }
            else{
                System.out.println("Comando incorrecto");
            }
        }

        //LISTAR EN VENTA
        else if(comando.equalsIgnoreCase("listar en venta")){
            listarVenta();
        }

        //IMPRIME LOS TIPOS DE AVATAR (MENU DE INICIO)
        else if (comando.equalsIgnoreCase("Consultar tipos avatar")) {
            System.out.println("Si se elige el modo avanzado al lanzar los dados, las piezas se comportarán de la siguiente manera:");
            System.out.println("\n\u001B[1mCoche:\u001B[0m si el valor de los dados es superior a 4, se avanzará tantas casillas como indicado y se podrá seguir lanzando los dados");
            System.out.println("\thasta 3 veces más mientras se siga obteniendo un 4 como mínimo. Se podrá realizar una compra en cualquiera de los turnos, pero solo en uno de ");
            System.out.println("\tellos. Si el valor es menor a 4, se retrocederá el número de casillas correspondiente y se perderán los dos próximos turnos.");
            System.out.println("");
            System.out.println("\u001B[1mEsfinge:\u001B[0m si el valor de los dados es superior a 4, se realizará el movimiento en zigzag entre los lados norte y sur del tablero.");
            System.out.println("\tMientras se siga obteniendo un 4 de mínimo, se podrán realizar hasta 2 tiradas extra. Si se obtiene menos de un 4, se desharán todos los cambios");
            System.out.println("\trealizados en el turno anterior.");
            System.out.println("");
            System.out.println("\u001B[1mSombrero:\u001B[0m se regirá por las mismas normas que la esfinge, pero el movimiento en zigzag se realizará entre los lados este y oeste.");
            System.out.println("");
            System.out.println("\u001B[1mPelota:\u001B[0m si el valor de los dados es inferior a 4, se retrocederá ese número de casillas. Si es superior se avanzará el valor dado");
            System.out.println("\tpero se irá parando en todas las casillas impares hasta el valor obtenido.");
            return;
        }

        else if (comando.startsWith("trucados")) {
            modoAvanzar(true);
            /*int valor1 = Integer.parseInt(comando.split(" ")[1]);
            int valor2 = Integer.parseInt(comando.split(" ")[2]);
            dadosTrucados(valor1, valor2);*/
        }
        else if(comando.equalsIgnoreCase("pobre")){
            jugadores.get(turno).setFortuna(0);
        }
        else{
            throw new GeneralException("Comando incorrecto");
        }

        return;
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes, boolean list) {
        String nombrejugador = partes[2];

        for (Jugador jugador : jugadores){
            if(jugador.getNombre().equalsIgnoreCase(nombrejugador)){
                StringBuilder info = new StringBuilder();

                info.append("Nombre:").append(nombrejugador).append("\n");
                info.append("Avatar:").append(jugador.getAvatar().getId()).append("\n");
                info.append("Fortuna:").append(jugador.getFortuna()).append("\n");

                info.append("Propiedades: [");
                for (short j=0; j< jugador.getPropiedades().size(); j++){
                    Casilla propiedad = jugador.getPropiedades().get(j);
                    info.append(propiedad.getNombre());
                    if(j<(jugador.getPropiedades().size()-1))  info.append(",");
                }

                info.append(Valor.WHITE + "]").append("\n");

                info.append("Hipotecas: [");

                for (short j=0; j< jugador.getPropiedades().size(); j++){
                    Casilla propiedad = jugador.getPropiedades().get(j);
                    if(propiedad.getEstarHipotecada() == true){
                        info.append(propiedad.getNombre());
                        if(j<(jugador.getPropiedades().size()-1))  info.append(",");
                    }
                    
                }

                info.append(Valor.WHITE + "]").append("\n");

                info.append("edificios: ");
                
                ArrayList<Casilla> propiedades = tablero.obtenerPropiedadesPorDuenho(jugador.getNombre());
                
                for(Casilla casilla : propiedades){
                    if(casilla.getTipo().equalsIgnoreCase("Solar")){
                        ArrayList<Edificio> edificios = casilla.getEdificios();
                       
                        
                        for(Edificio edificio: edificios){
                            info.append("[").append(edificio.getTipo()).append("-").append(edificio.getId()).append("] ");
                        }
                        
                    }
                }
                info.append("\n");

                System.out.println(info.toString());
                if(list == false) return;
            }
        }
        if(list == false){
            System.out.println("No se encontró el jugador con el nombre: " +nombrejugador);
            return;
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {

        for(int i = 0; i < avatares.size(); i++){
            if(avatares.get(i).getId().equalsIgnoreCase(ID)){
                StringBuilder info = new StringBuilder();
                info.append(Valor.WHITE + "Id: ").append(ID).append("\n");
                info.append("Tipo: ").append(avatares.get(i).getTipo()).append("\n");
                info.append("Jugador: ").append(avatares.get(i).getJugador().getNombre()).append("\n");
                info.append("Casilla: ").append(avatares.get(i).getLugar().getNombre()).append("\n");

                System.out.println(info.toString());
                return;
            }
        }

        System.out.println("No se encontró el avatar con el nombre: " + ID);
        return;
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre){
        Casilla casilla=  tablero.encontrar_casilla(nombre);

        if(casilla == null){
            System.out.print("\nCasilla no encontrada\n");
            return;
        }
        
        String info = casilla.infoCasilla();
        System.out.println(info);
        return;
    }

    //Antes de lanzar los dados, se le pregunta al jugador en que modo desea avanzar
    private void modoAvanzar(boolean trucado){
        Scanner scan = new Scanner(System.in);
        String eleccion;
        Jugador jugador = jugadores.get(turno);

        //Comprueba si el jugador está bloqueado este turno
        if(jugador.getBloqueado()>0){
            jugador.setBloqueado(jugador.getBloqueado()-1);
            if(jugador.getBloqueado()==0) System.out.println("El jugador " + jugador.getNombre() + " está bloqueado este turno. Podrá volver a tirar en el siguiente");
            else System.out.println("El jugador " + jugador.getNombre() + " está bloqueado este turno. Podrá volver a tirar en " + (jugador.getBloqueado()+1) + " turnos");
            tirado=true;
            acabarTurno(false);
            return;
        }

        //Si le quedan turnos extra, se vuelven a lanzar los dados en modo avanzado
        if(jugador.getAvatar().getExtras()!=0){
            jugador.getAvatar().setExtras(jugador.getAvatar().getExtras()-1);
            if(jugador.getAvatar().getExtras()==0){
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, false);
                }
                else lanzarDados(false);
            }
            else{
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, true);
                }
                else lanzarDados(true);}
            return;
        }
        //comprobar si se ha tirado en el turno
        else if(tirado==true){
            System.out.println("El jugador " + jugador.getAvatar().getId() + " ya ha tirado en este turno. ");
            return;
        }

        //En caso de haber sacado dobles para no volver a preguntar modo
        if(jugador.getModoElegido()==0){
            //Si no ha tirado ya, se pregunta modo de avanzar
            System.out.print("¿Desea moverse en modo simple o avanzado?\n");
            eleccion = scan.nextLine();
            if(eleccion.equalsIgnoreCase("avanzado")){
                jugador.setModoElegido(2); //Ya se ha elegido modo
                switch (jugador.getAvatar().getTipo()) {
                    case "pelota": jugador.getAvatar().setExtras(0);
                    jugador.setBloqueado(-1);
                        break;
                    case "coche": jugador.getAvatar().setExtras(3);
                        break;
                    default:
                        break;
                }
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, true);
                }
                else lanzarDados(true);
            }
            else if(eleccion.equalsIgnoreCase("simple")){
                jugador.setModoElegido(1); //Ya se ha elegido modo
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, false);
                }
                else lanzarDados(false);
            }
            else{
                System.out.print("Modo no reconocido\n");
                return;
            }
        }
        else{
            if(jugador.getModoElegido()==1){
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, false);
                }
                else lanzarDados(false);
            }
            else if(jugador.getModoElegido()==2){
                if(trucado==true){
                    System.out.print("Dado 1: ");
                    int dado1= scan.nextInt();
                    System.out.print("Dado 2: ");
                    int dado2= scan.nextInt();
                    dadosTrucados(dado1, dado2, true);
                }
                else lanzarDados(true);
            }
        }
    }


    private void lanzarCarcel(int d1, int d2){
        Jugador jugador = jugadores.get(turno);

        if(d1 == d2){
            System.out.println("Has sacado dobles y por lo tanto sales de la cárcel");
            jugadores.get(turno).setEncarcel(false);
            jugadores.get(turno).setTiradasCarcel(0); 
        }
        else{
            tablero.imprimirTablero();
            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
            if(jugador.getTiradasCarcel()<2){
                System.out.println("Como no has sacado dobles, debes permanecer en la cárcel");
                tirado=true;
                jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
                acabarTurno(false);
                return;
                }
            else{
                System.out.println("Has agotado tus intentos para salir de la cárcel");}
                salirCarcel(false);
        }
        return;
    }

  //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados(boolean avanzado) {
        Jugador jugador = jugadores.get(turno);
        String casillaAnterior = jugador.getAvatar().getLugar().getNombre(); //Nombre de la casilla anterior para prints

        //Se tiran los dados
        dado1.hacerTirada();
        dado2.hacerTirada();
        int casillasTotal = dado1.getValor() + dado2.getValor();

        //Para asegurarnos que va por la rama correcta, modificamos el valor de casillas total. Si es necesario, se volverá a cambiar más adelante (PARCHE)
        if((jugador.getBloqueado()==-2)||(jugador.getBloqueado()==-3)||(jugador.getBloqueado()==-4)) casillasTotal = 1;
        else if (jugador.getBloqueado()<=-5) casillasTotal = 6;
        else jugador.sumarTiradas(1);

        //Se comprueba si el jugador está en la cárcel
        if(jugador.getEncarcel()==true){
            lanzarCarcel(dado1.getValor(), dado2.getValor());
            if((jugador.getTiradasCarcel()==1)||(jugador.getTiradasCarcel()==2)) return; //Si no pudo salir, se termina
        }

        //Cuando pelota tiene que retroceder las últimas 1 o 2 casillas
        if(((jugador.getBloqueado()==-2||jugador.getBloqueado()==-4)&&(avanzado==false))){  //Solo ocurrirá cuando sea negativo <=4 y avanzado falso
            dado1.setValor(0); //Asegurarnos de que no son dobles accidentalmente
            casillasTotal = -1;
        }
        else if(jugador.getBloqueado()==-3){ //Si es -3, avanzado será falso así que sobra comprobación
            dado1.setValor(0);
            casillasTotal = -2;
        }
            //Si avatar pelota, ultimo turno avanza 1 o 2 casillas dependiendo si tirada original par o impar
        else if((jugador.getBloqueado()%2==0)&&(avanzado==false)&&(jugador.getBloqueado()<0)){
            dado1.setValor(0);
            casillasTotal = 1;}
        else if((jugador.getBloqueado()%2!=0)&&(avanzado==false)&&(jugador.getBloqueado()<0)){
            dado1.setValor(0); 
            casillasTotal = 2;}

        //verificar si son dobles, y en caso afirmativo incrementa el atributo del jugador
        if(dado1.getValor() == dado2.getValor()){
            if((avanzado==false)||(jugador.getBloqueado()==-1)){ //Solo se cuenta sacar dobles en el modo avanzado
                jugador.incrementarDobles(); 
                if(avanzado==true) jugadores.get(turno).setPrimeraDobles(true);
            }
        }
                
            if(jugador.getDobles() == 3){
                jugadores.get(turno).encarcelar(tablero.getPosiciones());
                tablero.imprimirTablero();
                System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
                System.out.println("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel y se acaba tu turno");
                tirado = true;
                acabarTurno(false);
                return;
                }
        

        //Si se ha seleccionado el modo avanzado, dependiendo del avatar se realizaran distintas acciones
        if(avanzado==true){
            switch (jugador.getAvatar().getTipo()) {
                case "pelota":
                    if((casillasTotal>4)&&(casillasTotal!=5)){
                        // Suma los turnos extra correspondientes. Solo lo hace la primera vez gracias a la comprobación
                        if(jugador.getBloqueado()==-1){
                            int min=5;
                            
                            while(min<casillasTotal){ 
                                jugador.getAvatar().setExtras(jugadores.get(turno).getAvatar().getExtras()+1);
                                min = min+2;
                            } 
                    } // La primera llamada hace que bloqueado sea el valor negativo de la tirada original. Así almacenamos el valor
                        if(jugador.getBloqueado()==-1){
                            jugador.setBloqueado(-1*casillasTotal);
                            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor() + ". Por lo tanto, avanzará ese número de casillas parando en todos los movimientos impares, empezando por el 5.");
                            casillasTotal = 5;
                        }
                        else{
                            casillasTotal=2;
                            System.out.println("El jugador " + jugadores.get(turno).getAvatar().getId() + " avanza hasta la siguiente casilla impar");
                        }
                    }
                    else if(casillasTotal==5){
                        avanzado=false;
                        jugador.setBloqueado(0);}
                    else{ //Si menos que 4, retrocedes
                        if(jugador.getBloqueado()==-1){                            
                            if(casillasTotal==4) jugador.getAvatar().setExtras(2);
                            else jugador.getAvatar().setExtras(1);
                        } 
                        // La primera llamada hace que bloqueado sea el valor negativo de la tirada original. Así almacenamos el valor
                        if(jugador.getBloqueado()==-1){
                            jugador.setBloqueado(-1*casillasTotal);
                            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor() + ". Como es un 4 o menos, retrocede el valor obtenido parando en todos los movimientos impares.");
                            casillasTotal = -1;
                        }
                        else{
                            casillasTotal=-2;
                            System.out.println("El jugador " + jugadores.get(turno).getAvatar().getId() + " retrocede hasta la siguiente casilla impar");
                        }
                    }
                    break;
                
                case "coche":
                    if(casillasTotal<=4){
                        //Se mueve el valor obtenido para atrás
                        casillasTotal = -1*casillasTotal;
                        jugador.setBloqueado(2);
                        //Se mueve el avatar del jugador el numero de casillas
                        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
                        tablero.imprimirTablero();
                        System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
                        System.out.println("Como es un 4 o menos, retrocede el valor obtenido y pierde sus 2 proximos turnos.");
                        tirado=true;
                        acabarTurno(false);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }     

        //Se mueve el avatar del jugador el numero de casillas
        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
        
        jugador.getAvatar().getLugar().sumarVisitas(1);
        jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);
        //Si la casilla es IrCárcel, mueve al jugador hasta ella
        if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("IrCarcel")){
            jugador.encarcelar(tablero.getPosiciones());
            tablero.imprimirTablero();
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + "IrCárcel y es llevado a la cárcel, perdiendo su turno. Oh no!");
            tirado=true;
            acabarTurno(false);
            return;
        }

        tablero.imprimirTablero();
        //Mostrar resultados de los dados. Como bloqueado tiene valor negativo para pelota avanzada, solo no se imprime en ese caso
        if(jugador.getBloqueado()>=0) System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
        // Si avatar pelota, avisa cuantas casillas finales se mueve hasta tirada original (depende de par/impar, ultima tirada en modo simple y bloqueado negativo porque solo para pelota)
        else if(((jugador.getBloqueado()==-2||jugador.getBloqueado()==-4)&&(avanzado==false)))  System.out.println("El jugador " + jugador.getAvatar().getId() + " retrocede la casilla restante");
        else if(jugador.getBloqueado()==-3)  System.out.println("El jugador " + jugador.getAvatar().getId() + " retrocede las 2 casillas restantes");
        else if ((jugador.getBloqueado()%2==0)&&(avanzado==false)&&(jugador.getBloqueado()<0)) System.out.println("El jugador " + jugador.getAvatar().getId() + " avanza la casilla restante");
        else if ((jugador.getBloqueado()%2!=0)&&(avanzado==false)&&(jugador.getBloqueado()<0))System.out.println("El jugador " + jugador.getAvatar().getId() + " avanza las 2 casillas restantes");

        //Comprueba si se puede realizar la acción de la casilla.
        solvente = jugador.getAvatar().getLugar().evaluarCasilla(jugador, banca,casillasTotal);

        if(avanzado==false){
            //Se vuelve a mirar los dobles para imprimirlo debajo del tablero
            if(dado1.getValor()==dado2.getValor()){
                System.out.println("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
                tirado = false;}
            else if(jugador.getPrimeraDobles()==true){ 
                System.out.println("Felicidades! Como tu tirada inicial fueron dobles, tienes otro lanzamiento!");
                tirado = false;
                jugador.setBloqueado(-1);
                jugador.setPrimeraDobles(false);
                jugador.getAvatar().setExtras(0);}
            else{
                tirado = true;
            }
        }
                //Si no, se acaban tus tiradas y se resetean tus dobles
        else{
            tirado = true; //marcar que el jugador ha lanzado
            if(jugador.getPrimeraDobles()!= true) jugador.resetearDobles(); //reseteamos os dobles
        }

        //Si es una casilla de su propiedad
        if(jugador == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de su propiedad.");
            }
        else if(banca == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de la banca.");   
            }

        //Si es de otro jugador
        else{
            float alquiler = jugador.getAvatar().getLugar().calcularAlquiler(jugador, casillasTotal);
            System.out.println( "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);
            //Si le puedes pagar, se paga automáticamente
            if(solvente == true){
                System.out.println( "Se han pagado " + alquiler + " euros de alquiler. ");
            }
            //Si no puede pagar pero tiene propiedades
            else{
                bancarrota(jugador,casillasTotal);
                }
            }

        this.lanzamientos += 1;
        if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Suerte")||jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Caja")){
            suerteComunidad(jugador); //Se encarga de realizar las acciones de las cartas de suerte/comunidad
        }

        if(comprobarVueltas()==true){
            tablero.incrementarPrecios();
            resetearVueltas();
        }
    return;
}

    
    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void dadosTrucados(int valor1, int valor2, boolean avanzado) {
        Jugador jugador = jugadores.get(turno);
        String casillaAnterior = jugador.getAvatar().getLugar().getNombre(); //Nombre de la casilla anterior para prints

        //Se tiran los dados
        dado1.setValor(valor1);
        dado2.setValor(valor2);
        int casillasTotal = valor1 + valor2;
        
        //Para asegurarnos que va por la rama correcta, modificamos el valor de casillas total. Si es necesario, se volverá a cambiar más adelante (PARCHE)
        if((jugador.getBloqueado()==-2)||(jugador.getBloqueado()==-3)||(jugador.getBloqueado()==-4)) casillasTotal = 1;
        else if (jugador.getBloqueado()<=-5) casillasTotal = 6;
        else jugador.sumarTiradas(1);

        //Se comprueba si el jugador está en la cárcel
        if(jugador.getEncarcel()==true){
            lanzarCarcel(dado1.getValor(), dado2.getValor());
            if((jugador.getTiradasCarcel()==1)||(jugador.getTiradasCarcel()==2)) return; //Si no pudo salir, se termina
        }

        //Cuando pelota tiene que retroceder las últimas 1 o 2 casillas
        if(((jugador.getBloqueado()==-2||jugador.getBloqueado()==-4)&&(avanzado==false))){  //Solo ocurrirá cuando sea negativo <=4 y avanzado falso
            dado1.setValor(0); //Asegurarnos de que no son dobles accidentalmente
            casillasTotal = -1;
        }
        else if(jugador.getBloqueado()==-3){ //Si es -3, avanzado será falso así que sobra comprobación
            dado1.setValor(0);
            casillasTotal = -2;
        }
            //Si avatar pelota, ultimo turno avanza 1 o 2 casillas dependiendo si tirada original par o impar
        else if((jugador.getBloqueado()%2==0)&&(avanzado==false)&&(jugador.getBloqueado()<0)){
            dado1.setValor(0);
            casillasTotal = 1;}
        else if((jugador.getBloqueado()%2!=0)&&(avanzado==false)&&(jugador.getBloqueado()<0)){
            dado1.setValor(0); 
            casillasTotal = 2;}

        //verificar si son dobles, y en caso afirmativo incrementa el atributo del jugador
        if(dado1.getValor() == dado2.getValor()){
            if((avanzado==false)||(jugador.getBloqueado()==-1)){ //Solo se cuenta sacar dobles en el modo avanzado
                jugador.incrementarDobles(); 
                if(avanzado==true) jugadores.get(turno).setPrimeraDobles(true);
            }
        }
                
            if(jugador.getDobles() == 3){
                jugadores.get(turno).encarcelar(tablero.getPosiciones());
                tablero.imprimirTablero();
                System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
                System.out.println("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel y se acaba tu turno");
                tirado = true;
                acabarTurno(false);
                return;
                }
        

        //Si se ha seleccionado el modo avanzado, dependiendo del avatar se realizaran distintas acciones
        if(avanzado==true){
            switch (jugador.getAvatar().getTipo()) {
                case "pelota":
                    if((casillasTotal>4)&&(casillasTotal!=5)){
                        // Suma los turnos extra correspondientes. Solo lo hace la primera vez gracias a la comprobación
                        if(jugador.getBloqueado()==-1){
                            int min=5;
                            
                            while(min<casillasTotal){ 
                                jugador.getAvatar().setExtras(jugadores.get(turno).getAvatar().getExtras()+1);
                                min = min+2;
                            } 
                    } // La primera llamada hace que bloqueado sea el valor negativo de la tirada original. Así almacenamos el valor
                        if(jugador.getBloqueado()==-1){
                            jugador.setBloqueado(-1*casillasTotal);
                            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor() + ". Por lo tanto, avanzará ese número de casillas parando en todos los movimientos impares, empezando por el 5.");
                            casillasTotal = 5;
                        }
                        else{
                            casillasTotal=2;
                            System.out.println("El jugador " + jugadores.get(turno).getAvatar().getId() + " avanza hasta la siguiente casilla impar");
                        }
                    }
                    else if(casillasTotal==5){
                        avanzado=false;
                        jugador.setBloqueado(0);}
                    else{ //Si menos que 4, retrocedes
                        if(jugador.getBloqueado()==-1){                            
                            if(casillasTotal==4) jugador.getAvatar().setExtras(2);
                            else jugador.getAvatar().setExtras(1);
                        } 
                        // La primera llamada hace que bloqueado sea el valor negativo de la tirada original. Así almacenamos el valor
                        if(jugador.getBloqueado()==-1){
                            jugador.setBloqueado(-1*casillasTotal);
                            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor() + ". Como es un 4 o menos, retrocede el valor obtenido parando en todos los movimientos impares.");
                            casillasTotal = -1;
                        }
                        else{
                            casillasTotal=-2;
                            System.out.println("El jugador " + jugadores.get(turno).getAvatar().getId() + " retrocede hasta la siguiente casilla impar");
                        }
                    }
                    break;
                
                case "coche":
                    if(casillasTotal<=4){
                        //Se mueve el valor obtenido para atrás
                        casillasTotal = -1*casillasTotal;
                        jugador.setBloqueado(2);
                        //Se mueve el avatar del jugador el numero de casillas
                        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
                        tablero.imprimirTablero();
                        System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
                        System.out.println("Como es un 4 o menos, retrocede el valor obtenido y pierde sus 2 proximos turnos.");
                        tirado=true;
                        acabarTurno(false);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }     

        if((avanzado==false)&&
        (jugador.getModoElegido()==2)&&
        (jugador.getAvatar().getTipo().equalsIgnoreCase("coche"))&&
        (casillasTotal<=4)){
            //Se mueve el valor obtenido para atrás
            casillasTotal = -1*casillasTotal;
            jugador.setBloqueado(2);
            //Se mueve el avatar del jugador el numero de casillas
            jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
            tablero.imprimirTablero();
            System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
            System.out.println("Como es un 4 o menos, retrocede el valor obtenido y pierde sus 2 proximos turnos.");
            tirado=true;
            acabarTurno(false);
            return;
        }



        //Se mueve el avatar del jugador el numero de casillas
        jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
        jugador.getAvatar().getLugar().sumarVisitas(1);
        jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);
        

        //Si la casilla es IrCárcel, mueve al jugador hasta ella
        if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("IrCarcel")){
            jugador.encarcelar(tablero.getPosiciones());
            tablero.imprimirTablero();
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + "IrCárcel y es llevado a la cárcel, perdiendo su turno. Oh no!");
            tirado=true;
            acabarTurno(false);
            return;
        }

        tablero.imprimirTablero();
        //Mostrar resultados de los dados. Como bloqueado tiene valor negativo para pelota avanzada, solo no se imprime en ese caso
        if(jugador.getBloqueado()>=0) System.out.println("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1.getValor() + " y " + dado2.getValor());
        // Si avatar pelota, avisa cuantas casillas finales se mueve hasta tirada original (depende de par/impar, ultima tirada en modo simple y bloqueado negativo porque solo para pelota)
        else if(((jugador.getBloqueado()==-2||jugador.getBloqueado()==-4)&&(avanzado==false)))  System.out.println("El jugador " + jugador.getAvatar().getId() + " retrocede la casilla restante");
        else if(jugador.getBloqueado()==-3)  System.out.println("El jugador " + jugador.getAvatar().getId() + " retrocede las 2 casillas restantes");
        else if ((jugador.getBloqueado()%2==0)&&(avanzado==false)&&(jugador.getBloqueado()<0)) System.out.println("El jugador " + jugador.getAvatar().getId() + " avanza la casilla restante");
        else if ((jugador.getBloqueado()%2!=0)&&(avanzado==false)&&(jugador.getBloqueado()<0))System.out.println("El jugador " + jugador.getAvatar().getId() + " avanza las 2 casillas restantes");

        //Comprueba si se puede realizar la acción de la casilla.
        solvente = jugador.getAvatar().getLugar().evaluarCasilla(jugador, banca,casillasTotal);

        if(avanzado==false){
            //Se vuelve a mirar los dobles para imprimirlo debajo del tablero
            if(dado1.getValor()==dado2.getValor()){
                System.out.println("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
                tirado = false;}
            else if(jugador.getPrimeraDobles()==true){ 
                System.out.println("Felicidades! Como tu tirada inicial fueron dobles, tienes otro lanzamiento!");
                tirado = false;
                jugador.setBloqueado(-1);
                jugador.setPrimeraDobles(false);
                jugador.getAvatar().setExtras(0);}
            else{
                tirado = true;
            }
        }
                //Si no, se acaban tus tiradas y se resetean tus dobles
        else{
            tirado = true; //marcar que el jugador ha lanzado
            if(jugador.getPrimeraDobles()!= true) jugador.resetearDobles(); //reseteamos os dobles
        }

        //Si es una casilla de su propiedad
        if(jugador == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de su propiedad.");
            }
        else if(banca == jugador.getAvatar().getLugar().getDuenho()){
            System.out.println(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de la banca.");   
            }

        //Si es de otro jugador
        else{
            float alquiler = jugador.getAvatar().getLugar().calcularAlquiler(jugador, casillasTotal);
            System.out.println( "El avatar "+ jugador.getAvatar().getId() + " avanza " + casillasTotal + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);
            //Si le puedes pagar, se paga 
            if(jugador.getAvatar().getLugar().getEstarHipotecada() == true){
                System.out.println( "La casilla esta hipotecada asi que el jugador no tiene que pagar nada. ");
            }
            else if(solvente == true){
                System.out.println( "Se han pagado " + alquiler + " euros de alquiler. ");
            }
            //Si no puede pagar pero tiene propiedades
            else{
                bancarrota(jugador,casillasTotal);
                }
            }

        this.lanzamientos += 1;
        if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Suerte")||jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Caja")){
            suerteComunidad(jugador); //Se encarga de realizar las acciones de las cartas de suerte/comunidad
        }

        if(comprobarVueltas()==true){
            tablero.incrementarPrecios();
            resetearVueltas();
        }
    return;
}

    private void bancarrota(Jugador jugador, int casillasTotal){
        //Hace arrays y les introduce las propiedades que tienen
        boolean solv = false;
        if(!jugador.getPropiedades().isEmpty()){
        
            ArrayList<Casilla> paraHipotecar = new ArrayList<>();
            ArrayList<Casilla> paraVenderEdificios = new ArrayList<>();

            for(int i = 0;jugador.getPropiedades().size() > i; i++){
                if(!jugador.getPropiedades().get(i).getEstarHipotecada() && !jugador.getPropiedades().get(i).tieneEdificios()){
                    paraHipotecar.add(jugador.getPropiedades().get(i));
                }else if(jugador.getPropiedades().get(i).tieneEdificios()){
                    paraVenderEdificios.add(jugador.getPropiedades().get(i));
                }
            }
                        
            //Si tiene propiedades sigue el codigo, si no, se acaba el juego
            if(paraHipotecar.size() > 0 || paraVenderEdificios.size() > 0){
                System.out.println("El jugador no puede permitirse pagar este alquiler, pero tiene solares que puede hipotecar o edificios que vender");
            }else{
                System.out.println("El jugador no puede permitirse pagar este alquiler, entra en bancarrota");
                eliminarJugador(jugador);
                acabarTurno(true);
                return;                        
            }

            Scanner scan = new Scanner(System.in);
            String nombreVender;
            //Mientras le queden propiedades y no sea solvente, sigue el bucle
            while(solv == false && !paraHipotecar.isEmpty()){
                        
                //Primero imprime todas sus propiedades
                System.out.println("Elija propiedad para hipotecar: \n");
                for(Casilla prop :paraHipotecar){
                    System.out.println("\t" + prop.getNombre());
                }
                System.out.println(Valor.WHITE + "\nO para vender edificios: \n");
                for(Casilla prop :paraVenderEdificios){
                    System.out.println("\t" + prop.getNombre());
                }
                            
                //Escanea cual quiere vender
                nombreVender = scan.nextLine();
                for(int i = 0; i < paraHipotecar.size(); i++){
                    Casilla prop = paraHipotecar.get(i);
                    if(prop.getNombreSinColor().equalsIgnoreCase(nombreVender)){
                        prop.hipotecarCasilla(jugador,this.banca);
                        paraHipotecar.remove(prop);
                        solv = jugador.getAvatar().getLugar().evaluarCasilla(jugador, banca,casillasTotal);
                        if(solv){
                            System.out.println("El jugador pudo pagar sus deudas");
                        }
                    }else{
                        System.out.println("Casilla no encontrada, vuelva a introducir una");
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

                            venderEdificio(tipo,nombreVender,cantidad);
    
                            if(!prop.tieneEdificios()){
                                paraVenderEdificios.remove(prop);
                                paraHipotecar.add(prop);
                            }
                            solv = jugador.getAvatar().getLugar().evaluarCasilla(jugador, banca,casillasTotal);
                            if(solv){
                                System.out.println("El jugador pudo pagar sus deudas");
                            }
                        }
                    }
                }
                
            }
            if(solv == false){
                System.out.println("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); //AQUI O XOGADOR PERDE NON SEI QUE HAI QUE FACERLLE
                eliminarJugador(jugador);
                acabarTurno(true);
                return;                    
            }

        }else{
            System.out.println("El jugador no puede permitirse pagar este alquiler, entra en bancarrota"); //AQUI O XOGADOR PERDE NON SEI QUE HAI QUE FACERLLE
            eliminarJugador(jugador);
            acabarTurno(true);
            return;
        }
    }


    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

        Casilla casillacomprada = tablero.encontrar_casilla(nombre);
        if(casillacomprada==null) return;
        int lanzamiento = dado1.getValor() + dado2.getValor();

        Jugador jugadoractual = jugadores.get(turno); //obter xogador actual

            switch(nombre){
                case "Salida":
                case "salida":
                case "Parking":
                case "parking":
                case "Suerte":
                case "suerte":
                case "Caja":
                case "caja":
                case "Carcel":
                case "carcel":
                case "IrCarcel":
                case "ircarcel":
                case "Ircarcel":
                case "irCarcel":
                case "Imp1":
                case "imp1":
                case "Imp2":
                case "imp2":
                System.out.println("Esta casilla no esta en venta.");
                return;

                default:
                //COMPROBAR QUE EL JUGADOR ESTÁ EN LA CASILLA QUE QUEREMOS COMPRAR, SI NO NO PUEDE.
                    if((tirado == false)&&(jugadoractual.getDobles()<1)){
                        System.out.println("El jugador aun no ha tirado los dados, por lo que no puede comprarla");
                        return;
                    }
                    else if(!jugadoractual.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase(casillacomprada.getNombreSinColor()) ){
                        System.out.println("El jugador no está en la casilla " + nombre + ", por lo que no puede comprarla");
                        return;
                    }//No se puede comprar una casilla si ya se ha hecho una compra ese turno
                    else if((jugadoractual.getComprado()==true)&&(jugadoractual.getModoElegido()==2)){
                        System.out.println("El jugador ya ha realizado una compra este turno.");
                        return;
                    }
                    else if(!casillacomprada.getDuenho().getNombre().equalsIgnoreCase("Banca")){
                        System.out.println("El jugador no puede comprar la casilla porque ya es propiedad de " + casillacomprada.getDuenho().getNombre());
                        return;
                    }
                    else if(casillacomprada.evaluarCasilla(jugadores.get(turno), banca, lanzamiento)==true){
                        casillacomprada.comprarCasilla(jugadores.get(turno), banca);
                    }
                    else{
                        System.out.println("Esta casilla no se puede comprar");
                    }
                break;
            }
        
        return;
    }

    public void hipotecar(String nombre){

        Casilla casilla = tablero.encontrar_casilla(nombre);
        if(casilla==null){
            System.out.println("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno); //obter xogador actual
        
            if(!casilla.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                System.out.println("El jugador no puede hipotecar la casilla porque no es de su propiedad");
                return;
            }
            else if(casilla.tieneEdificios()){
                System.out.println("El jugador tiene edificios en esta casilla, si desea hipotcarla debe venderlos primero");
            }
            else{
                casilla.hipotecarCasilla(jugadoractual, banca);
            }

        return;
    }

    public void deshipotecar(String nombre){

        Casilla casilla = tablero.encontrar_casilla(nombre);
        if(casilla==null){
            System.out.println("No se ha encontrado la casilla");
            return;
        }

        Jugador jugadoractual = jugadores.get(turno); //obter xogador actual
        
            if(!casilla.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
                System.out.println("El jugador no puede deshipotecar la casilla porque no es de su propiedad");
            }
            else if(!casilla.getEstarHipotecada()){
                System.out.println("La casilla no esta hipotecada, no puede deshipotecarse");
            }else if(casilla.getHipotecaValor() * 1.1f > jugadoractual.getFortuna()){
                System.out.println("El jugador no tiene suficiente dinero para deshipotecar la casilla " + nombre);
            }
            else{
                casilla.deshipotecarCasilla(jugadoractual, banca);
            }

        return;
    }


    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    public void salirCarcel(boolean pagado) {
        int tirada = dado1.getValor() + dado2.getValor();
            if(pagado==true){
                if(jugadores.get(turno).getAvatar().getLugar().evaluarCasilla(jugadores.get(turno), banca, tirada) == true){
                    System.out.println(jugadores.get(turno).getNombre() + " paga " + 0.25*SUMA_VUELTA + " y sale de la cárcel. Puede lanzar los dados");
                    jugadores.get(turno).pagar(0.25f*SUMA_VUELTA);
                    jugadores.get(turno).EstadisticaTasasImpuesto(0.25f*SUMA_VUELTA);
                    lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0);
                    return;
                }
                else System.out.println("El jugador no puede permitirse salir de la carcel");
            }
            else{
                if(jugadores.get(turno).getAvatar().getLugar().evaluarCasilla(jugadores.get(turno), banca, tirada) == true){
                    lanzamientos = 0;
                    tirado = false;
                    jugadores.get(turno).setEncarcel(false);
                    jugadores.get(turno).setTiradasCarcel(0); 
                    System.out.println("Por lo tanto, ha sido obligado a pagar la fianza para salir");
                    return;
                }
                else{
                    System.out.println("El jugador no puede permitirse salir de la carcel y ya ha agotado todos sus intentos. Por lo tanto, " + jugadores.get(turno).getNombre() + " se queda en bancarrota y pierde la partida.");
                    eliminarJugador(jugadores.get(turno)); //eliminamos o xogador porque está en bancarrota
                    
                }
            }
        return;
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        banca.listarPropiedadesenVenta();

        return;
    }
    
    public void Edificar(String tipo){
        Jugador jugadoractual = jugadores.get(turno);
        Casilla casillaActual = jugadoractual.getAvatar().getLugar();
        if(!tipo.equalsIgnoreCase("Casa") && !tipo.equalsIgnoreCase("Hotel")&&!tipo.equalsIgnoreCase("Pista")&&!tipo.equalsIgnoreCase("Piscina")){
            System.out.println("Las edificaciones pueden ser del tipo: 'casa' 'hotel' 'pista de deporte' 'piscina'");
        }
        if(tipo.equalsIgnoreCase("pista")){
            tipo = "pista de deporte";
        }

        if(casillaActual.getDuenho().getNombre().equalsIgnoreCase(jugadoractual.getNombre())){
            boolean resultado = casillaActual.Edificar(jugadoractual, tipo);
            if(resultado == false){
                System.out.println("No se ha podido edificar");
            }   
        }
        else{
            System.out.println("El jugador no puede edificar en " + casillaActual.getNombreSinColor() + "porque no es el dueño de la casilla");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {

        if(jugadores.isEmpty()){
            System.out.println("\nNo existe ningun jugador.");
        }else{
            boolean listar = true;
            System.out.println("\nJugadores: ");
            for(short i = 0; i< jugadores.size(); i++){

                Jugador jugador = jugadores.get(i);
                
                //creamos un arreglo para aprovechar la funcion descjugador que pide un string
                String[] partes = new String[3];
                partes[0] = "listar"; //valdria cualquier otro valor
                partes[1] = "jugador"; //valdria cualquier otro valor
                partes[2]= jugador.getNombre(); //nombre del jugador
    
                descJugador(partes, listar);
    
            }
        }

        return;
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {

        if(jugadores.isEmpty()){
            System.out.println("\nNo existe ningun jugador: ");
        }else{
            System.out.println("\nAvatares: ");
            for(short i = 0; i< jugadores.size();i++){
                Jugador jugador= jugadores.get(i);
    
                descAvatar(jugador.getAvatar().getId()); //Aprovechamos la funcion descAvatar que unicamente pide el id del avatar
            }
        }

        return;
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno(boolean vertablero) {
        Jugador jugador = jugadores.get(turno);

        if((tirado == false)&&(jugador.getEncarcel()==false)){
            System.out.println("Aún tienes una tirada, aprovéchala!");
            return;
        }
        else if((jugador.getAvatar().getExtras()!=0)&&(jugador.getEncarcel()==false)&&(jugador.getBloqueado()!=2)){
            System.out.println("Aún no has agotado tus movimientos, sigue avanzando lanzando el dado!");
            return;
        }

        //Resetea algunos de los atributos del jugador cuyo turno se ha acabado antes de cambiar
        jugador.resetearDobles();
        jugador.setComprado(false);
        if(jugador.getBloqueado()<0) jugador.setBloqueado(0);
        jugador.setPrimeraDobles(false);
        jugador.getAvatar().setExtras(0);
        jugador.setModo(0);

        turno++;
        if(turno > jugadores.size() -1){
            turno = 0;
        }
        
        lanzamientos = 0;
        tirado = false;
        solvente = true;
        if(vertablero==true) tablero.imprimirTablero();
        return;
    }


    private void crearJugador(String comando){

        //EXTRAEMOS EL NOMBRE Y EL AVATAR
        String[] partes = comando.split(" "); //Divide comando en partes, usa o espacio como delimitador

        if(jugadores.size() > 5){
            System.out.println("\nNo puede haber mas de 6 jugadores");
        }
        else if(partes.length != 4){
            System.out.println("\nFormato incorrecto. Usa: crear jugador 'nombre' 'tipo de avatar' ");
        }else{
            String nombre = partes[2]; //nombre
            String avatar = partes [3]; //tipo avatar
    
            Jugador nuevoJugador = new Jugador(nombre, avatar, tablero.encontrar_casilla("Salida"), avatares);       
            jugadores.add(nuevoJugador);
            avatares.add(nuevoJugador.getAvatar());
            tablero.getPosiciones().get(3).get(10).anhadirAvatar(nuevoJugador.getAvatar());

            tablero.imprimirTablero();

            System.out.println("\nNombre: " + nombre);
            System.out.println("Avatar: " + nuevoJugador.getAvatar().getId());
    
        }
        return;
    }


    /* NUNCA LA USAMOS PERO LA COMENTO POR SI ACASO
    private void mostrarJugadorTurno(){    
        if (jugadores.isEmpty()){
            System.out.println("\nNo hay jugadores en la partida.");
        }else{
            Jugador jugadorEnTurno = jugadores.get(turno);
            String nombre = jugadorEnTurno.getNombre();
            String avatar = jugadorEnTurno.getAvatar().getId();
    
            System.out.println("\nnombre: "+nombre);
            System.out.println("avatar: " + avatar);
        }
        return;
    }*/

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

    public void setLanzamientos(int lanzamientos){
        this.lanzamientos = lanzamientos;
    }

    public int getLanzamientos(){
        return this.lanzamientos;
    }

    public void eliminarJugador(Jugador jugadorEliminado){

        //comprobamos si el jugador no esta en la lista de jugadores
        if(!jugadores.contains(jugadorEliminado)){
            System.out.println("El jugador no está en partida, no se puede eliminar");
            return;
        }

        //Transferimos as propiedades do xogador eliminado a banca
        jugadorEliminado.transferirPropiedadesBanca(banca);

        //comprobar  si o xogador eliminado e o o que ten o turno, Volvemos ao turno anterior, para o acabar o turno que funcione
        if(jugadores.get(turno).equals(jugadorEliminado)){
            /*if(turno == jugadores.size()-1){
                turno = 0;
            }
            else{
                turno = (turno + 1) %jugadores.size();
            }*/
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
            System.out.println("El jugador ha sido eliminado y la partida continua.");
            return;
        }
    }
/*

    public void suerteComunidad(Jugador jugador){
        Scanner scan = new Scanner(System.in);
        int carta;
                
            System.out.println("");
            System.out.println("  -----------   ---------   -----------  -----------  -----------  -----------");
            System.out.println("  | 1       |  | 2       |  | 3       |  | 4       |  | 5       |  | 6       |");
            System.out.println("  |         |  |         |  |         |  |         |  |         |  |         |");
            System.out.println("  |         |  |         |  |         |  |         |  |         |  |         |");
            System.out.println("  |         |  |         |  |         |  |         |  |         |  |         |");
            System.out.println("  |       1 |  |       2 |  |       3 |  |       4 |  |       5 |  |       6 |");
            System.out.println("  -----------  -----------  -----------  -----------  -----------  -----------");
            System.out.println("");
            
            do{System.out.println("Escoja una carta (1-6): ");
            carta = scan.nextInt();
            }while(carta<1||carta>6);
            int id;
            Casilla aux;
            if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Suerte")){
                id=carta;
                carta=carta-1; //Borrar luego
                //COMENTADO PARA PODER LLAMAR A CARTA QUE QUERAMOS EN EL EXAMEN
                //Collections.shuffle(baraja.getSuerte());
                //id = baraja.getSuerte().get(carta).getId();
                switch (id) { //Todos los posibles casos, dependiendo del id de la carta escogida al azar
                        case 1:
                            aux = tablero.getPosiciones().get(3).get(5);
                            jugador.getAvatar().moverAvatar(tablero.getPosiciones(), aux, true);//Va a trans1 pasando por la salida
                            tablero.imprimirTablero();
                            break;
                        
                         case 2:
                            aux = tablero.getPosiciones().get(0).get(6);
                            jugador.getAvatar().moverAvatar(tablero.getPosiciones(), aux, false);//Va a Solar15 sin pasar por la salida
                            tablero.imprimirTablero();
                            break;

                         case 3:
                            jugador.sumarFortuna(5000000); //Cobras por vender billete
                            jugador.EstadisticaPremios(5000000);
                            break;

                         case 4:
                            aux = tablero.getPosiciones().get(3).get(4);
                            jugador.getAvatar().moverAvatar(tablero.getPosiciones(), aux, true);//Va a solar3 pasando por la salida
                            tablero.imprimirTablero();
                            break;

                        case 5:
                            jugador.encarcelar(tablero.getPosiciones()); //Cri cri criminal
                            acabarTurno(true);
                            break;

                         case 6:
                            jugador.sumarFortuna(1000000);
                            jugador.EstadisticaPremios(1000000); //Lotería
                            break;
                    
                        default:
                            System.out.println("Ha habido un error");
                            break;
                    }
            System.out.println("La carta lee:");
            baraja.imprimeMensaje(carta, 0);
            }
            else{
                id=carta;
                carta=carta-1;
                //COMENTADO PARA PODER LLAMAR A CARTA QUE QUERAMOS EN EL EXAMEN
                //Collections.shuffle(baraja.getComunidad());
                //id = baraja.getComunidad().get(carta).getId();
                switch (id) {
                        case 1:
                        if(jugador.getFortuna()>=500000){
                                jugador.pagar(500000); //Pagas por un balneario, va a parking
                                banca.sumarFortuna(500000);
                                jugador.EstadisticaTasasImpuesto(500000);
                        }
                        else bancarrota(jugador, 500000f);
                            break;
                        
                         case 2:
                            jugador.encarcelar(tablero.getPosiciones()); //Cri cri criminal
                            acabarTurno(true);
                            break;

                         case 3:
                            aux = tablero.getPosiciones().get(3).get(10);
                            jugador.getAvatar().moverAvatar(tablero.getPosiciones(), aux, true);//Va a Salida cobrando
                            tablero.imprimirTablero();
                            break;

                         case 4:
                            jugador.sumarFortuna(2000000); //Electrica
                            jugador.EstadisticaPremios(2000000);
                            break;

                        case 5:
                        if(jugador.getFortuna()>=1000000){
                            jugador.pagar(1000000); //Paga por invitar, va a parking
                            banca.sumarFortuna(1000000);
                            jugador.EstadisticaTasasImpuesto(1000000);
                        }
                        else bancarrota(jugador, 1000000f);
                            break;
                            
                        case 6:
                        if(jugador.getFortuna()>=200000*(jugadores.size()-1)){
                            int contador =0;
                            for (Jugador player : jugadores) {
                                if(!player.equals(jugadores.get(turno))){
                                    jugador.pagar(200000); //Pagas a cada jugador 
                                    player.sumarFortuna(200000);
                                    contador++;
                                }
                            }
                            jugador.EstadisticaTasasImpuesto(200000 * contador);
                        }
                        else bancarrota(jugador, 200000f);

                            break;
                    
                        default:
                            break;
                    }
            System.out.println("La carta lee:");
            //baraja.imprimeMensaje(carta, 1);
            baraja.imprimeMensaje(carta, 1);
            }    
            
    }
*/
    public void venderEdificio(String tipo, String nombre, int cantidad){
        Jugador jugador = jugadores.get(turno);

        //Verificar si a casilla existe
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if(casilla==null){
            System.out.println("No se ha encontrado la casilla");
            return;
        }
        
        if(tipo.equalsIgnoreCase("pista")){
            tipo = "Pista de Deporte";
        }

        //Verificar el tipo de edificio
        if (!tipo.equalsIgnoreCase("casa") && !tipo.equalsIgnoreCase("hotel") && !tipo.equalsIgnoreCase("piscina") && !tipo.equalsIgnoreCase("pista de deporte")) {
            System.out.println("El tipo de edificio indicado no es válido");
            return;
        }
        casilla.venderEdificios(cantidad, tipo, banca, jugador);
        
    }

    public void listarEdificios(){
        Jugador jugador = jugadores.get(turno);
        Casilla casillaActual = jugador.getAvatar().getLugar();

        if(casillaActual.getTipo().equalsIgnoreCase("Solar")){
            casillaActual.ListarEdificios();

        }
        else{
            System.out.println("No se puede listar los edificios de esta casilla porque no es de tipo Solar");
        }
    }

    public void listarEdificiosPorGrupo(String colorgrupo){
        Casilla casilla = new Casilla();
        
        if(colorgrupo.equalsIgnoreCase("black")){
             casilla = tablero.encontrar_casilla("Solar1");
        }
        else if(colorgrupo.equalsIgnoreCase("cyan")){
             casilla = tablero.encontrar_casilla("Solar3");
        }
        else if(colorgrupo.equalsIgnoreCase("purple")){
             casilla = tablero.encontrar_casilla("Solar6");
        }
        else if(colorgrupo.equalsIgnoreCase("naranja")){
             casilla = tablero.encontrar_casilla("Solar9");
        }
        else if(colorgrupo.equalsIgnoreCase("red")){
             casilla = tablero.encontrar_casilla("Solar12");
        }
        else if(colorgrupo.equalsIgnoreCase("marron")){
             casilla = tablero.encontrar_casilla("Solar15");
        }
        else if(colorgrupo.equalsIgnoreCase("green")){
             casilla = tablero.encontrar_casilla("Solar18");
        }
        else if(colorgrupo.equalsIgnoreCase("blue")){
             casilla = tablero.encontrar_casilla("Solar21");
        }
        else{
            System.out.println("El color especificado no existe");
            return;
        }

        casilla.getGrupo().listarEdificiosGrupo();
        
    }

    public void estadisticasJugador(String nombre){

        int contador = 0;

        for(Jugador jugador: jugadores){
            if(jugador.getNombre().equalsIgnoreCase(nombre)){
                jugador.mostrarEstadisticas();
                contador++;
            }
        }

        if(contador==0){
            System.out.println("No se ha reconocido ningún jugador con ese nombre");
        }
    }
    
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


                if(casilla.getRentabilidad() > cant){
                    nombre = casilla.getNombre();
                    cant = casilla.getRentabilidad();
                }

                if(casilla.getVisitas() > cant2){
                    nombre2 = casilla.getNombre();
                    cant2 = casilla.getVisitas();
                }

                if(casilla.getTipo().equalsIgnoreCase("Solar")){
                    if(casilla.getGrupo().getRentabilidad() > cantGrupo){
                        nombreGrupo = casilla.getGrupo().getColor();
                        cantGrupo = casilla.getGrupo().getRentabilidad();
                    }
                }
                
            }
        }
        System.out.println("Casilla mas rentable: " + nombre + Valor.WHITE);
        System.out.println("Grupo mas rentable: " + nombreGrupo + Valor.WHITE);
        System.out.println("Casilla mas visitada: " + nombre2 + Valor.WHITE);

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
            for(Casilla casilla: jugador.getPropiedades()){
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

        System.out.println("Jugador que mas vueltas al tablero dio: " + nombre3);
        System.out.println("Jugador que mas veces tiro los dados: " + nombre2);
        System.out.println("Jugador en cabeza: " + nombre);

        return;
    }
/*
    public Baraja getBaraja(){
        return this.baraja;
    }*/




    public void trato(String jugadorNombre){
        

        for(Jugador jugador : jugadores){
            if(jugador.getNombre().equalsIgnoreCase(jugadorNombre)){
                jugador
            }
        }
    }
}