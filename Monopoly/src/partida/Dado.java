package partida;
import java.util.Random;

public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        Random ran = new Random();
        valor = ran.nextInt(6)+1;
        return valor; //devolvemos o valor obtido
    }

    public void setValor(int valor){
        this.valor=valor;
    }

    public int getValor(){
        return this.valor;
    }

}
