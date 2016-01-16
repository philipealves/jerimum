package br.com.jerimum.fw.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 */
public final class WebServiceUtils {

	private WebServiceUtils() {

	}

	public static Service getService(String wsdlLocation, String targetNamespace, String serviceName)
			throws MalformedURLException {

		URL url = new URL(wsdlLocation);
		QName qname = new QName(targetNamespace, serviceName);
		return Service.create(url, qname);
	}

	public static <T> T getPort(Class<T> portInterface, Service service, String wsdlLocation) {

		T port = service.getPort(portInterface);
		BindingProvider bindingProvider = (BindingProvider) port;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation);
		return port;
	}

	public static <T> T getPort(Class<T> portInterface, String wsdlLocation, String targetNamespace, String serviceName)
			throws MalformedURLException {

		Service service = getService(wsdlLocation, targetNamespace, serviceName);
		return getPort(portInterface, service, wsdlLocation);
	}

	/**
	 * Retorna o endereco IP de origem da solicitacao.
	 * 
	 * @param wsContext
	 * @return {@link String}
	 */
	public static String getRemoteIpAddress(WebServiceContext wsContext) {

		MessageContext msgx = wsContext != null ? wsContext.getMessageContext() : null;
		HttpServletRequest request = (HttpServletRequest) (msgx != null ? msgx.get(MessageContext.SERVLET_REQUEST)
				: null);
		return getRemoteIpAddress(request);
	}

	/**
	 * Retorna o endereco IP de origem da solicitacao.
	 * 
	 * @param wsContext
	 * @return {@link String}
	 */
	public static String getRemoteIpAddress(HttpServletRequest request) {

		String ipAddress = null;
		if (request != null) {
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (StringUtils.isBlank(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
		}

		return ipAddress;
	}
}
