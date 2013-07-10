package sample.component.jcoap;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

public class JcoapEndpoint  extends DefaultEndpoint{

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
