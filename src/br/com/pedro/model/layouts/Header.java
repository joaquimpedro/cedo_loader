package br.com.pedro.model.layouts;


public class Header {

	private String recordType;
	private String fileCreationDate;
	private String timeOfFileGeneration;

	public Header() {
	}

	public Header(String recordType, String fileCreationDate,
			String timeOfFileGeneration) {
		this.recordType = recordType;
		this.fileCreationDate = fileCreationDate;
		this.timeOfFileGeneration = timeOfFileGeneration;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getFileCreationDate() {
		return fileCreationDate;
	}

	public void setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}

	public String getTimeOfFileGeneration() {
		return timeOfFileGeneration;
	}

	public void setTimeOfFileGeneration(String timeOfFileGeneration) {
		this.timeOfFileGeneration = timeOfFileGeneration;
	}

}
