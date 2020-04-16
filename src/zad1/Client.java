package zad1;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socketClient = null;
    private Socket socketWait = null;
    private ServerSocket socketCome = null;
    private DataInputStream input = null;
    private DataInputStream inputCome = null;
    private DataOutputStream out = null;
    static String word;
    static GUI gui = new GUI();
    static  String valueFromGui;



    public static void main(String args[]) throws IOException {

        Client client = new Client(7000);

    }


    public Client(int port) throws IOException {
        word = "";
        valueFromGui = "";
        gui.inputDateFrame();



            try{
                socketCome = new ServerSocket(port);
                while (!valueFromGui.equals("done")) {
                    while (valueFromGui.equals("")) {
                        System.out.print("");
                    }
                    connectToMainServ("localhost", 6661);
                    socketWait = socketCome.accept();
                    inputCome = new DataInputStream(new BufferedInputStream(socketWait.getInputStream()));
                    word = inputCome.readUTF();
                    inputCome.close();
                    gui.resultFrame(word);
                    setValueFromGui("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                socketCome.close();
            } catch (IOException e) {
                e.printStackTrace();
            }





    }

    public static void setValueFromGui(String slowo) {
        valueFromGui = slowo;
    }

    public void connectToMainServ(String adres, int port) throws IOException {
        socketClient = new Socket(adres, port);
        System.out.println("polaczono");
        input = new DataInputStream(System.in);
        out = new DataOutputStream(socketClient.getOutputStream());

        word = valueFromGui + "/" + socketCome.getLocalPort();
        System.out.println(word);
        out.writeUTF(word);
        socketClient.close();
        input.close();
        out.close();
    }
}
