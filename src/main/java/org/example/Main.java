package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingFormatArgumentException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    public final String url= "jdbc:postgresql://localhost:5432/currency_exchange";
    public final String login= "postgres";
    public final String password= "postgres";


    public static void main(String[] args) {
        Main main = new Main();

        try (Connection conn = DriverManager.getConnection(main.url, main.login, main.password)) {
            System.out.println("okay");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




    }
}