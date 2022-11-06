package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsePrinter {

    final String NL = System.lineSeparator();

    public void printRequest(HttpServletResponse response, String title, String text) throws IOException {
        response.getWriter().print(
                "<html><body>" + NL + title + ": " + NL + text + NL + "</body></html>" + NL
        );
    }

    public void printResponseNum(HttpServletResponse response, String title, long value) throws IOException {
        printRequest(response, title, String.valueOf(value));
    }

    public void printResponseProducts(HttpServletResponse response, String title, List<Product> products)
            throws IOException {
        String text = products.stream().map(prd -> prd.toString("\t")).collect(Collectors.joining(NL));
        printRequest(response, title, text);
    }

}
