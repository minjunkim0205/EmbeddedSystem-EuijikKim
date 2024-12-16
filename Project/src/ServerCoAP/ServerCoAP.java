package ServerCoAP;

import org.ws4d.coap.core.rest.CoapResourceServer;

public class ServerCoAP {
	/* Field */
	private static ServerCoAP coapServer;
	private CoapResourceServer resourceServer;

	/* Method */
	public void start() {
		System.out.println("===Run CoAP Server ===");
		// create server
		if (this.resourceServer != null) {
			this.resourceServer.stop();
		}
		this.resourceServer = new CoapResourceServer();
		// initialize resource
		Relay relay = new Relay();
		DHT11 dht11 = new DHT11();
		// add resource to server
		this.resourceServer.createResource(relay);
		this.resourceServer.createResource(dht11);
		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* Main */
	public static void main(String[] args) {
		coapServer = new ServerCoAP();
		coapServer.start();
	}
}
