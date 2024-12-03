package monopoly;
import partida.*;

//Define los métodos que deben implementarse en cualquier clase que la implemente (Juego)
public interface Comando {
    
    void crearJugador(String comando);

    void iniciarPartida();

    void listarJugadores(String comando);

    void listarEdificiosCasilla();

    void listarEdificiosGrupo(String comando);

    void listarAvatares();

    void listarEnVenta();

    void consultarAvatar();

    //void trucados(String comando);

    //void pobre(String comando);

    void lanzarDados(Jugador jugador);
    public void lanzarDados(Jugador jugador, int dado1, int dado2);  //Trucados

    void acabarTurno(boolean vertablero);

    void pagarFianza();

    void verTablero();

    void describirJugador(String comando);

    void describirAvatar(String comando);

    void describirCasilla(String comando);

    void estadisticasPartida();

    void estadisticasJugador(String comando);

    void comprarCasilla(String comando);

    void hipotecarCasilla(String comando);

    void deshipotecarCasilla(String comando);

    void edificar(String comando);

    void venderEdificio(String comando);
 
}
