package org.example.dao;

import org.example.model.Currency;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {

    private static final CurrencyDAO INSTANCE = new CurrencyDAO();

    public static final String FIND_ALL = """
            SELECT *
            FROM currencies;
            """;

    public static final String FIND_BY_CODE = """
            SELECT *
            FROM currencies
            WHERE code = ?;
            """;

    public static final String FIND_BY_NAME = """
            SELECT * 
            FROM currencies
            WHERE fullname= ?;
            """;
    public static final String FIND_BY_SIGN = """
                    SELECT *
                    FROM currencies
                    WHERE sign = ?;
            """;

    public static final String INSERT_CURRENCY = """
            INSERT INTO currencies(code, fullname, sign)
            VALUES (?, ?, ?);
            """;

    private CurrencyDAO() {
    }


    public boolean isCurrencyExists(Currency currency) {
        try (var connection = ConnectionManager.get()) {

            try (var statement = connection.prepareStatement(FIND_BY_CODE)) {
                statement.setString(1, currency.getCode());

                try (var resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    public Optional<Currency> findByCode(String code) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_CODE)) {

            preparedStatement.setString(1, code);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(buildCurrency(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency save(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CURRENCY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullname());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Id not generated.");
                }
                int id = generatedKeys.getInt("id");
                return new Currency(id, currency.getCode(), currency.getFullname(), currency.getSign());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("fullname"),
                resultSet.getString("sign")
        );
    }

    public static CurrencyDAO getInstance() {
        return INSTANCE;
    }
}