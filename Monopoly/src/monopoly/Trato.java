package monopoly;

import excepcions.ExcepcionCreacionTrato;
import java.util.Random;
import monopoly.casillas.*;
import partida.*;

public class Trato{

    //El originario es aquel que propone el trato, el destinatario quien lo recibe
    //Si los atributos estan en sus valores por defecto significa que no se usan en el trato
    private Jugador originario = null;
    private Jugador destinatario = null;
    private CasillaPropiedad prop1 = null;
    private CasillaPropiedad prop2 = null;
    private float cantidad = 0.0f;
    private int tipo;
    private int id = -1;
    private boolean aceptado;
    private String frase;

    private ConsolaNormal consola;

    public Trato(){}

    //Cambiar propiedad por propiedad
    public Trato(CasillaPropiedad propiedad1, CasillaPropiedad propiedad2, Jugador emisor, Jugador receptor) throws ExcepcionCreacionTrato{
        
        if(!propiedad1.getDuenho().getNombre().equals(emisor.getNombre())){
            throw new ExcepcionCreacionTrato("El emisor del trato no es el propietario de la propiedad impliada");
        }

        if(!propiedad2.getDuenho().getNombre().equals(receptor.getNombre())){
            throw new ExcepcionCreacionTrato("El receptor del trato no es el propietario de la propiedad implicada");
        }

        consola = new ConsolaNormal();

        this.prop1 = propiedad1;
        this.prop2 = propiedad2;
        this.originario = emisor;
        this.destinatario = receptor;

        this.tipo = 1;
        this.cantidad = 0;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;

        this.frase = "Hola "+receptor.getNombre()+". Soy el jugador " + emisor.getNombre() + "¿Te doy la propiedad "+propiedad1.getNombreSinColor()+ " a cambio de tu propiedad "+propiedad2.getNombreSinColor()+ "?";
        this.aceptado = false;
    }

    //Vender propiedad por dinero
    public Trato(CasillaPropiedad propiedad1, Jugador receptor, float cantidad, Jugador emisor) throws ExcepcionCreacionTrato{
        if(!propiedad1.getDuenho().getNombre().equalsIgnoreCase(emisor.getNombre())){
            throw new ExcepcionCreacionTrato("No eres el dueño de esta propiedad para involucrarla en el trato");
        }

        consola = new ConsolaNormal();

        this.prop1 = propiedad1;
        this.prop2 = null;
        this.cantidad = cantidad;
        this.originario = emisor;
        this.destinatario = receptor;
        this.tipo = 2;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;

        this.frase = "Hola "+receptor.getNombre()+". Soy el jugador " + emisor.getNombre() + "¿Te vendo la propiedad "+propiedad1.getNombreSinColor()+ " a cambio de esta cantidad de dinero: "+ cantidad +"?";
        this.aceptado = false;
    }

    //Comprar propiedad por dinero
    public Trato(Jugador emisor, Jugador receptor, float cantidad, CasillaPropiedad propiedad1) throws ExcepcionCreacionTrato{
        if(!propiedad1.getDuenho().getNombre().equalsIgnoreCase(receptor.getNombre())){
            throw new ExcepcionCreacionTrato("El receptor del trato no es el dueño de esta propiedad para involucrarla en el trato");
        }

        consola = new ConsolaNormal();
        
        this.prop1 = propiedad1;
        this.prop2 = null;
        this.originario = emisor;
        this.destinatario = receptor;
        this.tipo = 3;
        this.cantidad = cantidad;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;

        this.frase = "Hola "+receptor.getNombre()+". Soy el jugador " + emisor.getNombre() + "¿Te doy la cantidad de "+cantidad+ " a cambio de tu propiedad: "+ propiedad1.getNombreSinColor() +"?";
        this.aceptado = false;
    }

