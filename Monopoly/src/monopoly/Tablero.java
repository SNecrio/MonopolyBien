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
    private Baraja baraja;

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        this.banca=banca;
        this.generarCasillas();
        this.baraja = new Baraja();
    }

    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoNorte();
        this.insertarLadoOeste();
        this.insertarLadoEste();
        this.insertarLadoSur();
    }

    public Grupo obtenerGrupoPorNombre(String nombreGrupo) {
        return grupos.get(nombreGrupo); // Devuelve el Grupo correspondiente al nombre
    }
    
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {

        ArrayList<Casilla> filaNorte = new ArrayList<>(11);

        Grupo grupoRojo = new Grupo();
        grupoRojo.setColor("RED");
        grupos.put("RED",grupoRojo);

        Grupo grupoMarron = new Grupo();
        grupoMarron.setColor("MARRON");
        grupos.put("MARRON",grupoMarron);
        Casilla casilla;

        casilla = new CasillaEspecial(0f,Valor.WHITE + "Parking",20);
        filaNorte.add(casilla); //Engadila ao arrayList

        casilla = new PropiedadSolar(grupoRojo,1142440f,banca,Valor.RED + "Solar12",21);
        ((PropiedadSolar)casilla).setGrupo(grupoRojo);
        grupoRojo.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);

        
        casilla = new CasillaAccion(Valor.WHITE + "Suerte" , 22);
        filaNorte.add(casilla);        

        casilla = new PropiedadSolar(grupoRojo,1142440f,banca,Valor.RED + "Solar13",23);
        ((PropiedadSolar)casilla).setGrupo(grupoRojo);
        grupoRojo.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoRojo,1142440f,banca,Valor.RED + "Solar14",24);
        ((PropiedadSolar)casilla).setGrupo(grupoRojo);
        grupoRojo.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadTransporte(1301328.584f,banca,Valor.WHITE + "Trans3", 25);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadTransporte)casilla);
        
        casilla = new PropiedadSolar(grupoMarron,1485172f,banca,Valor.MARRON + "Solar15",26);
        ((PropiedadSolar)casilla).setGrupo(grupoMarron);
        grupoMarron.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoMarron,1485172f,banca,Valor.MARRON + "Solar16",27);
        ((PropiedadSolar)casilla).setGrupo(grupoMarron);
        grupoMarron.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadServicio(975996.438f,banca,Valor.WHITE + "Serv2",28);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadServicio)casilla);
        
        casilla = new PropiedadSolar(grupoMarron,1485172f,banca,Valor.MARRON + "Solar17",29);
        ((PropiedadSolar)casilla).setGrupo(grupoMarron);
        grupoMarron.anhadirCasilla((PropiedadSolar)casilla);
        filaNorte.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new CasillaEspecial(0f,"IrCarcel",30);
        filaNorte.add(casilla);

        this.posiciones.add(filaNorte); //Engadimos o arrayList fila ao arrayList posiciones
    }
        
    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {

        ArrayList<Casilla> filaSur = new ArrayList<>(11);

        Grupo grupoCyan = new Grupo();
        grupoCyan.setColor("CYAN");
        grupos.put("CYAN",grupoCyan);

        Grupo grupoNegro = new Grupo();
        grupoNegro.setColor("BLACK");
        grupos.put("BLACK",grupoNegro);

        Casilla casilla;
        
        //casilla = new CasillaEspecial(Valor.WHITE + "Carcel" , "Especial", 10, banca);
        casilla = new CasillaEspecial(Valor.SUMA_VUELTA * 0.25f, Valor.WHITE + "Carcel", 10);
        filaSur.add(casilla);

        casilla = new PropiedadSolar(grupoCyan,520000f, banca, Valor.CYAN + "Solar5",9);
        ((PropiedadSolar)casilla).setGrupo(grupoCyan);
        grupoCyan.anhadirCasilla((PropiedadSolar)casilla);        
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoCyan,520000f, banca, Valor.CYAN + "Solar4",8);
        ((PropiedadSolar)casilla).setGrupo(grupoCyan);
        grupoCyan.anhadirCasilla((PropiedadSolar)casilla);        
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        
        casilla = new CasillaAccion(Valor.WHITE + "Suerte",  7);
        filaSur.add(casilla);
        
        
        casilla = new PropiedadSolar(grupoCyan,520000f, banca, Valor.CYAN + "Solar3",6);
        ((PropiedadSolar)casilla).setGrupo(grupoCyan);
        grupoCyan.anhadirCasilla((PropiedadSolar)casilla);        
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadTransporte(1301328.584f,banca,Valor.WHITE + "Trans1", 5);
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadTransporte)casilla);
        
        casilla = new CasillaImpuesto(650664.292f, Valor.WHITE + "Imp1", 4);
        filaSur.add(casilla);

        casilla = new PropiedadSolar(grupoNegro,600000f, banca, Valor.BLACK + "Solar2",3);
        ((PropiedadSolar)casilla).setGrupo(grupoNegro);
        grupoNegro.anhadirCasilla((PropiedadSolar)casilla);        
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        
        casilla = new CasillaAccion(Valor.WHITE + "Caja", 2);
        filaSur.add(casilla);
        

        casilla = new PropiedadSolar(grupoNegro,600000f, banca, Valor.BLACK + "Solar1",1);
        ((PropiedadSolar)casilla).setGrupo(grupoNegro);
        grupoNegro.anhadirCasilla((PropiedadSolar)casilla);        
        filaSur.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new CasillaEspecial(0f, "Salida", 0); //Crear unha casilla
        filaSur.add(casilla); //Engadila ao arrayList
        
        this.posiciones.add(filaSur); //Engadimos o arrayList fila ao arrayList posiciones  
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {

        ArrayList<Casilla> filaOeste = new ArrayList<>(9);
        
        Grupo grupoNaranja = new Grupo();
        grupoNaranja.setColor("NARANJA");
        grupos.put("NARANJA",grupoNaranja);

        Grupo grupoMorado = new Grupo();
        grupoMorado.setColor("PURPLE");
        grupos.put("PURPLE",grupoMorado);

        Casilla casilla;

        casilla = new PropiedadSolar(grupoNaranja,878800f, banca, Valor.NARANJA + "Solar11",19);
        ((PropiedadSolar)casilla).setGrupo(grupoNaranja);
        grupoNaranja.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoNaranja,878800f, banca, Valor.NARANJA + "Solar10",18);
        ((PropiedadSolar)casilla).setGrupo(grupoNaranja);
        grupoNaranja.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        
        casilla = new CasillaAccion(Valor.WHITE + "Caja", 17); 
        filaOeste.add(casilla);       
        
        casilla = new PropiedadSolar(grupoNaranja,878800f, banca, Valor.NARANJA + "Solar9",16);
        ((PropiedadSolar)casilla).setGrupo(grupoNaranja);
        grupoNaranja.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadTransporte(1301328.584f,banca,Valor.WHITE + "Trans2", 15);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadTransporte)casilla);
        
        casilla = new PropiedadSolar(grupoMorado,676000f, banca, Valor.PURPLE + "Solar8",14);
        ((PropiedadSolar)casilla).setGrupo(grupoMorado);
        grupoMorado.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoMorado,676000f, banca, Valor.PURPLE + "Solar7",13);
        ((PropiedadSolar)casilla).setGrupo(grupoMorado);
        grupoMorado.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadServicio(975996.438f,banca,Valor.WHITE + "Serv1",12);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadServicio)casilla);
        
        casilla = new PropiedadSolar(grupoMorado,676000f, banca, Valor.PURPLE + "Solar6",11);
        ((PropiedadSolar)casilla).setGrupo(grupoMorado);
        grupoMorado.anhadirCasilla((PropiedadSolar)casilla);
        filaOeste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);


        this.posiciones.add(filaOeste);
    }

    private void insertarLadoEste() { 

        ArrayList<Casilla> filaEste = new ArrayList<>(9);
        
        Grupo grupoVerde = new Grupo();
        grupoVerde.setColor("GREEN");
        grupos.put("GREEN",grupoVerde);

        Grupo grupoAzul = new Grupo();
        grupoAzul.setColor("BLUE");
        grupos.put("BLUE",grupoAzul);

        Casilla casilla;

        casilla = new PropiedadSolar(grupoVerde,1930723.6f, banca, Valor.GREEN + "Solar18",31);
        ((PropiedadSolar)casilla).setGrupo(grupoVerde);
        grupoVerde.anhadirCasilla((PropiedadSolar)casilla);
        filaEste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new PropiedadSolar(grupoVerde,1930723.6f, banca, Valor.GREEN + "Solar19",32);
        ((PropiedadSolar)casilla).setGrupo(grupoVerde);
        grupoVerde.anhadirCasilla((PropiedadSolar)casilla);
        filaEste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        
        casilla = new CasillaAccion(Valor.WHITE + "Caja",  33); 
        filaEste.add(casilla);
        
        
        casilla = new PropiedadSolar(grupoVerde,1930723.6f, banca, Valor.GREEN + "Solar20",34);
        ((PropiedadSolar)casilla).setGrupo(grupoVerde);
        grupoVerde.anhadirCasilla((PropiedadSolar)casilla);
        filaEste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);

        casilla = new PropiedadTransporte(1301328.584f,banca,Valor.WHITE + "Trans4", 35);
        filaEste.add(casilla); 
        banca.anhadirPropiedad((PropiedadTransporte)casilla);
        
        
        casilla = new CasillaAccion(Valor.WHITE + "Suerte",  36); 
        filaEste.add(casilla); 
        
        
        casilla = new PropiedadSolar(grupoAzul,3764911.02f, banca, Valor.BLUE + "Solar21",37);
        ((PropiedadSolar)casilla).setGrupo(grupoAzul);
        grupoAzul.anhadirCasilla((PropiedadSolar)casilla);
        filaEste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);
        
        casilla = new CasillaImpuesto(650664.292f, Valor.WHITE + "Imp2", 38);
        filaEste.add(casilla); 
        
        casilla = new PropiedadSolar(grupoAzul,3764911.02f, banca, Valor.BLUE + "Solar22",39);
        ((PropiedadSolar)casilla).setGrupo(grupoAzul);
        grupoAzul.anhadirCasilla((PropiedadSolar)casilla);
        filaEste.add(casilla);
        banca.anhadirPropiedad((PropiedadSolar)casilla);


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

    public void imprimirTablero(){
        System.out.print(this.toString());
    }

    
    public void incrementarPrecios() {
        for (ArrayList<Casilla> fila : posiciones) {
            for (Casilla casilla : fila) {
                float valor = ((CasillaPropiedad)casilla).getValor();
                ((CasillaPropiedad)casilla).sumarValor(valor * 0.05f); // Aumenta el precio de la casilla individualmente
            }
        }
    }

    public ArrayList<Casilla> obtenerPropiedadesPorDuenho(String jugadorBuscado) {
        ArrayList<Casilla> propiedadesDelJugador = new ArrayList<>();
        
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                // Verificamos si el dueño de la casilla es el jugador que buscamos
                if (((PropiedadSolar)casilla).getDuenho().getNombre().equalsIgnoreCase(jugadorBuscado)) {
                    propiedadesDelJugador.add(casilla);
                }
            }
        }
    
        // Devolvemos la lista de propiedades del jugador
        return propiedadesDelJugador;
    }
    
    public Baraja getBaraja(){
        return this.baraja;
    }

    public ArrayList<ArrayList<Casilla>> getPosiciones(){
        return this.posiciones;
    }

}