package aparCar;


public class CalculoPlazas {


	public static int getNumPlazasCompartidas(double numUsuariosCompartidos, double numUsuariosSoloConductor, double numPlazasLibres) {
		
		double numUsuariosTotales = numUsuariosCompartidos + numUsuariosSoloConductor;
				
		double probAparcar = numPlazasLibres/numUsuariosTotales;
		double probCompartido = numUsuariosCompartidos/numUsuariosTotales;
		double probSoloConductor = numUsuariosSoloConductor/numUsuariosTotales;
		
		double probAparcarCompartido = probAparcar/(probCompartido+0.5*probSoloConductor);
		
		double plazasCocheCompartido = (probAparcarCompartido * numUsuariosCompartidos);
		plazasCocheCompartido = Math.round(plazasCocheCompartido);
		
		if ((int) plazasCocheCompartido > (int) numUsuariosCompartidos) {
			return (int) numUsuariosCompartidos;
		}
		else {
			return (int) plazasCocheCompartido;
		}
		
	}

}
