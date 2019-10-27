import java.util.*;

public class LLTeste{
	public static void main(String[] args){
		LinkedList[] listaProntos = new LinkedList[4];
		
		LinkedList<BCP> filaProcessos0 = new LinkedList<BCP>();
		LinkedList<BCP> filaProcessos1 = new LinkedList<BCP>();
		LinkedList<BCP> filaProcessos2 = new LinkedList<BCP>();
		LinkedList<BCP> filaProcessos3 = new LinkedList<BCP>();
		
		BCP bcp1 = new BCP(3, null, "PROG-1");
		BCP bcp2 = new BCP(2, null, "PROG-2");
		BCP bcp3 = new BCP(3, null, "PROG-3");
		BCP bcp4 = new BCP(1, null, "PROG-4");
		BCP bcp5 = new BCP(0, null, "PROG-5");
		BCP bcp6 = new BCP(2, null, "PROG-6");
		BCP bcp7 = new BCP(3, null, "PROG-7");
		BCP bcp8 = new BCP(2, null, "PROG-8");
		
		filaProcessos0.add(bcp5);
		filaProcessos1.add(bcp4);
		filaProcessos2.addFirst(bcp8);
		filaProcessos2.addFirst(bcp6);
		filaProcessos2.addFirst(bcp2);
		filaProcessos3.addFirst(bcp7);
		filaProcessos3.addFirst(bcp3);
		filaProcessos3.addFirst(bcp1);
		
		listaProntos[0] = filaProcessos0;
		listaProntos[1] = filaProcessos1;
		listaProntos[2] = filaProcessos2;
		listaProntos[3] = filaProcessos3;
		
		Iterator<BCP> it;
		for (int i = 0; i < listaProntos.length; i++){
			it = listaProntos[i].iterator();
			System.out.print("Fila " + i + ": ");
			while (it.hasNext()){
				System.out.print(it.next().getNomePrograma() + " ");
			}
			System.out.println();
		}
		
		
	}
}