package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Run {


    static  ArrayList<String> lista;
    static {
        try {
            lista = walk("/Users/admin/IdeaProjects/TPO3_TP_S17579/Slowniki");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {

        Thread threadServer = new Thread(() -> {
            try {
                System.out.println("Indirect Server Start");
                IndirectServer indirectServer = new IndirectServer();
                indirectServer.server(6661, getMapLanguageServer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadServer.start();


        for(int i = 0; i<lista.size(); i++){
            int finalI = i;
            Thread thread = new Thread(() -> {

                try {

                    LanguageServer languageServer = new LanguageServer();
                    languageServer.languageServer(6662 + finalI,  getSlownik(lista.get(finalI)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

    }

    public static  Map<String, Integer> getMapLanguageServer(){
        Map<String, Integer> mapLanguageServer =  new HashMap<String, Integer>();

        for(int i = 0; i<lista.size(); i++)
        {
            String[] splitLine = lista.get(i).split("/", 3);
            mapLanguageServer.put(splitLine[1].substring(0,2), 6662 + i);
        }

        return mapLanguageServer;
    }

        public static Map<String, String> getSlownik(String slownik) throws Exception
            {
                Map<String, String> map = new HashMap<String, String>();
                BufferedReader in = new BufferedReader(new FileReader(slownik));
                String line = "";
                while ((line = in.readLine()) != null) {
                    String parts[] = line.split("/");
                    map.put(parts[0], parts[1]);
                }
                in.close();
                return map;
            }

    public static ArrayList<String> walk(String rootPath) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Files.walkFileTree(Paths.get(rootPath), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) throws IOException {
                String[] splitLine = aFile.toString().split("/");
               list.add(splitLine[5]+ "/" + splitLine[6]);
                return FileVisitResult.CONTINUE;
            }
        });
        return list;
    }






}
