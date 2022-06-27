package org.example.testes;

import org.example.Dao.CompraDao;
import org.example.Dao.EditoraDao;
import org.example.Dao.MangaDao;
import org.example.Model.Compra;
import org.example.Model.DetalheCompra;
import org.example.Model.Editora;
import org.example.Model.Manga;

import java.util.Calendar;
import java.util.List;

public class testeCompra {

    public static void main(String[] args) {
        testarGravar();
        testarListar();
    }

    private static void testarGravar(){

        try {
            List<Manga> mangas = new MangaDao().listar();
            List<Editora> editoras = new EditoraDao().listar();


            DetalheCompra detalhe = new DetalheCompra();
            detalhe.setPreco(11.50);
            detalhe.setQuantidade(3);
            detalhe.setManga(mangas.get(3));

            Compra compra = new Compra();
            Calendar calendar = Calendar.getInstance();
            compra.setDate(calendar.getTime());
            compra.setDetalheCompra(detalhe);
            compra.setEditora(editoras.get(0));

            new CompraDao().gravar(compra);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testarListar(){
        try {
            List<Compra> compras = new CompraDao().listar();

            for(Compra e: compras){
                System.out.println(e.getId()
                        + " - "  + e.getDate()
                        + " - " + e.getDetalheCompra().getManga().getId()
                        + " - " + e.getDetalheCompra().getManga().getTitulo()
                        + " - " + e.getDetalheCompra().getManga().getAutor()
                        + " - " + e.getEditora().getCnpj()
                        + " - " + e.getEditora().getNome()
                        + " - " + e.getDetalheCompra().getQuantidade()
                        + " - " + e.getDetalheCompra().getPreco()
                );
            }
        }catch (Exception e){

        }
    }

    private static void testarAuterar(){

        try {
            List<Editora> editoras = new EditoraDao().listar();
            List<Manga> mangas = new MangaDao().listar();

            DetalheCompra detalhe = new DetalheCompra();
            detalhe.setPreco(11.50);
            detalhe.setQuantidade(3);
            detalhe.setManga(mangas.get(2));

            Compra compra = new Compra();
            Calendar calendar = Calendar.getInstance();
            compra.setDate(calendar.getTime());
            compra.setDetalheCompra(detalhe);
            compra.setEditora(editoras.get(0));
            compra.setId(10);

            new CompraDao().alterar(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static void testarDelete(){

        try {
            List<Compra> compras = new CompraDao().listar();

            new CompraDao().excluir(compras.get(8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
