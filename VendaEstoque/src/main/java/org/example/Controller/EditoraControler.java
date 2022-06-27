package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Dao.ClienteDao;
import org.example.Dao.EditoraDao;
import org.example.Model.Cliente;
import org.example.Model.Editora;

import java.util.ArrayList;
import java.util.List;

public class EditoraControler {
    private EditoraDao editoraDao = new EditoraDao();
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtCnpj;

    @FXML
    private Button BtnNovo;

    @FXML
    private Button BtnCadastrar;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnEditar;

    @FXML
    private ListView<Editora> ListItens;



    @FXML
    private void initialize() throws Exception {
        atualizarLista();
    }

    private void atualizarLista(){
        List<Editora> editoras = null;
        try{
            editoras = editoraDao.listar();
        }catch (Exception e){
            editoras = new ArrayList<Editora>();
        }

        ObservableList<Editora> ob = FXCollections.observableArrayList(editoras);
        ListItens.setItems(ob);

    }

    private void habilitarInterface(Boolean habilitar, Boolean editar){
        if(editar){
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtCnpj.setDisable(habilitar);
            TxtNome.setDisable(!habilitar);

        }else{
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtCnpj.setDisable(!habilitar);
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
        TxtCnpj.setText("");
    }

    private void exibir(){
        Editora editora = ListItens.getSelectionModel().getSelectedItem();
        if (editora == null) return;

        TxtNome.setText(editora.getNome());
        TxtCnpj.setText(editora.getCnpj());
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
        TxtCnpj.requestFocus();
    }

    @FXML
    void btnCadastrasAction(ActionEvent event) throws Exception {
        String nome = TxtNome.getText();
        String cnpj = TxtCnpj.getText();

        Editora editora = new Editora(nome, cnpj);

        boolean filtrado = false;

        List<Editora> editoras = this.editoraDao.listar();

        for (Editora x: editoras) {
            if(x.getCnpj().equals(editora.getCnpj())){
                filtrado = true;
            }
        }

       try{
            if(filtrado) {
                editoraDao.alterar(editora);
            }else{
                editoraDao.gravar(editora);
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
        String cnpj = TxtCnpj.getText();

        Editora editora = new Editora(nome, cnpj);
        try{
            editoraDao.excluir(editora);
        }catch (Exception e){
            e.printStackTrace();
        }
        atualizarLista();

        habilitarInterface(false, false);
        habilitarInterfaceEspecial(true);
        limparTela();
    }

}
