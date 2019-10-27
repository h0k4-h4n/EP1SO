import java.io.*;
import java.util.*;
import java.util.Iterator;

public class Executador {

	public static int N_COM; 	// Numero de Comandos que cada processo tem direito a executar nas
								// condicoes iniciais, obtido pelo arquivo quantum.txt
	public static LogFile logFile;		

	// Constroi um buffer com o codigo do programa, para que haja o controle
	// realizado pelo PC
	public static String[] constroiBufferPrograma(BufferedReader arquivoPrograma, int contador) {
		String[] buffer = new String[contador];

		try {
			String linha = arquivoPrograma.readLine();

			for (int i = 0; linha != null; i++) {
				buffer[i] = linha;
				linha = arquivoPrograma.readLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo: " + arquivoPrograma.toString() + " nao encontrado");
			return null;
		} catch (IOException e) {
			System.err.println("Erro " + e.toString() + " na leitura do arquivo: " + arquivoPrograma.toString());
			return null;
		}

		return buffer;
	}

	// Realiza a contagem de linhas do arquivo programa
	public static int contaLinhasPrograma(BufferedReader arquivoPrograma) {
		int contador = 0;

		try {
			String linha = arquivoPrograma.readLine();

			while (linha != null) {
				contador++;
				linha = arquivoPrograma.readLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo: " + arquivoPrograma.toString() + " nao encontrado");
			return -1;
		} catch (IOException e) {
			System.err.println("Erro " + e.toString() + " na leitura do arquivo: " + arquivoPrograma.toString());
			return -1;
		}
		return contador;
	}

	public static BufferedReader[] geraArqProg(BufferedReader[] entrada) throws Exception {
		int auxNomeTxt = 0;
		String aux = "";
		try {
			for (int i = 0; i < 10; i++) {
				auxNomeTxt++;
				if (Integer.toString(auxNomeTxt).length() == 1) {
					aux = "0" + Integer.toString(auxNomeTxt) + ".txt";
				} else {
					aux = Integer.toString(auxNomeTxt) + ".txt";
				}
				entrada[i] = new BufferedReader(new FileReader(aux));
			}
			return entrada;
		} catch (Exception e) {
			throw e;
		}
	}

	public static int obtemMaiorPrioridade(BufferedReader arqPrioridade) {
		int maiorPrioridade = 0;
		int aux;

		try {
			String linha = arqPrioridade.readLine();

			while (linha != null) {
				aux = Integer.parseInt(linha);

				if (aux > maiorPrioridade)
					maiorPrioridade = aux;

				linha = arqPrioridade.readLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo: " + arqPrioridade.toString() + " nao encontrado");
			return -1;
		} catch (IOException e) {
			System.err.println("Erro " + e.toString() + " na leitura do arquivo: " + arqPrioridade.toString());
			return -1;
		}

		return maiorPrioridade;
	}

	// Rotina de inicializacao do escalonador - leitura dos arquivos e carregamento
	// dos processos
	public static void carregaProcessos(Escalonador escalonador) throws Exception {

		// Declaracao das instancias dos apontadores dos arquivos
		BufferedReader[] arqProg = new BufferedReader[10];

		arqProg = geraArqProg(arqProg);

		BufferedReader arqPrioridade = new BufferedReader(new FileReader("prioridades.txt"));
		BufferedReader arqQuantum = new BufferedReader(new FileReader("quantum.txt"));

		// Marcacao da quantidade maxima de caracteres permitida ate o Reset
		for (int i = 0; i < 10; i++) {
			arqProg[i].mark(1000);
		}
		arqPrioridade.mark(1000);

		// Declaracao dos buffers dos codigos de cada programa
		// Declaracao dos buffers dos codigos de cada programa
		String[] codProg1 = null;
		String[] codProg2 = null;
		String[] codProg3 = null;
		String[] codProg4 = null;
		String[] codProg5 = null;
		String[] codProg6 = null;
		String[] codProg7 = null;
		String[] codProg8 = null;
		String[] codProg9 = null;
		String[] codProg10 = null;

		// Declaracao das variaveis que contabilizam a quantidade de linhas de cada
		// programa

		int qtdLinhas[] = new int[10];
		for (int i = 0; i < 10; i++) {
			qtdLinhas[i] = contaLinhasPrograma(arqProg[i]);
		}

		// Testa se o retorno da contagem foi valida, em caso positivo encaminha para a
		// construcao dos buffers dos programas
		if (qtdLinhas[0] >= 0 && qtdLinhas[1] >= 0 && qtdLinhas[2] >= 0 && qtdLinhas[3] >= 0 && qtdLinhas[4] >= 0
				&& qtdLinhas[5] >= 0 && qtdLinhas[6] >= 0 && qtdLinhas[7] >= 0 && qtdLinhas[8] >= 0
				&& qtdLinhas[9] >= 0) {

			// Reposiciona cada ponteiro para a marcacao inicial de cada Stream
			for (int i = 0; i < 10; i++) {
				arqProg[i].reset();
			}

			// Chamada ao construtor do buffer
			codProg1 = constroiBufferPrograma(arqProg[0], qtdLinhas[0]);
			codProg2 = constroiBufferPrograma(arqProg[1], qtdLinhas[1]);
			codProg3 = constroiBufferPrograma(arqProg[2], qtdLinhas[2]);
			codProg4 = constroiBufferPrograma(arqProg[3], qtdLinhas[3]);
			codProg5 = constroiBufferPrograma(arqProg[4], qtdLinhas[4]);
			codProg6 = constroiBufferPrograma(arqProg[5], qtdLinhas[5]);
			codProg7 = constroiBufferPrograma(arqProg[6], qtdLinhas[6]);
			codProg8 = constroiBufferPrograma(arqProg[7], qtdLinhas[7]);
			codProg9 = constroiBufferPrograma(arqProg[8], qtdLinhas[8]);
			codProg10 = constroiBufferPrograma(arqProg[9], qtdLinhas[9]);

			// Caso algum deles retorne null, nao sera adicionado as estruturas - adiante
		} else
			System.exit(1); // Valores negativos indicam excecao tratada - arquivo comprometido - parada do
							// processamento

		int maiorPrioridade = obtemMaiorPrioridade(arqPrioridade);

		if (maiorPrioridade < 0) {
			System.err.println("Erro na obtencao da maior prioridade - inicializacao");
			System.exit(1);
		}

		arqPrioridade.reset();

		// Construcao da Tabela de Processos, da Lista de Prontos e da Lista de
		// Bloqueados
		escalonador.tabelaProcessos = new LinkedList<BCP>();
		escalonador.listaBloqueados = new LinkedList<BCP>();
		escalonador.listaProntos = new LinkedList[maiorPrioridade + 1];

		for (int i = 0; i < maiorPrioridade + 1; i++)
			escalonador.listaProntos[i] = new LinkedList<BCP>();

		// Declaracao dos BCPs de cada processo/programa
		BCP bcp1 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg1, codProg1[0]);
		BCP bcp2 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg2, codProg2[0]);
		BCP bcp3 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg3, codProg3[0]);
		BCP bcp4 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg4, codProg4[0]);
		BCP bcp5 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg5, codProg5[0]);
		BCP bcp6 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg6, codProg6[0]);
		BCP bcp7 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg7, codProg7[0]);
		BCP bcp8 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg8, codProg8[0]);
		BCP bcp9 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg9, codProg9[0]);
		BCP bcp10 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg10, codProg10[0]);

		// Se possuirem codigo a ser lido, os BCPs criados sao adicionados a Tabela de
		// Processos e a Lista de Prontos
		// (Ha a precedencia de que todo processo recem chegado vem com status 'P' -
		// pronto) - como consequencia, a Lista
		// de Bloqueados e inicializada somente com o no cabeca (sem processos
		// adicionados)

		if (codProg1 != null) {
			escalonador.tabelaProcessos.add(bcp1);
			escalonador.listaProntos[bcp1.getCreditos()].add(bcp1);
		}
		if (codProg2 != null) {
			escalonador.tabelaProcessos.add(bcp2);
			escalonador.listaProntos[bcp2.getCreditos()].add(bcp2);
		}
		if (codProg3 != null) {
			escalonador.tabelaProcessos.add(bcp3);
			escalonador.listaProntos[bcp3.getCreditos()].add(bcp3);
		}
		if (codProg4 != null) {
			escalonador.tabelaProcessos.add(bcp4);
			escalonador.listaProntos[bcp4.getCreditos()].add(bcp4);
		}
		if (codProg5 != null) {
			escalonador.tabelaProcessos.add(bcp5);
			escalonador.listaProntos[bcp5.getCreditos()].add(bcp5);
		}
		if (codProg6 != null) {
			escalonador.tabelaProcessos.add(bcp6);
			escalonador.listaProntos[bcp6.getCreditos()].add(bcp6);
		}
		if (codProg7 != null) {
			escalonador.tabelaProcessos.add(bcp7);
			escalonador.listaProntos[bcp7.getCreditos()].add(bcp7);
		}
		if (codProg8 != null) {
			escalonador.tabelaProcessos.add(bcp8);
			escalonador.listaProntos[bcp8.getCreditos()].add(bcp8);
		}
		if (codProg9 != null) {
			escalonador.tabelaProcessos.add(bcp9);
			escalonador.listaProntos[bcp9.getCreditos()].add(bcp9);
		}
		if (codProg10 != null) {
			escalonador.tabelaProcessos.add(bcp10);
			escalonador.listaProntos[bcp10.getCreditos()].add(bcp10);
		}

		// Leitura do arquivo quantum.txt e declaracao do LogFile
		N_COM = Integer.parseInt(arqQuantum.readLine());
		logFile = LogFile.getInstance(N_COM);

		// Fechamento das Streams dos arquivos
		for (int i = 0; i < 10; i++) {
			arqProg[i].close();
		}
		arqPrioridade.close();
		arqQuantum.close();

		// Confeccao do log de carregamento dos processos - cria um iterador, um buffer
		// com os nomes dos processos e
		// chama o metodo do logFile
		Iterator<BCP> it;
		String[] nomesProcessos = new String[10];
		int j = 0;

		for (int i = escalonador.listaProntos.length - 1; i >= 0; i--) {
			it = escalonador.listaProntos[i].iterator();

			while (it.hasNext()) {
				nomesProcessos[j] = it.next().getNomePrograma();
				j++;
			}
		}

		logFile.msgCarregaProcessos(nomesProcessos);
	}

	// Interpreta uma linha de codigo recebida e executa as instrucoes nela contidas
	public static boolean interpretaCodigo(BCP bcp, int qtdInstrucoes, Escalonador escalonador) {
		char[] linhaFatorada = bcp.getInstrucao(bcp.getPC()).toCharArray();

		if (linhaFatorada[0] == 'E') {
			logFile.msgESProcesso(bcp.getNomePrograma());
			bcp.setStatusProcesso('B');

			if (escalonador.listaProntos[bcp.getCreditos()].remove(bcp) == false)
				System.err.println("BCP " + bcp.getNomePrograma() + " nao encontrado na lista de prontos para que"
						+ " seja removido");

			escalonador.listaBloqueados.addLast(bcp);
			bcp.setTemporizador(3); // Inicia com 3 pois o decremento ocorre ao fim do ciclo, antes que outro
									// processo assuma a CPU
		}

		else if (linhaFatorada[0] == 'X') {
			if (linhaFatorada.length > 3) {
				String valor = "";

				for (int i = 2; i < linhaFatorada.length; i++)
					valor += linhaFatorada[i];

				bcp.setX(Integer.parseInt(valor));
			} else
				bcp.setX(Character.getNumericValue(linhaFatorada[2]));
		}

		else if (linhaFatorada[0] == 'Y') {
			if (linhaFatorada.length > 3) {
				String valor = "";

				for (int i = 2; i < linhaFatorada.length; i++)
					valor += linhaFatorada[i];

				bcp.setY(Integer.parseInt(valor));
			} else
				bcp.setY(Character.getNumericValue(linhaFatorada[2]));
		}

		else if (linhaFatorada[0] == 'S') {
			logFile.msgFimProcesso(bcp.getNomePrograma(), bcp.getX(), bcp.getY());

			if (escalonador.tabelaProcessos.remove(bcp) == false
					|| escalonador.listaProntos[bcp.getCreditos()].remove(bcp) == false)
				System.err.println("BCP " + bcp.getNomePrograma() + " nao encontrado na tabela ou na lista de prontos"
						+ " para que seja removido");
			else
				return true;
		}

		return false;
	}

