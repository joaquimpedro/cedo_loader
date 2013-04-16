package br.com.pedro.model.layouts;

public class Detail {

	private String recordType;
	private BarCode barCode;
	private String returnCode;

	public Detail() {
	}

	public Detail(String recordType, BarCode barCode, String returnCode) {
		this.recordType = recordType;
		this.barCode = barCode;
		this.returnCode = returnCode;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public BarCode getBarCode() {
		return barCode;
	}

	public void setBarCode(BarCode barCode) {
		this.barCode = barCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

}
