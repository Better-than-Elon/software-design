package exchange.customer_cabinet;

import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.customer_cabinet.model.Company;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientCabinetAppTest {

    @ClassRule
    public static GenericContainer<?> simpleWebServer =
            new FixedHostPortGenericContainer<>("stock-exchange:1.0-SNAPSHOT")
                    .withFixedExposedPort(9000, 9000)
                    .withExposedPorts(9000);

    @Test
    public void testCompany1() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9000/get-company?companyId=1"))
                .GET()
                .build();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        ObjectMapper mapper = new ObjectMapper();
        Company company = mapper.readValue(response.body(), Company.class);

        Assert.assertEquals(1, company.companyId);
        Assert.assertEquals("ozon", company.companyName);
    }

    @Test
    public void testBuyAddStock() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9000/buy-stocks?companyId=1&count=1"))
                .GET()
                .build();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        String answer1 = mapper.readValue(response.body(), String.class);

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9000/add-stocks?companyId=1&count=1"))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        String answer2 = mapper.readValue(response.body(), String.class);

        Assert.assertEquals("true", answer1);
        Assert.assertEquals("true", answer2);
    }
}
