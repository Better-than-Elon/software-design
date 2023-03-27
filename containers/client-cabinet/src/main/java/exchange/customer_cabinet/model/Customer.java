package exchange.customer_cabinet.model;

import java.util.List;

public class Customer {
    private int clientId;
    private String customerLogin;
    private double money;
    private List<Stock> clientStocks;

    public Customer(int clientId, String customerLogin, double money, List<Stock> clientStocks) {
        this.clientId = clientId;
        this.customerLogin = customerLogin;
        this.money = money;
        this.clientStocks = clientStocks;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(String customerLogin) {
        this.customerLogin = customerLogin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<Stock> getClientStocks() {
        return clientStocks;
    }

    public void setClientStocks(List<Stock> clientStocks) {
        this.clientStocks = clientStocks;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "clientId=" + clientId +
                ", customerLogin='" + customerLogin + '\'' +
                ", money=" + money +
                ", clientStocks=" + clientStocks +
                '}';
    }
}
