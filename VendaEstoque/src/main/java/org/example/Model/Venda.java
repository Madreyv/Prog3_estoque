package org.example.Model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Venda {
    private long id;
    private Date date;
    private Cliente cliente;
    private DetalheVenda detalheVenda;

    public Venda() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DetalheVenda getDetalheVenda() {
        return detalheVenda;
    }

    public void setDetalheVenda(DetalheVenda detalheVenda) {
        this.detalheVenda = detalheVenda;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", Data :" + date + ", Cliente: " + cliente.getNome();
    }
}
