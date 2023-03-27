package exchange.customer_cabinet.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomerService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public boolean buyStocks(int companyId, int count) {
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(
                                    URI.create(
                                            "http://localhost:9000/buy-stocks?companyId=%s&count=%s"
                                                    .formatted(companyId, count)
                                    )
                            )
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            String body = response.body();
            return "true".equals(body);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
