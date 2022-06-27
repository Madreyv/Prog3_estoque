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
import org.example.Model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

public class ClienteControler {
    private ClienteDao clienteDao = new ClienteDao();
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtCpf;

    @FXML
    private Button BtnNovo;

    @FXML
    private Button BtnCadastrar;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnEditar;

    @FXML
    private ListView<Cliente> ListItens;



    @FXML
    private void initialize() throws Exception {
        atualizarLista();
    }

    private void atualizarLista(){
        List<Cliente> clientes = null;
        try{
            clientes = clienteDao.listar();
        }catch (Exception e){
            clientes = new ArrayList<Cliente>();
        }

        ObservableList<Cliente> clienteOb = FXCollections.observableArrayList(clientes);
        ListItens.setItems(clienteOb);

    }

    private void habilitarInterface(Boolean habilitar, Boolean editar){
        if(editar){
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtCpf.setDisable(habilitar);
            TxtNome.setDisable(!habilitar);

        }else{
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtCpf.setDisable(!habilitar);
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
        TxtCpf.setText("");
    }

    private void exibir(){
        Cliente cliente = ListItens.getSelectionModel().getSelectedItem();
        if (cliente == null) return;

        TxtNome.setText(cliente.getNome());
        TxtCpf.setText(cliente.getCpf());
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
        TxtCpf.requestFocus();
    }

    @FXML
    void btnCadastrasAction(ActionEvent event) throws Exception {
        String nome = TxtNome.getText();
        String cpf = TxtCpf.getText();

        Cliente cliente = new Cliente(nome, cpf);

        boolean filtrado = false;

        List<Cliente> clientes = this.clienteDao.listar();

        for (Cliente x: clientes) {
            if(x.getCpf().equals(cliente.getCpf())){
                filtrado = true;
            }
        }

        System.out.println(filtrado);

       try{
            if(filtrado) {
                clienteDao.alterar(cliente);
            }else{
                clienteDao.gravar(cliente);
            }
       }catch (Exception e){
            e.printStackTrace();
       }
       atualizarLista();
       habilitarInterfaceEspecial(true);
       habilitarInterface(false, false);
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
        String cpf = TxtCpf.getText();

        Cliente cliente = new Cliente(nome, cpf);
        try{
            clienteDao.excluir(cliente);
        }catch (Exception e){
            e.printStackTrace();
        }
        atualizarLista();
        habilitarInterfaceEspecial(true);
        habilitarInterface(false, false);
        limparTela();
    }

}
