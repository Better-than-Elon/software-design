package market.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import market.model.Product;
import market.model.User;
import market.model.Wallet;
import org.bson.Document;
import rx.Observable;

public class MarketDB {

    private final MongoClient MongoClient = createMongoClient();
    private final MongoDatabase Database = MongoClient.getDatabase("market-db");

    public Observable<User> getAllUsers() {
        return Observable.from(usersCollection().find().map(User::new));
    }

    public Observable<Product> getAllProducts() {
        return Observable.from(productsCollection().find().map(Product::new));
    }

    public void addUser(User user) {
        usersCollection().insertOne(user.toDocument());
    }

    public void addProduct(Product product) {
        productsCollection().insertOne(product.toDocument());
    }

    public Wallet getWalletByUserLogin(String login) {
        Document bson = new Document();
        bson.append("login", login);
        return Wallet.valueOf(usersCollection().find(bson).first().getString("wallet"));
    }

    private MongoCollection<Document> usersCollection() {
        return Database.getCollection("user");
    }

    private MongoCollection<Document> productsCollection() {
        return Database.getCollection("product");
    }

    private com.mongodb.client.MongoClient createMongoClient() {
        return MongoClients.create("mongodb://root:pass@localhost:27017");
    }
}
