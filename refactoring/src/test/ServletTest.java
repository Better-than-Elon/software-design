import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.database.Dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

public class ServletTest {

    private final HttpClient client = HttpClient.newHttpClient();
    public final Dao dao = new Dao("jdbc:sqlite:test.db");

    public ServletTest() {
    }

    private HttpResponse<String> makeValidRequest(String req) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(req)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
        return response;
    }

    private String fillDB(int n) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            makeValidRequest("http://localhost:8081/add-product?name=prod" + i + "&price=" + i * 100);
            sb.append("prod").append(i).append("\t").append(i * 100).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void testQuery(String query, String expectedTitle, String expectedData)
            throws IOException, InterruptedException {
        HttpResponse<String> response = makeValidRequest("http://localhost:8081/query?command=" + query);
        Assertions.assertEquals(
                "<html><body>" + System.lineSeparator() +
                        expectedTitle + ": " + System.lineSeparator() +
                        expectedData +
                        "</body></html>" + System.lineSeparator()
                , response.body());
    }

    @BeforeEach
    public void deleteTable() throws SQLException {
        dao.delete();
    }

    @Test
    public void testAdd() throws IOException, InterruptedException, SQLException {
        int prods = 10;
        fillDB(prods);
        Assertions.assertEquals(prods, dao.getAll().size());
    }

    @Test
    public void testGet() throws IOException, InterruptedException {
        int prods = 10;
        String expectedData = fillDB(prods);
        HttpResponse<String> response = makeValidRequest("http://localhost:8081/get-products");
        Assertions.assertEquals(
                "<html><body>" + System.lineSeparator() +
                        "Get products: " + System.lineSeparator() +
                        expectedData +
                        "</body></html>" + System.lineSeparator()
                , response.body());
    }

    @Test
    public void testMax() throws IOException, InterruptedException {
        int prods = 10;
        String data = "prod9\t900" + System.lineSeparator();
        fillDB(prods);
        testQuery("max", "Product with max price", data);
    }

    @Test
    public void testMin() throws IOException, InterruptedException {
        int prods = 10;
        String data = "prod0\t0" + System.lineSeparator();
        fillDB(prods);
        testQuery("min", "Product with min price", data);
    }

    @Test
    public void testSum() throws IOException, InterruptedException {
        int prods = 10;
        String data = "4500" + System.lineSeparator();
        fillDB(prods);
        testQuery("sum", "Summary price", data);
    }

    @Test
    public void testCount() throws IOException, InterruptedException {
        int prods = 10;
        String data = "10" + System.lineSeparator();
        fillDB(prods);
        testQuery("count", "Number of products", data);
    }
}
