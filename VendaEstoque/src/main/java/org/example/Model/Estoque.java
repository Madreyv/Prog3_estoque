package org.example.Model;

public class Estoque {
    private int quantidade;

    public Estoque() {
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        if (this.quantidade > 0){
            return  quantidade + " unidades";
        }else{
            return quantidade + " unidade";
        }
    }
}
