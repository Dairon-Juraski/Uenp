/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Juraski
 */
public class TCPCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        try {
            String mensagem = "";
            String servidor = "localhost";
            int porta = 8080;

            Scanner ler = new Scanner(System.in);

            //Configura o socket de conexão TCP
            Socket conexao = new Socket(servidor, porta);
            System.out.println("Conectado ao servidor " +
                               servidor + ", na porta: " + porta + "\n");

             
            //Objeto saída enviará a mensagem ao do servidor
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            //Objeto entrada receberá a mensagem do servidor
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
 
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Menu");
            System.out.println("2 - Listar pedidos");
            System.out.println("4 - Sair\n");

            do {
                //Mensagem de saida
                System.out.print("Mensagem..: ");
                mensagem = ler.nextLine();
                saida.writeObject(mensagem);   
                //Envia a mensagem
                saida.flush();
                
                if (mensagem.equals("4")){
                    break;
                }                
                //lendo a mensagem enviada pelo servidor
                mensagem = (String) entrada.readObject();
                System.out.println("Servidor>>\n"+mensagem);
            } while (true);
 
            //Fechando as conexões
            saida.close();
            entrada.close();
            conexao.close();
 
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }
    
}
