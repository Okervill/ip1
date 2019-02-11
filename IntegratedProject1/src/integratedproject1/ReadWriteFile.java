/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ReadWriteFile {

    public static void main(String[] args) throws IOException {
        createFile();
        readFile();
        updateFile();
        readFile();
    }

    public static void createFile() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter("test.txt", "UTF-8")) {
            writer.println("Username: test01");
            writer.println("Username: test02");
        }
    }

    public static void updateFile() throws IOException {
        File file = new File("test.txt");
        FileWriter fr = new FileWriter(file, true);
        fr.write("Username: test03");
        fr.close();
    }

    public static void readFile() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("test.txt"));
        String str;

        ArrayList<String> list = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("Username:")) {
                String user = list.get(i).substring(10);
                System.out.println(user);
            }
        }
    }

}
