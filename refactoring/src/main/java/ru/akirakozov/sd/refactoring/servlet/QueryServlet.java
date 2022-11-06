package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.Dao;
import ru.akirakozov.sd.refactoring.printer.ResponsePrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final Dao dao;

    public QueryServlet(Dao dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");
        ResponsePrinter printer = new ResponsePrinter();
        try {
            if ("max".equals(command)) {
                printer.printResponseProducts(response, "Product with max price", List.of(dao.getMax()));
            } else if ("min".equals(command)) {
                printer.printResponseProducts(response, "Product with min price", List.of(dao.getMin()));
            } else if ("sum".equals(command)) {
                printer.printResponseNum(response, "Summary price", dao.getSum());
            } else if ("count".equals(command)) {
                printer.printResponseNum(response, "Number of products", dao.getCount());
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
