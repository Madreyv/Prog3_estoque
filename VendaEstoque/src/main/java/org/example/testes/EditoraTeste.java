package org.example.testes;

import org.example.Dao.ClienteDao;
import org.example.Dao.EditoraDao;
import org.example.Model.Cliente;
import org.example.Model.Editora;

import java.util.List;

public class EditoraTeste {

    public static void main(String[] args) {
        testarListar();
    }

    private static void testarGravar(){
        Editora editora = new Editora("EDITORA JBC2","00.531.662/0002-98");

        try {
            new EditoraDao().gravar(editora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarListar(){
        try {
            List<Editora> editoras = new EditoraDao().listar();

            for(Editora e: editoras){
                System.out.println(e.getNome() + " - " + e.getCnpj());
            }
        }catch (Exception e){

        }
    }

    private static void testarAuterar(){
        Editora editora = new Editora("EDITORA JBC para excluir","00.531.662/0002-98");
        try {
            new EditoraDao().alterar(editora);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void testarDelete(){
        Editora editora = new Editora("EDITORA JBC para excluir","00.531.662/0002-98");

        try {
            new EditoraDao().excluir(editora);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
