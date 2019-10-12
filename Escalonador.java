// Classe que simula o comportamento do escalonador
import java.util.Collection;
import java.util.TreeSet;
import java.util.Iterator;

public class Escalonador {
	

	
/* ------------ CONSTRUTORES -------------- */
	
	public Escalonador() {
		
	}
	
/* ---------- MÉTODOS -------------- */
	
	public static void main(String[] args) {
		Collection<BCP> listaProntos = new TreeSet<BCP>();
		
		String[] codProg1 = new String[2];
		String[] codProg2 = new String[3];
		String[] codProg3 = new String[3];
		String[] codProg4 = new String[3];
		String[] codProg5 = new String[3];
		String[] codProg6 = new String[3];
		String[] codProg7 = new String[3];
		String[] codProg8 = new String[3];
		String[] codProg9 = new String[3];
		String[] codProg10 = new String[3];
		
		BCP bcp1 = new BCP(1, codProg1, "TESTE-1");
		BCP bcp2 = new BCP(3, codProg2, "TESTE-2");
		BCP bcp3 = new BCP(4, codProg3, "TESTE-3");
		BCP bcp4 = new BCP(3, codProg4, "TESTE-4");
		BCP bcp5 = new BCP(4, codProg5, "TESTE-5");
		BCP bcp6 = new BCP(2, codProg6, "TESTE-6");
		BCP bcp7 = new BCP(4, codProg7, "TESTE-7");
		BCP bcp8 = new BCP(3, codProg8, "TESTE-8");
		BCP bcp9 = new BCP(3, codProg9, "TESTE-9");
		BCP bcp10 = new BCP(2, codProg10, "TESTE-10");
		
		listaProntos.add(bcp1);
		listaProntos.add(bcp2);
		listaProntos.add(bcp3);
		listaProntos.add(bcp4);
		listaProntos.add(bcp5);
		listaProntos.add(bcp6);
		listaProntos.add(bcp7);
		listaProntos.add(bcp8);
		listaProntos.add(bcp9);
		listaProntos.add(bcp10);
		
		Iterator<BCP> it = listaProntos.iterator();
		
		while(it.hasNext())
			System.out.println(it.next().getNomePrograma());
	}
		
	
	
		
	

}