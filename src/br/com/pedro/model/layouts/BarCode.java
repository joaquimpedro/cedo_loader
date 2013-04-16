package br.com.pedro.model.layouts;

public class BarCode {

	private String mainService;
	private String codeAdminContract;
	private String controlDate;
	private String sequentialNumberObject;
	private String codeControlCliente;

	public BarCode(String mainService, String codeAdminContract, String controlDate, String sequentialNumberObject, String codeControlCliente) {
		super();
		this.mainService = mainService;
		this.codeAdminContract = codeAdminContract;
		this.controlDate = controlDate;
		this.sequentialNumberObject = sequentialNumberObject;
		this.codeControlCliente = codeControlCliente;
	}

	public BarCode() {
	}

	public String getMainService() {
		return mainService;
	}

	public void setMainService(String mainService) {
		this.mainService = mainService;
	}

	public String getCodeAdminContract() {
		return codeAdminContract;
	}

	public void setCodeAdminContract(String codeAdminContract) {
		this.codeAdminContract = codeAdminContract;
	}

	public String getControlDate() {
		return controlDate;
	}

	public void setControlDate(String controlDate) {
		this.controlDate = controlDate;
	}

	public String getSequentialNumberObject() {
		return sequentialNumberObject;
	}

	public void setSequentialNumberObject(String sequentialNumberObject) {
		this.sequentialNumberObject = sequentialNumberObject;
	}

	public String getCodeControlCliente() {
		return codeControlCliente;
	}

	public void setCodeControlCliente(String codeControlCliente) {
		this.codeControlCliente = codeControlCliente;
	}

}
