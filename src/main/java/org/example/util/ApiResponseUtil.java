package org.example.util;

import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseUtil {

    private static ApiResponseUtil INSTANCE = new ApiResponseUtil(); // del mb

    public static ApiResponseUtil getInstance(){
        return INSTANCE;
    }

    public void sendError(int status, String message, HttpServletResponse response){
        Map<String, String> map = new HashMap<>();
        switch (response.getStatus()) {
            case 404 -> map.put("message", "Валюта не найдена.");
            case 400 -> map.put("message", "Некорректный запрос.");

        }



    }






}
