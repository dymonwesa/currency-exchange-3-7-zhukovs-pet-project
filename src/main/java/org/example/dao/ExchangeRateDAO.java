package org.example.dao;

import org.example.model.ExchangeRate;

public class ExchangeRateDAO{

    private static final ExchangeRateDAO INSTANCE = new ExchangeRateDAO();
    private ExchangeRateDAO() {

    }

    public static ExchangeRateDAO getInstance() {
        return  INSTANCE;
    }

}


