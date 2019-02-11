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
        try (PrintWriter writer = new PrintWriter("test.yml", "UTF-8")) {
            writer.println("<!-- METADATA");
            writer.println("    SectionLength: 5");
            writer.println("--!>");
        }
    }

    public static void updateFile() throws IOException {
        File file = new File("test.yml");
        FileWriter fr = new FileWriter(file, true);
        fr.write("Username: test01");
        fr.write(System.getProperty("line.separator"));
        fr.write("Password: abcdef");
        fr.write(System.getProperty("line.separator"));
        fr.write("Firstname: John");
        fr.write(System.getProperty("line.separator"));
        fr.write("Surname: Doe");
        fr.write(System.getProperty("line.separator"));
        fr.write("Manager: No");
        fr.write(System.getProperty("line.separator"));
        fr.write("--");
        fr.write(System.getProperty("line.separator"));
        fr.write("Username: test02");
        fr.write(System.getProperty("line.separator"));
        fr.write("Password: ghijkl");
        fr.write(System.getProperty("line.separator"));
        fr.write("Firstname: Jane");
        fr.write(System.getProperty("line.separator"));
        fr.write("Surname: Doe");
        fr.write(System.getProperty("line.separator"));
        fr.write("Manager: Yes");
        fr.write(System.getProperty("line.separator"));
        fr.close();
    }

    public static void readFile() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("test.yml"));
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
