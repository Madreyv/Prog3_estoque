package org.example.Model;

public class DetalheVenda {
    private Integer quantidade;
    private Double preco;
    private Manga manga;

    public DetalheVenda(Integer quantidade, Double preco) {
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public DetalheVenda(){};

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }
}
