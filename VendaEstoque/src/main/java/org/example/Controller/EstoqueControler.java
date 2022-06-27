package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.Dao.MangaDao;
import org.example.Model.Estoque;
import org.example.Model.Manga;

import java.util.ArrayList;
import java.util.List;

public class EstoqueControler {
    private MangaDao mangaDao = new MangaDao();

    @FXML
    private TableView<Manga> tblEstoque;

    @FXML
    private TableColumn<Manga, Long> ColumnID;

    @FXML
    private TableColumn<Estoque, Estoque> ColumnQtd;

    @FXML
    private TableColumn<Manga, String> ColumnNome;

    @FXML
    private void initialize() throws Exception {
        preencherTabela();
    }

   private void preencherTabela(){
       try {
           List<Manga> mangas = mangaDao.listarMangasEstoque();
           ColumnNome.setCellValueFactory(new PropertyValueFactory<Manga, String>("titulo"));
           ColumnID.setCellValueFactory(new PropertyValueFactory<Manga, Long>("id"));
           ColumnQtd.setCellValueFactory(new PropertyValueFactory<Estoque, Estoque>("estoque"));

           ObservableList<Manga> ob = FXCollections.observableArrayList(mangas);

           tblEstoque.setItems(ob);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
