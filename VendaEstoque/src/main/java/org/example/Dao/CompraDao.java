package org.example.Dao;

import org.example.Model.Compra;
import org.example.Model.DetalheCompra;
import org.example.Model.Editora;
import org.example.Model.Manga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDao extends DaoPostgres implements  Dao<Compra>{
    @Override
    public List<Compra> listar() throws Exception {
       String sql = "select " +
                "compra.id as compra_id, " +
                "compra.data as compra_data, " +
                "manga.id as manga_id, " +
                "manga.titulo as manga_titulo, " +
                "manga.autor as manga_autor, " +
                "editora.cnpj as editora_cnpj, " +
                "editora.nome as editora_nome, " +
                "detalhe.id_compra as detalhe_id_compra, " +
                "detalhe.quantidade as quantidade, " +
                "detalhe.preco as valor " +
                "from compra, detalhe_compra as detalhe, editora, manga " +
                "where editora.cnpj = compra.cnpj_editora and detalhe.id_compra = compra.id and manga.id = detalhe.id_manga " +
                "order by compra_id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();
        List<Compra> compras = new ArrayList<Compra>();

        while (rs.next()){
            Manga manga = new Manga();
            manga.setId(rs.getLong("manga_id"));
            manga.setAutor(rs.getString("manga_autor"));
            manga.setTitulo(rs.getString("manga_titulo"));

            Editora editora = new Editora();
            editora.setCnpj(rs.getString("editora_cnpj"));
            editora.setNome(rs.getString("editora_nome"));

            DetalheCompra detalhe = new DetalheCompra();
            detalhe.setPreco(rs.getDouble("valor"));
            detalhe.setQuantidade(rs.getInt("quantidade"));
            detalhe.setManga(manga);

            Compra compra = new Compra();
            compra.setDate(rs.getDate("compra_data"));
            compra.setId(rs.getLong("compra_id"));
            compra.setDetalheCompra(detalhe);
            compra.setEditora(editora);

            compras.add(compra);

        }
        return compras;
    }

    @Override
    public void gravar(Compra value) throws Exception {
        String sql = "INSERT INTO compra (data, cnpj_editora) VALUES (?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        java.sql.Date dtValue = java.sql.Date.valueOf(value.getDate());
        ps.setDate(1,dtValue);
        ps.setString(2, value.getEditora().getCnpj());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

        sql = "insert into detalhe_compra (id_compra, quantidade, preco, id_manga) VALUES (?,?,?,?)";
        PreparedStatement ps2 = (getPreparedStatement(sql, true));
        ps2.setLong(1, value.getId());
        ps2.setInt(2, value.getDetalheCompra().getQuantidade());
        ps2.setDouble(3, value.getDetalheCompra().getPreco());
        ps2.setLong(4, value.getDetalheCompra().getManga().getId());

        ps2.executeUpdate();

        updateEstoque("inserir", value);
    }

    @Override
    public void alterar(Compra value) throws Exception {
        String sql = "update compra set data = ?, cnpj_editora = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        java.sql.Date dtValue = java.sql.Date.valueOf(value.getDate());
        ps.setDate(1, dtValue);
        ps.setString(2,value.getEditora().getCnpj());
        ps.setLong(3,value.getId());

        ps.executeUpdate();

        updateEstoque("alterar",value);

        sql = "update detalhe_compra set preco = ?, quantidade = ? where id_compra = ? and id_manga = ?";
        PreparedStatement ps2 = (getPreparedStatement(sql, false));
        ps2.setDouble(1, value.getDetalheCompra().getPreco());
        ps2.setLong(3, value.getId());
        ps2.setInt(2, value.getDetalheCompra().getQuantidade());
        ps2.setLong(4, value.getDetalheCompra().getManga().getId());

        ps2.executeUpdate();


    }

    @Override
    public void excluir(Compra value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            String sql = "delete from detalhe_compra where id_compra = ? and id_manga = ?";
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            ps1.setLong(1, value.getId());
            ps1.setLong(2, value.getDetalheCompra().getManga().getId());
            ps1.executeUpdate();

            sql = "delete from compra where id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql);
            ps2.setLong(1, value.getId());
            ps2.executeUpdate();

            updateEstoque("excluir", value);

            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }
    }

    private void updateEstoque(String tipo, Compra value){

        try {
            String sql = "select id_manga, quantidade from estoque where id_manga = ?";
            PreparedStatement ps3 = (getPreparedStatement(sql, false));
            ps3.setLong(1, value.getDetalheCompra().getManga().getId());

            ResultSet res = ps3.executeQuery();
            res.next();

            int quantidade = 0;

            if(tipo.equals("inserir")){
                quantidade = res.getInt("quantidade") + value.getDetalheCompra().getQuantidade();
            }else if(tipo.equals("alterar")){
                sql = "select quantidade from detalhe_compra where id_compra = ?";
                PreparedStatement ps5 = (getPreparedStatement(sql, false));
                ps5.setLong(1, value.getId());

                ResultSet rs = ps5.executeQuery();
                rs.next();
                int valor = rs.getInt(1);

                quantidade = res.getInt("quantidade") + value.getDetalheCompra().getQuantidade() - valor;
            }else{
                quantidade = res.getInt("quantidade") - value.getDetalheCompra().getQuantidade();
            }

            sql = "update estoque set quantidade = ? where id_manga = ?";
            PreparedStatement ps4 = (getPreparedStatement(sql, false));
            ps4.setInt(1, quantidade);
            ps4.setLong(2, value.getDetalheCompra().getManga().getId());

            ps4.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
