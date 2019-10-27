import java.io.*;

/* Classe que estabelece as caracteristicas e comportamentos do Logfile */

public class LogFile{
	
/* -------- ATRIBUTOS -------- */
	
	private static LogFile logFile = null;		// Instancia dessa classe, inicializada com null
	private BufferedWriter arqLogFile;			// Ponteiro para o arquivo de saida 
	private String[] bufferResposta;			// Buffer de Resposta
	private int iterador;						// Controlador global do indice do buffer de resposta

/* -------- CONSTRUTOReS -------- */
	
	// Recebe como parametro o quantum (N_COM) para que esse seja parte do nome do arquivo gerado. Constroi a stream do
	// arquivo de saida (LogFile), declara um buffer de resposta com 1000 linhas e inicia o iterador global
	private LogFile(int N_COM) throws IOException{
		String nomeDoArquivo = "log";
		
		if (N_COM < 10)
			nomeDoArquivo = nomeDoArquivo + 0;
		nomeDoArquivo = nomeDoArquivo + N_COM + ".txt";
		
		this.arqLogFile = new BufferedWriter(new FileWriter(nomeDoArquivo));
		this.bufferResposta = new String[1000];
		
		for (String linha : bufferResposta)
			linha = null;
		
		this.iterador = 0;
	}

/* -------- METODOS -------- */

	// Inclui no buffer de resposta as mensagens de carregamento dos processos, recebendo como parametro uma lista 
	// contendo os nomes dos processos ja ordenados pelo escalonador
	public void msgCarregaProcessos(String[] listaProcessos){
		for (String processo : listaProcessos){
			this.bufferResposta[this.iterador] = "Carregando " + processo;
			this.iterador++;
		}
	}
	
	// Inclui no buffer de resposta uma mensagem de processo deixando o status 'P' para 'e' (pronto -> executando), recebendo
	// como parametro o nome do processo em questao
	public void msgExecutaProcesso(String nomeProcesso){
		if (this.iterador < 1000){
			this.bufferResposta[this.iterador] = "Executando " + nomeProcesso;
			this.iterador++;
			System.out.println("Executando " + nomeProcesso);
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta uma mensagem de processo sendo interrompido apos o termino do seu quantum, recebendo
	// como parametros o nome do processo e quantidade de instrucoes que foi capaz de executar ate aquele momento
	public void msgInterrompeProcesso(String nomeProcesso, int qtdInstrucoes){
		if (this.iterador < 1000){
			String temp;
			String temp1 = "Interrompendo " + nomeProcesso + " após " + qtdInstrucoes;
			
			if (qtdInstrucoes == 1)
				temp = temp1 + " instrução";
			else
				temp = temp1 + " instruções";

			this.bufferResposta[this.iterador] = temp;
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta duas mensagens, sendo a primeira a deteccao de inicio de instrucao E/S e a segunda
	// do interrompimento provocado justamente por essa instrucao (que possivelmente ocorre antes do termino do quantum
	// do processo. Recebe como parametros o nome do processo e a quantidade de instrucoes que foi capaz de executar ate
	// aquele momento
	public void msgESProcesso(String nomeProcesso){
		if (this.iterador < 1000){
			this.bufferResposta[this.iterador] = "E/S iniciada em " + nomeProcesso;
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta a mensagem de termino de processamento de um dado processo, recebendo como parametros o
	// nome do processo e os valores contidos nos registradores X e Y
	public void msgFimProcesso(String nomeProcesso, int x, int y){
		if (this.iterador < 1000){
			this.bufferResposta[this.iterador] = nomeProcesso + " terminado. X=" + x + ". Y=" + y;
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta as mensagens de estatisticas, e chama o finalizador para gerar o .txt com o log pronto.
	// Recebe como parametros a media de trocas de processos, a media de instrucoes e o quantum utilizado como referência
	public void msgEstatisticas(double mediaTrocas, double mediaInstrucoes, int quantum){
		if (this.iterador < 998){
			this.bufferResposta[this.iterador] = "MEDIA DE TROCAS: " + mediaTrocas;
			iterador++;
			
			this.bufferResposta[this.iterador] = "MEDIA DE INSTRUCOES: " + mediaInstrucoes;
			iterador++;
			
			this.bufferResposta[this.iterador] = "QUANTUM: " + quantum;
			
			try {
				gravaBuffer();
			}
			catch(IOException e) {
				System.err.println("Erro na gravacao do LogFile");
				return;
			}
			
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Grava o buffer de respostas no arquivo e fecha a Stream, determinando o fim do processo e entrega do LogFile para o
	// o usuario
	private void gravaBuffer() throws IOException{
		if (this.arqLogFile != null){
			for (String linha : this.bufferResposta){
				if (linha != null){
					this.arqLogFile.write(linha, 0, linha.length());
					this.arqLogFile.newLine();
				}
				else 
					break; // Caso encontre alguma linha nula significa que desse momento em diante o buffer esta vazio
			}
			arqLogFile.close();
			System.out.println("Logfile gravado com sucesso");
		}
		else
			System.err.println("Erro na gravacao do LogFile");
	}
	
	// Metodo publico para retornar a instancia unica do objeto de LogFile. Recebe como parametro o quantum utilizado (N_COM)
	public static LogFile getInstance(int N_COM) throws IOException{
		if (logFile == null)
			logFile = new LogFile(N_COM);
		return logFile;	
	}
}