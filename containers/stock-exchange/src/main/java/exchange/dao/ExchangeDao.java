package exchange.dao;

import exchange.model.Company;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class ExchangeDao extends JdbcDaoSupport {
    public ExchangeDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public boolean addCompany(Company company) {
        String sql = "insert into Company (company_name, stock_price, company_stock_count) values (?, ?, ?)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, company.companyName, company.stockPrice, company.companyStockCount) > 0;
    }

    public Company getCompany(int companyId) {
        String sql = "select company_id, company_name, stock_price, company_stock_count from Company where company_id = ?";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class), companyId).get(0);
    }

    public List<Company> getCompanies() {
        String sql = "select company_id, company_name, stock_price, company_stock_count from Company";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }

    public boolean buyStocks(int companyId, int count) {
        String sql = "update Company set company_stock_count = company_stock_count - ? where company_id = ? and company_stock_count > ?";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, count, companyId, count) > 0;
    }

    public boolean addStocks(int companyId, int count) {
        String sql = "update Company set company_stock_count = company_stock_count + ? where company_id = ?";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, count, companyId) > 0;
    }

    public void changeCourse() {
        String sql = "update Company set stock_price = stock_price * (select random() * (2 - 0.5) + 0.5)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        jdbcTemplate.update(sql);
    }
}
