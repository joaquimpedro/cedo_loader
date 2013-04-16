package br.com.pedro.model.layouts;

public class Trailler {

	private String recordType;
	private String numberOfRecords;

	public Trailler() {
	}

	public Trailler(String recordType, String numberOfRecords) {
		this.recordType = recordType;
		this.numberOfRecords = numberOfRecords;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(String numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

}
