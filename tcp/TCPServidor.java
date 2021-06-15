/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author Juraski
 */
public class TCPServidor {

    /**
     * @param args the command line arguments
     */

    //Declaração de variáveis globais
     static String cardapio = "| ---------------------   MENU   --------------------- |\n" +
                              "| Codigo | ------ |     Pizza         | ------ | Valor |\n" +
                              "|   10   | ------ | Quatro Queijo     | ------ | 50,00 |\n" +
                              "|   11   | ------ | Bacon             | ------ | 60,00 |\n" +
                              "|   12   | ------ | Picanha           | ------ | 70,00 |\n" +
                              "|   13   | ------ | Queijo e Presunto | ------ | 30,00 |\n" + 
                              "| ---------------------------------------------------- |\n"; 
    //lista de pedidos
    static ArrayList<String> pedidos = new ArrayList<String>();     

    //Função case statement
     static public String choice(String option) {
        switch (option) {
            case "1":
                return cardapio + "\n";
            
            case "2":
                return pedidos + "\n";

            case "10":
                pedidos.add("10");
                return "Option: 10\tTotal:50.00 R$\n";

            case "11":
                pedidos.add("11");
                return "Option: 11\tTotal:60.00 R$\n";
            

            case "12":
                pedidos.add("12");
                return "Option: 12\tTotal:70.00 R$\n";
            

            case "13":
                pedidos.add("13");
                return "Option: 13\tTotal:30.00 R$\n";
            
            default:
                return "Entrada inválida Imbecíl!!!";
        }
    }
    
    public static void main(String[] args) {
        try{                    
            int porta = 8080;

            // criando um socket para ouvir na porta e com uma fila de tamanho 10
            ServerSocket servidor = new ServerSocket(porta);
            Socket conexao;

            boolean sair = false;
            String mensagem = "";

            System.out.println("Ouvindo na porta: " + porta);

            //ficará bloqueado até algum cliente se conectar
            conexao = servidor.accept();

            //obtendo os fluxos de entrada e de saida
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());

            System.out.println("Conexao estabelecida com: " + 
                               conexao.getInetAddress().getHostAddress());

             
            //enviando cardapio ao cliente
            //saida.writeObject(cardapio + "\n");
            

            do {//fica aqui ate' o cliente enviar a mensagem FIM
                try {
                    //obtendo a mensagem enviada pelo cliente
                    mensagem = (String) entrada.readObject();
                    System.out.println("Cliente>> " + mensagem);

                    //enviando a mensagem de confirmação ao cliente
                    // saida.writeObject("Escolha recebida. Processando..." );

                    //Processamento da escolha
                    saida.writeObject(choice(mensagem));

                } catch (IOException iOException) {
                    System.err.println("erro: " + iOException.toString());
                }
            } while (!mensagem.equals("4"));

            System.out.println("Conexao encerrada pelo cliente");
            saida.close();
            entrada.close();
            conexao.close();
        }
        catch (Exception ex){
            
        }
    }
}
        

    
