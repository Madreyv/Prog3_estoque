package org.example.Dao;

import org.example.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDao extends DaoPostgres implements  Dao<Venda>{
    @Override
    public List<Venda> listar() throws Exception {
       String sql = "select " +
                "venda.id as venda_id, " +
                "venda.data as venda_data, " +
                "manga.id as manga_id, " +
                "manga.titulo as manga_titulo, " +
                "manga.autor as manga_autor, " +
                "cliente.cpf as cliente_cpf, " +
                "cliente.nome as cliente_nome, " +
                "detalhe.id_venda as detalhe_id_venda, " +
                "detalhe.quantidade as quantidade, " +
                "detalhe.preco as valor " +
                "from venda, detalhe_venda as detalhe, cliente, manga " +
                "where cliente.cpf = venda.cpf_cliente and detalhe.id_venda = venda.id and manga.id = detalhe.id_manga " +
                "order by venda_id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();
        List<Venda> vendas = new ArrayList<Venda>();

        while (rs.next()){
            Manga manga = new Manga();
            manga.setId(rs.getLong("manga_id"));
            manga.setAutor(rs.getString("manga_autor"));
            manga.setTitulo(rs.getString("manga_titulo"));

            Cliente cliente = new Cliente();
            cliente.setCpf(rs.getString("cliente_cpf"));
            cliente.setNome(rs.getString("cliente_nome"));

            DetalheVenda detalhe = new DetalheVenda();
            detalhe.setPreco(rs.getDouble("valor"));
            detalhe.setQuantidade(rs.getInt("quantidade"));
            detalhe.setManga(manga);

            Venda venda = new Venda();
            venda.setDate(rs.getDate("venda_data"));
            venda.setId(rs.getLong("venda_id"));
            venda.setDetalheVenda(detalhe);
            venda.setCliente(cliente);

            vendas.add(venda);

        }
        return vendas;
    }

    @Override
    public void gravar(Venda value) throws Exception {
        String sql = "INSERT INTO venda (data, cpf_cliente) VALUES (?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        java.sql.Date dtValue = java.sql.Date.valueOf(value.getDate());
        ps.setDate(1,dtValue);
        ps.setString(2, value.getCliente().getCpf());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

        sql = "insert into detalhe_venda (id_venda, quantidade, preco, id_manga) VALUES (?,?,?,?)";
        PreparedStatement ps2 = (getPreparedStatement(sql, true));
        ps2.setLong(1, value.getId());
        ps2.setInt(2, value.getDetalheVenda().getQuantidade());
        ps2.setDouble(3, value.getDetalheVenda().getPreco());
        ps2.setLong(4, value.getDetalheVenda().getManga().getId());

        ps2.executeUpdate();

        updateEstoque("inserir", value);

    }

    @Override
    public void alterar(Venda value) throws Exception {
        String sql = "update venda set data = ?, cpf_cliente = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        java.sql.Date dtValue = java.sql.Date.valueOf(value.getDate());
        ps.setDate(1, dtValue);
        ps.setString(2,value.getCliente().getCpf());
        ps.setLong(3,value.getId());

        ps.executeUpdate();

        updateEstoque("alterar",value);

        sql = "update detalhe_venda set preco = ?, quantidade = ? where id_venda = ? and id_manga = ?";
        PreparedStatement ps2 = (getPreparedStatement(sql, false));
        ps2.setDouble(1, value.getDetalheVenda().getPreco());
        ps2.setInt(2, value.getDetalheVenda().getQuantidade());
        ps2.setLong(3, value.getId());
        ps2.setLong(4, value.getDetalheVenda().getManga().getId());

        ps2.executeUpdate();

    }

    @Override
    public void excluir(Venda value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            String sql = "delete from detalhe_venda where id_venda = ? and id_manga = ?";
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            ps1.setLong(1, value.getId());
            ps1.setLong(2, value.getDetalheVenda().getManga().getId());
            ps1.executeUpdate();

            sql = "delete from venda where id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql);
            ps2.setLong(1, value.getId());
            ps2.executeUpdate();

            updateEstoque("deletar", value);

            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }


    }

    private void updateEstoque(String tipo, Venda value){
        try {
            String sql = "select id_manga, quantidade from estoque where id_manga = ?";
            PreparedStatement ps3 = (getPreparedStatement(sql, false));
            ps3.setLong(1, value.getDetalheVenda().getManga().getId());

            ResultSet res = ps3.executeQuery();
            res.next();

            int quantidade = 0;

            if(tipo.equals("inserir")){
                quantidade = res.getInt("quantidade") - value.getDetalheVenda().getQuantidade();

            }else if(tipo.equals("alterar")){
                sql = "select quantidade from detalhe_venda where id_venda = ?";
                PreparedStatement ps5 = (getPreparedStatement(sql, false));
                ps5.setLong(1, value.getId());

                ResultSet rs = ps5.executeQuery();
                rs.next();
                int valor = rs.getInt(1);

                quantidade = res.getInt("quantidade") + value.getDetalheVenda().getQuantidade() - valor;
            }else{
                quantidade = res.getInt("quantidade") + value.getDetalheVenda().getQuantidade();
            }

            sql = "update estoque set quantidade = ? where id_manga = ?";
            PreparedStatement ps4 = (getPreparedStatement(sql, false));
            ps4.setInt(1, quantidade);
            ps4.setLong(2, value.getDetalheVenda().getManga().getId());

            ps4.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