/* --------------- MAIN --------------- */

	public static void main(String[] args) {

		Escalonador escalonador = new Escalonador();

		try {
			carregaProcessos(escalonador);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BCP bcp = null;
		int contaInstrucoes;
		double contaTrocas = 0;
		double acumuladorInstrucoes = 0;
		double acumuladorQuantum = 0;
		int auxIndiceListaProntos;

		// O escalonador executara os processos ate que todos tenham terminado. Para tal
		// o contador de instrucoes
		// inicia cada quantum com zero, obtem-se o BCP do processo de maior prioridade
		// e o status do mesmo e alterado para E - Executando

		while (escalonador.getProcessos().size() > 0) {
			contaInstrucoes = 0;
			boolean processoConcluido = false;

			if (escalonador.listaProntosVazia() == false) {
				bcp = escalonador.obtemBCPMaiorCredito();
				bcp.setStatusProcesso('E');
				logFile.msgExecutaProcesso(bcp.getNomePrograma());
				acumuladorQuantum += (N_COM * bcp.getQuantum());

				escalonador.imprimeListaProntos();

				// Executa instrucoes enquanto estiver com status E - Executando ou enquanto a
				// contagem de instrucoes nao superar
				// o numero de comandos por quantum multiplicado pela quantidade de quantum que
				// o processo detem
				while ((contaInstrucoes < bcp.getQuantum() * N_COM) && (bcp.getStatusProcesso() == 'E')
						&& processoConcluido == false) {
					contaInstrucoes++;
					acumuladorInstrucoes++;
					processoConcluido = interpretaCodigo(bcp, contaInstrucoes, escalonador);
					bcp.setPC(bcp.getPC() + 1);
				}

				if (processoConcluido == false) {
					logFile.msgInterrompeProcesso(bcp.getNomePrograma(), contaInstrucoes);

					if (bcp.getCreditos() > 0 && bcp.getStatusProcesso() != 'B') {
						auxIndiceListaProntos = bcp.getCreditos();
						escalonador.listaProntos[auxIndiceListaProntos].remove(bcp);
						auxIndiceListaProntos -= 2;

						if (auxIndiceListaProntos < 0)
							auxIndiceListaProntos = 0;

						escalonador.listaProntos[auxIndiceListaProntos].addFirst(bcp);
					} else if (bcp.getCreditos() == 0 && bcp.getStatusProcesso() != 'B')
						escalonador.listaProntos[0].addLast(bcp);

					bcp.setQuantum(bcp.getQuantum() + 1);
					bcp.setCreditos(bcp.getCreditos() - 2);

					if (bcp.getCreditos() <= 0) {
						bcp.setCreditos(0);
						bcp.setQuantum(1);
					}

					if(escalonador.getBloqueados()!= null){
						if (escalonador.getBloqueados().size() > 0)
							escalonador.decrementaTempBloqueados();
					}

					if (escalonador.creditoNulo()) 
						escalonador.restituiCreditos();
			
					contaTrocas++;
				}
			} else
				escalonador.decrementaTempBloqueados();
		}
		double mediaTrocas = contaTrocas / 10;
		double mediaInstrucoes = acumuladorInstrucoes / acumuladorQuantum;
		logFile.msgEstatisticas(mediaTrocas, mediaInstrucoes, N_COM);
	}

}