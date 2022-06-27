package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Dao.EditoraDao;
import org.example.Dao.MangaDao;
import org.example.Model.Editora;
import org.example.Model.Manga;

import java.util.ArrayList;
import java.util.List;

public class MangaControler {
    private MangaDao mangaDao = new MangaDao();
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtNome;

    @FXML
    private Button BtnNovo;

    @FXML
    private ListView<Manga> ListItens;

    @FXML
    private TextField TxtAutor;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnEditar;

    @FXML
    private TextField TxtId;

    @FXML
    private Button BtnCadastrar;



    @FXML
    private void initialize() throws Exception {
        atualizarLista();
    }

    private void atualizarLista(){
        List<Manga> mangas = null;
        try{
            mangas = mangaDao.listar();
        }catch (Exception e){
            mangas = new ArrayList<Manga>();
        }

        ObservableList<Manga> ob = FXCollections.observableArrayList(mangas);
        ListItens.setItems(ob);

    }

    private void habilitarInterface(Boolean habilitar, Boolean editar){
        if(editar){
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtId.setDisable(habilitar);
            TxtAutor.setDisable(!habilitar);
            TxtNome.setDisable(!habilitar);

        }else{
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtId.setDisable(habilitar);
            TxtAutor.setDisable(!habilitar);
            TxtNome.setDisable(!habilitar);
        }

        BtnNovo.setDisable(habilitar);
    }

    private void habilitarInterfaceEspecial(boolean habilitar){
        BtnEditar.setDisable(habilitar);
        BtnExcluir.setDisable(habilitar);
    }

    private void limparTela(){
        TxtNome.setText("");
        TxtId.setText("");
        TxtAutor.setText("");
    }

    private void exibir(){
        Manga manga = ListItens.getSelectionModel().getSelectedItem();
        if (manga == null) return;

        TxtNome.setText(manga.getTitulo());
        TxtId.setText(String.valueOf(manga.getId()));
        TxtAutor.setText(manga.getAutor());
    }

    @FXML
    private void ListItensMouseClicked(MouseEvent evento) {
        exibir();
        habilitarInterfaceEspecial(false);
    }

    @FXML
    void btnIncluirAction(ActionEvent event) {
        this.habilitarInterface(true, false);
        this.limparTela();
        TxtId.requestFocus();
    }

    @FXML
    void btnCadastrasAction(ActionEvent event) throws Exception {
        String nome = TxtNome.getText();
        String autor = TxtAutor.getText();
        String id = TxtId.getText();

        Manga manga = new Manga(nome, autor);

       boolean filtrado = false;

        List<Manga> mangas = this.mangaDao.listar();

       try{
            if(id.equals("")) {
                mangaDao.gravar(manga);
            }else{
                manga.setId(Long.parseLong(id));
                mangaDao.alterar(manga);
            }
       }catch (Exception e){
            e.printStackTrace();
       }
       atualizarLista();

       habilitarInterface(false, false);
       habilitarInterfaceEspecial(true);
       limparTela();


    }

    @FXML
    void BtnCancelarAction(ActionEvent event) {
        this.habilitarInterface(false, false);
        habilitarInterfaceEspecial(true);
        this.limparTela();
    }

    @FXML
    void btnEditarAction(ActionEvent event) {
        this.habilitarInterface(true, true);
    }

    @FXML
    void BtnExcluirAction(ActionEvent event) {
        String nome = TxtNome.getText();
        String autor = TxtAutor.getText();
        long id = Long.parseLong(TxtId.getText());

        Manga manga = new Manga(nome, autor);
        manga.setId(id);
        try{
            mangaDao.excluir(manga);
        }catch (Exception e){
            e.printStackTrace();
        }
        atualizarLista();

        habilitarInterface(false, false);
        habilitarInterfaceEspecial(true);
        limparTela();
    }

}
