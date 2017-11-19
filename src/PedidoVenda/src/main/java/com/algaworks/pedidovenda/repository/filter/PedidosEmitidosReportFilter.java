package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class PedidosEmitidosReportFilter implements Serializable {

	private static final long serialVersionUID = -7818929622602443786L;

	@NotNull
	private Date dataCriacaoDe;
	
	@NotNull
	private Date dataCriacaoAte;

	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}

	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}

	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}

	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}

}
