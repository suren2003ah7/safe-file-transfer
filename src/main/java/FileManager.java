package main.java;

import java.io.*;

public class FileManager {
    public static String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = bufferedReader.readLine()) != null)
            sb.append(line);
        bufferedReader.close();
        return sb.toString();
    }

    public static void writeToFile(String path, String content) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
