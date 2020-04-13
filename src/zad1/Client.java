package zad1;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client {

    private Socket socketSend = null;
    private Socket socketWait = null;
    private ServerSocket socketCome = null;
    private DataInputStream input = null;
    private DataInputStream inputCome = null;
    private DataOutputStream out = null;

    public Client(String adres, int port){
        GUI gui = new GUI();
        gui.inputDateFrame();
        
        //Slowo;Jezyk;Port
        try{
            socketSend = new Socket(adres, port);
            System.out.println("polaczono");
            if()
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socketSend.getOutputStream());
            String word = "";
            while (!word.equals("koniec")){
                socketCome = new ServerSocket(6665);
                word = gui.setText() + "/" + socketCome.getLocalPort();
                out.writeUTF(word);
                socketWait = socketCome.accept();
                inputCome =  new DataInputStream(new BufferedInputStream(socketWait.getInputStream()));
                word = inputCome.readUTF();
                GUI.resultFrame(word);
                System.out.println(word);
                socketCome.close();
            }
            inputCome.close();
            input.close();
            out.close();
            socketCome.close();
            socketSend.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Client client = new Client("localhost", 6661);
    }
}
