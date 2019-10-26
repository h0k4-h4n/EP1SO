public class Executador {
/* --------------- MAIN --------------- */

public static void main(String[] args) {
    Escalonador escalonador = new Escalonador();

    Construtor construtor = new Construtor();
    // Declara um BCP generico para realizar as chamadas, um contador de instrucoes
    // para determinar quantas instrucoes
    // cada processo realizou e chama a rotina de carregamento dos processos
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
    double acumuladorQuantum = 0;		// Soma o quantum (N_COM * quantum) a cada troca de processos

    // O escalonador executara os processos ate que todos tenham terminado. Para tal
    // o contador de instrucoes
    // inicia cada quantum com zero, obtem-se o BCP do processo de maior prioridade
    // (dada a implementacao via TreeSet,
    // esse processo estara por ultimo) e o status do mesmo e alterado para E -
    // Executando
    while (tabelaProcessos.size() > 0) {
        contaInstrucoes = 0;
        boolean finalizado = false;
        if(listaProntos.isEmpty()==false) {
            bcp = ((TreeSet<BCP>) listaProntos).last();
            bcp.setStatusProcesso('E');
            logFile.msgExecutaProcesso(bcp.getNomePrograma());
            acumuladorQuantum += (N_COM * bcp.getQuantum());

            // Executa instrucoes enquanto estiver com status E - Executando ou enquanto a
            // contagem de instrucoes nao superar
            // o numero de comandos por quantum multiplicado pela quantidade de quantum que
            // o processo detem
            while ((contaInstrucoes < bcp.getQuantum() * N_COM) && (bcp.getStatusProcesso() == 'E')
                    && finalizado == false) {
                contaInstrucoes++;
                acumuladorInstrucoes++;
                finalizado = interpretaCodigo(bcp, contaInstrucoes);
                bcp.setPC(bcp.getPC() + 1);
            }

            if (finalizado == false) {
                logFile.msgInterrompeProcesso(bcp.getNomePrograma(), contaInstrucoes);
                bcp.setQuantum(bcp.getQuantum()+1);
                bcp.setCreditos(bcp.getCreditos() - 2);
                if (bcp.getCreditos() < 0) {
                    bcp.setCreditos(0);
                }
                if(listaBloqueados.size()>0) {
                    decrementaTempBloqueados();
                }
                if(creditoNulo()){
                    restituiCreditos();
                }
                contaTrocas++;
                listaProntos = reordena();
            }
        }
        else {
            decrementaTempBloqueados();
        }
        //imprimeTabelaProcessos();
        //imprimeListaProntos();
    }
    double mediaTrocas = contaTrocas/10;
    double mediaInstrucoes = acumuladorInstrucoes/acumuladorQuantum;
    logFile.msgEstatisticas(mediaTrocas, mediaInstrucoes, N_COM);

}
}
}