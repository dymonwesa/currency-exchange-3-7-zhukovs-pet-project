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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

       Gson gson = new Gson();


        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            List<Currency> currencies = currencyDAO.findAll();
            String json = gson.toJson(currencies);
            writer.write(json);
        } else if(pathInfo.length()==1){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendError(resp.getStatus(), "Некорректный запрос.", resp);
        }
            else {
            Optional<Currency> currency = currencyDAO.findByCode(pathInfo.substring(1));
            if(currency.isPresent()){
                resp.setStatus(HttpServletResponse.SC_OK);
                String json = gson.toJson(currency.get());
                writer.write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                sendError(resp.getStatus(), "Валюта не найдена.", resp);
            }




        }



    }

    public void sendError(int status, String message, HttpServletResponse resp) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        writer.write(gson.toJson(map));
    }
}
