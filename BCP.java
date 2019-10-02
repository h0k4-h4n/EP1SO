// Classe que representa o BCP de um processo

public class BCP {
	
/* ------------ ATRIBUTOS ------------- */
	
	private int PC = 1;					// Representa a instrução (linha) que está sendo executada - default = 1, pois a primeira linha do processo é o 
										//                                                                                             nome do programa
	private char statusProcesso = 'P';	// Recebe 'E', 'P' ou 'B' - Executando, Pronto ou Bloqueado, respectivamente
	private int prioridade;				// Inteiro, iniciado com o valor originado pelo arquivo .txt e que sofrerá alterações ao longo das execuções
	private int X = 0;					// 1º registrador de uso geral - inicializado com zero (0)
	private int Y = 0;					// 2º registrador de uso geral - inicializado com zero (0)
	private String[] referenciaMemoria;	// Referência para a região de memória em que está o código do programa executado
	private String nomePrograma;		// Nome do programa a que cada instância de BCP estará atrelada
	
/* ---------------- CONSTRUTORES ----------------- */
	
	// PC, Status do Processo, X e Y são inicializados de modo padrão. Prioridade é recebida após extração via arquivo, bem como o Nome do Programa.
	//     Referência de Memória é apenas um endereço de memória do próprio Java, tendo sua declaração na construção do BCP
	
	public BCP(int prioridade, String[] buffer, String nomePrograma) {
		this.prioridade = prioridade;
		this.referenciaMemoria = buffer;
		this.nomePrograma = nomePrograma;
	}
	
/* --------------- MÉTODOS ------------------- */
	
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
	
	// Estabelece os critérios de comparação segundo a precedência estabelecida (primeiramente verifica os créditos), depois,
	// somente em caso de créditos iguais, desempata por ordem alfabética
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