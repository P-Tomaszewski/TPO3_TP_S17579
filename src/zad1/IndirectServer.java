package zad1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class IndirectServer {

    private Socket socket = null;
    private Socket socketSend = null;
    private ServerSocket server = null;
    private DataInputStream inServer = null;
    private DataInputStream inputClient = null;
    private DataOutputStream outputClient = null;

    public static void main(String args[]) {
        int indirectServerPort = 6661;
        IndirectServer indirectServer = new IndirectServer();
        indirectServer.server(indirectServerPort);
    }

    public static Map<String, Integer> mapLanguageServer = new HashMap<String, Integer>(){
       {
           put("RUS",6664);
           put("FR", 6663);
           put("ANG", 6662);
        }
    };

    public void server(int port) {

        try {
            server = new ServerSocket(port);
            System.out.println("Start serwera");
            System.out.println("Oczekiwanie na klienta");

            String text = "";
            while (!text.equals("koniec")) {

                socket = server.accept();
                inServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                System.out.println("Klient przyjęty");

                text = inServer.readUTF();
                System.out.println(text);
                String[] splitLine = text.split("/");

                try {
                    socketSend = new Socket("localhost", mapLanguageServer.get(splitLine[1]));
                    inputClient = new DataInputStream(System.in);
                    outputClient = new DataOutputStream(socketSend.getOutputStream());
                    outputClient.writeUTF(splitLine[0] + "/" + splitLine[2]);

                } catch (NullPointerException a) {
                    System.out.println("konczenie polaczenia");
                }
                socketSend.close();
                inputClient.close();
                outputClient.close();
            }
            socket.close();
            inServer.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}
