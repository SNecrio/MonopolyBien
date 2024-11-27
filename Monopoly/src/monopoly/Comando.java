package monopoly;

//Define los métodos que deben implementarse en cualquier clase que la implemente (Juego)
public interface Comando {
    
    void crearJugador(String comando);

    void iniciarPartida();

    void listarJugadores(String comando);

    void listarEdificiosCasilla(String comando);

    void listarEdificiosGrupo(String comando);

    void listarAvatares(String comando);

    void listarEnVenta(String comando);

    void consultarAvatar(String comando);

    void trucados(String comando);

    void pobre(String comando);

    void lanzarDados(String comando);

    void acabarTurno(String comando);

    void pagarFianza(String comando);

    void verTablero(String comando);

    void describirJugador(String comando);

    void describirAvatar(String comando);

    void describirCasilla(String comando);

    void estadisticasPartida(String comando);

    void estadisticasJugador(String comando);

    void comprarCasilla(String comando);

    void hipotecarCasilla(String comando);

    void deshipotecarCasilla(String comando);

    void edificar(String comando);

    void venderEdificio(String Comando);
 
}
