package day1.normal;

import java.io.*;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 08/11/13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class Demo {

    private final static String WRITE_DIRECTORY = "C:/destinations/";

    public static void main(String[] args) {
        try {
            URL url = Demo.class.getClassLoader().getResource("source");
            final String sourceFolderPath = url.getPath();
            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(WRITE_DIRECTORY);
            System.out.print(destinationFolder.getPath());
            if(!destinationFolder.exists()) destinationFolder.createNewFile();
            recurseAndWrite(sourceFolder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void recurseAndWrite(File file) throws IOException {
        if (file.isDirectory()) {
            File[] innerFiles = file.listFiles();
            for (File innerFile : innerFiles) {
                recurseAndWrite(innerFile);
            }
        } else {
            String filename = file.getName();
            String content = readFromFile(file);
            File destinationFile = new File(WRITE_DIRECTORY + filename);
            writeToFile(destinationFile,content);
        }
    }

    private static String readFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder stb = new StringBuilder();
        String temp = null;
        while((temp = br.readLine()) != null){
            stb.append(temp);
        }
        br.close();
        return stb.toString();
    }

    private static void writeToFile(File file, String content) throws IOException {
        System.out.println(file.getPath());
        if(!file.exists())file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.append(content);
        bw.flush();
        bw.close();
    }

}
