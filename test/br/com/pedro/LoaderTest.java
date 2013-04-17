package br.com.pedro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.pedro.exception.DetailException;
import br.com.pedro.exception.HeaderException;
import br.com.pedro.exception.TraillerException;
import br.com.pedro.model.Cedo;

public class LoaderTest {

	private static final String HEADER = "1150420130938";
	private static final String DETAIL = "209";
	private static final String TRAILLER = "310000000003";

	private static final String ERROR_HEADER = " Não possui Header";
	private static final String ERROR_DETAIL = " Não possui Detail";
	private static final String ERROR_TRAILLER = " Não possui Trailler";
	private static final String ERROR_QNT_LINHAS = " Quantidade de registros detalhe não bate com informação vinda do Trailler;";

	Loader loader;

	@Before
	public void setUp() {
		loader = new Loader();
	}

	@Test
	public void deveriaRealizarLeituraDoArquivo() {
		File file = new File("test/resources/layout.txt");
		List<Cedo> cedos;
		try {
			cedos = loader.load(new FileInputStream(file));

			assertNotNull(cedos);
			assertEquals("numero de itens", 3, cedos.size());

			Cedo cedo = cedos.get(0);

			assertEquals("header", HEADER, cedo.getHeader().getRecordType() + cedo.getHeader().getFileCreationDate()
					+ cedo.getHeader().getTimeOfFileGeneration());
			assertEquals("detail", DETAIL, cedo.getDetail().getRecordType() + cedo.getDetail().getReturnCode());
			assertEquals("trailler", TRAILLER, cedo.getTrailler().getRecordType() + cedo.getTrailler().getNumberOfRecords());

			// Asserting of BarCode Object
			assertEquals(cedo.getDetail().getBarCode().getMainService(), "22");
			assertEquals(cedo.getDetail().getBarCode().getCodeAdminContract(), "88888888");
			assertEquals(cedo.getDetail().getBarCode().getControlDate(), "666666");
			assertEquals(cedo.getDetail().getBarCode().getSequentialNumberObject(), "7777777");
			assertEquals(cedo.getDetail().getBarCode().getCodeControlCliente(), "10000000001");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void deveriaImportarArquivoSemHeaderEDarErro() {
		File file = new File("test/resources/layout_sem_header.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (HeaderException e) {
			assertEquals("Menssagem de erro", e.getMessage(), "Arquivo corrompido!" + ERROR_HEADER);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void deveriaImportarArquivoSemDetailEDarErro() {
		File file = new File("test/resources/layout_sem_detail.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (DetailException e) {
			assertEquals("Menssagem de erro", e.getMessage(), "Arquivo corrompido!" + ERROR_DETAIL);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void deveriaImportarArquivoSemTraillerEDarErro() {
		File file = new File("test/resources/layout_sem_trailler.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (TraillerException e) {
			assertEquals("Menssagem de erro", e.getMessage(), "Arquivo corrompido!" + ERROR_TRAILLER);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = IllegalStateException.class)
	public void deveriaDarErroAoLerArquivoComHeaderTrailerInvalido() {
		File file = new File("test/resources/layout_header_trailler_invalido.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (IllegalStateException e) {
			assertEquals("Layout do arquivo", "Arquivo corrompido! Header não possui tamanho 13; Trailler não possui tamanho 12;", e.getMessage());
			throw e;
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void deveriaPararImportacaoCom1DetailInvalido() {
		File file = new File("test/resources/layout_detail_invalido.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (Exception e) {
			assertEquals("Invalid Column", "Invalid Column at line: 2; 'Bar Code - Code of control client'.", e.getMessage());
		}

	}

	@Test
	public void deveriaDarErroAoLerArquivosComQuantidadeDeItensIncorreto() {
		File file = new File("test/resources/layout_qnt_linhas_incorreta.txt");
		try {
			loader.load(new FileInputStream(file));
		} catch (Exception e) {
			assertEquals("quantidade de linhas", "Arquivo corrompido!" + ERROR_QNT_LINHAS, e.getMessage());
		}
	}
}
