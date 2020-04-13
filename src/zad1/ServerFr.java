package zad1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerFr {
    private Socket socket = null;
    private Socket socketSend = null;
    private ServerSocket socketServer = null;
    private DataInputStream inServer = null;
    private DataInputStream inputServer = null;
    private DataOutputStream outputServer = null;

    private Map<String, String> mapDictionary = Map.of(
            "Koza", "chèvre",
            "Ogien", "feu",
            "Rower", "vélo",
            "Noc", "nuit");

    public void server(int port) {

        try {
            socketServer = new ServerSocket(port);
            System.out.println("Start serwera");
            System.out.println("Oczekiwanie na klienta");

            String text = "";
            while (!text.equals("koniec")) {
                socket = socketServer.accept();
                System.out.println("Klient przyjęty");
                inServer = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                text = inServer.readUTF();
                String[] splitLine = text.split("/");
                //serwer staje sie klientem
                socketSend = new Socket("localhost", Integer.parseInt(splitLine[1]));
                inputServer = new DataInputStream(System.in);
                outputServer = new DataOutputStream(socketSend.getOutputStream());
                try {
                    outputServer.writeUTF(mapDictionary.get(splitLine[0]));
                }catch (NullPointerException a){
                    outputServer.writeUTF("Slowa nie znaleziono w slowniku Francuskim");
                }

            }
            System.out.println("konczenie polaczenia");
            socket.close();
            inServer.close();

        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] arg){
        int frServerPort = 6663;
        ServerFr serverFr = new ServerFr();
        serverFr.server(frServerPort);
    }
}
