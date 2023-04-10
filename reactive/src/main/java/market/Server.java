package market;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.ResponseContentWriter;
import market.model.Product;
import market.model.User;
import market.service.MarketService;
import rx.Observable;

public class Server {

    private static final MarketService marketService = new MarketService();

    private static final String PATH_GET_ALL_USERS = "/get-all-users";
    private static final String PATH_ADD_USER = "/add-user";
    private static final String PATH_GET_PRODUCTS_FOR_USER = "/get-products-for-user";
    private static final String PATH_ADD_PRODUCT = "/add-product";

    public static void main(String[] args) {
        HttpServer.newServer(8080).start((req, resp) -> {
            try {
                String path = req.getDecodedPath();
                if (PATH_GET_ALL_USERS.equals(path)) {
                    return handleGetAllUsers(resp);
                }
                if (PATH_ADD_USER.equals(path)) {
                    return handleAddUser(req, resp);
                }
                if (PATH_GET_PRODUCTS_FOR_USER.equals(path)) {
                    return handleGetProductsForUser(req, resp);
                }
                if (PATH_ADD_PRODUCT.equals(path)) {
                    return handleAddProduct(req, resp);
                }
                return resp.writeString(Observable.just("Incorrect request"));
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                return resp.writeString(Observable.just(e.getMessage()));
            }
        }).awaitShutdown();
    }

    private static ResponseContentWriter<ByteBuf> handleGetAllUsers(HttpServerResponse<ByteBuf> resp) {
        return resp.writeString(Observable.concat(
                Observable.just("Users:\n"),
                marketService.getAllUsers().map(User::toString)
        ));
    }

    private static ResponseContentWriter<ByteBuf> handleAddUser(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        return resp.writeString(Observable.concat(
                Observable.just("Add result:\n"),
                marketService.addUser(req)
        ));
    }

    private static ResponseContentWriter<ByteBuf> handleGetProductsForUser(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        Iterable<String> productStrings = (Iterable<String>) marketService.getProductsForUser(req).map(Product::toString);
        for (String productString : productStrings) {
            System.out.println(productString);
        }
        return resp.writeString(Observable.concat(
                Observable.just("Products:\n"),
                Observable.from(productStrings)
        ));
    }

    private static ResponseContentWriter<ByteBuf> handleAddProduct(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        return resp.writeString(Observable.concat(
                Observable.just("Add product result:\n"),
                marketService.addProduct(req)
        ));
    }
}
