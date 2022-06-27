package org.example.Model;

import java.util.Date;

public class RelatorioVenda {
    private String titulo;
    private Integer quantidade;
    private Double valor;
    private Date date;

    public RelatorioVenda() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
