package monopoly;

import java.util.ArrayList;

public class Baraja {
    
    //Atributos:
    private ArrayList<Carta> suerte; //Array que contiene todas las cartas de suerte
    private ArrayList<Carta> comunidad; //Array que contiene todas las cartas de suerte
    private ConsolaNormal consola;

    //Constructores:
    public Baraja() {
    //damoslle valores por defecto
    this.suerte = cartasSuerte();
    this.comunidad = cartasComunidad();
    consola = new ConsolaNormal();
    }

    //Genera las cartas que se usarán en el juego, tanto las de suerte como las de comunidad
    public ArrayList<Carta> cartasSuerte(){
        ArrayList<Carta> luck = new ArrayList<>(6);
        Carta carta = new Carta(1, "Ve a Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        luck.add(carta);
        carta = new Carta(2, "Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida.");
        luck.add(carta);
        carta = new Carta(3, "Vendes tu billete de avión para Solar17 en una subasta por internet. Cobra 500.000 euros");
        luck.add(carta);
        carta = new Carta(4, "Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        luck.add(carta);
        carta = new Carta(5, "Los acreedores te persiguen por impago. Ve a la Cárcel sin pasar por la casilla de Salida.");
        luck.add(carta);
        carta = new Carta(6, "¡Has ganado la lotería! Recibe 1.000.000 de euros");
        luck.add(carta);
        return luck;
    }

    public ArrayList<Carta> cartasComunidad(){
        ArrayList<Carta> box = new ArrayList<>(6);
        Carta carta = new Carta(1, "Paga 500.000 por un fin de semana en un balneario de 5 estrellas.");
        box.add(carta);
        carta = new Carta(2, "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        box.add(carta);
        carta = new Carta(3, "Colócate en la casilla de Salida. Cobra la cantidad habitual.");
        box.add(carta);
        carta = new Carta(4, "Tu compañía de Internet obtiene beneficios. Recibe 2000.000 euros");
        box.add(carta);
        carta = new Carta(5, "Paga 1.000.000 de euros por invitar a todos tus amigos a un viaje a Solar14.");
        box.add(carta);
        carta = new Carta(6, "Alquilas a tus comopañeros una villa en Solar7 durante una semana. Paga 200.000 euros a cada jugador");
        box.add(carta);
        return box;
    }

    public ArrayList<Carta> getSuerte(){
        return this.suerte;
    }

    public ArrayList<Carta> getComunidad(){
        return this.comunidad;
    }

    public void imprimeMensaje(int num, int tipo) {
        String  mensaje = "";
        if(tipo==0) mensaje += suerte.get(num).getMensaje();
        else mensaje += comunidad.get(num).getMensaje();
        consola.imprimir(mensaje);
    }
}
