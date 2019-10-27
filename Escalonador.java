import java.io.*;
import java.util.*;

// Classe que simula o comportamento do escalonador
public class Escalonador {

/* ------------- ATRIBUTOS ------------- */

	public LinkedList<BCP> tabelaProcessos; 		// Tabela de Processos (Lista de BCPs, contendo todos os processos
														// carregados - sem ordenacao especifica)
	public LinkedList[] listaProntos; 				// Lista dos processos prontos (múltiplas filas)
	public LinkedList<BCP> listaBloqueados; 		// Lista dos processos bloqueados (Lista de BCPs) - ordenada por
														// ordem de chegada
	public LogFile logFile;						// Instancia de LogFile

/* ------------ CONSTRUTORES -------------- */

	public Escalonador() {
	}
	
/* ------------ GETTERS -------------- */

	public LinkedList<BCP> getProcessos() {
		return tabelaProcessos;
	}

	public LinkedList[] getProntos() {
		return listaProntos;
	}

	public LinkedList<BCP> getBloqueados() {
		return listaBloqueados;
	}

/* ------------ SETTERS -------------- */

	public void setProcessos(LinkedList<BCP> processos) {
		this.tabelaProcessos = processos;
	}

	public void setProntos(LinkedList[] prontos) {
		this.listaProntos = prontos;
	}

	public void setBloqueados(LinkedList<BCP> bloqueados) {
		this.listaBloqueados = bloqueados;
	}

/* ---------- METODOS -------------- */
	
	public int obtemMaiorPrioridade(BufferedReader arqPrioridade){
		int maiorPrioridade = 0;
		int aux;
		
		try{
			String linha = arqPrioridade.readLine();
		
			while (linha != null){
				aux = Integer.parseInt(linha);
				
				if (aux > maiorPrioridade)
					maiorPrioridade = aux;
				
				linha = arqPrioridade.readLine();
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Arquivo: " + arqPrioridade.toString() + " nao encontrado");
			return -1;
		} 
		catch (IOException e) {
			System.err.println("Erro " + e.toString() + " na leitura do arquivo: " + arqPrioridade.toString());
			return -1;
		}
		
		return maiorPrioridade;
	}
	
	// Rotina de inicializacao do escalonador - leitura dos arquivos e carregamento
	// dos processos
	

	public void decrementaTempBloqueados() {
		Iterator<BCP> iterador = listaBloqueados.iterator();
		BCP aux;
		
		while(iterador.hasNext()) {
			aux = iterador.next();
			aux.setTemporizador(aux.getTemporizador() - 1);
			
			if(aux.getTemporizador() == 0){
				iterador.remove();
				listaProntos[aux.getCreditos()].addLast(aux);
				aux.setStatusProcesso('P');
			}
		}
	}

	public boolean creditoNulo() {
		Iterator<BCP> it;
		
		for (int i = 0; i < listaProntos.length; i++){
			it = listaProntos[i].iterator();
			
			while (it.hasNext()){
				if (it.next().getCreditos() != 0)
					return false;
			}
		}
		
		it = listaBloqueados.iterator();
		
		while (it.hasNext()){
			if (it.next().getCreditos() != 0)
				return false;
		}
		
		return true;
	}

	public void restituiCreditos() {
		Iterator<BCP> it = listaProntos[0].iterator();
		BCP[] filaIteracao = new BCP[totalProcessosAtivos()];
		BCP bcp;
		
		for (int i = 0; it.hasNext(); i++)
			filaIteracao[i] = it.next();
		
		for (int i = 0; i < filaIteracao.length && filaIteracao[i] != null; i++){
			bcp = filaIteracao[i];
			listaProntos[bcp.getCreditos()].remove(bcp);
			bcp.setCreditos(bcp.getPrioridade());
			bcp.setQuantum(1);
			listaProntos[bcp.getCreditos()].add(bcp);
		}
	
		it = listaBloqueados.iterator();
		
		while (it.hasNext()){
			bcp = it.next();
			bcp.setCreditos(bcp.getPrioridade());
			bcp.setQuantum(1);
		}
	}
	
	public boolean listaProntosVazia(){
		LinkedList<BCP> fila;
		
		for (int i = listaProntos.length - 1; i >= 0; i--){
			fila = listaProntos[i];
		
			if (fila.size() > 0)
				return false;
		}
		
		return true;
	}
	
	public BCP obtemBCPMaiorCredito(){
		LinkedList<BCP> fila;
		
		for (int i = listaProntos.length - 1; i >= 0; i--){
			fila = listaProntos[i];
			
			if (fila.size() > 0)
				return fila.getFirst();	
		}
		
		return null;
	}	
	
	public int totalProcessosAtivos(){
		int somatoria = 0;
		
		for (int i = 0; i < listaProntos.length; i++)
			somatoria += listaProntos[i].size();
		
		return somatoria;
	}
	
	public void reordenaListaProntos(){
		/* Iterator<BCP> it;
		BCP[] bcps = new BCP[totalProcessosAtivos()];
		int j = 0;
		
		for (int i = 0; i < listaProntos.length; i++){
			it = listaProntos[i].iterator();
			
			while (it.hasNext()){
				bcps[j] = it.next();
				j++;
			}
		}
		
		Pattern padrao = Pattern.compile("TESTE-");
		String[] comp1 = padrao.split(this.nomePrograma);
		String[] comp2 = padrao.split(bcp.getNomePrograma());
		
		if (Integer.parseInt(comp1[1]) < Integer.parseInt(comp2[1]))
			return 1;
		else if (Integer.parseInt(comp1[1]) > Integer.parseInt(comp2[1])) */
	}

	public void imprimeListaProntos(){
		Iterator<BCP> it;
		
		for (int i = listaProntos.length - 1; i >= 0; i--){
			it = listaProntos[i].iterator();
			
			System.out.print("Fila " + i + ": ");
			
			while (it.hasNext())
				System.out.print(it.next().getNomePrograma() + ", ");
			System.out.println();
		}
		System.out.println();
	}
}