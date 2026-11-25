package org.example.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ApiResponseUtil {

    private static final ApiResponseUtil INSTANCE = new ApiResponseUtil(); // del mb

    public static ApiResponseUtil getInstance(){
        return INSTANCE;
    }

    public void sendError(int status, String message, HttpServletResponse resp) throws IOException {
        Map<String, String> map = new HashMap<>();
        resp.setStatus(status);
        map.put("message", message);
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        writer.write(gson.toJson(map));

    }

    public void sendSuccess(int status, String json, HttpServletResponse resp) throws IOException {
        resp.setStatus(status);
        PrintWriter writer = resp.getWriter();
        writer.write(json);

    }






}
