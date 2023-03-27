package exchange.customer_cabinet.model;

public class Stock {
    private String clientName;
    private String companyName;
    private int clientStockCount;
    private double stockPrice;

    public Stock(String clientName, String companyName, int clientStockCount, double stockPrice) {
        this.clientName = clientName;
        this.companyName = companyName;
        this.clientStockCount = clientStockCount;
        this.stockPrice = stockPrice;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getClientStockCount() {
        return clientStockCount;
    }

    public void setClientStockCount(int clientStockCount) {
        this.clientStockCount = clientStockCount;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "clientName='" + clientName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", clientStockCount=" + clientStockCount +
                ", stockPrice=" + stockPrice +
                '}';
    }
}
