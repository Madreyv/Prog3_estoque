package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.Dao.CompraDao;
import org.example.Dao.EditoraDao;
import org.example.Dao.MangaDao;
import org.example.Model.Compra;
import org.example.Model.DetalheCompra;
import org.example.Model.Editora;
import org.example.Model.Manga;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EntradaControler {
    private MangaDao mangaDao = new MangaDao();
    private CompraDao compraDao = new CompraDao();
    private EditoraDao editoraDao = new EditoraDao();

    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtQuantidade;

    @FXML
    private Button BtnNovo;

    @FXML
    private DatePicker datePick;

    @FXML
    private ListView<Compra> ListItens;

    @FXML
    private ComboBox<Editora> cboEditora;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnEditar;

    @FXML
    private TextField TxtId;

    @FXML
    private Button BtnCadastrar;

    @FXML
    private ComboBox<Manga> cboManga;

    @FXML
    private TextField TxtValor;

    @FXML
    void getDate(ActionEvent event) {

    }



    @FXML
    private void initialize() throws Exception {
        atualizarLista();
    }

    private void atualizarLista(){
        List<Compra> compras = null;
        List<Manga> mangas = null;
        List<Editora> editoras = null;
        try{
            compras = compraDao.listar();
            mangas = mangaDao.listar();
            editoras = editoraDao.listar();
        }catch (Exception e){
            compras = new ArrayList<Compra>();
            editoras = new ArrayList<Editora>();
            mangas = new ArrayList<Manga>();
        }

        ObservableList<Compra> ob = FXCollections.observableArrayList(compras);
        ObservableList<Manga> obm = FXCollections.observableArrayList(mangas);
        ObservableList<Editora> obe = FXCollections.observableArrayList(editoras);

        ListItens.setItems(ob);
        cboManga.setItems(obm);
        cboEditora.setItems(obe);

    }

    private void habilitarInterface(Boolean habilitar, Boolean editar){
        if(editar){
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            cboManga.setDisable(habilitar);
            TxtQuantidade.setDisable(!habilitar);
            TxtValor.setDisable(!habilitar);
        }else{
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtValor.setDisable(!habilitar);
        }

        cboEditora.setDisable(!habilitar);
        cboManga.setDisable(!habilitar);
        datePick.setDisable(!habilitar);
        TxtQuantidade.setDisable(!habilitar);
        TxtId.setDisable(true);
        BtnNovo.setDisable(habilitar);
    }

    private void habilitarInterfaceEspecial(boolean habilitar){
        BtnEditar.setDisable(habilitar);
        BtnExcluir.setDisable(habilitar);

        /*
         ObservableList<Livro> livrosOb = FXCollections.observableArrayList(livros);
        ListItensLivros.setItems(livrosOb);
        CBOLivros.setItems(livrosOb);
         */
    }

    private void limparTela(){
        TxtId.setText("");
        TxtQuantidade.setText("");
        TxtValor.setText("");
        cboEditora.setValue(null);
        cboManga.setValue(null);
        datePick.setValue(null);

    }

    private void exibir(){
        Compra compra = ListItens.getSelectionModel().getSelectedItem();
        if (compra == null) return;

        TxtId.setText(String.valueOf(compra.getId()));
        TxtValor.setText(compra.getDetalheCompra().getPreco().toString());
        TxtQuantidade.setText(compra.getDetalheCompra().getQuantidade().toString());
        cboManga.setValue(compra.getDetalheCompra().getManga());
        cboEditora.setValue(compra.getEditora());
        datePick.setValue(LocalDate.parse(compra.getDate()));
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
        Integer quantidade = Integer.valueOf(TxtQuantidade.getText());
        String valor = TxtValor.getText();
        //Calendar calendar = Calendar.getInstance();
        Manga manga = cboManga.getValue();
        Editora editora = cboEditora.getValue();

        DetalheCompra detalheCompra = new DetalheCompra();
        detalheCompra.setQuantidade(quantidade);
        detalheCompra.setPreco(Double.valueOf(valor));
        detalheCompra.setManga(manga);

        Compra compra = new Compra();
        compra.setDetalheCompra(detalheCompra);
        compra.setEditora(editora);
        Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        compra.setDate(date);

        String id = TxtId.getText();

        System.out.println(compra.getId()
                + " - "  + compra.getDate()
                + " - " + compra.getDetalheCompra().getManga().getId()
                + " - " + compra.getDetalheCompra().getManga().getTitulo()
                + " - " + compra.getDetalheCompra().getManga().getAutor()
                + " - " + compra.getEditora().getCnpj()
                + " - " + compra.getEditora().getNome()
                + " - " + compra.getDetalheCompra().getQuantidade()
                + " - " + compra.getDetalheCompra().getPreco());

       try{
           if(id.equals("")) {
                compraDao.gravar(compra);
            }else{
                compra.setId(Long.parseLong(id));
                compraDao.alterar(compra);
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
        Integer quantidade = Integer.valueOf(TxtQuantidade.getText());
        String valor = TxtValor.getText();
        //Calendar calendar = Calendar.getInstance();
        Manga manga = cboManga.getValue();
        Editora editora = cboEditora.getValue();

        DetalheCompra detalheCompra = new DetalheCompra();
        detalheCompra.setQuantidade(quantidade);
        detalheCompra.setPreco(Double.valueOf(valor));
        detalheCompra.setManga(manga);

        Compra compra = new Compra();
        compra.setDetalheCompra(detalheCompra);
        compra.setEditora(editora);
        Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        compra.setDate(date);

        String id = TxtId.getText();
        compra.setId(Long.parseLong(id));

        try{
            compraDao.excluir(compra);
        }catch (Exception e){
            e.printStackTrace();
        }
        atualizarLista();

        habilitarInterface(false, false);
        habilitarInterfaceEspecial(true);
        limparTela();
    }

}
