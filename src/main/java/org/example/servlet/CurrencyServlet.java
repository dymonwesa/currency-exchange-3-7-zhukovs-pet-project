package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CurrencyDAO;
import org.example.model.Currency;
import org.example.util.ApiResponseUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {
        "/currency/*",
        "/currencies"
})
public class CurrencyServlet extends HttpServlet {
    private static final String NOT_FOUND_MESSAGE = "Валюта не найдена.";
    private static final String BAD_REQUEST_MESSAGE = "Неверный запрос.";
    private static final String INCORRECT_PATH_MESSAGE = "Неверный путь.";
    private static final String CURRENCY_ALREADY_EXISTS_MESSAGE = "Валюта уже существует.";
    private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private final ApiResponseUtil apiResponseUtil = ApiResponseUtil.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

       Gson gson = new Gson();
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        if (servletPath.equals("/currencies")) {
            List<Currency> currencies = currencyDAO.findAll();
            String json = gson.toJson(currencies);
            apiResponseUtil.sendSuccess(200, json, resp);
            return;
        } else if (servletPath.equals("/currency")) {

            if (pathInfo == null || !pathInfo.matches("/[A-Za-z]{3}")) {
                apiResponseUtil.sendError(400, BAD_REQUEST_MESSAGE, resp);
                return;
            } else {
                Optional<Currency> currency = currencyDAO.findByCode(pathInfo.substring(1));
                if (currency.isPresent()) {
                    String json = gson.toJson(currency.get());
                    apiResponseUtil.sendSuccess(200, json, resp);
                    return;
                } else {
                    apiResponseUtil.sendError(404, NOT_FOUND_MESSAGE, resp);
                    return;
                }
            }
        } else {
            apiResponseUtil.sendError(404, INCORRECT_PATH_MESSAGE, resp);
        }



    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/currencies")) {
            apiResponseUtil.sendError(404, INCORRECT_PATH_MESSAGE, resp);
            return;
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        if (name == null || code == null || sign == null ||
                name.isBlank() || code.isBlank() || sign.isBlank()) {
            apiResponseUtil.sendError(400, BAD_REQUEST_MESSAGE, resp);
            return;
        }
        if (!code.matches("[A-Za-z]{3}")){
            apiResponseUtil.sendError(400, BAD_REQUEST_MESSAGE, resp);
            return;
        }
        Currency saveCurrency = new Currency(0, code, name,sign);
        if (currencyDAO.isCurrencyExists(saveCurrency)) {
            apiResponseUtil.sendError(409, CURRENCY_ALREADY_EXISTS_MESSAGE, resp);
            return;
        }
        try {
            Currency resultCurrency = currencyDAO.save(saveCurrency);
            Gson gson = new Gson();
            String json = gson.toJson(resultCurrency);
            apiResponseUtil.sendSuccess(201, json, resp);

        } catch (RuntimeException e) {

            if (e.getCause() instanceof SQLException sqlException
                    && "23505".equals(sqlException.getSQLState())) {

                apiResponseUtil.sendError(
                        409,
                        "Валюта с таким кодом уже существует.",
                        resp
                );
                return;
            }

            throw e;
        }



    }

}
