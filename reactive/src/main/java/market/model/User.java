package market.model;

import org.bson.Document;

public class User {
    public final int id;
    public final String name;
    public final String login;
    public final Wallet wallet;

    public User(Document doc) {
        this(
            doc.getInteger("id"),
            doc.getString("name"),
            doc.getString("login"),
            Wallet.valueOf(doc.getString("wallet"))
        );
    }

    public User(int id, String name, String login, Wallet wallet) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.wallet = wallet;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Document toDocument() {
        return new Document("id", id)
            .append("name", name)
            .append("login", login)
            .append("wallet", wallet.name());
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", login='" + login + '\'' +
            ", wallet=" + wallet +
            "}\n";
    }
}
