/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ReadWriteFile {

    static String loginFile = "src/Login/LoginData.txt";
    static String patientFile = "src/integratedproject1/PatientFile.txt";

    public static void createLoginFile() throws IOException, FileNotFoundException, UnsupportedEncodingException {

        FileWriter fw = new FileWriter(loginFile, false);
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

    public static void updateLoginFile(String f, String s, String u, String p, String m) throws IOException {
        File file = new File(loginFile);
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

    public static ArrayList getLoginData(String username) throws IOException {

        ArrayList<String> data = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(loginFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }

        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Username: " + username)) {
                data.add(allData.get(i).substring(10));//username
                data.add(allData.get(i + 1).substring(10));//password
                data.add(allData.get(i + 2).substring(11));//firstname
                data.add(allData.get(i + 3).substring(9));//surname
                data.add(allData.get(i + 4).substring(9));//manager
            }
        }
        return data;
    }

    public static ArrayList getUsernames() throws IOException {

        ArrayList<String> usernames = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(loginFile));
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

    public static void createPatientFile() throws IOException, FileNotFoundException, UnsupportedEncodingException {

        FileWriter fw = new FileWriter(patientFile, false);
        fw.write("<!-- METADATA");
        newLine(fw);
        fw.write("    SectionLength: 8");
        newLine(fw);
        fw.write("--!>");
        newLine(fw);
        fw.write("");
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
    }

    public static void updatePatientFile(String f, String s, String e, String m, String d, String g, String p, int n) throws IOException { //(firstname, surname, email, mobile, dob, gender, postcode);
        File file = new File(patientFile);
        FileWriter fw = new FileWriter(file, true);
        fw.write("Forename: " + f);
        newLine(fw);
        fw.write("Surname: " + s);
        newLine(fw);
        fw.write("Email: " + e);
        newLine(fw);
        fw.write("Mobile: " + m);
        newLine(fw);
        fw.write("DoB: " + d);
        newLine(fw);
        fw.write("Gender: " + g);
        newLine(fw);
        fw.write("Postcode: " + p);
        newLine(fw);
        fw.write("Patient Number: " + n);
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
    }

    public static ArrayList getPatientData(String u) throws IOException {

        ArrayList<String> data = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(patientFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }

        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Patient Number: " + u)) {
                data.add(allData.get(i - 7).substring(10));//Forename
                data.add(allData.get(i - 6).substring(9));//Surname
                data.add(allData.get(i - 5).substring(7));//Email
                data.add(allData.get(i - 4).substring(8));//mobile
                data.add(allData.get(i - 3).substring(5));//DoB
                data.add(allData.get(i - 2).substring(8));//Gender
                data.add(allData.get(i - 1).substring(10));//Postcode
                data.add(allData.get(i).substring(16));//PatientNo
            }
        }
        int found = data.size() / 8;
        data.add(Integer.toString(found));
        return data;
    }

    public static int countPatients() throws FileNotFoundException, IOException {
        int count = 0;
        BufferedReader in = new BufferedReader(new FileReader(patientFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }

        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Forename")) {
                count++;
            }
        }
        return count;
    }

    public static void editLoginFile(String oldString, String newString) {
        File file = new File(loginFile);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(file));  //from https://javaconceptoftheday.com/modify-replace-specific-string-in-text-file-in-java/
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            //Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceAll(oldString, newString);
            //Rewriting the input text file with newContent
            writer = new FileWriter(file);

            writer.write(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
