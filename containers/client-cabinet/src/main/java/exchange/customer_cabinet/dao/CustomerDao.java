package exchange.customer_cabinet.dao;

import exchange.customer_cabinet.model.Customer;
import exchange.customer_cabinet.model.Stock;
import exchange.customer_cabinet.model.StockSum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class CustomerDao extends JdbcDaoSupport {
    public CustomerDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public boolean addCustomer(Customer client) {
        String sql = "insert into Client (client_login, money) values (?, ?)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, client.customerLogin, client.money) > 0;
    }

    public Customer getClient(int clientId) {
        String sql = """
                select client_id, client_login, money
                from Client
                where client_id = ?
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), clientId).get(0);
    }

    public List<Customer> getClients() {
        String sql = """
                select client_id, client_login, money
                from Client
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
    }

    public boolean addMoney(int clientId, double amount) {
        String sql = "update Client set money = money + ? where client_id = ?";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, amount, clientId) > 0;
    }

    public boolean addClientStock(int clientId, int companyId, int count) {
        String sql = """
            insert into ClientStock
                (client_id, company_id, client_stock_count)
            values
                (?, ?, ?)
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, clientId, companyId, count) > 0;
    }

    public List<Stock> getClientStocks(int clientId) {
        String sql = """
            select client_login, company_name, client_stock_count, stock_price
            from ClientStock
            natural join Client
            natural join Company
            where client_id = ?
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), clientId);
    }

    public StockSum getClientStocksValue(int clientId) {
        String sql = """
            select sum(stock_price * client_stock_count) as stock_sum
            from Client
            natural left join ClientStock
            natural left join Company
            where client_id = ?
            group by client_id
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StockSum.class), clientId).get(0);
    }

}
