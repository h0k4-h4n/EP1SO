import java.util.regex.*;

// Classe que representa o BCP de um processo
public class BCP implements Comparable<BCP> {
	
/* ------------ ATRIBUTOS ------------- */
	
	private int PC = 1;					// Representa a instrução (linha) que está sendo executada - default = 1, pois a primeira linha do processo é o 
										//                                                                                         nome do programa
	private char statusProcesso = 'P';	// Recebe 'E', 'P' ou 'B' - Executando, Pronto ou Bloqueado, respectivamente
	private int prioridade;				// Inteiro, iniciado com o valor originado pelo arquivo .txt e que sofrer alteraes ao longo das execues
	private int X = 0;					// 1 registrador de uso geral - inicializado com zero (0)
	private int Y = 0;					// 2 registrador de uso geral - inicializado com zero (0)
	private String[] referenciaMemoria;	// Referncia para a regio de memria em que est o cdigo do programa executado
	private String nomePrograma;		// Nome do programa a que cada instncia de BCP estar atrelada
	private int creditos;				// Quantidade de creditos que o processo dispoe para que seja corretamente ordenado na lista de prontos
	
/* ---------------- CONSTRUTORES ----------------- */
	
	// PC, Status do Processo, X e Y so inicializados de modo padro. Prioridade  recebida aps extrao via arquivo, bem como o Nome do Programa.
	//  Referência de Memória  sendo o ponteiro para o buffer com o código do programa
	//	Créditos é inicializado com o mesmo valor de prioridade recebido
	
	public BCP(int prioridade, String[] buffer, String nomePrograma) {
		this.prioridade = prioridade;
		this.creditos = prioridade;
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
	
	public String getInstrucao(int linha) {
		return this.referenciaMemoria[linha];
	}
	
	public String getNomePrograma() {
		return this.nomePrograma;
	}
	
	public int getCreditos(){
		return this.creditos;
	}
	
	public void setPC(int PC) {
		this.PC = PC;
	}
	
	public void setStatusProcesso(char statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public void setCreditos(int creditos){
		this.creditos = creditos;
	}
	
	// Estabelece os critrios de comparao segundo a precedncia estabelecida (primeiramente verifica os crditos), depois,
	// somente em caso de crditos iguais, desempata por ordem alfabtica
	public int compareTo(BCP bcp) throws NullPointerException, ClassCastException{
		if (this.creditos < bcp.getCreditos())
			return -1;
		if (this.creditos > bcp.getCreditos())
			return 1;
		
		Pattern padrao = Pattern.compile("TESTE-");
		String[] comp1 = padrao.split(this.nomePrograma);
		String[] comp2 = padrao.split(bcp.getNomePrograma());
		
		if (Integer.parseInt(comp1[1]) < Integer.parseInt(comp2[1]))
			return -1;
		else
			return 1;		
	}
}