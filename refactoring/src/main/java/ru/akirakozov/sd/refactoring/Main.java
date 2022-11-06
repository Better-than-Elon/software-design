package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.database.Dao;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final String DB_NAME = "jdbc:sqlite:test.db";
        Dao dao = new Dao(DB_NAME);
        dao.initDB();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(dao)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(dao)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(dao)),"/query");

        server.start();
        server.join();
    }
}
