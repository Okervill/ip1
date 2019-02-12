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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ReadWriteFile {

    static String location = "LoginData.txt";

    public static void createFile() throws IOException, FileNotFoundException, UnsupportedEncodingException {

        FileWriter fw = new FileWriter(location, false);
        fw.write("<!-- METADATA");
        newLine(fw);
        fw.write("    SectionLength: 5");
        newLine(fw);
        fw.write("--!>");
        newLine(fw);
        fw.write("");
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
    }

    public static void updateFile(String f, String s, String u, String p, Boolean m) throws IOException {
        File file = new File(location);
        FileWriter fw = new FileWriter(file, true);
        fw.write("Username: " + u);
        newLine(fw);
        fw.write("Password: " + p);
        newLine(fw);
        fw.write("Firstname: " + f);
        newLine(fw);
        fw.write("Surname: " + s);
        newLine(fw);
        fw.write("Manager: " + m);
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
    }

    public static void newLine(FileWriter fw) throws IOException {
        fw.write(System.getProperty("line.separator"));
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
