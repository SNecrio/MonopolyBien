package monopoly;

public class ConsolaNormal implements Consola{
	@Override
	public void imprimir(String mensaje){
		System.out.println(mensaje);
	}

	@Override
	public String leer(String descripcion){
        String leido="";
		//Scan.in...;
		return leido;
	}

}

