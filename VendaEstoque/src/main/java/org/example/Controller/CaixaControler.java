package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Dao.CaixaDao;
import org.example.Model.RelatorioCompra;
import org.example.Model.RelatorioVenda;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CaixaControler {
    private CaixaDao caixaDao = new CaixaDao();
    private Double valorTotalCompras = 0.0;
    private Double getValorTotalVendas = 0.0;

    @FXML
    private TextField TxtTotalVendas;

    @FXML
    private Button BtnCarregar;

    @FXML
    private TextField TxtTotalDia;

    @FXML
    private TableColumn<RelatorioCompra, Integer> ColumnVendasQuantidade;

    @FXML
    private TableColumn<RelatorioCompra, Double> ColumnComprasValor;

    @FXML
    private TableColumn<RelatorioCompra, String> ColumnVendasTitulo;

    @FXML
    private TableView<RelatorioCompra> tblCompras;

    @FXML
    private TableView<RelatorioVenda> tblVendas;

    @FXML
    private TableColumn<RelatorioCompra, String> ColumnComprasData;

    @FXML
    private TableColumn<RelatorioCompra, String> ColumnVendasData;

    @FXML
    private TableColumn<RelatorioCompra, Double> ColumnVendasValor;

    @FXML
    private TableColumn<RelatorioCompra, String> ColumnComprasTitulo;

    @FXML
    private TableColumn<RelatorioCompra, Integer> ColumnComprasQtd;

    @FXML
    private TextField TxtTotalCompras;

    @FXML
    private DatePicker DatePk;

    @FXML
    void CarregarCaixa(ActionEvent event) throws Exception {
        CarregarTabelaCompras();
        CarregarTabelaVendas();
        Double total = getValorTotalVendas - valorTotalCompras;
        TxtTotalDia.setText(total.toString());
    }

    public void CarregarTabelaCompras() throws Exception {
        Date date = Date.from(DatePk.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<RelatorioCompra> relatorioCompras = caixaDao.criarRelatorioCompras(date);

        ColumnComprasData.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, String>("date"));
        ColumnComprasQtd.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, Integer>("quantidade"));
        ColumnComprasTitulo.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, String>("titulo"));
        ColumnComprasValor.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, Double>("valor"));

        ObservableList<RelatorioCompra> ob = FXCollections.observableArrayList(relatorioCompras);

        tblCompras.setItems(ob);
        Double total = 0.0;
        for (int i = 0; i< relatorioCompras.size(); i++){
            total = total + relatorioCompras.get(i).getValor();
        }
        TxtTotalCompras.setText(total.toString());
        valorTotalCompras = total;
    }

    public void CarregarTabelaVendas() throws Exception {
        Date date = Date.from(DatePk.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<RelatorioVenda> relatorioVendas = caixaDao.criarRelatorioVendas(date);

        ColumnVendasData.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, String>("date"));
        ColumnVendasQuantidade.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, Integer>("quantidade"));
        ColumnVendasTitulo.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, String>("titulo"));
        ColumnVendasValor.setCellValueFactory(new PropertyValueFactory<RelatorioCompra, Double>("valor"));

        ObservableList<RelatorioVenda> ob = FXCollections.observableArrayList(relatorioVendas);

        tblVendas.setItems(ob);
        Double total = 0.0;
        for (int i = 0; i< relatorioVendas.size(); i++){
            total = total + relatorioVendas.get(i).getValor();
        }
        TxtTotalVendas.setText(total.toString());
        getValorTotalVendas = total;
    }
}
