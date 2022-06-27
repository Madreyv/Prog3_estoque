package org.example.testes;

import org.example.Dao.*;
import org.example.Model.*;

import java.util.Calendar;
import java.util.List;

public class testeVenda {

    public static void main(String[] args) {
        testarDelete();
        //testarGravar();
        testarListar();
    }

    private static void testarGravar(){

        try {
            List<Manga> mangas = new MangaDao().listar();
            List<Cliente> clientes = new ClienteDao().listar();


            DetalheVenda detalhe = new DetalheVenda();
            detalhe.setPreco(20.50);
            detalhe.setQuantidade(1);
            detalhe.setManga(mangas.get(3));

            Venda venda = new Venda();
            Calendar calendar = Calendar.getInstance();
            venda.setDate(calendar.getTime());
            venda.setDetalheVenda(detalhe);
            venda.setCliente(clientes.get(0));

            new VendaDao().gravar(venda);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testarListar(){
        try {
            List<Venda> vendas = new VendaDao().listar();

            for(Venda e: vendas){
                System.out.println(e.getId()
                        + " - "  + e.getDate()
                        + " - " + e.getDetalheVenda().getManga().getId()
                        + " - " + e.getDetalheVenda().getManga().getTitulo()
                        + " - " + e.getDetalheVenda().getManga().getAutor()
                        + " - " + e.getCliente().getCpf()
                        + " - " + e.getCliente().getNome()
                        + " - " + e.getDetalheVenda().getQuantidade()
                        + " - " + e.getDetalheVenda().getPreco()
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testarAuterar(){

        try {
            List<Cliente> clientes = new ClienteDao().listar();
            List<Manga> mangas = new MangaDao().listar();

            DetalheVenda detalhe = new DetalheVenda();
            detalhe.setPreco(11.50);
            detalhe.setQuantidade(3);
            detalhe.setManga(mangas.get(2));

            Venda venda = new Venda();
            Calendar calendar = Calendar.getInstance();
            venda.setDate(calendar.getTime());
            venda.setDetalheVenda(detalhe);
            venda.setCliente(clientes.get(0));
            venda.setId(1);

            new VendaDao().alterar(venda);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static void testarDelete(){

        try {
            List<Venda> vendas = new VendaDao().listar();

            new VendaDao().excluir(vendas.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
