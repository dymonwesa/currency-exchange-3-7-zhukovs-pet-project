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
import java.util.List;
import java.util.Optional;


@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final String NOT_FOUND_MESSAGE = "Валюта не найдена.";
    private static final String BAD_REQUEST_MESSAGE = "Неверный запрос.";
    private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private final ApiResponseUtil apiResponseUtil = ApiResponseUtil.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

       Gson gson = new Gson();
        String pathInfo = req.getPathInfo();



        if (pathInfo == null) {
            List<Currency> currencies = currencyDAO.findAll();
            String json = gson.toJson(currencies);
            apiResponseUtil.sendSuccess(200, json, resp);
        } else if(pathInfo.length()==1) {
            apiResponseUtil.sendError(400, BAD_REQUEST_MESSAGE, resp);
        } else {
            Optional<Currency> currency = currencyDAO.findByCode(pathInfo.substring(1));
            if(currency.isPresent()){
                String json = gson.toJson(currency.get());
                apiResponseUtil.sendSuccess(200, json, resp);
            } else {
                apiResponseUtil.sendError(404, NOT_FOUND_MESSAGE, resp);
            }

        }



    }

}
