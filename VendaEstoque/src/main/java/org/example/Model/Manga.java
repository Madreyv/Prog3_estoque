package org.example.Model;

public class Manga {
    private Long id;
    private String titulo;
    private String autor;
    private Estoque estoque;

    public Manga(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public Manga(long id,String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
    }

    public Manga() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Id:   " + id +
                " titulo: " + titulo;
    }
}
