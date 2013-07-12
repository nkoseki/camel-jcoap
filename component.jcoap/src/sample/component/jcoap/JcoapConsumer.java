package sample.component.jcoap;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ws4d.coap.connection.BasicCoapChannelManager;
import org.ws4d.coap.interfaces.CoapChannelManager;
import org.ws4d.coap.interfaces.CoapMessage;
import org.ws4d.coap.interfaces.CoapRequest;
import org.ws4d.coap.interfaces.CoapServer;
import org.ws4d.coap.interfaces.CoapServerChannel;
import org.ws4d.coap.messages.CoapMediaType;
import org.ws4d.coap.messages.CoapResponseCode;




public class JcoapConsumer extends DefaultConsumer implements CoapServer{

	private static final transient Log LOG = LogFactory.getLog(JcoapConsumer.class);
	
	private JcoapEndpoint endpoint;
	private Processor processor;
	
	private static final int PORT = 5683;
    static int counter = 0;
    
	public JcoapConsumer(JcoapEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
		this.processor = processor;
		LOG.debug("JcoapConsumer called");
		
	}

	/**
	 * コネクタが起動した時に呼び出される
	 * 
	 * @see org.apache.camel.impl.DefaultConsumer#doStart()
	 */
	protected void doStart() throws Exception {
		LOG.debug("doStart() called");
		
        System.out.println("Start CoAP Server on port " + PORT);

        CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();
        channelManager.createServerListener(this, PORT);
        super.doStart();
	}

	@Override
	public CoapServer onAccept(CoapRequest request) {
		LOG.debug("********** BasicCoapServer#onAccept called");
		System.out.println("Accept connection...");
		return this;
	}

	@Override
	public void onRequest(CoapServerChannel channel, CoapRequest request) {
		LOG.debug("*********** BasicCoapServer#onRequest called");
		System.out.println("Received message: " + request.toString()+ " URI: " + request.getUriPath());
		//////////////////////////////////////////////////////////
		Exchange exchange = endpoint.createExchange();
//		String val1 = endpoint.getKey1();
//		String val2 = endpoint.getKey2();
//		String val3 = endpoint.getKey3();
//		String props = "PROPERTIES: " + val1 + ", " + val2 + ", " + val3;
		
		exchange.getIn().setBody(request);
		
		try{
				getProcessor().process(exchange);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(exchange.getException()!= null){
				getExceptionHandler().handleException("-------Error processing exchange", 
						exchange, exchange.getException());
			}
		}
		LOG.debug("-------------exchange: " + exchange.getIn().getBody(String.class));
		String resultMessage = exchange.getIn().getBody(String.class);
		//////////////////////////////////////////////////////////
		CoapMessage response = channel.createResponse(request,
				CoapResponseCode.Content_205);
		response.setContentType(CoapMediaType.text_plain);
		
		//response.setPayload("payload...".getBytes());
		response.setPayload(resultMessage.getBytes());
		
		if (request.getObserveOption() != null){
			System.out.println("Client wants to observe this resource.");
		}
		
		response.setObserveOption(1);
		channel.sendMessage(response);
	}

	@Override
	public void onSeparateResponseFailed(CoapServerChannel channel) {
		System.out.println("Separate response transmission failed.");
	}

}
