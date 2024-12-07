package monopoly;
import partida.*;
import excepcions.*;

//Define los métodos que deben implementarse en cualquier clase que la implemente (Juego)
public interface Comando {
    
    void crearJugador(String comando);

    void iniciarPartida();

    void listarJugadores(String comando) throws ExcepcionComando, ExcepcionJugador;

   void listarEdificiosCasilla() throws ExcepcionTipoSolar;

    void listarEdificiosGrupo(String comando) throws ExcepcionComando;

    void listarAvatares() throws ExcepcionJugador;
    void listarEnVenta();

    void consultarAvatar();

    //void trucados(String comando);

    //void pobre(String comando);

    void lanzarDados(Jugador jugador);

    void lanzarDados(Jugador jugador, int dado1, int dado2);  //Trucados

    void acabarTurno(boolean vertablero);

    void pagarFianza();

    void verTablero();

    void describirJugador(String comando);

    void describirAvatar(String comando);

    void describirCasilla(String comando);

     void estadisticasPartida(String mensaje);

     void estadisticasJugador(String comando) throws ExcepcionComando, ExcepcionJugadorIncorrecto;

    void comprarCasilla(String comando);

    void hipotecarCasilla(String comando);

    void deshipotecarCasilla(String comando);

    void edificar(String comando) throws ExcepcionComando;
    void venderEdificio(String comando) throws ExcepcionEdificar; 
}
