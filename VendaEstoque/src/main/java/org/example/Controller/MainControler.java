package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainControler {
    @FXML
    private MenuItem menuCadManga;

    @FXML
    private MenuItem menuCadEditora;

    @FXML
    private MenuItem menuCadCompra;

    @FXML
    private MenuItem menuCadCliente;

    @FXML
    private MenuItem menuCadVenda;

    @FXML
    private MenuBar menuCadButton;

    @FXML
    private void initialize() throws Exception {

        // emprestimoDao.carregar();
    }

    @FXML
    void acessarFormularioCliente(ActionEvent event) throws IOException {
        abrirFormulario("ClienteView");
    }

    @FXML
    void acessarFormularioEditora(ActionEvent event) throws IOException {
        abrirFormulario("EditoraView");
    }

    @FXML
    void acessarFormularioManga(ActionEvent event) throws IOException {
        abrirFormulario("MangaView");
    }


    @FXML
    void acessarFormularioCompra(ActionEvent event) throws IOException {
        abrirFormulario("EntradaView");
    }

    @FXML
    void acessarFormularioVenda(ActionEvent event) throws IOException {
        abrirFormulario("VendaView");
    }

    public void abrirFormulario(String formulario) throws IOException {
        URL url = getClass().getResource( "/fxml/" + formulario + ".fxml");
        System.out.println(formulario);
        Parent form = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setScene((new Scene(form)));
        //stage.setTitle("Formulário Autor");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();


    }

    @FXML
    public void acessarEstoque(ActionEvent event) throws IOException {
        abrirFormulario("EstoqueView");
    }

    public void acessarCaixa(ActionEvent event) throws IOException {
        abrirFormulario("CaixaView");
    }
}
