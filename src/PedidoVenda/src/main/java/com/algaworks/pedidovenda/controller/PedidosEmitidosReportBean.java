package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.algaworks.pedidovenda.repository.filter.PedidosEmitidosReportFilter;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class PedidosEmitidosReportBean implements Serializable {

	private static final long serialVersionUID = -6244592547458687314L;

	private PedidosEmitidosReportFilter filtro;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private EntityManager manager;
	
	public PedidosEmitidosReportBean() {
		filtro = new PedidosEmitidosReportFilter();
	}

	public void emitir() {		
		Map<String, Object> parametros = new HashMap<>();
		// chave dos parametros (data_inicio e data_fim) devem ser exatamente iguais às variáveis  
		// declaradas na construção do jasper report.
		parametros.put("data_inicio", this.getFiltro().getDataCriacaoDe());
		parametros.put("data_fim", this.getFiltro().getDataCriacaoAte());
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_pedidos_emitidos.jasper", this.response, parametros, "Pedidos emitidos.pdf");		
		Session session = manager.unwrap(Session.class);
		
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			// coloco responseComplete() porque depois que o PDF é gerado, o JSF deve interromper
			// seu fluxo de execução. Assim, a página não é renderizada de novo para o usuário.
			// Colocando responseComplete() explicitamente assim estou dizendo ao framework do 
			// JSF que eu já fiz tudo que era preciso.
			// Se eu não colocar isso, pode ocorrer erro toda vez que eu pedir para executar um report.
			facesContext.responseComplete();			
		} else {
			FacesUtil.addWarningMessage("A execução do relatório não retornou dados.");
		}
	}

	public PedidosEmitidosReportFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(PedidosEmitidosReportFilter filtro) {
		this.filtro = filtro;
	}

}
