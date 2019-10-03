// Classe que representa o BCP de um processo
import java.io.*;

public class BCP {
	
/* ------------ ATRIBUTOS ------------- */
	
	private int PC = 1;					// Representa a instruo (linha) que est sendo executada - default = 1, pois a primeira linha do processo  o 
										//                                                                                             nome do programa
	private char statusProcesso = 'P';	// Recebe 'E', 'P' ou 'B' - Executando, Pronto ou Bloqueado, respectivamente
	private int prioridade;				// Inteiro, iniciado com o valor originado pelo arquivo .txt e que sofrer alteraes ao longo das execues
	private int X = 0;					// 1 registrador de uso geral - inicializado com zero (0)
	private int Y = 0;					// 2 registrador de uso geral - inicializado com zero (0)
	private String[] referenciaMemoria;	// Referncia para a regio de memria em que est o cdigo do programa executado
	private String nomePrograma;		// Nome do programa a que cada instncia de BCP estar atrelada
	
/* ---------------- CONSTRUTORES ----------------- */
	
	// PC, Status do Processo, X e Y so inicializados de modo padro. Prioridade  recebida aps extrao via arquivo, bem como o Nome do Programa.
	//     Referncia de Memria  apenas um endereo de memria do prprio Java, tendo sua declarao na construo do BCP
	
	public BCP(int prioridade, String[] buffer, String nomePrograma) {
		this.prioridade = prioridade;
		this.referenciaMemoria = buffer;
		this.nomePrograma = nomePrograma;
	}
	
/* --------------- MTODOS ------------------- */
	
	// Getters de todos os atributos e Setteres somente d PC, X, Y, Status do Processo e Prioridade 
	
	public int getPC() {
		return this.PC;
	}
	
	public char getStatusProcesso() {
		return this.statusProcesso;
	}
	
	public int getPrioridade() {
		return this.prioridade;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public String[] getReferenciaMemoria() {
		return this.referenciaMemoria;
	}
	
	public String getNomePrograma() {
		return this.nomePrograma;
	}
	
	public void setPC(int PC) {
		this.PC = PC;
	}
	
	public void setStatusProcesso(char statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	// Estabelece os critrios de comparao segundo a precedncia estabelecida (primeiramente verifica os crditos), depois,
	// somente em caso de crditos iguais, desempata por ordem alfabtica
	public int compareTo(BCP bcp) throws NullPointerException, ClassCastException{
		if (this.prioridade < bcp.prioridade)
			return -1;
		if (this.prioridade > bcp.prioridade)
			return 1;
		char[] comp1 = this.nomePrograma.toCharArray();
		char[] comp2 = bcp.nomePrograma.toCharArray();
		if (comp1[0] < comp2[0])
			return -1;
		if (comp1[0] > comp2[0])
			return 1;
		if (comp1[1] < comp2[1])
			return -1;
		return 1;
	}
}