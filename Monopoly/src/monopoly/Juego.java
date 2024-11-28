package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;  // CUANDO SE DESCOMNTEN LAS CARTAS SE NECESITA
import static monopoly.Valor.SUMA_VUELTA;
import partida.*;
import monopoly.casillas.*;
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
    private Baraja baraja;
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
        this.baraja = new Baraja();
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

        if(!tipo.equalsIgnoreCase("Casa") && !tipo.equalsIgnoreCase("Hotel")&&!tipo.equalsIgnoreCase("Pista")&&!tipo.equalsIgnoreCase("Piscina")){
            consola.imprimir("Las edificaciones pueden ser del tipo: 'casa' 'hotel' 'pista de deporte' 'piscina'"); //EXCEPCION
        }
        if(tipo.equalsIgnoreCase("pista")){
            tipo = "pista de deporte";
        }

        if(casillaActual instanceof PropiedadSolar){
            PropiedadSolar solar = (PropiedadSolar)casillaActual;
            solar.Edificar(jugadoractual, null, tipo); //MIRAR AQUI COMO HAI QUE FACER MANDANDOLLE O EDIFICIO
        }

    }

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


        //TENGO QUE METER TIRAR EN CÁRCEL



        //Avance en modo simple
        if(jugador.getModo()==false){
            jugador.getAvatar().moverEnBasico(dado1.getValor(), dado2.getValor(), jugador, tablero, banca);
            if(dado1.getValor()!=dado2.getValor() || jugador.getEncarcel()==true) tirado = true;
            else consola.imprimir("Felicidades! Has lanzado dobles, tienes otro lanzamiento!");
        }
        //Avance en modo avanzado
        else {
            jugador.getAvatar().moverEnAvanzado(dado1.getValor(), dado2.getValor(), jugador, tablero, banca);
            if(jugador.getEncarcel()==true || jugador.getAvatar().getExtras()<0 || jugador.getDobles()==1) tirado = true;
        }

        //Determina que se imprimirá en cada casilla

        if(jugador.getAvatar().getBloqueado()==0){

         //Si es una casilla de su propiedad
         if(jugador == jugador.getAvatar().getLugar().getDuenho()){
            consola.imprimir(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + (dado1.getValor()+dado2.getValor()) + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de su propiedad.");
            }
        else if(banca == jugador.getAvatar().getLugar().getDuenho()){
            consola.imprimir(
            "El avatar "+ jugador.getAvatar().getId() + " avanza " + (dado1.getValor()+dado2.getValor()) + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE + 
            ". Es una casilla de la banca.");   
            }

        //Si es de otro jugador
        else{
            float alquiler = jugador.getAvatar().getLugar().calcularAlquiler(jugador, (dado1.getValor()+dado2.getValor()));
            consola.imprimir( "El avatar "+ jugador.getAvatar().getId() + " avanza " + (dado1.getValor()+dado2.getValor()) + " posiciones, desde " + 
            casillaAnterior + Valor.WHITE + " hasta " + jugador.getAvatar().getLugar().getNombre() + Valor.WHITE);
            //Si le puedes pagar, se paga automáticamente
            if(jugador.getAvatar().getSolvente() == true){
                consola.imprimir( "Se han pagado " + alquiler + " euros de alquiler al dueño de la casilla. ");
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
            if(!paraHipotecar.isEmpty() || !paraVenderEdificios.isEmpty()){
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, pero tiene solares que puede hipotecar o edificios que vender");
            }else{
                consola.imprimir("El jugador no puede permitirse pagar este alquiler, entra en bancarrota");
                eliminarJugador(jugador);
                acabarTurno(true);
                return;                        
            }

            Scanner scan = new Scanner(System.in);
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
                nombreVender = scan.nextLine();
                for(int i = 0; i < paraHipotecar.size(); i++){
                    Casilla prop = paraHipotecar.get(i);
                    if(prop.getNombreSinColor().equalsIgnoreCase(nombreVender)){
                        prop.hipotecarCasilla(jugador,this.banca);
                        paraHipotecar.remove(prop);
                        solv = (jugador.getFortuna() > dineroPagar);
                        if(solv){
                            jugador.sumarFortuna(-dineroPagar);
                            jugador.sumarGastos(dineroPagar);
                            consola.imprimir("El jugador pudo pagar sus deudas");
                        }
                    }else{
                        consola.imprimir("Casilla no encontrada, vuelva a introducir una");
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
            consola.imprimir("El jugador ha sido eliminado y la partida continua.");
        }
    }


    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno(boolean vertablero) {
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
        jugador.setPrimeraDobles(false);
        if(jugador.getAvatar().getBloqueado()!=0) jugador.getAvatar().setBloqueado(jugador.getAvatar().getBloqueado() - 1); 
        //jugador.getAvatar().setExtras(0);

        turno++;
        if(turno > jugadores.size() -1){
            turno = 0;
        }
        
        lanzamientos = 0;
        tirado = false;
        jugador.getAvatar().setSolvente(true);
        if(vertablero==true) tablero.imprimirTablero();
    }


    public void venderEdificio(String tipo, String nombre, int cantidad){
        Jugador jugador = jugadores.get(turno);

        //Verificar si a casilla existe
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if(casilla==null){
            consola.imprimir("No se ha encontrado la casilla");
            return;
        }
        
        if(tipo.equalsIgnoreCase("pista")){
            tipo = "Pista de Deporte";
        }

        //Verificar el tipo de edificio
        if (!tipo.equalsIgnoreCase("casa") && !tipo.equalsIgnoreCase("hotel") && !tipo.equalsIgnoreCase("piscina") && !tipo.equalsIgnoreCase("pista de deporte")) {
            consola.imprimir("El tipo de edificio indicado no es válido");
            return;
        }
        casilla.venderEdificios(cantidad, tipo, banca, jugador);
        
    }


}
