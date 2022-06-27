package org.example.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Compra {
    private long id;
    private Date date;
    private DetalheCompra detalheCompra;
    private Editora editora;


    public Compra() {}

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

    public DetalheCompra getDetalheCompra() {
        return detalheCompra;
    }

    public void setDetalheCompra(DetalheCompra detalheCompra) {
        this.detalheCompra = detalheCompra;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    @Override
    public String toString() {
        return "Id : " + id + ", Date :" + date + ", Editora: " + editora.getNome();
    }
}
