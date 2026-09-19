package org.example.dao;

import com.sun.jdi.connect.spi.Connection;
import org.example.model.Currency;

import java.util.List;

public class CurrencyDAO implements Dao<Integer, Currency>{

    private static final CurrencyDAO INSTANCE = new CurrencyDAO();
    private CurrencyDAO(){
    }


    @Override
    public List<Currency> findAll() {

        return List.of();
    }
}
