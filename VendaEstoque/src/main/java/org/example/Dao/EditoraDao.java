package org.example.Dao;

import org.example.Model.Cliente;
import org.example.Model.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDao extends DaoPostgres implements  Dao<Editora>{
    @Override
    public List<Editora> listar() throws Exception {
        String sql = "select * from editora order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Editora> editoraDados = new ArrayList<Editora>();

        while (rs.next()) {
            boolean ativo = rs.getBoolean("ativo");
            if(ativo){
                Editora editora = new Editora(rs.getString("nome"),rs.getString("cnpj") );
                editoraDados.add(editora);
            }
        }

        return editoraDados;
    }

    @Override
    public void gravar(Editora value) throws Exception {
        String sql = "INSERT INTO editora (nome, cnpj, ativo) VALUES (?,?, true)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getCnpj());

        ps.executeUpdate();
    }

    @Override
    public void alterar(Editora value) throws Exception {
        String sql = "update editora set nome = ? where cnpj = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getCnpj());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Editora value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            //String sql = "delete from editora where cnpj = ?";
            String sql = "update editora set ativo = false where cnpj = ?";
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            ps1.setString(1, value.getCnpj());
            ps1.executeUpdate();

            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }
    }
}
