package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Run {
    public static void main(String args[]) throws Exception {
        LanguageServer languageServer1 = new LanguageServer();
//        LanguageServer languageServer2 = new LanguageServer();
//        LanguageServer languageServer3 = new LanguageServer();
//        languageServer1.server(6662, getSlownik("SlownikAng.txt"));
//        languageServer2.server(6663, getSlownik("SlownikFr.txt"));
//        languageServer3.server(6664, getSlownik("SlownikRus.txt"));
        ArrayList<String> lista = new ArrayList();
        lista.add("SlownikAng.txt");
        lista.add("SlownikFr.txt");
        lista.add("SlownikRus.txt");

        Thread thread = new Thread(() -> {

                try {
                    System.out.println("k");
                    languageServer1.server(6662, getSlownik(lista.get(0)));
                } catch (Exception e) {
                    e.printStackTrace();
                }



        });

        Thread thread1 = new Thread(() -> {

                try {
                    System.out.println("k");
                    languageServer1.server(6663, getSlownik(lista.get(1)));
                } catch (Exception e) {
                    e.printStackTrace();
                }



        });

        Thread thread2 = new Thread(() -> {
                try {
                    System.out.println("k");
                    languageServer1.server(6664, getSlownik(lista.get(2)));
                } catch (Exception e) {
                    e.printStackTrace();
                }



        });

        thread.start();
        thread1.start();
        thread2.start();

    }

        public static Map<String, String> getSlownik(String slownik) throws Exception
            {
                Map<String, String> map = new HashMap<String, String>();
                BufferedReader in = new BufferedReader(new FileReader(slownik));
                String line = "";
                while ((line = in.readLine()) != null) {
                    String parts[] = line.split("\t");
                    map.put(parts[0], parts[1]);
                }
                in.close();
                System.out.println(map.toString());
                return map;
            }





}
