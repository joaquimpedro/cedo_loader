package br.com.pedro.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileUtils {

	/**
	 * Abre <code>file</code> usando <b>UTF-8</b> como charset e retorna um
	 * <code>BufferedReader</code>. É necessário fechar o reader após
	 * utilização.
	 */
	public static BufferedReader readFileWithUtf8Encoding(File file) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			return reader;
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"Não é possível abrir arquivo para leitura com encoding UTF-8.",
					e);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(
					"Arquivo não existe ou não tem permissão de leitura.", e);
		}
	}
}
