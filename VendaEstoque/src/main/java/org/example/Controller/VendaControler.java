package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.Dao.*;
import org.example.Model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VendaControler {
    private MangaDao mangaDao = new MangaDao();
    private VendaDao vendaDao = new VendaDao();
    private ClienteDao clienteDao = new ClienteDao();

    @FXML
    private Button BtnCancelar;

    @FXML
    private ComboBox<Cliente> cboCliente;

    @FXML
    private Button BtnNovo;

    @FXML
    private DatePicker datePick;

    @FXML
    private ListView<Venda> ListItens;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnEditar;

    @FXML
    private TextField TxtId;

    @FXML
    private Button BtnCadastrar;

    @FXML
    private TextField TxtValor;

    @FXML
    private ComboBox<Manga> cboManga;

    @FXML
    private ComboBox<Integer> cboQuantidade;


    @FXML
    private void initialize() throws Exception {
        atualizarLista();
    }

    private void atualizarLista(){
        List<Venda> vendas = null;
        List<Manga> mangas = null;
        List<Cliente> clientes = null;
        try{
            vendas = vendaDao.listar();
            mangas = mangaDao.listarMangasEstoquePositivo();
            //mangas = mangaDao.listar();
            clientes = clienteDao.listar();
        }catch (Exception e){
            vendas = new ArrayList<Venda>();
            clientes = new ArrayList<Cliente>();
            mangas = new ArrayList<Manga>();
        }

        ObservableList<Venda> ob = FXCollections.observableArrayList(vendas);
        ObservableList<Manga> obm = FXCollections.observableArrayList(mangas);
        ObservableList<Cliente> obe = FXCollections.observableArrayList(clientes);

        ListItens.setItems(ob);
        cboManga.setItems(obm);
        cboCliente.setItems(obe);
        cboQuantidade.setItems(null);

    }

    private void habilitarInterface(Boolean habilitar, Boolean editar){
        if(editar){
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            cboManga.setDisable(habilitar);
            cboQuantidade.setDisable(!habilitar);
            TxtValor.setDisable(!habilitar);
        }else{
            BtnCadastrar.setDisable(!habilitar);
            BtnCancelar.setDisable(!habilitar);
            TxtValor.setDisable(!habilitar);
            cboQuantidade.setDisable(habilitar);
        }

        cboCliente.setDisable(!habilitar);
        cboManga.setDisable(!habilitar);
        datePick.setDisable(!habilitar);
        TxtId.setDisable(true);
        BtnNovo.setDisable(habilitar);
    }

    private void habilitarInterfaceEspecial(boolean habilitar){
        BtnEditar.setDisable(habilitar);
        BtnExcluir.setDisable(habilitar);

    }

    private void limparTela(){
        TxtId.setText("");
        cboQuantidade.setValue(null);
        cboQuantidade.setItems(null);
        TxtValor.setText("");
        cboCliente.setValue(null);
        cboManga.setValue(null);
        datePick.setValue(null);

    }

    private void exibir(){
        Venda venda = ListItens.getSelectionModel().getSelectedItem();
        if (venda == null) return;

        TxtId.setText(String.valueOf(venda.getId()));
        TxtValor.setText(venda.getDetalheVenda().getPreco().toString());
        cboManga.setValue(venda.getDetalheVenda().getManga());
        try {
            Manga manga = venda.getDetalheVenda().getManga();
            List<Manga> teste = mangaDao.listar()
                    .stream().filter(m -> m.getId() == manga.getId())
                    .collect(Collectors.toList());
            carregarCboQuantidade(teste.get(0).getEstoque().getQuantidade());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cboQuantidade.setValue(venda.getDetalheVenda().getQuantidade());
        cboCliente.setValue(venda.getCliente());
        datePick.setValue(LocalDate.parse(venda.getDate()));
        cboQuantidade.setDisable(true);
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

    void carregarCboQuantidade(Integer quantidade){
        List<Integer> listaQuantidades = new ArrayList<Integer>();
        for (int i = 1; i <= quantidade; i++){
            listaQuantidades.add(i);
        }
        ObservableList<Integer> ob = FXCollections.observableArrayList(listaQuantidades);
        cboQuantidade.setDisable(false);
        cboQuantidade.setItems(ob);
    }

    @FXML
    void getSelectedManga(ActionEvent event) throws Exception {

        Manga manga = cboManga.getValue();
        if (manga == null) return;
        System.out.println(manga);
        List<Manga> teste = mangaDao.listar()
                .stream().filter(m -> m.getId() == manga.getId())
                .collect(Collectors.toList());

        if(teste != null){
            //manga = teste.get(0);
            int quantidade = teste.get(0).getEstoque().getQuantidade();
            carregarCboQuantidade(quantidade);
        }

    }

    @FXML
    void btnCadastrasAction(ActionEvent event) throws Exception {
        Integer quantidade = Integer.valueOf(cboQuantidade.getValue());
        String valor = TxtValor.getText();
        Manga manga = cboManga.getValue();
        Cliente cliente = cboCliente.getValue();

        DetalheVenda detalheVenda = new DetalheVenda();
        detalheVenda.setQuantidade(quantidade);
        detalheVenda.setPreco(Double.valueOf(valor));
        detalheVenda.setManga(manga);

        Venda venda = new Venda();
        venda.setDetalheVenda(detalheVenda);
        venda.setCliente(cliente);
        Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        venda.setDate(date);

        String id = TxtId.getText();

        System.out.println(venda.getId()
                + " - "  + venda.getDate()
                + " - " + venda.getDetalheVenda().getManga().getId()
                + " - " + venda.getDetalheVenda().getManga().getTitulo()
                + " - " + venda.getDetalheVenda().getManga().getAutor()
                + " - " + venda.getCliente().getCpf()
                + " - " + venda.getCliente().getNome()
                + " - " + venda.getDetalheVenda().getQuantidade()
                + " - " + venda.getDetalheVenda().getPreco());

       try{
           if(id.equals("")) {
                vendaDao.gravar(venda);
            }else{
                venda.setId(Long.parseLong(id));
                vendaDao.alterar(venda);
            }


       }catch (Exception e){
            e.printStackTrace();
       }
       atualizarLista();

       habilitarInterface(false, false);
       habilitarInterfaceEspecial(true);
       limparTela();
        cboQuantidade.setDisable(true);

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
        Integer quantidade = Integer.valueOf(cboQuantidade.getValue());
        String valor = TxtValor.getText();
        Manga manga = cboManga.getValue();
        Cliente cliente = cboCliente.getValue();

        DetalheVenda detalheVenda = new DetalheVenda();
        detalheVenda.setQuantidade(quantidade);
        detalheVenda.setPreco(Double.valueOf(valor));
        detalheVenda.setManga(manga);

        Venda venda = new Venda();
        venda.setDetalheVenda(detalheVenda);
        venda.setCliente(cliente);
        Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        venda.setDate(date);

        String id = TxtId.getText();
        venda.setId(Long.parseLong(id));

        try{
            vendaDao.excluir(venda);
        }catch (Exception e){
            e.printStackTrace();
        }
        atualizarLista();

        habilitarInterface(false, false);
        habilitarInterfaceEspecial(true);
        cboQuantidade.setDisable(true);
        limparTela();
    }

}
