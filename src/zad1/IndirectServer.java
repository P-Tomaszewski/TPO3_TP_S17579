package zad1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class IndirectServer {

    private Socket socket = null;
    private Socket socketSend = null;
    private ServerSocket server = null;
    private DataInputStream inServer = null;
    private DataInputStream inputClient = null;
    private DataOutputStream outputClient = null;

    public static Map<String, Integer> mapLanguageServer = Map.of("RUS", 6664, "FR", 6663, "ANG", 6662);

    public void server(int port) {

        try {
            server = new ServerSocket(port);
            System.out.println("Start serwera");
            System.out.println("Oczekiwanie na klienta");
            socket = server.accept();
            inServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            System.out.println("Klient przyjęty");
            String text = "";
            while (!text.equals("koniec")) {
                text = inServer.readUTF();
                System.out.println(text);
                String[] splitLine = text.split("/");

                System.out.println();
                try {
                    socketSend = new Socket("localhost", mapLanguageServer.get(splitLine[1]));
                    inputClient = new DataInputStream(System.in);
                    outputClient = new DataOutputStream(socketSend.getOutputStream());
                    outputClient.writeUTF(splitLine[0] + "/" + splitLine[2]);
                } catch (NullPointerException a) {
                    System.out.println("konczenie polaczenia");
                }
            }
            socket.close();
            inServer.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
        public static void main(String args[]) {
            int indirectServerPort = 6661;
            IndirectServer indirectServer = new IndirectServer();
            indirectServer.server(indirectServerPort);
    }
}
