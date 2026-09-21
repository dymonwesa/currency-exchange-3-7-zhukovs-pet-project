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
        writer.write(currencyDAO.findAll().toString());

    }
}
