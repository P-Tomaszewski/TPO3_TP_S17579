package zad1;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socketSend = null;
    private Socket socketWait = null;
    private ServerSocket socketCome = null;
    private DataInputStream input = null;
    private DataInputStream inputCome = null;
    private DataOutputStream out = null;
    static String word;
    static GUI gui = new GUI();


    public static void main(String args[]) {
        gui.inputDateFrame();
    }


    public Client(String adres, int port, String slowo){


        //Slowo;Jezyk;Port
        word = "";
        try{
            socketSend = new Socket(adres, port);
            System.out.println("polaczono");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socketSend.getOutputStream());


                    socketCome = new ServerSocket(6665);
                    word =  slowo + "/" + socketCome.getLocalPort();
                    System.out.println(word);
                    out.writeUTF(word);
                    socketWait = socketCome.accept();
                    inputCome = new DataInputStream(new BufferedInputStream(socketWait.getInputStream()));
                    word = inputCome.readUTF();
                    System.out.println(word);


            inputCome.close();
            input.close();
            out.close();
            socketCome.close();
            socketSend.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.resultFrame(word);
    }

}
