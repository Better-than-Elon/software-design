package exchange.customer_cabinet.controller;

import exchange.customer_cabinet.dao.CustomerDao;
import exchange.customer_cabinet.model.Customer;
import exchange.customer_cabinet.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public final CustomerDao userDao;

    public final CustomerService customerService;

    public UserController(CustomerDao userDao, CustomerService customerService) {
        this.userDao = userDao;
        this.customerService = customerService;
    }

    @RequestMapping("/add-customer")
    public boolean addCustomer(
            @RequestParam String customerLogin,
            @RequestParam double money
    ) {
        Customer customer = new Customer();
        customer.customerLogin = customerLogin;
        customer.money = money;
        return userDao.addCustomer(customer);
    }

    @RequestMapping("/get-customer")
    public Customer getCustomer(
            @RequestParam int customerId
    ) {
        Customer customer = userDao.getCustomer(customerId);
        customer.customerStocks = userDao.getCustomerStocks(customer.customerId);
        return customer;
    }

    @RequestMapping("/get-customers")
    public List<Customer> getCustomers(
    ) {
        return userDao.getCustomers();
    }

    @RequestMapping("/add-money-to-customer")
    public boolean addMoney(
            @RequestParam int customerId,
            @RequestParam double amount
    ) {
        return userDao.addMoney(customerId, amount);
    }

    @RequestMapping("/get-customer-stocks-value")
    public double getCustomerStocksValue(
            @RequestParam int customerId
    ) {
        return userDao.getCustomerStocksValue(customerId).stockSum;
    }

    @RequestMapping("/buy-stocks")
    public boolean buyStocks(
            @RequestParam int customerId,
            @RequestParam int companyId,
            @RequestParam int count
    ) {
        return customerService.buyStocks(companyId, count)
                && userDao.addCustomerStock(customerId, companyId, count);
    }
}