    //Cambiar propiedad por dinero y propiedad del otro
    public Trato(CasillaPropiedad propiedad1, CasillaPropiedad propiedad2, float cantidad, Jugador emisor, Jugador receptor) throws ExcepcionCreacionTrato{
        if (!propiedad1.getDuenho().getNombre().equalsIgnoreCase(emisor.getNombre())){
            throw new ExcepcionCreacionTrato("No eres el dueño de la casilla que involucra el trato");
        }
        if (!propiedad2.getDuenho().getNombre().equalsIgnoreCase(receptor.getNombre())){
            throw new ExcepcionCreacionTrato("El receptor del trato no es el dueño de la casilla que involucra el trato");
        }

        consola = new ConsolaNormal();

        this.prop1 = propiedad1;
        this.prop2 = propiedad2;
        this.originario = emisor;
        this.destinatario = receptor;
        this.cantidad = cantidad;
        this.tipo = 4;
        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;

        this.frase = "Hola "+receptor.getNombre()+". Soy el jugador " + emisor.getNombre() + "¿Te doy mi propiedad "+propiedad1.getNombreSinColor()+" a cambio de la cantidad de: "+cantidad+ " y de tu propiedad: "+ propiedad2.getNombreSinColor() +"?";
        this.aceptado = false;
    }

    //Cambiar propiedad y dinero por propiedad del otro
    public Trato(CasillaPropiedad propiedad1, float cantidad, CasillaPropiedad propiedad2, Jugador emisor, Jugador receptor) throws ExcepcionCreacionTrato{
        if (!propiedad1.getDuenho().getNombre().equalsIgnoreCase(emisor.getNombre())){
            throw new ExcepcionCreacionTrato("No eres el dueño de la casilla que involucra el trato");
        }
        if (!propiedad2.getDuenho().getNombre().equalsIgnoreCase(receptor.getNombre())){
            throw new ExcepcionCreacionTrato("El receptor del trato no es el dueño de la casilla que involucra el trato");
        }

        consola = new ConsolaNormal();

        this.prop1 = propiedad1;
        this.prop2 = propiedad2;

        this.originario = emisor;
        this.destinatario = receptor;

        this.cantidad = cantidad;
        this.tipo = 5;

        Random ran = new Random();
        this.id = ran.nextInt(99) + 1;

        this.frase = "Hola "+receptor.getNombre()+". Soy el jugador " + emisor.getNombre() + "¿Te doy mi propiedad "+propiedad1.getNombreSinColor()+"y la cantidad de: " +cantidad+" a cambio de tu propiedad: "+ propiedad2.getNombreSinColor() +"?";
        this.aceptado = false;
    }

    public Jugador getOrigen(){
        return this.originario;
    }

    public Jugador getDestino(){
        return this.destinatario;
    }

    public int getTipo(){
        return this.tipo;
    }

    public String getFrase(){
        return this.frase;
    }

    public boolean isAceptado() {
        return this.aceptado;
    }

