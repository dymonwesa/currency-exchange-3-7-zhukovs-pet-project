package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CurrencyDAO;

import java.io.IOException;


@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
