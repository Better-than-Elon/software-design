package market.model;

import org.bson.Document;

public class Product {
    public final int id;
    public final String name;
    public final double value;
    public final Wallet wallet;

    public Product(Document doc) {
        this(
            doc.getInteger("id"),
            doc.getString("name"),
            doc.getDouble("value"),
            Wallet.valueOf(doc.getString("wallet"))
        );
    }

    public Product(int id, String name, double value, Wallet wallet) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Document toDocument() {
        return new Document()
            .append("id", id)
            .append("name", name)
            .append("value", value)
            .append("wallet", wallet.name());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", value=" + value +
            ", wallet=" + wallet +
            "}\n";
    }
}
