package sample.component.jcoap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ws4d.coap.Constants;
import org.ws4d.coap.connection.BasicCoapChannelManager;
import org.ws4d.coap.interfaces.CoapChannelManager;
import org.ws4d.coap.interfaces.CoapClient;
import org.ws4d.coap.interfaces.CoapClientChannel;
import org.ws4d.coap.interfaces.CoapRequest;
import org.ws4d.coap.interfaces.CoapResponse;
import org.ws4d.coap.messages.CoapMediaType;
import org.ws4d.coap.messages.CoapRequestCode;

public class JcoapProducer extends DefaultProducer implements CoapClient{

	private static final transient Log LOG = LogFactory.getLog(JcoapProducer.class);
	
	private JcoapEndpoint endpoint;
    private CoapChannelManager channelManager = null;
    private CoapClientChannel clientChannel = null;
    private CoapRequest coapRequest = null;

	private String SERVER_ADDRESS = "localhost";
    private int PORT = Constants.COAP_DEFAULT_PORT;

	
	public JcoapProducer(Endpoint endpoint) {
		super(endpoint);
		this.endpoint = (JcoapEndpoint) endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		//
		connect();
		super.doStart();
	}
	
	
	synchronized private void connect() {
		
		if(endpoint.getUriHost() != null){
			SERVER_ADDRESS = endpoint.getUriHost();
		}
		if(endpoint.getUriPort() != null){
			PORT = Integer.parseInt(endpoint.getUriPort());
		}
		channelManager = BasicCoapChannelManager.getInstance();
		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(SERVER_ADDRESS), PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GET 固め打ち→GET,POST,PUT,DELETEの4つが必要
		coapRequest = clientChannel.createRequest(true, CoapRequestCode.GET);
		//これは固定？？？
		//coapRequest.setContentType(CoapMediaType.octet_stream);
		//coapRequest.setToken("ABCD".getBytes());
		
		if(endpoint.getUriPath() != null){
			coapRequest.setUriPath(endpoint.getUriPath());
		}
		if(endpoint.getUriQuery() != null){
			coapRequest.setUriQuery(endpoint.getUriQuery());
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		
		clientChannel.sendMessage(coapRequest);
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		System.out.println("Received response");
		//System.out.println("contents: " + response.getPayload());
		byte[] b = response.getPayload();
		String sb = new String(b);
		System.out.println("sb: " + sb); //これで内容が見える
		for(int i=0; i<b.length; i++){
			//System.out.println(Integer.toHexString(b[i]& 0xff));
			System.out.printf("%02x", b[i]); 
			//System.out.print(String.format("%02x", b[i]));
		}

		
	}

	@Override
	public void onConnectionFailed(CoapClientChannel channel,
			boolean notReachable, boolean resetByServer) {
		// TODO Auto-generated method stub
		
	}


}
