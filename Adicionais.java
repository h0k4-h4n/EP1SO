import java.io.*;
import java.util.LinkedList;

// Classe que contém lógicas adicionais, para serem remanejadas em suas respectivas classes futuramente

public class Adicionais {
	
/* -------- ATRIBUTOS ----------- */
	
	// Tabela de Processos (Array ou Lista de BCPs, contendo todos os processos carregados - sem ordenação específica)
	private LinkedList<BCP> tabelaDeProcessos;
	
	// Lista dos processos prontos (Lista de listas de BCPs - ordenação externa por créditos e interna por ordem alfabética)
	private LinkedList<LinkedList<BCP>> listaDeProntos;
	
	// Lista dos processos bloqueados (Array ou Lista de BCPs) - ordenada por ordem de chegada
	private LinkedList<BCP> listaDeBloqueados;
	
	// N_COM - número de comandos que o processador utilizará como parâmetro base - obtido via arquivo quantum.txt
	private int N_COM;
	
/* ---------- MÉTODOS ------------ */
	
	// Constrói um buffer com o código do programa, para que haja o controle realizado pelo PC 
	
	public String[] bufferizaPrograma(BufferedReader arquivoPrograma) {
		String[] buffer = new String[22];
		
		try {
			String linha = arquivoPrograma.readLine();
		
			for (int i = 0; i < 22 && linha != null; i++) {
				buffer[i] = linha;
				linha = arquivoPrograma.readLine();
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo: " + arquivoPrograma.toString() + " não encontrado");
			return null;
		}
		catch(IOException e) {
			System.out.println("Erro " + e.toString() + " na leitura do arquivo: " + arquivoPrograma.toString());
			return null;
		}
		
		return buffer;
	}
	
	// Interpreta uma linha de código recebida e - no lugar dos printlns - aplicará ou invocará classes/métodos que executem as devidas funções
	
	public static void interpretaCodigo(String linhaDeCodigo){
		char[] linhaFatorada = linhaDeCodigo.toCharArray();
		
		if (linhaFatorada[0] == 'E')
			System.out.println("Processo fara E/S");
		
		else if (linhaFatorada[0] == 'C')
			System.out.println("Processo executou COMANDO");
		
		else if (linhaFatorada[0] == 'S')	
			System.out.println("Processo finalizado - SAIDA");
		
		else if (linhaFatorada[0] == 'X')
			System.out.println("Atualizar o registrador X para: " + (char)linhaFatorada[2]);
		
		else
			System.out.println("Atualizar o registrador Y para: " + (char)linhaFatorada[2]);
	}
	
	// Getter e Setters dos atributos
	
	public int getN_COM() {
		return this.N_COM;
	}
	
	public void setN_COM(int N_COM) {
		this.N_COM = N_COM;
	}
}