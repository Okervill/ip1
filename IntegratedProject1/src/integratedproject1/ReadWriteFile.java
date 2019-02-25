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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReadWriteFile {

    static String loginFile = "src/Login/LoginData.txt";
    static String patientFile = "src/integratedproject1/PatientFile.txt";
    static String appointmentFile = "src/integratedproject1/appointments.txt";

    //----------------------------//
    //      Login File stuff      //
    //----------------------------//
    public static void createFile(File file, int m) throws IOException, FileNotFoundException, UnsupportedEncodingException {
        FileWriter fw = new FileWriter(file, false);
        fw.write("<!-- METADATA");
        newLine(fw);
        fw.write("    SectionLength: " + m);
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
        Hash h1 = new Hash();
        p = h1.hash(p);
        fw.write("Username: " + u);
        newLine(fw);
        fw.write("Password: " + p);
        newLine(fw);
        fw.write("Firstname: " + f);
        newLine(fw);
        fw.write("Surname: " + s);
        newLine(fw);
        fw.write("Type: " + m);
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
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
                data.add(allData.get(i + 4).substring(6));//type
            }
        }
        return data;
    }

    public static ArrayList getUsernames(String type) throws IOException {

        ArrayList<String> usernames = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(loginFile));
        String str;

        ArrayList<String> list = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }
        if (type.equals("therapist")) {
            for (int i = 4; i < list.size(); i++) {
                if (list.get(i).contains("Type: therapist")) {
                    usernames.add(list.get(i - 4).substring(10));
                }
            }
        } else if (type.equals("receptionist")) {
            for (int i = 4; i < list.size(); i++) {
                if (list.get(i - 4).contains("Type: receptionist")) {
                    usernames.add(list.get(i).substring(10));
                }
            }
        } else if (type.equals("manager")) {
            for (int i = 4; i < list.size(); i++) {
                if (list.get(i).contains("Type: manager")) {
                    usernames.add(list.get(i - 4).substring(10));
                }
            }
        } else if (type.equals("all")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains("Username")) {
                    usernames.add(list.get(i).substring(10));
                }
            }
        }
        return usernames;
    }

    public static ArrayList<String> getAllTherapists() throws FileNotFoundException, IOException {

        ArrayList<String> Therapists = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(loginFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }

        for (int i = 2; i < allData.size(); i++) {
            if (allData.get(i).contains("Type: therapist")) {
                Therapists.add(allData.get(i - 2).substring(11) + " " + allData.get(i - 1).substring(9));//Firstname + Surname
            }
        }
        return Therapists;
    }

    //---------------------------------//
    //        Patient File Stuff       //
    //---------------------------------//
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
 
    //-------------------------------------//
    //        Appointment File Stuff       //
    //-------------------------------------//

    public static int countAppointments() throws IOException {

        int count = 0;

        BufferedReader in = new BufferedReader(new FileReader(appointmentFile));
        String str;

        while ((str = in.readLine()) != null) {
            if (str.contains("Appointment Number:")) {
                count++;
            }
        }
        return count;
    }
    
    

    public static void newAppointment(int a, int p, String t, LocalDate d, LocalTime time, String serv, int c, String stat) throws IOException {
        File file = new File(appointmentFile);
        FileWriter fw = new FileWriter(file, true);
        fw.write("Appointment Number: " + a);
        newLine(fw);
        fw.write("Patient: " + p);
        newLine(fw);
        fw.write("Therapist: " + t);
        newLine(fw);
        fw.write("Date: " + d);
        newLine(fw);
        fw.write("Time: " + time);
        newLine(fw);
        fw.write("Service: " + serv);
        newLine(fw);
        fw.write("Cost: " + c);
        newLine(fw);
        fw.write("Status: " + stat);
        newLine(fw);
        fw.write("--");
        newLine(fw);
        fw.close();
    }

    public static ArrayList getPatientAppointments(int patient) throws FileNotFoundException, IOException {
        ArrayList<String> appointments = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(appointmentFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Patient: " + patient)) {
                appointments.add(allData.get(i - 1).substring(20));//Appointment Number
                appointments.add(allData.get(i).substring(9));//Patient
                appointments.add(allData.get(i + 1).substring(11));//Therapist
                appointments.add(allData.get(i + 2).substring(6));//Date
                appointments.add(allData.get(i + 3).substring(6));//Time
                appointments.add(allData.get(i + 4).substring(8));//Service
                appointments.add(allData.get(i + 5).substring(6));//Cost
                appointments.add(allData.get(i + 6).substring(8));//Status
            }
        }
        int found = appointments.size() / 8;
        appointments.add(Integer.toString(found));
        return appointments;
    }

    public static ArrayList getDateAppointments(LocalDate date) throws FileNotFoundException, IOException {
        ArrayList<String> appointments = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(appointmentFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Date: " + date)) {
                appointments.add("Appointment Number: " + allData.get(i - 3).substring(20));//Appointment Number
                appointments.add("Patient: " + getPatientData(allData.get(i - 2).substring(9)).get(0) + " " + getPatientData(allData.get(i - 2).substring(9)).get(1));//Patient
                appointments.add("Therapist: " + getLoginData(allData.get(i - 1).substring(11)).get(2) + " " + getLoginData(allData.get(i - 1).substring(11)).get(3));//Therapist
                appointments.add("Time: " + allData.get(i + 1).substring(6));//Time
                appointments.add("Service: " + allData.get(i + 2).substring(8));//Service
                appointments.add("Cost: " + allData.get(i + 3).substring(6));//Cost
                appointments.add("Status: " + allData.get(i + 4).substring(8));//Status
            }
        }
        return appointments;
    }

    public static ArrayList getAppointmentNumberInfo(String n) throws FileNotFoundException, IOException {
        ArrayList<String> appointments = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(appointmentFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Appointment Number: " + n)) {
                appointments.add("Appointment Number: " + allData.get(i).substring(20));//Appointment Number
                appointments.add("Patient: " + getPatientData(allData.get(i + 1).substring(9)).get(0) + " " + getPatientData(allData.get(i + 1).substring(9)).get(1));//Patient
                appointments.add("Therapist: " + getLoginData(allData.get(i + 2).substring(11)).get(2) + " " + getLoginData(allData.get(i + 2).substring(11)).get(3));//Therapist
                appointments.add("Time: " + allData.get(i + 4).substring(6));//Time
                appointments.add("Service: " + allData.get(i + 5).substring(8));//Service
                appointments.add("Cost: " + allData.get(i + 6).substring(6));//Cost
                appointments.add("Status: " + allData.get(i + 7).substring(8));//Status
            }
        }
        return appointments;
    }

    public static ArrayList getShortAppointments(LocalDate date, String user) throws FileNotFoundException, IOException {

        ArrayList<String> appointments = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(appointmentFile));
        String str;

        ArrayList<String> allData = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            allData.add(str);
        }
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).contains("Date: " + date) && allData.get(i - 1).contains("Therapist: " + user)) {
                appointments.add("Appointment: " + allData.get(i - 3).substring(20) + " Patient: " + getPatientData(allData.get(i - 2).substring(9)).get(0) + " " + getPatientData(allData.get(i - 2).substring(9)).get(1) + " Time: " + allData.get(i + 1).substring(6));//Patient + Time
            }
        }
        return appointments;
    }

    public static void editLoginFile(String fileLocation, String oldString, String newString) {
        File file = new File(fileLocation);
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

    public static void newLine(FileWriter fw) throws IOException {
        fw.write(System.getProperty("line.separator"));
    }
}
