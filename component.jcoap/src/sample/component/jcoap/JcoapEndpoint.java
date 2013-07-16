package sample.component.jcoap;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JcoapEndpoint  extends DefaultEndpoint{

	private static final transient Log LOG = LogFactory.getLog(JcoapEndpoint.class);
	
	private String ContentType;
	private String Token;
	private String UriHost;
	private String UriPort;
	private String UriPath;
	private String UriQuery;
	private String ProxyUri;

	public JcoapEndpoint(){
		
	}
	
	public JcoapEndpoint(String uri, JcoapComponent component){
		super(uri, component);
	}
	


	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new JcoapConsumer(this, processor);
	}

	@Override
	public Producer createProducer() throws Exception {
		return new JcoapProducer(this);
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getUriHost() {
		return UriHost;
	}

	public void setUriHost(String uriHost) {
		UriHost = uriHost;
	}

	public String getUriPort() {
		return UriPort;
	}

	public void setUriPort(String uriPort) {
		UriPort = uriPort;
	}

	public String getUriPath() {
		return UriPath;
	}

	public void setUriPath(String uriPath) {
		UriPath = uriPath;
	}

	public String getUriQuery() {
		return UriQuery;
	}

	public void setUriQuery(String uriQuery) {
		UriQuery = uriQuery;
	}

	public String getProxyUri() {
		return ProxyUri;
	}

	public void setProxyUri(String proxyUri) {
		ProxyUri = proxyUri;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
}
