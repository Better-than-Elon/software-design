package exchange.model;

public class Company {
    private int companyId;
    private String companyName;
    private double stockPrice;
    private int companyStockCount;

    public Company(int companyId, String companyName, double stockPrice, int companyStockCount) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.stockPrice = stockPrice;
        this.companyStockCount = companyStockCount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getCompanyStockCount() {
        return companyStockCount;
    }

    public void setCompanyStockCount(int companyStockCount) {
        this.companyStockCount = companyStockCount;
    }
}
