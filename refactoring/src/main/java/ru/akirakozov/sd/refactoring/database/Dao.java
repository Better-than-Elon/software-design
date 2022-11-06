package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    private final String url;

    public Dao(String url) {
        this.url = url;
    }

    private void applySql(String sql, String errorMassage) throws SQLException {
        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(errorMassage, e);
        }
    }

    public void initDB() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";

        applySql(sql, "can't init db");
    }


    public void delete() throws SQLException {
        String sql = "DELETE FROM PRODUCT";
        applySql(sql, "can't delete product from db");
    }


    public void insertProduct(Product product) throws SQLException {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
        applySql(sql, "can't insert product");
    }


    public List<Product> getAll() throws SQLException {
        List<Product> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            String sql = "SELECT * FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                result.add(new Product(name, price));
            }

            rs.close();

            return result;
        } catch (SQLException e) {
            throw new SQLException("can't get products", e);
        }
    }


    public Product getMax() throws SQLException {
        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            return getProduct(stmt, sql);
        } catch (SQLException e) {
            throw new SQLException("can't get max", e);
        }
    }


    public Product getMin() throws SQLException {
        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            return getProduct(stmt, sql);
        } catch (SQLException e) {
            throw new SQLException("can't get min", e);
        }
    }

    private Product getProduct(Statement stmt, String sql) throws SQLException {
        Product product = null;
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            product = new Product(name, price);
        }
        rs.close();
        return product;
    }


    public long getSum() throws SQLException {
        int sum = 0;

        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            String sql = "SELECT SUM(price) FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sum = rs.getInt(1);
            }
            rs.close();
            return sum;
        } catch (SQLException e) {
            throw new SQLException("can't get sum", e);
        }
    }


    public long getCount() throws SQLException {
        int count = 0;

        try (Connection c = DriverManager.getConnection(url);
             Statement stmt = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } catch (SQLException e) {
            throw new SQLException("can't get count", e);
        }
    }
}
