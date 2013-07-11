package sample.component.jcoap;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JcoapComponent extends DefaultComponent{

	private static final transient Log LOG = LogFactory.getLog(JcoapComponent.class);
	
	@Override
	protected Endpoint createEndpoint(String uri, String remaining,
			Map<String, Object> parameters) throws Exception {
		
		LOG.debug("JcoapComponent#createEndpoint Called");

		Endpoint endpoint = new JcoapEndpoint(uri, this);
		setProperties(endpoint, parameters);
		return endpoint;
	}

}
