package com.example.exchange.controller;

import com.example.exchange.dao.ExchangeDao;
import com.example.exchange.model.Company;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

    public final ExchangeDao exchangeDao;

    public ExchangeController(ExchangeDao exchangeDao) {
        this.exchangeDao = exchangeDao;
    }

    @RequestMapping("/add-company")
    public boolean addCompany(
            @RequestParam String companyName,
            @RequestParam double stockPrice,
            @RequestParam int stockCount
    ) {
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setCompanyStockCount(stockCount);
        company.setStockPrice(stockPrice);
        return exchangeDao.addCompany(company);
    }

    @RequestMapping("/get-company")
    public Company getCompany(
            @RequestParam int companyId
    ) {
        return exchangeDao.getCompany(companyId);
    }

    @RequestMapping("/get-companies")
    public List<Company> getCompanies() {
        return exchangeDao.getCompanies();
    }

    @RequestMapping("/buy-stocks")
    public boolean buyStocks(
            @RequestParam int companyId,
            @RequestParam int count
    ) {
        return exchangeDao.buyStocks(companyId, count);
    }

    @RequestMapping("/add-stocks")
    public boolean addStocks(
            @RequestParam int companyId,
            @RequestParam int count
    ) {
        return exchangeDao.addStocks(companyId, count);
    }

    @RequestMapping("/change-course")
    public void changeCourse() {
        exchangeDao.changeCourse();
    }
}
