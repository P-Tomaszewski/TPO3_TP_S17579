package zad1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerRus {
    private Socket socket = null;
    private Socket socketSend = null;
    private ServerSocket socketServer = null;
    private DataInputStream inServer = null;
    private DataInputStream inputServer = null;
    private DataOutputStream outputServer = null;

    public static void main(String[] arg){
        int rusServerPort = 6664;
        ServerRus serverRus = new ServerRus();
        serverRus.server(rusServerPort);
    }

    public static Map<String, String> mapDictionary = new HashMap<String, String>(){
        {
            put("Koza", "коза");
            put("Ogien", "огонь");
            put("Rower", "велосипед");
            put("Noc", "ночь");
        }
    };

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
                if("".equals(splitLine[0]))
                    outputServer.writeUTF("Nie podano slowa");
                try {
                    outputServer.writeUTF(mapDictionary.get(splitLine[0]));
                }catch (NullPointerException a){
                    outputServer.writeUTF("Nie znaleziono w slowniku Rosyjskim");
                }

            }
            System.out.println("konczenie polaczenia");
            socket.close();
            inServer.close();

        } catch (IOException i) {
            System.out.println(i);
        }
    }


}
