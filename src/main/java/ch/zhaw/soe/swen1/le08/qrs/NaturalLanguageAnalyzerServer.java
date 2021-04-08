package ch.zhaw.soe.swen1.le08.qrs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Server which publishes a service to analyze sentences for inappropriate language.
 * This is just a dummy implementation, but the interface (the expected HTTP request and the
 * response) is the same as the real server. 
 *
 */
public class NaturalLanguageAnalyzerServer {
    private int port;
    private int backlog;
    private HttpServer server;
    
    public NaturalLanguageAnalyzerServer(int port, int backlog) {
        super();
        this.port = port;
        this.backlog = backlog;
    }

    public void start() throws IOException { 
        server = HttpServer.create(new InetSocketAddress(port), backlog);
        HttpContext context = server.createContext("/naturallanguageanalyzer");
        context.setHandler(new RequestHandler(server)); 
        server.start();
    }
    
    public void stop() {
        server.stop(1); 
    }
    
    public static void main(String[] args) throws IOException, InterruptedException { 
        System.out.println("Server started and listening");
        NaturalLanguageAnalyzerServer server = new NaturalLanguageAnalyzerServer(12000, 0);
        server.start();
        Thread.sleep(10000); // 10 seconds to use the server
        server.stop(); 
        System.out.println("Server stopped");
   }
        
    protected static class RequestHandler implements HttpHandler {
        private Charset utf8 = Charset.forName("UTF-8");

        public RequestHandler(HttpServer server) {
            super();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            URI requestURI = exchange.getRequestURI();
            String apiKey = exchange.getRequestHeaders().getFirst("apikey");
            if("demokey".equals(apiKey) && requestMethod.equals("GET") && requestURI.getPath().startsWith("/naturallanguageanalyzer")) {
                if (requestURI.getPath().equals("/naturallanguageanalyzer/handlesentence")) {
                    String sentence = exchange.getRequestHeaders().getFirst("sentence");
                    // The real language processing would take place here. 
                    if (sentence.equals("Not Ok")) {
                        // special input for enforcing response that the sentence is invalid
                        sendResponse(400, "INVALID", exchange, true);                        
                    } else {
                        // all other sentences are valid
                        sendResponse(400, "VALID", exchange, true);               
                    }
                } else if (requestURI.getPath().equals("/naturallanguageanalyzer/handleparagraph")) {
                    List<String> lines = readLinesFromRequestBody(exchange);
                    // The real language processing would take place here. 
                    if (lines.size() == 1) {
                        // special input for enforcing response that the paragraph is invalid
                        sendResponse(400, "INVALID", exchange, false);                                                
                    } else {
                        sendResponse(400, "VALID", exchange, false);               
                    }
                } else {
                    returnBadRequest(exchange, true);                    
                }
            } else {
                returnBadRequest(exchange, true);
            }      
            exchange.close();
        }
        
        private List<String> readLinesFromRequestBody(HttpExchange exchange) throws IOException{
            List<String> result = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), utf8))){
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            }
            return result;
        }
        
        private void returnBadRequest(HttpExchange exchange, boolean consumeInputStream) throws IOException {
            sendResponse(400, "Bad Request", exchange, consumeInputStream); 
        }
        
        private void sendResponse(int code, String response, HttpExchange exchange, 
                boolean consumeInputStream) throws IOException {
            if (consumeInputStream) {
                try (InputStream requestBody = exchange.getRequestBody()){
                    for(int b = requestBody.read(); b != -1; b = requestBody.read());
                }
            }
            exchange.sendResponseHeaders(code, response.getBytes(utf8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
   }
}
