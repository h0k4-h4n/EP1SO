import java.io.*;
import java.util.*;

public class Construtor {

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
}