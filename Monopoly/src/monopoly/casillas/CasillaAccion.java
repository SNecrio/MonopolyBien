package monopoly.casillas;

public class CasillaAccion extends Casilla{










            
        if(jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Suerte")||jugador.getAvatar().getLugar().getNombreSinColor().equalsIgnoreCase("Caja")){
            suerteComunidad(jugador); //Se encarga de realizar las acciones de las cartas de suerte/comunidad
        }
    
}
