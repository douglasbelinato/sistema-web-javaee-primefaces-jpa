package com.algaworks.pedidovenda.util.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.algaworks.pedidovenda.service.NegocioException;

// 9.1. Tratando a exceção ViewExpiredException
// 9.2. Tratando outras exceções

public class JsfExceptionHandler extends ExceptionHandlerWrapper {
	
	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

	private ExceptionHandler wrapped;
	
	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public ExceptionHandler getWrapped() {		
		return this.wrapped;
	}
	
	// Chamado quando houver alguma exceção
	@Override
	public void handle() throws FacesException {		
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();		// representa o evento da exceção (há uma exceção)
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource(); // obtem o contexto com a origem da exceção
			
			Throwable exception = context.getException();		// obtem a exceção em si
			NegocioException negocioException = getNegocioException(exception);		// 9.2. Tratando outras exceções
			
			boolean handled = false;
			
			try {
				// Agora que eu já sei qual é a exceção, eu trato ela da forma como eu quero
				if(exception instanceof ViewExpiredException) {
					handled = true;
					redirect("/");
				} else if (negocioException != null) {		// 9.2. Tratando outras exceções
					handled = true;
					FacesUtil.addErrorMessage(negocioException.getMessage());
				} else {
					handled = true;
					log.error("Erro de sistema:" + exception.getMessage(), exception);
					redirect("/Erro.xhtml");
				}
			} finally {
				if (handled) {
					//remove o evento identificado no tratamento
					events.remove();
				}
			}
		}
		
		// Chama o handle() para informar ao tratador de eventos do JSF que nós já terminamos o nosso tratamento
		// dos eventos (exceções) . Assim, para as demais exceções que possam existir, o JSF pode manipulá-las e
		// tratá-las da forma como quiser.externalContext
		getWrapped().handle();
	}	
	
	// 9.2. Tratando outras exceções
	private NegocioException getNegocioException(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}
		
		return null;
	}
	
	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();		
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();	//nesse caso, retorna o contexto "PedidoVenda" (http://localhost:8080/PedidoVenda/)
			
			externalContext.redirect(contextPath + page);		
			facesContext.responseComplete();	// informa que a resposta está completa evitando mais ações no ciclo de vida do JSF
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}
