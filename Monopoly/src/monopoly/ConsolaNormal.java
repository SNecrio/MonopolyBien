package monopoly;
import java.util.Scanner;

public class ConsolaNormal implements Consola{

	//Atributos:
    private Scanner scanner;

 	//Constructor.
    public ConsolaNormal(){
        this.scanner = new Scanner(System.in);
	}


	@Override
	public void imprimir(String mensaje){
		System.out.println(mensaje);
	}

	@Override
	public void imprimirSinEspacio(String mensaje){
		System.out.print(mensaje);
	}

	@Override
	public void imprimirArgumentos(String formato, String arg1, String arg2){
		System.out.printf(formato, arg1, arg2);
	}

	@Override
	public String leer(){
    	String leido = scanner.nextLine();   // Lee la entrada del usuario
    	return leido;
	}

	@Override
	public int leerNumero(){
    	int leido = scanner.nextInt();   // Lee la entrada del usuario
    	return leido;
	}

	@Override
	public int leerNumero(String mensaje){
		System.out.println(mensaje);
		int leido = scanner.nextInt();   // Lee la entrada del usuario
    	return leido;
	}

	@Override
	public String leer(String mensaje){
		System.out.println(mensaje);

		String leido = scanner.nextLine();   // Lee la entrada del usuario
    	return leido;
	}
	public void cerrarScanner() {  //Llamar al acabar todo
        if (scanner != null) {
            scanner.close();
        }
    }

}

