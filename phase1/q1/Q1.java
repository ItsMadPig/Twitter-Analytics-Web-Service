/*
15619 Project Phase1
Team Oak: Ziyuan Song, Aaron Hsu, Jiali Chen
*/
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.Map;
import java.util.TimeZone;

public class Q1 {
	/*
	 * return Y: this will always be a prime positive number
	 */
	int getY(String strXY) {
		final String strX = "8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773";
		BigInteger x = new BigInteger(strX);
		BigInteger xy = new BigInteger(strXY);
		BigInteger y = xy.divide(x);
		int inty = y.intValue();
		return inty;
	}

	/*
	 * Get Message from key and ciphertext Length of c is a perfect square
	 * length example: c=URYEXYBJB -> intermediate message i: URYYBJBEX
	 */
	String getMessage(String key, String ciphertext) {
		int y = getY(key);

		char[] messageI = getMessageI(ciphertext);

		// Get IntermediateKey Z
		int z = 1 + (y % 25);
		// System.out.println("z is "+z);

		// Convert messageI to messageM
		String messageM = getMessageM(messageI, z);

		return messageM;
	}

	/*
	 * Get message M from Intermediate message I and intermediate Key Z
	 */
	private String getMessageM(char[] chsI, int z) {
		// System.out.println("messageI "+Arrays.toString(chsI));
		for (int i = 0; i < chsI.length; i++) {
			chsI[i] = (char) (chsI[i] - z) >= 'A' ? (char) (chsI[i] - z)
					: (char) (chsI[i] + 26 - z);
		}

		// System.out.println("messageI "+Arrays.toString(chsI));
		return String.valueOf(chsI);
	}

	private char[] getMessageI(String ciphertext) {
		// reprensent ciphertext as char[][]--grid
		int len = ciphertext.length();
		int rows = (int) Math.round(Math.sqrt(len));
		char[] chs = ciphertext.toCharArray();
		int count = 0;
		char[][] grid = new char[rows][rows];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++) {
				grid[i][j] = chs[count++];
			}

		// transform gridC to chsI
		char[] chsI = transformCtoI(grid);

		return chsI;
	}

	private char[] transformCtoI(char[][] matrix) {
		int len = matrix.length;
		char[] chsI = new char[len * len];
		int rowFirst = 0, colFirst = 0;
		int rowEnd = len - 1, colEnd = len - 1;
		int count = 0;
		while (rowFirst <= rowEnd && colFirst <= colEnd) {
			for (int i = rowFirst; i <= rowEnd; i++)
				chsI[count++] = matrix[colFirst][i];
			colFirst++;
			for (int j = colFirst; j <= colEnd; j++)
				chsI[count++] = matrix[j][rowEnd];
			rowEnd--;
			if (colEnd >= colFirst)
				for (int i = rowEnd; i >= rowFirst; i--)
					chsI[count++] = matrix[colEnd][i];
			colEnd--;
			if (rowFirst <= rowEnd)
				for (int j = colEnd; j >= colFirst; j--)
					chsI[count++] = matrix[j][rowFirst];
			rowFirst++;
		}
		return chsI;
	}

	public void start(String host) {
		Undertow server = Undertow.builder().addHttpListener(80, host)
				.setHandler(new HttpHandler() {

					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						//System.out.println(exchange.getRequestPath());
						if (exchange.getRequestPath().equals("/q1"))
							processRequest(exchange);
						else {
							System.out
									.println("Waiting for another correct url request");
							exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
									"text/plain");
							exchange.getResponseSender().send("I got your request :D");
							
						}
					}
				}).build();
		server.start();

	}

	protected void processRequest(HttpServerExchange exchange) {
		// String request = exchange.getRequestURL();
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String strXY = paras.get("key").getFirst();

			String c = paras.get("message").getFirst();

			String response = getResponse(strXY, c);

			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
					"text/plain");
			exchange.getResponseSender().send(response);
		} catch (Exception e) {
			System.out
					.println("You didn't input your key and cipherText in your request url. Please try again!");
			System.out.println(e);
		}

	}

	/*
	 * Response Format: TEAMID,AWS_ACCOUNT_ID1,AWS_ACCOUNT_ID2,
	 * AWS_ACCOUNT_ID3\n yyyy-MM-dd HH:mm:ss\n <The decrypted message M>\n
	 */
	private String getResponse(String strXY, String c) {
		String message = getMessage(strXY, c);
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		String strDate = dateFormat.format(date);
		String response = String.format("%s,%s,%s,%s\n%s\n%s\n", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, strDate,
				message);

		return response;
	}

	public static void main(String[] args) {
		Q1 q1 = new Q1();
		if (args.length > 0 && args[0].length() > 0)
			q1.start(args[0]);
		else
			System.out.println("Please input : java Q1 <Your EC2 DNS name>");

	}

}
