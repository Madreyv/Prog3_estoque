package org.example.Dao;

import org.example.Model.Cliente;
import org.example.Model.Estoque;
import org.example.Model.Manga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MangaDao extends DaoPostgres implements  Dao<Manga>{

    @Override
    public List<Manga> listar() throws Exception {
        String sql = "select * from manga";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Manga> magaDados = new ArrayList<Manga>();

        while (rs.next()) {
            boolean ativo = rs.getBoolean("ativo");
            if(ativo){
                Manga manga = new Manga(rs.getLong("id") ,rs.getString("titulo"),rs.getString("autor") );

                String sql2 = "select id_manga, quantidade from estoque where id_manga = ?";
                PreparedStatement ps3 = (getPreparedStatement(sql2, false));
                ps3.setLong(1, rs.getLong("id"));
                //ps3.setLong(1, 6);


                ResultSet res = ps3.executeQuery();

                if(res.next()){
                    int quantidade = res.getInt("quantidade");
                    Estoque estoque = new Estoque();
                    estoque.setQuantidade(quantidade);
                    manga.setEstoque(estoque);
                }

                magaDados.add(manga);
            }
        }

        return magaDados;
    }

    public List<Manga> listarMangasEstoquePositivo() throws Exception {
        String sql = "select * from manga";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Manga> magaDados = new ArrayList<Manga>();

        while (rs.next()) {
            boolean ativo = rs.getBoolean("ativo");
            if(ativo){
                Manga manga = new Manga(rs.getLong("id") ,rs.getString("titulo"),rs.getString("autor") );

                String sql2 = "select id_manga, quantidade from estoque where id_manga = ?";
                PreparedStatement ps3 = (getPreparedStatement(sql2, false));
                ps3.setLong(1, rs.getLong("id"));
                //ps3.setLong(1, 6);


                ResultSet res = ps3.executeQuery();

                if(res.next()){
                    int quantidade = res.getInt("quantidade");
                    if(quantidade > 0){
                        Estoque estoque = new Estoque();
                        estoque.setQuantidade(quantidade);
                        manga.setEstoque(estoque);

                        magaDados.add(manga);
                    }
                }
            }
        }

        return magaDados;
    }

    public List<Manga> listarMangasEstoque() throws Exception {
        String sql = "select * from manga";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Manga> magaDados = new ArrayList<Manga>();

        while (rs.next()) {
            boolean ativo = rs.getBoolean("ativo");
            if(ativo){
                Manga manga = new Manga(rs.getLong("id") ,rs.getString("titulo"),rs.getString("autor") );

                String sql2 = "select id_manga, quantidade from estoque where id_manga = ?";
                PreparedStatement ps3 = (getPreparedStatement(sql2, false));
                ps3.setLong(1, rs.getLong("id"));
                //ps3.setLong(1, 6);


                ResultSet res = ps3.executeQuery();

                if(res.next()){
                    int quantidade = res.getInt("quantidade");
                    Estoque estoque = new Estoque();
                    estoque.setQuantidade(quantidade);
                    manga.setEstoque(estoque);

                    magaDados.add(manga);
                }
            }
        }

        return magaDados;
    }


    @Override
    public void gravar(Manga value) throws Exception {
        String sql = "INSERT INTO manga (titulo, autor, ativo) VALUES (?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getTitulo());
        ps.setString(2, value.getAutor());
        ps.setBoolean(3, true);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(3));

        sql = "INSERT INTO estoque (id_manga, quantidade) VALUES (?,0)";
        PreparedStatement ps2 = getPreparedStatement(sql, true);
        ps2.setLong(1, value.getId());

        ps2.executeUpdate();
    }

    @Override
    public void alterar(Manga value) throws Exception {
        String sql = "update manga set titulo = ?, autor = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getTitulo());
        ps.setString(2, value.getAutor());
        ps.setLong(3, value.getId());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Manga value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            //String sql = "delete from manga where id = ?";
            String sql = "update manga set ativo = ? where id = ?";
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            //ps1.setLong(1, value.getId());
            ps1.setBoolean(1,false);
            ps1.setLong(2, value.getId());
            ps1.executeUpdate();
            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }
    }
}
