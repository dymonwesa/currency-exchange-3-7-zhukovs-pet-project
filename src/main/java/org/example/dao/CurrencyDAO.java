package org.example.dao;

import org.example.model.Currency;
import org.example.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO implements Dao<Integer, Currency>{

    private static final CurrencyDAO INSTANCE = new CurrencyDAO();

    public static final String FIND_ALL = """
            SELECT *
            FROM currencies;
            """;

    private CurrencyDAO(){
    }


    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try (var connection = ConnectionManager.get()){
            var preparedStatement = connection.prepareStatement(FIND_ALL);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                currencies.add(new Currency(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("fullname"),
                        resultSet.getString("sign")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    public static CurrencyDAO getInstance(){
        return INSTANCE;
    }
}
