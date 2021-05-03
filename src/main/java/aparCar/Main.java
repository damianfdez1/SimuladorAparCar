package aparCar;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Main {
	
	static int PLAZAS_TOTALES = 2;
	static int USUARIOS_COMPARTIDOS = 3;
	static int USUARIOS_NO_COMPARTIDOS = 3;
	static int DIAS_SIMULACION = 10;
	
	
	 public static void main(String[] args) throws Exception {
		 
		 //Creamos hoja de calculo
		 WritableWorkbook workbook =Workbook.createWorkbook(new File("simulacion.xls"));
		 WritableSheet sheet_resultadosPorDia =workbook.createSheet("ResultadosPorDia", 0);
		 WritableSheet sheet_resultadosFinales =workbook.createSheet("ResultadosFinales", 1);

		 int idUsuario=0;
		 ArrayList<Usuario> arrayTodosUsuarios = new ArrayList<Usuario>();
		 ArrayList<Usuario> arrayUsuariosCompartido = new ArrayList<Usuario>();
		 ArrayList<Usuario> arrayUsuariosNoCompartido = new ArrayList<Usuario>();
		 
	    //Usuarios compartidos	
    	for(int i=0;i<USUARIOS_COMPARTIDOS;i++) {
    		idUsuario++;
    		Usuario aux = new Usuario(idUsuario, true);
    		arrayTodosUsuarios.add(aux);
    		arrayUsuariosCompartido.add(aux);
    	}
	    	
	    //Usuarios no compartidos
    	for(int i=0;i<USUARIOS_NO_COMPARTIDOS;i++) {
    		idUsuario++;
    		Usuario aux = new Usuario(idUsuario, false);
    		arrayTodosUsuarios.add(aux);
    		arrayUsuariosNoCompartido.add(aux);
    	}
	    	
    	//Calculo plazas compartidas
    	int plazasCompartidas = CalculoPlazas.getNumPlazasCompartidas(USUARIOS_COMPARTIDOS, USUARIOS_NO_COMPARTIDOS, PLAZAS_TOTALES);
    	int restoPlazas = PLAZAS_TOTALES - plazasCompartidas;
	    
    	//Simulacion
    	for (int k=0; k<DIAS_SIMULACION; k++) {
    		
    		int plazasCompartidasRestantes = plazasCompartidas;
    		int restoPlazasRestantes = restoPlazas;
    		
	    	Collections.sort(arrayUsuariosCompartido);
	    	Collections.sort(arrayUsuariosNoCompartido);
	    	
	    	for (Usuario aux : arrayUsuariosCompartido) {
	    		if(plazasCompartidasRestantes==0) break;
	    		aux.tienePlaza=true;
	    		plazasCompartidasRestantes--;
	    	}
	    	
	    	for (Usuario aux : arrayUsuariosNoCompartido) {
	    		if (restoPlazasRestantes==0) break;
	    		aux.tienePlaza=true;
	    		restoPlazasRestantes--;
	    	}
	    	
	    	//Se imprimen los datos de los empleados por dia
	    	imprimirPorPantalla(k+1,arrayTodosUsuarios);
	    	imprimirHojaDeCalculoResultadoDia(sheet_resultadosPorDia,k+1,arrayTodosUsuarios);
	  
	    	//Se modifican puntuacion, numero de veces aparcado y se resetea la variable tienePlaza
	    	for (Usuario aux : arrayTodosUsuarios) {
	    		if(aux.tienePlaza) {
	    			aux.puntuacion=50;
	    			aux.veces++;
	    			aux.tienePlaza=false;
	    		}
	    		else {
	    			aux.puntuacion++;
	    		}
	    	}	
	 
    	}
	    	
    		//Se imprimen en la hoja de calculo los resultados finales de cada empleado
	    	imprimirHojaDeCalculoResultadoFinal (sheet_resultadosFinales, arrayTodosUsuarios);
	    	
	    	//Escribimos y cerramos hoja de calculo
	    	workbook.write();
			workbook.close();
	
	}
	 
	 
	 public static void imprimirPorPantalla(int dia, ArrayList<Usuario> arrayUsuarios) {
		System.out.println("-----DIA "+dia+"-----");
	    System.out.println("ID\tCompartido\tPuntuacion\tVeces aparcado anteriormente\tAparca");
	    for (Usuario aux: arrayUsuarios) {
	    	System.out.println(aux.id+"\t"+aux.compartido+"\t\t"+aux.puntuacion+"\t\t"+aux.veces+"\t\t\t\t"+aux.tienePlaza);
	    }
	 }
	 
	 
	 public static void imprimirHojaDeCalculoResultadoDia (WritableSheet sheet, int dia, ArrayList<Usuario> arrayUsuarios) throws Exception {
		 
		 	int numUsuarios = arrayUsuarios.size();
		 	int numFila = ((numUsuarios+3)*dia)-(numUsuarios+3);
		 	
		 	WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		 	cellFont.setBoldStyle(WritableFont.BOLD);
		 	WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		 
			sheet.addCell(new jxl.write.Label(0, numFila, "DIA "+dia, cellFormat));
			numFila++;
			sheet.addCell(new jxl.write.Label(0, numFila, "ID", cellFormat));
			sheet.addCell(new jxl.write.Label(1, numFila, "Compartido", cellFormat));
			sheet.addCell(new jxl.write.Label(2, numFila, "Puntuacion", cellFormat));
			sheet.addCell(new jxl.write.Label(3, numFila, "Veces aparcado anteriormente", cellFormat));
			sheet.addCell(new jxl.write.Label(4, numFila, "Aparca", cellFormat));
			numFila++;
			
			for (Usuario aux : arrayUsuarios) {
				sheet.addCell(new jxl.write.Number(0,numFila,aux.id));
				sheet.addCell(new jxl.write.Boolean(1,numFila,aux.compartido));
				sheet.addCell(new jxl.write.Number(2,numFila,aux.puntuacion));
				sheet.addCell(new jxl.write.Number(3,numFila,aux.veces));
				sheet.addCell(new jxl.write.Boolean(4,numFila,aux.tienePlaza));
				numFila++;
			}
		 
	 }
	 
	 
	 public static void imprimirHojaDeCalculoResultadoFinal (WritableSheet sheet,ArrayList<Usuario> arrayUsuarios) throws Exception {
		 
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		cellFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	
		sheet.addCell(new jxl.write.Label(0, 0, "ID", cellFormat));
		sheet.addCell(new jxl.write.Label(1, 0, "Compartido", cellFormat));
		sheet.addCell(new jxl.write.Label(2, 0, "Puntuacion", cellFormat));
		sheet.addCell(new jxl.write.Label(3, 0, "Veces aparcado anteriormente", cellFormat));
		
		int numFila=1;
		for (Usuario aux : arrayUsuarios) {
			sheet.addCell(new jxl.write.Number(0,numFila,aux.id));
			sheet.addCell(new jxl.write.Boolean(1,numFila,aux.compartido));
			sheet.addCell(new jxl.write.Number(2,numFila,aux.puntuacion));
			sheet.addCell(new jxl.write.Number(3,numFila,aux.veces));
			numFila++;
		}
		
	 }

}
