package org.example.testes;

import org.example.Dao.EditoraDao;
import org.example.Dao.MangaDao;
import org.example.Model.Editora;
import org.example.Model.Manga;

import java.util.List;

public class testeManga {

    public static void main(String[] args) {
        //testarDelete();
        testarGravar();
        testarListar();
    }

    private static void testarGravar(){
        Manga manga = new Manga("Naruto v4","Masashi Kishimoto");

        try {
            new MangaDao().gravar(manga);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(manga.getId());
    }

    private static void testarListar(){
        try {
            List<Manga> mangas = new MangaDao().listar();

            for(Manga e: mangas){
                System.out.println(e.getId() + " - " + e.getTitulo() + " - " + e.getAutor());
            }
        }catch (Exception e){

        }
    }

    private static void testarAuterar(){
        Manga manga = new Manga(1,"Naruto volume 1","Masashi Kishimoto");        try {
            new MangaDao().alterar(manga);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void testarDelete(){
        Manga manga = new Manga(4,"Naruto volume 1","Masashi Kishimoto");

        try {
            new MangaDao().excluir(manga);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
