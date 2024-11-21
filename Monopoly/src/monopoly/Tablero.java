package monopoly;

import java.util.ArrayList;
import java.util.HashMap;

import partida.*;
import monopoly.casillas.*;



public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        this.banca=banca;
        this.generarCasillas();

    }
    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoNorte();
        this.insertarLadoOeste();
        this.insertarLadoEste();
        this.insertarLadoSur();
    }
    
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> filaNorte = new ArrayList<>(11);
        Casilla casilla = new Casilla(Valor.WHITE + "Parking" , "Especial", 20, banca); //Crear unha casilla
        filaNorte.add(casilla); //Engadila ao arrayList
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.RED + "Solar12" , "Solar", 21, 1142440, banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Suerte" , "Suerte", 22, banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.RED + "Solar13" , "Solar", 23, 1142440, banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.RED + "Solar14" , "Solar", 24, 1142440, banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Trans3" ,"Transporte", 25,1301328.584f , banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.MARRON + "Solar15" , "Solar", 26, 1485172 , banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.MARRON + "Solar16" , "Solar", 27, 1485172 , banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Serv2" ,"Servicio", 28,975996.438f , banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.MARRON + "Solar17" , "Solar", 29, 1485172 , banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "IrCarcel" , "Especial", 30, banca);
        filaNorte.add(casilla);
        banca.anhadirPropiedad(casilla);

        Grupo grupo = new Grupo(filaNorte.get(1),filaNorte.get(3),filaNorte.get(4),"RED");
        filaNorte.get(1).setGrupo(grupo);filaNorte.get(3).setGrupo(grupo);filaNorte.get(4).setGrupo(grupo);
        grupos.put("RED",grupo);

        grupo = new Grupo(filaNorte.get(6),filaNorte.get(7),filaNorte.get(9),"MARRON");
        filaNorte.get(6).setGrupo(grupo);filaNorte.get(7).setGrupo(grupo);filaNorte.get(9).setGrupo(grupo);
        grupos.put("MARRON",grupo);

        this.posiciones.add(filaNorte); //Engadimos o arrayList fila ao arrayList posiciones
    }
        
    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> filaSur = new ArrayList<>(11);

        Casilla casilla = new Casilla(Valor.WHITE + "Carcel" , "Especial", 10, banca); 
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.CYAN + "Solar5" , "Solar", 9,520000 , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.CYAN + "Solar4" , "Solar", 8,520000 , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Suerte" , "Suerte", 7, banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.CYAN + "Solar3" , "Solar", 6,520000 , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Trans1" ,"Transporte", 5,1301328.584f , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Imp1" , 4,650664.292f , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.BLACK + "Solar2" , "Solar", 3,600000 , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Caja" , "Comunidad", 2, banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.BLACK + "Solar1" , "Solar", 1,600000 , banca);
        filaSur.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla("Salida" , "Especial", 0, banca); //Crear unha casilla
        filaSur.add(casilla); //Engadila ao arrayList
        banca.anhadirPropiedad(casilla);
        
        Grupo grupo = new Grupo(filaSur.get(7),filaSur.get(9),"BLACK");
        filaSur.get(7).setGrupo(grupo);filaSur.get(9).setGrupo(grupo);
        grupos.put("BLACK",grupo);

        grupo = new Grupo(filaSur.get(1),filaSur.get(2),filaSur.get(4),"CYAN");
        filaSur.get(1).setGrupo(grupo);filaSur.get(2).setGrupo(grupo);filaSur.get(4).setGrupo(grupo);
        grupos.put("CYAN",grupo);
        
        this.posiciones.add(filaSur); //Engadimos o arrayList fila ao arrayList posiciones  
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> filaOeste = new ArrayList<>(9);
        Casilla casilla = new Casilla(Valor.NARANJA + "Solar11" , "Solar", 19, 878800 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.NARANJA + "Solar10" , "Solar", 18, 878800 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Caja" , "Comunidad", 17,  banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.NARANJA + "Solar9" , "Solar", 16, 878800 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Trans2" , "Transporte", 15, 1301328.584f , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.PURPLE + "Solar8" , "Solar", 14, 676000 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.PURPLE + "Solar7" , "Solar", 13, 676000 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Serv1" , "Servicio", 12, 975996.438f, banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.PURPLE + "Solar6" , "Solar", 11, 676000 , banca); 
        filaOeste.add(casilla);
        banca.anhadirPropiedad(casilla);

        Grupo grupo = new Grupo(filaOeste.get(0),filaOeste.get(1),filaOeste.get(3),"NARANJA");
        filaOeste.get(0).setGrupo(grupo);filaOeste.get(1).setGrupo(grupo);filaOeste.get(3).setGrupo(grupo);
        grupos.put("NARANJA",grupo);

        grupo = new Grupo(filaOeste.get(5),filaOeste.get(6),filaOeste.get(8),"PURPLE");
        filaOeste.get(5).setGrupo(grupo);filaOeste.get(6).setGrupo(grupo);filaOeste.get(8).setGrupo(grupo);
        grupos.put("PURPLE",grupo);

        this.posiciones.add(filaOeste);
    }

    private void insertarLadoEste() { 

        ArrayList<Casilla> filaEste = new ArrayList<>(9); 
        Casilla casilla = new Casilla(Valor.GREEN + "Solar18", "Solar", 31, 1930723.6f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.GREEN + "Solar19", "Solar", 32, 1930723.6f, banca); 
        filaEste.add(casilla);
        banca.anhadirPropiedad(casilla); 
        casilla = new Casilla(Valor.WHITE + "Caja", "Comunidad", 33,  banca); 
        filaEste.add(casilla);
        banca.anhadirPropiedad(casilla); 
        casilla = new Casilla(Valor.GREEN + "Solar20", "Solar", 34, 1930723.6f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Trans4", "Transporte", 35, 1301328.584f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Suerte", "Suerte", 36, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.BLUE + "Solar21", "Solar", 37, 3764911.02f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.WHITE + "Imp2", 38, 650664.292f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);
        casilla = new Casilla(Valor.BLUE + "Solar22", "Solar", 39, 3764911.02f, banca); 
        filaEste.add(casilla); 
        banca.anhadirPropiedad(casilla);

        Grupo grupo = new Grupo(filaEste.get(0),filaEste.get(1),filaEste.get(3),"GREEN");
        filaEste.get(0).setGrupo(grupo);filaEste.get(1).setGrupo(grupo);filaEste.get(3).setGrupo(grupo);
        grupos.put("GREEN",grupo);

        grupo = new Grupo(filaEste.get(6),filaEste.get(8),"BLUE");
        filaEste.get(6).setGrupo(grupo);filaEste.get(8).setGrupo(grupo);
        grupos.put("BLUE",grupo);

        this.posiciones.add(filaEste); 
    } 

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        String toString = "";
        String aux = "";

        for (int i = 0; i < posiciones.size(); i++) {  // Recorre las filas del tablero

            if ((i==0)||(i==3)){    //Formato de las filas norte y sur

                //Fila horizontal para enmarcar las casillas
                for(int a=0;a<122;a++){
                toString += Valor.WHITE + "-";}   
                toString += "\n";

                // Recorre cada casilla dentro de la fila
                for (int j = 0; j < posiciones.get(i).size(); j++) {  
                    Casilla casilla =  posiciones.get(i).get(j);
                    //Guarda el nombre de la casilla para imprimirla por pantalla
                    toString += Valor.WHITE + "| " + casilla.getNombre();
                    // Se asegura de que todas las casillas ocupen lo mismo en pantalla, independientemente de la longitud de su nombre
                    switch (casilla.getNombreSinColor().length()) { 
                        case 4:
                            toString += "     ";
                            break;
                        case 5:
                            toString += "    ";
                            break;
                        case 6:
                            toString += "   ";
                            break;
                        case 7:
                            toString += "  ";
                            break;
                        default:
                            toString += " ";
                            break;}
                    // Enmarca la casilla final de la fila
                    if (j==10) toString += "|";}

                toString +="\n";
                // Deja un hueco debajo del nombre de la casilla para poner avatares en caso de haberlos
                for(int e=0; e<posiciones.get(i).size(); e++){ 
                    aux = "|";
                    // Anhade los Ids de los avatares en el tablero
                    Casilla casilla =  posiciones.get(i).get(e);
                    for(int m=0; m<casilla.getAvatares().size(); m++){ // Añade los avatares correspondientes a las casillas
                        aux += casilla.getAvatares().get(m).getId();}
                    while(aux.length() < 11) aux+= " ";
                    toString += aux;
                    if(e==10) toString += "|";}

                toString +="\n";
                 //Fila horizontal para enmarcar las casillas
                for(int a=0;a<122;a++){
                    toString += "-";}
                toString += "\n";}

            else if(i==1){  //Formato de las filas este y oeste
                // Recorre cada casilla dentro de la fila
                for (int j = 0; j < posiciones.get(i).size(); j++) {
                    // Primero imprime la casilla oeste
                    Casilla casilla = posiciones.get(i).get(j);
                    toString +=  Valor.WHITE + "| " + casilla.getNombre();
                    switch (casilla.getNombreSinColor().length()) { 
                        case 4:
                            toString += "     ";
                            break;
                        case 5:
                            toString += "    ";
                            break;
                        case 6:
                            toString += "   ";
                            break;
                        case 7:
                            toString += "  ";
                            break;
                        default:
                            toString += " ";
                            break;}
                    toString += Valor.WHITE + "|";

                    // Hueco en blanco entre las casillas este y oeste
                    for(int a=0;a<98;a++){      
                        toString += " ";}

                    // Luego imprime su correspondiente casilla este
                    casilla = posiciones.get(2).get(j); //Casilla este
                    toString +=  Valor.WHITE + "| " + casilla.getNombre();
                    switch (casilla.getNombreSinColor().length()) {
                        case 4:                          
                            toString += "     ";
                            break;
                        case 5:
                            toString += "    ";
                            break;
                        case 6:
                            toString += "   ";
                            break;
                        case 7:
                            toString += "  ";
                            break;
                        default:
                            toString += " ";
                            break;
                    }
                    toString += Valor.WHITE + "|\n";

                    // Deja un hueco debajo del nombre de la casilla para poner avatares en caso de haberlos
                    aux = "|";
                    casilla =  posiciones.get(i).get(j);
                    for(int m=0; m<casilla.getAvatares().size(); m++){ // Añade los avatares correspondientes a las casillas
                        aux += casilla.getAvatares().get(m).getId();}
                    while(aux.length() < 11) aux+= " ";
                    toString += aux + "|";


                    //Hueco en blanco entre las casillas este y oeste
                    for(int a=0;a<98;a++){
                        toString += " ";}

                    // Deja un hueco debajo del nombre de la casilla para poner avatares en caso de haberlos
                    aux = "|";
                    // Anhade los Ids de los avatares en el tablero
                    casilla =  posiciones.get(2).get(j);
                    for(int m=0; m<casilla.getAvatares().size(); m++){ // Añade los avatares correspondientes a las casillas
                        aux += casilla.getAvatares().get(m).getId();}
                    while(aux.length() < 11) aux+= " ";
                    toString += aux + "|\n";
   
                    // Mientras no sea la última casilla de la columna, imprime la linea horizontal que separa las casillas 
                    // de las columnas
                    if (j!=8){
                    for(int a=0;a<12;a++){
                      toString += Valor.WHITE + "-";}
                    for(int a=0;a<98;a++){
                        toString += " ";}
                    for(int x=0;x<12;x++){
                        toString += Valor.WHITE + "-";}
                        toString += "\n";}
                }}
            }
        return toString;
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento
    public Casilla encontrar_casilla(String nombre){

        //Buscamos en cada casilla de cada lado se o nome e igual ao dado, se non se encontra, devolve null

        for(short i = 0; i<this.posiciones.size();i++){
            for(short u = 0; u< (this.posiciones.get(i)).size(); u++){
                
                if( ((posiciones.get(i)).get(u)).getNombreSinColor().equalsIgnoreCase(nombre)){
                    return ((this.posiciones.get(i)).get(u));
                }
            }
        }
        System.out.println("La casilla deseada no existe.");
        return null;

    }

    

    public ArrayList<ArrayList<Casilla>> getPosiciones(){
        return this.posiciones;
    }

    public void imprimirTablero(){
        System.out.print(this.toString());
    }

    public void incrementarPrecios() {
        for (ArrayList<Casilla> fila : posiciones) {
            for (Casilla casilla : fila) {
                float valor = casilla.getValor();
                casilla.sumarValor(valor * 0.05f); // Aumenta el precio de la casilla individualmente
            }
        }
    }

    public ArrayList<Casilla> obtenerPropiedadesPorDuenho(String jugadorBuscado) {
        ArrayList<Casilla> propiedadesDelJugador = new ArrayList<>();
        
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                // Verificamos si el dueño de la casilla es el jugador que buscamos
                if (casilla.getDuenho().getNombre().equalsIgnoreCase(jugadorBuscado)) {
                    propiedadesDelJugador.add(casilla);
                }
            }
        }
    
        // Devolvemos la lista de propiedades del jugador
        return propiedadesDelJugador;
    }
    
}