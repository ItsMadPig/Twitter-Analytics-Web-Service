import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;


public class Test {

	public static void main(String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0")
				.setHandler(new HttpHandler() {

					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						String requestPath = exchange.getRequestPath();
						if (requestPath.equals("/q1")) {
							Q1 q1 = new Q1();
							q1.processRequest(exchange);
						}
						else 
						 if (requestPath.equals("/q2")) {
							 Q2 q2 = new Q2();
							 q2.processRequest(exchange);
							 
						 }
						 else
						{
							System.out
									.println("Waiting for another correct url request");
							exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
									"text/plain");
							exchange.getResponseSender().send("I got your data.:D");
							
						}
					}
				}).build();
		server.start();

	}

}
