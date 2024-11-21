package monopoly;

public class Carta{

    //Atributos:
    private int id; //Valor que diferencia a unas cartas de otras
    private String mensaje; //Texto de la carta

    //Constructores:
    public Carta(int num, String mensaje) {
        //damoslle valores por defecto
        this.id = num;
        this.mensaje = mensaje;                
    }

    public String getMensaje(){
        return this.mensaje;
    }

    public int getId(){
        return this.id;
    }
    
}
