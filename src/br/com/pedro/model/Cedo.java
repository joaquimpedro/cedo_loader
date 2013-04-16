package br.com.pedro.model;

import br.com.pedro.model.layouts.Detail;
import br.com.pedro.model.layouts.Header;
import br.com.pedro.model.layouts.Trailler;

public class Cedo {

	private Header header;
	private Detail detail;
	private Trailler trailler;

	public Cedo() {
	}

	public Cedo(Header header, Detail detail, Trailler trailler) {
		this.header = header;
		this.detail = detail;
		this.trailler = trailler;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public Trailler getTrailler() {
		return trailler;
	}

	public void setTrailler(Trailler trailler) {
		this.trailler = trailler;
	}
}
