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
    
    static String location = "LoginData.txt";

    public static void main(String[] args) throws IOException {
        createFile();
        demoUpdateFile();
    }

    public static void createFile() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter(location, "UTF-8")) {
            writer.println("<!-- METADATA");
            writer.println("    SectionLength: 5");
            writer.println("--!>");
            writer.println("");
        }
    }

    public static void demoUpdateFile() throws IOException {
        File file = new File(location);
        FileWriter fr = new FileWriter(file, true);
        fr.write("Username: test01");
        newLine(fr);
        fr.write("Password: abcdef");
        newLine(fr);
        fr.write("Firstname: John");
        newLine(fr);
        fr.write("Surname: Doe");
        newLine(fr);
        fr.write("Manager: False");
        newLine(fr);
        fr.write("--");
        newLine(fr);
        fr.write("Username: test02");
        newLine(fr);
        fr.write("Password: ghijkl");
        newLine(fr);
        fr.write("Firstname: Jane");
        newLine(fr);
        fr.write("Surname: Doe");
        newLine(fr);
        fr.write("Manager: True");
        newLine(fr);
        fr.close();
    }
    
    public static void updateFile(String f, String s, String u, String p, Boolean m) throws IOException {
        File file = new File(location);
        FileWriter fr = new FileWriter(file, true);
        fr.write("Username: " + u);
        newLine(fr);
        fr.write("Password: " + p);
        newLine(fr);
        fr.write("Firstname: " + f);
        newLine(fr);
        fr.write("Surname: " + s);
        newLine(fr);
        fr.write("Manager: " + m);
        newLine(fr);
        fr.write("--");
        newLine(fr);
        fr.close();
    }
    
    public static void newLine(FileWriter fr) throws IOException{
        fr.write(System.getProperty("line.separator"));
    }

    public static ArrayList getUsernames() throws IOException {
        
        ArrayList<String> usernames = new ArrayList<>();
        
        BufferedReader in = new BufferedReader(new FileReader(location));
        String str;

        ArrayList<String> list = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("Username:")) {
                usernames.add(list.get(i).substring(10));
            }
        }
        return usernames;
    }
    
    public static ArrayList getPasswords() throws IOException {
        
        ArrayList<String> passwords = new ArrayList<>();
        
        BufferedReader in = new BufferedReader(new FileReader(location));
        String str;

        ArrayList<String> list = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("Password:")) {
                passwords.add(list.get(i).substring(10));
            }
        }
        return passwords;
    }
}
