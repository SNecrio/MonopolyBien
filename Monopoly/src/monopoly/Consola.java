package monopoly;

public interface Consola{
	public void imprimir(String mensaje);
	public String leer(String mensaje);
	public int leerNumero(String mensaje);
	public int leerNumero();
	public String leer();
	public void imprimirSinEspacio(String mensaje);
	public void imprimirArgumentos(String formato, String arg1, String arg2);
}

