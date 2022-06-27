package org.example.Dao;

import org.example.Model.Estoque;
import org.example.Model.Manga;
import org.example.Model.RelatorioCompra;
import org.example.Model.RelatorioVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaixaDao extends DaoPostgres{

   public List<RelatorioCompra> criarRelatorioCompras(Date date) throws Exception {
       String sql = "select compra.data as data, " +
               "manga.titulo as titulo, detalhe.quantidade as quantidade, " +
               "detalhe.preco as valor from manga, detalhe_compra as detalhe, compra " +
               "where detalhe.id_compra = compra.id and manga.id = detalhe.id_manga and compra.data = ? ";
       PreparedStatement ps = getPreparedStatement(sql, false);
       java.sql.Date data = new java.sql.Date(date.getTime());
       ps.setDate(1,  data);
       ResultSet rs = ps.executeQuery();

       List<RelatorioCompra> relatorioCompras = new ArrayList<RelatorioCompra>();

       while (rs.next()) {
           RelatorioCompra rel = new RelatorioCompra();
           rel.setDate(rs.getDate("data"));
           rel.setQuantidade(rs.getInt("quantidade"));
           rel.setTitulo(rs.getString("titulo"));
           rel.setValor(rs.getDouble("valor"));

           relatorioCompras.add(rel);
       }

       return relatorioCompras;
   }

   public List<RelatorioVenda> criarRelatorioVendas(Date date) throws Exception {
        String sql = "select venda.data as data, " +
                "manga.titulo as titulo, detalhe.quantidade as quantidade, " +
                "detalhe.preco as valor from manga, detalhe_venda as detalhe, venda " +
                "where detalhe.id_venda = venda.id and manga.id = detalhe.id_manga and venda.data = ? ";
        PreparedStatement ps = getPreparedStatement(sql, false);
        java.sql.Date data = new java.sql.Date(date.getTime());
        ps.setDate(1,  data);
        ResultSet rs = ps.executeQuery();

        List<RelatorioVenda> relatorioVendas = new ArrayList<RelatorioVenda>();

        while (rs.next()) {
            RelatorioVenda rel = new RelatorioVenda();
            rel.setDate(rs.getDate("data"));
            rel.setQuantidade(rs.getInt("quantidade"));
            rel.setTitulo(rs.getString("titulo"));
            rel.setValor(rs.getDouble("valor"));

            relatorioVendas.add(rel);
        }

        return relatorioVendas;
    }


}
