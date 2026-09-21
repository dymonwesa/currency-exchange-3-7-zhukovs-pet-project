package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CurrencyDAO;
import org.example.model.Currency;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        List<Currency> currencies = currencyDAO.findAll();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < currencies.size(); i++) {
            Currency c = currencies.get(i);
            json.append(String.format(
                    "{\"id\":%d,\"code\":\"%s\",\"name\":\"%s\",\"sign\":\"%s\"}",
                    c.getId(), c.getCode(), c.getFullname(), c.getSign()
            ));
            if (i < currencies.size() - 1) json.append(",");
        }
        json.append("]");
        writer.write(json.toString());

    }
}
