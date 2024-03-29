import java.util.Arrays;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;


public class Test {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		Undertow server = Undertow.builder().addHttpListener(80, "0.0.0.0")
				.setHandler(new HttpHandler() {
				

					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						if (exchange.isInIoThread()) {
						      exchange.dispatch(this);
						      return;
						    }
						String requestPath = exchange.getRequestPath();
						if (requestPath.equals("/q1")) {
							Q1 q1 = new Q1();
							q1.processRequest(exchange);
						}
						else  if (requestPath.equals("/q2")) {
							
							 long startTime = System.currentTimeMillis();
							 Q2Hbase q2 = new Q2Hbase();
							 q2.processRequest(exchange);
							 long endTime   = System.currentTimeMillis();
							 long totalTime = endTime - startTime;
							 System.out.println("runtime: "+totalTime);
							 
						} else {
							System.out.println("Waiting for another correct url request");
							exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,"text/plain");
							exchange.getResponseSender().send("I got your data.:D");
							
						}
					}
				}).build();
		server.start();

	}

}
