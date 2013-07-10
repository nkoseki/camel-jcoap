package sample.component.jcoap;


import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JcoapConsumer extends DefaultConsumer{

	private static final transient Log LOG = LogFactory.getLog(JcoapConsumer.class);
	
	private JcoapEndpoint endpoint;
	private Processor processor;
	
	public JcoapConsumer(JcoapEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
		this.processor = processor;
	}

	/**
	 * コネクタが起動した時に呼び出される
	 * 
	 * @see org.apache.camel.impl.DefaultConsumer#doStart()
	 */
	protected void doStart() throws Exception {

	}

}
