package org.example.dao;

import org.example.model.Currency;
import org.example.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;

public class CurrencyDAO implements Dao<Integer, Currency> {

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

    private CurrencyDAO() {
    }

    @Override
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