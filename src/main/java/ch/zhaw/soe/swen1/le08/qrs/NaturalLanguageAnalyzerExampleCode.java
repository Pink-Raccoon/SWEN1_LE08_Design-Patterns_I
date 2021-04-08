package ch.zhaw.soe.swen1.le08.qrs;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * Class with example code to invoke the NaturalLanguageAnalyzerServer.
 *
 */
public class NaturalLanguageAnalyzerExampleCode {
    private static final int port = 12000;

    /**
     * Main example method. Starts the server, invokes both functions with appropriate and
     * inappropriate language and stops the server. 
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        NaturalLanguageAnalyzerServer server = new NaturalLanguageAnalyzerServer(port, 0);
        server.start();
        try{
            System.out.println("Client started");
            sendRequestHandleSentence("Normal Sentence");
            sendRequestHandleSentence("Not Ok");
            sendRequestHandleParagraph(new String[] {"Line 1", "Line 2"});
            sendRequestHandleParagraph(new String[] {"Line 1"});
        } finally {
             server.stop();
        }
    }

    protected static String sendRequestHandleSentence(String sentence) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/naturallanguageanalyzer/handlesentence"))
            .header("accept", "text/*")
            .header("apikey", "demokey")
            .header("sentence", sentence)
            .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println("Response for '" + sentence + "' is '" + response.body() + "'");
        return response.body();
    }

    protected static String sendRequestHandleParagraph(String[] paragraph) throws IOException, InterruptedException {
        String body = String.join("\n", paragraph);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/naturallanguageanalyzer/handleparagraph"))
            .header("accept", "text/*")
            .header("apikey", "demokey")
            .method("GET", BodyPublishers.ofString(body))
            .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println("Response for '" + String.join(";", paragraph) + "' is '" + response.body() + "'");
        return response.body();
    }
}
