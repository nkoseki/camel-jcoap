package sample.component.jcoap;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JcoapEndpoint  extends DefaultEndpoint{

	private static final transient Log LOG = LogFactory.getLog(JcoapEndpoint.class);
	
	private String key1;
	private String key2;
	private String key3;
	
	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
}
