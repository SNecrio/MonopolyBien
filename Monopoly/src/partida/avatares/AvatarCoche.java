package partida.avatares;

import monopoly.*;
import partida.Jugador;

public class AvatarCoche extends Avatar {

    //Atributos
    private int extras; //Cuenta el numero de tiradas extra correspondiente a un tipo de avatar
    private int bloqueado; //Cuenta los turnos que un jugador no puede tirar
    private final ConsolaNormal consola;
    //private boolean dobles;

    //Constructor
    public AvatarCoche(){
    this.extras = 3;
    this.bloqueado = 0;
    this.consola = new ConsolaNormal();
    //this.dobles = false;
    }


/* 

    int casillasTotal = dado1 + dado2;

    //COMPROBAR EN CARCEL

    //Comprueba si son dobles y cuantos dobles lleva
    if(dado1==dado2){
        jugador.incrementarDobles();
        if(jugador.getDobles()==3){
            jugador.encarcelar(tablero.getPosiciones());
            tablero.imprimirTablero();
            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);
            consola.imprimir("Oh no! Has lanzado dobles 3 veces consecutivas, vas a la cárcel");
            //acabarTurno(false);
            return;
        }
    }

    jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);

    //Comprueba si se puede realizar la acción de la casilla.
    solvente = jugador.getAvatar().getLugar().EvaluarCasilla(jugador, banca, casillasTotal, tablero);

    //Atributos estadísticos
    //jugador.getAvatar().getLugar().sumarVisitas(1);
    //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);

    tablero.imprimirTablero();

    consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);

*/

    @Override
    public void moverEnAvanzado(int dado1, int dado2, Jugador jugador, Tablero tablero, Jugador banca){
        int casillasTotal = dado1 + dado2;

        if(casillasTotal<=4){

            //Se mueve el valor obtenido para atrás
            casillasTotal = -1*casillasTotal;
            jugador.setBloqueado(2);
            //Se mueve el avatar del jugador el numero de casillas
            jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
            tablero.imprimirTablero();
            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2 + "\nComo es un 4 o menos, retrocede el valor obtenido y pierde sus 2 proximos turnos.");
            extras = 3; //Vuelve a poner extras a 3, para la ronda en la que el jugador se desbloquee
        }
        else{

            if(extras==0){
                if(dado1==dado2 && jugador.getDobles()==0){
                    extras+=1;
                    jugador.setDobles(1);}
            }

            jugador.getAvatar().moverAvatar(tablero.getPosiciones(),casillasTotal);
            //Comprueba si se puede realizar la acción de la casilla.

            jugador.getAvatar().setSolvente(jugador.getAvatar().getLugar().EvaluarCasilla(jugador, banca, casillasTotal, tablero));

            //Atributos estadísticos
            //jugador.getAvatar().getLugar().sumarVisitas(1);
            //jugador.getAvatar().getLugar().sumarJugadoresVisitantes(jugador);

            tablero.imprimirTablero();

            consola.imprimir("El jugador " + jugador.getAvatar().getId() + " lanzó: " + dado1 + " y " + dado2);

            extras-=1;

        }


        /* 
        if(extras!=0){
            extras-=1; //Cada turno extra que pase, tenemos uno menos
        }
        else{
            if(dado1==dado2) extras+=1;  //Si en nuestra cuarta tirada sacamos dobles, podremos volver a tirar una vez (si>=4)

            extras-=1;
        }

*/

    }



    /* 
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
}*/

    @Override
    public int getExtras(){
        return this.extras;
    }

    @Override
    public void setExtras(int extras){
        this.extras=extras;
    }

    @Override
    public int getBloqueado(){
        return this.bloqueado;
    }

    @Override
    public void setBloqueado(int block){
        this.bloqueado=block;
    }

    /*
    @Override
    public boolean getDobles(){
        return this.dobles;
    }

    @Override
    public void setDobles(boolean dobles){
        this.dobles=dobles;
    }
        */


}