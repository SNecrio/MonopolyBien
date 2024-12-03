package monopoly.casillas;

import monopoly.ConsolaNormal;
import monopoly.Tablero;
import partida.avatares.*;
import partida.Jugador;
import java.util.Collections;
import java.util.ArrayList;



public class CasillaAccion extends Casilla{

    //Atributos
    private ConsolaNormal consola;

    //CONSTRUCTOR
    public CasillaAccion(){
        super();
        this.consola = new ConsolaNormal();
    }

    public CasillaAccion(String nombre, int posicion){
        super(nombre, "Accion", posicion);
        this.consola = new ConsolaNormal();
    }

    public void describirEspecifico(StringBuilder info){
        listarAvatares(info);
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> lista){

        int carta;

        consola.imprimir("");
        consola.imprimir("  -----------   ---------   -----------  -----------  -----------  -----------");
        consola.imprimir("  | 1       |  | 2       |  | 3       |  | 4       |  | 5       |  | 6       |");
        consola.imprimir("  |         |  |         |  |         |  |         |  |         |  |         |");
        consola.imprimir("  |         |  |         |  |         |  |         |  |         |  |         |");
        consola.imprimir("  |         |  |         |  |         |  |         |  |         |  |         |");
        consola.imprimir("  |       1 |  |       2 |  |       3 |  |       4 |  |       5 |  |       6 |");
        consola.imprimir("  -----------  -----------  -----------  -----------  -----------  -----------");
        consola.imprimir("");

        do{
            consola.imprimir("Escoja una carta (1-6): ");
            carta = consola.leerNumero();
        }while(carta<1||carta>6);
        //comenteino pa que fora 
        /*if(actual.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Suerte")) return suerte(actual, tablero, carta, banca);
        else return comunidad(actual, tablero, carta, banca,);*/
        return true; //QUITALO DESPOIS
        }
    
    public boolean comunidad(Jugador jugador, Tablero tablero, int carta, Jugador banca, ArrayList<Jugador> lista){
        int id;
        Casilla aux;

        // Cartas fijas
        // id=carta;
        // carta=carta-1;

        // Cartas barajadas
        Collections.shuffle(tablero.getBaraja().getComunidad());
        id = tablero.getBaraja().getComunidad().get(carta).getId();

        switch (id) {
            case 1:
                if(jugador.getFortuna()>=500000){
                    jugador.pagar(500000); //Pagas por un balneario, va a parking
                    banca.sumarFortuna(500000);
                    jugador.EstadisticaTasasImpuesto(500000);
                }
                else return false;
                break;
                        
            case 2:
                jugador.encarcelar(tablero.getPosiciones()); //Cri cri criminal
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
                }else return false;
                break;
                            
            case 6:
                if(jugador.getFortuna()>=200000*(lista.size()-1)){
                    int contador =0;
                    for (Jugador player : lista) {
                        if(!player.equals(jugador)){
                            jugador.pagar(200000); //Pagas a cada jugador 
                            player.sumarFortuna(200000);
                            contador++;
                        }
                    }
                    jugador.EstadisticaTasasImpuesto(200000 * contador);
                }
                else return false;
                    break;
                
            default:
                consola.imprimir("Ha habido un error");
                    break;
        }
        consola.imprimir("La carta lee:");
        tablero.getBaraja().imprimeMensaje(carta, 1);       
        return true;
    }


    public boolean suerte(Jugador jugador, Tablero tablero, int carta, Jugador banca){
        int id;
        Casilla aux;

        // Cartas fijas
        // id=carta;
        // carta=carta-1;

        // Cartas barajadas
        Collections.shuffle(tablero.getBaraja().getSuerte());
        id = tablero.getBaraja().getSuerte().get(carta).getId();
                
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
                    break;

                case 6:
                    jugador.sumarFortuna(1000000);
                    jugador.EstadisticaPremios(1000000); //Lotería
                    break;
                    
                default:
                    consola.imprimir("Ha habido un error");
                            break;
            }
            consola.imprimir("La carta lee:");
            tablero.getBaraja().imprimeMensaje(carta, 0);
            return true;
         
    }

}

