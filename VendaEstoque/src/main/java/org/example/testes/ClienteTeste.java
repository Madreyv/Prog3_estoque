package org.example.testes;

import org.example.Dao.ClienteDao;
import org.example.Model.Cliente;

import java.util.List;

public class ClienteTeste {

    public static void main(String[] args) {
        testarDelete();
    }

    private static void testarGravar(){
        Cliente cliente = new Cliente("Madreyv2","11111111111");

        try {
            new ClienteDao().gravar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarListar(){
        try {
            List<Cliente> clientes = new ClienteDao().listar();

            for(Cliente c: clientes){
                System.out.println(c.getNome() + " - " + c.getCpf());
            }
        }catch (Exception e){

        }
    }

    private static void testarAuterar(){
        Cliente cliente = new Cliente("Madreyv G","11111111111");
        try {
            new ClienteDao().alterar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void testarDelete(){
        Cliente cliente = new Cliente("Madreyv G","11111111111");

        try {
            new ClienteDao().excluir(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
