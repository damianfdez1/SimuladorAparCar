package aparCar;

public class Usuario implements Comparable <Usuario>{
	
	public int id;
	public boolean compartido;
	public int puntuacion;
	public int veces;
	public boolean tienePlaza;
	
	
	public Usuario(int id, boolean compartido) {
		this.id = id;
		this.compartido = compartido;
		this.puntuacion = 50;
		this.veces = 0;
		tienePlaza=false;
	}
	
	public int compareTo(Usuario empleado1) {
		if(empleado1.puntuacion>= this.puntuacion) return 1;
		else return -1;	
	}
	
}
