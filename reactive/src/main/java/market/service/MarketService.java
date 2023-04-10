package market.service;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import market.db.MarketDB;
import market.exception.ParseUserException;
import market.currency.CurrencyConverter;
import market.model.Product;
import market.model.User;
import market.model.Wallet;
import rx.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarketService {
    private final MarketDB db = new MarketDB();

    public Observable<User> getAllUsers() {
        return db.getAllUsers();
    }

    public Observable<String> addUser(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        try {
            User user = parseUser(params);
            db.addUser(user);
            return Observable.just("User " + user.getName() + " added");
        } catch (ParseUserException e) {
            return Observable.just(e.getMessage());
        }
    }

    public Observable<Product> getProductsForUser(HttpServerRequest<ByteBuf> req) {
        try {
            String login = getParam("login", req.getQueryParameters());
            Wallet userWallet = db.getWalletByUserLogin(login);
            Observable<Product> allProducts = db.getAllProducts();
            return allProducts.map(p -> new Product(
                    p.getId(),
                    p.getName(),
                    CurrencyConverter.convert(p.getWallet(), p.getValue(), userWallet),
                    userWallet
            ));
        } catch (ParseUserException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Observable<String> addProduct(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        try {
            Product product = parseProduct(params);
            db.addProduct(product);
            return Observable.just("Product " + product.getName() + " added");
        } catch (ParseUserException e) {
            return Observable.just(e.getMessage());
        }
    }

    private User parseUser(Map<String, List<String>> params) throws ParseUserException {
        int id = parseIntParam("id", params);
        String name = getParam("name", params);
        String login = getParam("login", params);
        Wallet wallet = parseWallet(getParam("wallet", params));
        return new User(id, name, login, wallet);
    }

    private Product parseProduct(Map<String, List<String>> params) throws ParseUserException {
        int id = parseIntParam("id", params);
        String name = getParam("name", params);
        double value = parseDoubleParam("value", params);
        Wallet wallet = parseWallet(getParam("wallet", params));
        return new Product(id, name, value, wallet);
    }

    private Wallet parseWallet(String walletParam) throws ParseUserException {
        try {
            return Wallet.valueOf(walletParam);
        } catch (IllegalArgumentException e) {
            throw new ParseUserException("no such wallet " + walletParam + ". Allowed: " + Arrays.toString(Wallet.values()));
        }
    }

    private int parseIntParam(String paramName, Map<String, List<String>> params) throws ParseUserException {
        try {
            return Integer.parseInt(getParam(paramName, params));
        } catch (NumberFormatException e) {
            throw new ParseUserException(paramName + " is not int");
        }
    }

    private double parseDoubleParam(String paramName, Map<String, List<String>> params) throws ParseUserException {
        try {
            return Double.parseDouble(getParam(paramName, params));
        } catch (NumberFormatException e) {
            throw new ParseUserException(paramName + " is not a valid number");
        }
    }

    private String getParam(String paramName, Map<String, List<String>> param) throws ParseUserException {
        try {
            return param.get(paramName).get(0);
        } catch (NullPointerException e) {
            throw new ParseUserException("no " + paramName + " param");
        }
    }
}
