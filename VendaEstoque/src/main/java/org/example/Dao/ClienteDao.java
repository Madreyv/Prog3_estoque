package org.example.Dao;


import org.example.Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends DaoPostgres implements  Dao<Cliente>{

    @Override
    public List<Cliente> listar() throws Exception {
        String sql = "select nome, cpf, ativo from cliente order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Cliente> clienteDados = new ArrayList<Cliente>();

        while (rs.next()) {
            boolean ativo = rs.getBoolean("ativo");
            if(ativo){
                Cliente cliente = new Cliente(rs.getString("nome"),rs.getString("cpf") );
                clienteDados.add(cliente);
            }
        }

        return clienteDados;
    }

    @Override
    public void gravar(Cliente value) throws Exception {
        String sql = "INSERT INTO Cliente (nome, cpf, ativo) VALUES (?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getCpf());
        ps.setBoolean(3, true);

        ps.executeUpdate();

    }

    @Override
    public void alterar(Cliente value) throws Exception {
        String sql = "update Cliente set nome = ? where cpf = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getCpf());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Cliente value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            //String sql = "delete from Cliente where cpf = ?";
            String sql = "Update cliente set ativo = false where cpf = ?";
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            ps1.setString(1, value.getCpf());
            ps1.executeUpdate();
            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }
    }
}
