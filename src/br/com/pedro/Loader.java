package br.com.pedro;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.pedro.exception.ColumnException;
import br.com.pedro.exception.DetailException;
import br.com.pedro.exception.HeaderException;
import br.com.pedro.exception.TraillerException;
import br.com.pedro.model.Cedo;
import br.com.pedro.model.layouts.BarCode;
import br.com.pedro.model.layouts.Detail;
import br.com.pedro.model.layouts.Header;
import br.com.pedro.model.layouts.Trailler;
import br.com.pedro.util.FileUtils;

public class Loader {

	private String line;
	private Header header;
	private Trailler trailler;

	public List<Cedo> load(File file) throws Exception {

		List<Cedo> cedos = new ArrayList<Cedo>();
		List<Detail> details = new ArrayList<Detail>();
		try {
			BufferedReader reader = FileUtils.readFileWithUtf8Encoding(file);

			validations(file);

			line = reader.readLine();
			while (line != null) {
				try {
					Detail detail = null;
					Object obj = importer();

					if (isDetail()) {
						detail = (Detail) obj;
					} else if (isHeader()) {
						header = (Header) obj;
					} else if (isTrailler()) {
						trailler = (Trailler) obj;
					}

					if (detail != null)
						details.add(detail);
				} catch (ColumnException e) {
					e.printStackTrace();
				}
				line = reader.readLine();
			}

			for (Detail detail : details) {
				cedos.add(new Cedo(header, detail, trailler));
			}

		} catch (Exception e) {
			throw e;
		}
		return cedos;
	}

	private void validations(File file) throws IOException, ColumnException, HeaderException, DetailException, TraillerException {
		BufferedReader reader = FileUtils.readFileWithUtf8Encoding(file);
		String errorMessage = "";
		line = reader.readLine();

		boolean header = false;
		boolean detail = false;
		boolean trailler = false;
		int qntLines = 0;
		int qntLinesReal = 0;
		while (line != null) {
			qntLinesReal = qntLinesReal == 0 ? 1 : qntLinesReal;

			header = isHeader() ? isHeader() : header;
			detail = isDetail() ? isDetail() : detail;
			if (isTrailler()) {
				trailler = true;

				if (line.length() < 12) {
					errorMessage = errorMessage + " Trailler não possui tamanho 12;";
					qntLines = qntLinesReal - 2;
					break;
				}

				qntLines = Integer.valueOf(getNumberOfRecords());
			}

			if (isHeader() && line.length() != 13)
				errorMessage = errorMessage + " Header não possui tamanho 13;";

			if ((line = reader.readLine()) != null)
				qntLinesReal++;
		}

		if (qntLines != (qntLinesReal - 2))
			errorMessage = errorMessage + " Quantidade de registros detalhe não bate com informação vinda do Trailler;";
		if (!header)
			throw new HeaderException("Arquivo corrompido! Não possui Header");
		if (!detail)
			throw new DetailException("Arquivo corrompido! Não possui Detail");
		if (!trailler)
			throw new TraillerException("Arquivo corrompido! Não possui Trailler");

		if (!errorMessage.isEmpty())
			throw new IllegalStateException("Arquivo corrompido!" + errorMessage);

		line = null;
	}

	private Object importer() throws ColumnException {
		if (isHeader())
			return getHeader();
		else if (isDetail())
			return getDetail();
		else
			return getTrailler();
	}

	private Object getTrailler() throws ColumnException {
		return new Trailler(getRecordType(2), getNumberOfRecords());
	}

	private Object getDetail() throws ColumnException {
		return new Detail(getRecordType(1), getBarCode(), getReturnCode());
	}

	private Object getHeader() throws ColumnException {
		return new Header(getRecordType(1), getFileCreationDate(), getTimeOfFileGeneration());
	}

	private String getNumberOfRecords() throws ColumnException {
		return substring(2, 12, "Number of records");
	}

	private String getReturnCode() throws ColumnException {
		return substring(35, 37, "Return code");
	}

	private BarCode getBarCode() throws ColumnException {
		return new BarCode(substring(1, 3, "Bar Code - Main Service"), substring(3, 11, "Bar Code - Code Admin"), substring(11, 17, "Bar Code - Control Date"),
				substring(17, 24, "Bar Code - Number sequential of object"), substring(24, 35, "Bar Code - Code of control client"));
	}

	private String getTimeOfFileGeneration() throws ColumnException {
		return substring(9, 13, "Time of file generation");
	}

	private String getFileCreationDate() throws ColumnException {
		return (substring(1, 9, "File creation date"));
	}

	private String getRecordType(int lengthOfRecordType) throws ColumnException {
		return substring(0, lengthOfRecordType, "Record Type");
	}

	private String substring(int start, int end, String columnName) throws ColumnException {
		try {
			return line.substring(start, end);
		} catch (Exception e) {
			throw new ColumnException("Invalid Column '" + columnName + "'.");
		}
	}

	// private Date toDate(String data, String columnName) {
	// try {
	// return DateUtils.parseDate(data, new String[] { "ddMMyy" });
	// } catch (ParseException e) {
	// throw new IllegalArgumentException("Invalid Column '" + columnName +
	// "' (invalid date).");
	// }
	// }

	private boolean isHeader() {
		return line.startsWith("1");
	}

	private boolean isDetail() {
		return line.startsWith("2");
	}

	private boolean isTrailler() {
		return line.startsWith("31");
	}
}
