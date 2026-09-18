package org.example;

import java.nio.channels.SelectableChannel;
import java.sql.*;
import java.util.MissingFormatArgumentException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    public final String url= "jdbc:postgresql://localhost:5432/currency_exchange";
    public final String login= "postgres";
    public final String password= "postgres";
    public final String SELECT = "SELECT * FROM currencies";


    public static void main(String[] args) {
        Main main = new Main();

        try (Connection conn = DriverManager.getConnection(main.url, main.login, main.password);
        var statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(main.SELECT);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String fullname = resultSet.getString("fullname");
                String sign = resultSet.getString("sign");

                System.out.println(id + " " + code + " " + fullname + " " + sign);


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




    }
}