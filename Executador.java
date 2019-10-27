import java.util.Iterator;

public class Executador {
/* --------------- MAIN --------------- */

	public static void main(String[] args) {

		Escalonador escalonador = new Escalonador();
	
		try {
			escalonador.carregaProcessos();
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
		
		while (escalonador.tabelaProcessos.size() > 0) {
			contaInstrucoes = 0;
			boolean processoConcluido = false;
			
			if(escalonador.listaProntosVazia() == false) {
				bcp = escalonador.obtemBCPMaiorCredito();
				bcp.setStatusProcesso('E');
				escalonador.logFile.msgExecutaProcesso(bcp.getNomePrograma());
				acumuladorQuantum += (escalonador.N_COM * bcp.getQuantum());
				
				escalonador.imprimeListaProntos();

				// Executa instrucoes enquanto estiver com status E - Executando ou enquanto a
				// contagem de instrucoes nao superar
				// o numero de comandos por quantum multiplicado pela quantidade de quantum que
				// o processo detem
				while ((contaInstrucoes < bcp.getQuantum() * escalonador.N_COM) && (bcp.getStatusProcesso() == 'E')
						&& processoConcluido == false) {
					contaInstrucoes++;
					acumuladorInstrucoes++;
					processoConcluido = escalonador.interpretaCodigo(bcp, contaInstrucoes);
					bcp.setPC(bcp.getPC() + 1);
				}

				if (processoConcluido == false) {
					escalonador.logFile.msgInterrompeProcesso(bcp.getNomePrograma(), contaInstrucoes);
					
					if (bcp.getCreditos() > 0 && bcp.getStatusProcesso() != 'B'){
						auxIndiceListaProntos = bcp.getCreditos();
						escalonador.listaProntos[auxIndiceListaProntos].remove(bcp);
						auxIndiceListaProntos -= 2;
						
						if (auxIndiceListaProntos < 0)
							auxIndiceListaProntos = 0;
					
						escalonador.listaProntos[auxIndiceListaProntos].addFirst(bcp);
					}
					else if (bcp.getCreditos() == 0 && bcp.getStatusProcesso() != 'B')
						escalonador.listaProntos[0].addLast(bcp);
					
					bcp.setQuantum(bcp.getQuantum() + 1);
					bcp.setCreditos(bcp.getCreditos() - 2);
					
					if (bcp.getCreditos() <= 0) {
						bcp.setCreditos(0);
						bcp.setQuantum(1);
					}
					
					if(escalonador.listaBloqueados.size() > 0) 
						escalonador.decrementaTempBloqueados();
					
					if(escalonador.creditoNulo()){
						escalonador.restituiCreditos();
						escalonador.reordenaListaProntos();
					}
					
					contaTrocas++;
				}
			}
			else 
				escalonador.decrementaTempBloqueados();
		} 
		double mediaTrocas = contaTrocas / 10;
		double mediaInstrucoes = acumuladorInstrucoes / acumuladorQuantum;
		escalonador.logFile.msgEstatisticas(mediaTrocas, mediaInstrucoes, escalonador.N_COM);
	}
}