    public int getId(){
        return this.id;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
 
    
    public void Aceptar() throws ExcepcionCreacionTrato{
        
        switch(tipo){
            case 1:
                //Actualizamos propietarios
                prop1.setDuenho(destinatario);
                prop2.setDuenho(originario);
                if(!(prop1.getDuenho().equals(originario) && prop2.getDuenho().equals(destinatario))){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque alguna de las propiedades ya no pertenece a alguno de los jugadores");
                }
                originario.getPropiedades().remove(prop1);
                destinatario.getPropiedades().remove(prop2);

                originario.getPropiedades().add(prop2);
                destinatario.getPropiedades().add(prop1);

                consola.imprimir("ACEPTADO TRATO: cambiar("+prop1.getNombreSinColor()+"):("+prop2.getNombreSinColor()+")");
                this.aceptado = true;
                break;

            
            // Tipo 2: Cambiar propiedad1 por cantidad
            case 2:

                if(destinatario.getFortuna()<this.cantidad){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque el jugador que lo acepta no tiene el dinero suficiente");
                }
                if(!prop1.getDuenho().equals(destinatario)){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque alguna de las propiedades ya no pertenece a alguno de los jugadores");
                }

                prop1.setDuenho(destinatario);

                originario.getPropiedades().remove(prop1);
                destinatario.getPropiedades().add(prop1);

                originario.sumarFortuna(cantidad);
                destinatario.sumarGastos(cantidad);
                destinatario.pagar(cantidad);

                consola.imprimir("ACEPTADO TRATO: cambiar("+prop1.getNombreSinColor()+"):("+cantidad+")");
                this.aceptado = true;
                break;

            //Tipo 3: Comprar propiedad por dinero
            case 3:

                if(originario.getFortuna()<this.cantidad){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque el jugador que lo acepta no tiene el dinero suficiente");
                }
                if(!prop1.getDuenho().equals(destinatario)){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque alguna de las propiedades ya no pertenece a alguno de los jugadores");
                }

                prop1.setDuenho(originario);

                originario.getPropiedades().add(prop1);
                destinatario.getPropiedades().remove(prop1);

                originario.pagar(cantidad);
                destinatario.sumarFortuna(cantidad);
                originario.sumarGastos(cantidad);
                consola.imprimir("ACEPTADO TRATO: cambiar("+cantidad+"):("+prop1.getNombreSinColor()+")");
                this.aceptado = true;
                break;
            
            //Tipo 4:Cambiar propiedad por dinero y propiedad del otro
            case 4:

                if(destinatario.getFortuna()<this.cantidad){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque el jugador que lo acepta no tiene el dinero suficiente");
                }
                if(!(prop1.getDuenho().equals(originario) && prop2.getDuenho().equals(destinatario))){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque alguna de las propiedades ya no pertenece a alguno de los jugadores");
                }

                prop1.setDuenho(destinatario);
                prop2.setDuenho(originario);
 
                originario.getPropiedades().remove(prop1);
                destinatario.getPropiedades().add(prop1);
 
                destinatario.getPropiedades().remove(prop2);
                originario.getPropiedades().add(prop2);
 
                
                originario.sumarFortuna(cantidad);
                destinatario.pagar(cantidad);

                consola.imprimir("ACEPTADO TRATO: cambiar("+prop1.getNombreSinColor()+"):("+prop2.getNombreSinColor()+","+cantidad+")");
                this.aceptado = true;
                break;

            //Tipo 5: cambiar propiedad y dinero por propiedad del otro
            case 5:

                if(originario.getFortuna()<this.cantidad){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque el jugador que lo acepta no tiene el dinero suficiente");
                }
                if(!(prop1.getDuenho().equals(destinatario) && prop2.getDuenho().equals(originario))){
                    throw new ExcepcionCreacionTrato("No se puede aceptar el trato porque alguna de las propiedades ya no pertenece a alguno de los jugadores");
                }

                prop1.setDuenho(destinatario);
                prop2.setDuenho(originario);

                originario.getPropiedades().remove(prop1);
                originario.getPropiedades().add(prop2);

                destinatario.getPropiedades().remove(prop2);
                destinatario.getPropiedades().add(prop1);

                originario.pagar(cantidad);
                destinatario.sumarFortuna(cantidad);

                consola.imprimir("ACEPTADO TRATO: cambiar("+prop1.getNombreSinColor()+","+cantidad+"):("+prop2.getNombreSinColor()+")");
                this.aceptado = true;
                break;     
                
            default:
                consola.imprimir("Tipo de trato no válido");
                break;
        }
    }    

    public void Listar(){
        
        consola.imprimir("Id: trato" + this.id);
        consola.imprimir("Jugador que propone: " + this.originario.getNombre());

        switch(this.tipo){
            case 1: consola.imprimir("Trato: cambiar (" + this.prop1.getNombreSinColor() + ", " + this.prop2.getNombreSinColor() + ")"); break;
            case 2: consola.imprimir("Trato: cambiar (" + this.prop1.getNombreSinColor() + ", " + this.cantidad + ")"); break;
            case 3: consola.imprimir("Trato: cambiar (" + this.cantidad + ", " + this.prop1.getNombreSinColor()+")"); break;
            case 4: consola.imprimir("Trato: cambiar (" + this.prop1.getNombreSinColor() + ", " + this.prop2.getNombreSinColor() + " y "+ this.cantidad + ")"); break;
            case 5: consola.imprimir("Trato: cambiar (" + this.prop1.getNombreSinColor() + " y " + this.cantidad + ", " + this.prop2.getNombreSinColor() + ")"); break;
        }
    }
}