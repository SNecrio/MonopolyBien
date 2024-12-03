package monopoly;

import partida.*;
import monopoly.casillas.*;
import java.util.Random;

public class Trato{

    //El oriinario es aquel que propone el trato, el destinatario quien lo recibe
    //Si los atributos estan en sus valores por defecto significa que no se usan en el trato
    Jugador originario = null;
    Jugador destinatario = null;
    CasillaPropiedad propOrig = null;
    CasillaPropiedad propDest = null;
    float dineroOrig = 0;
    float dineroDest = 0;
    int id = -1;

    public Trato(){}

    //Cambiar propiedad por propiedad
    public Trato(CasillaPropiedad casOr, CasillaPropiedad casDes){
        this.propOrig = casOr;
        this.propDest = casDes;
        this.originario = casOr.getDuenho();
        this.destinatario = casDes.getDuenho();

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;
    }

    //Vender propiedad por dinero
    public Trato(CasillaPropiedad casOr, Jugador jugDest, float cantidad){
        this.propOrig = casOr;
        this.originario = casOr.getDuenho();
        this.destinatario = jugDest;
        this.dineroDest = cantidad;
        
        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;
    }

    //Comprar propiedad por dinero
    public Trato(Jugador jugOr, float cantidad, CasillaPropiedad casDes){
        this.propDest = casDes;
        this.originario = jugOr;
        this.destinatario = casDes.getDuenho();
        this.dineroOrig = cantidad;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;
    }

    //Cambiar propiedad por dinero y propiedad del otro
    public Trato(CasillaPropiedad casOr, CasillaPropiedad casDes, float cantidad){
        this.propOrig = casOr;
        this.propDest = casDes;
        this.originario = casOr.getDuenho();
        this.destinatario = casDes.getDuenho();
        this.dineroDest = cantidad;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;
    }

    //Cambiar propiedad y dinero por propiedad del otro
    public Trato(CasillaPropiedad casOr, float cantidad, CasillaPropiedad casDes){
        this.propOrig = casOr;
        this.propDest = casDes;
        this.originario = casOr.getDuenho();
        this.destinatario = casDes.getDuenho();
        this.dineroOrig = cantidad;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;
    }

    public Jugador getOrigen(){
        return this.originario;
    }

    public Jugador getDestino(){
        return this.destinatario;
    }

    public void Aceptar(){
        
        //Si el originario ofrecio una propiedad, cambia de dueño
        if(this.propOrig != null){
            originario.eliminarPropiedad(this.propOrig);
            destinatario.anhadirPropiedad(this.propOrig);
        }
        
        //Si el destinatario fue pedido una propiedad, cambia de dueño
        if(this.propDest != null){
            destinatario.eliminarPropiedad(this.propDest);
            originario.anhadirPropiedad(this.propDest);
        }

        //Suma la cantidad ofertada a los demas, siempre suma porque si no se oferta es 0
        this.originario.sumarFortuna(-this.dineroOrig);
        this.destinatario.sumarFortuna(this.dineroOrig);

        this.destinatario.sumarFortuna(-this.dineroDest);
        this.originario.sumarFortuna(this.dineroDest);
    }

    public void Listar(){

        


    }

}