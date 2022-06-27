package org.example.Dao;

import java.sql.*;

public abstract class DaoPostgres {
    protected static String ENDERECO = "localhost";
    protected static String BD = "prog3Estoque";
    protected static String PORTA = "5432";
    protected static String USUARIO = "postgres";
    protected static String SENHA = "123456";

    protected Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://" + ENDERECO + ":" + PORTA + "/" + BD;
        Connection connection = DriverManager.getConnection(url, USUARIO, SENHA);
        return connection;
    }

    protected PreparedStatement getPreparedStatement(String sql, Boolean insercao) throws Exception {
        PreparedStatement ps = null;
        if (insercao) {
            return getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            return getConexao().prepareStatement(sql);
        }
    }

}
