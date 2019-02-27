/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import integratedproject1.Hash;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class SQLHandler {

    Connection conn = SQLHandler.getConn();
    PreparedStatement query;

    public SQLHandler() {

    }

    //----------------------//
    // CONNECT TO SQLITE DB //
    //----------------------//
    public static Connection getConn() {

        //When running from netbeans set to true. When running the jar
        //set to false and we will adjust path if need be.
        boolean demo = true;
        String url;
        if (demo) {
            url = "jdbc:sqlite:src/SQL/HealthClinic.db";
        } else {
            url = "jdbc:sqlite:../src/SQL/HealthClinic.db";
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //-----------------------------//
    // ADD NEW DATA TO LOGIN TABLE //
    //-----------------------------//
    public void addToLogin(String username, String password, String firstname, String surname, String usertype) throws SQLException {

        String sql = "INSERT INTO login (username, password, firstname, surname, usertype) VALUES(?,?,?,?,?)";

        Hash h1 = new Hash();
        password = h1.hash(password);
        
        query = conn.prepareStatement(sql);
        
        query.setString(1, username);
        query.setString(2, password);
        query.setString(3, firstname);
        query.setString(4, surname);
        query.setString(5, usertype);
        
        query.executeUpdate();

    }

    //-------------------------------//
    // ADD NEW DATA TO PATIENT TABLE //
    //-------------------------------//
    public void addToPatient(String firstname, String surname, String email, String mobile, LocalDate dob, String gender, String postcode, String patientNumber) throws SQLException {

        String sql = "INSERT INTO patient (firstname, surname, email, mobile, dob, gender, postcode, patientnumber) VALUES(?,?,?,?,?,?,?,?)";

        query = conn.prepareStatement(sql);
        
        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, email);
        query.setString(4, mobile);
        query.setString(5, dob.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        query.setString(6, gender);
        query.setString(7, postcode);
        query.setString(8, patientNumber);
        
        query.executeUpdate();
    }

    //-----------------------------------//
    // ADD NEW DATA TO APPOINTMENT TABLE //
    //-----------------------------------//
    public void addToAppointment(String AppointmentNumber, String PatientNumber, String Therapist, LocalDate Date, LocalTime Time, String Service, String Cost, String Status) throws SQLException {

        String sql = "INSERT INTO appointment (appointmentnumber, patientnumber, therapist, date, time, service, cost, status) VALUES(?,?,?,?,?,?,?,?)";

        query = conn.prepareStatement(sql);
        
        query.setString(1, AppointmentNumber);
        query.setString(2, PatientNumber);
        query.setString(3, Therapist);
        query.setString(4, Date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        query.setString(5, Time.format(DateTimeFormatter.ofPattern("HH:mm")));
        query.setString(6, Service);
        query.setString(7, Cost);
        query.setString(8, Status);
        
        query.executeUpdate();
    }

    //-----------------------------------//
    // SEARCH FOR ITEM IN FIELD IN TABLE //
    //-----------------------------------//
    public ArrayList<String> search(String table, String searchField, String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();

        switch (table) {
            case "login":
                {
                    String sql = "SELECT username, password, firstname, surname, usertype FROM login WHERE " + searchField + " = \"" + searchQuery + "\"";
                    query = conn.prepareStatement(sql);
                    ResultSet rs = query.executeQuery();
                    while (rs.next()) {
                        output.add((rs.getString("username")));
                        output.add((rs.getString("password")));
                        output.add((rs.getString("firstname")));
                        output.add((rs.getString("surname")));
                        output.add((rs.getString("usertype")));
                    }       break;
                }
            case "patient":
                {
                    String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE " + searchField + " = \"" + searchQuery + "\"";
                    query = conn.prepareStatement(sql);
                    ResultSet rs = query.executeQuery();
                    while (rs.next()) {
                        output.add((rs.getString("firstname")));
                        output.add((rs.getString("surname")));
                        output.add((rs.getString("email")));
                        output.add((rs.getString("mobile")));
                        output.add((rs.getString("dob")));
                        output.add((rs.getString("gender")));
                        output.add((rs.getString("postcode")));
                        output.add((rs.getString("patientnumber")));
                    }       break;
                }
            case "appointment":
                {
                    String sql = "SELECT appointmentnumber, patientnumber, therapist, date, time, service, cost, status FROM appointment WHERE " + searchField + " = \"" + searchQuery + "\"";
                    query = conn.prepareStatement(sql);
                    ResultSet rs = query.executeQuery();
                    while (rs.next()) {
                        output.add((rs.getString("appointmentnumber")));
                        output.add((rs.getString("patientnumber")));
                        output.add((rs.getString("therapist")));
                        output.add((rs.getString("date")));
                        output.add((rs.getString("time")));
                        output.add((rs.getString("service")));
                        output.add((rs.getString("cost")));
                        output.add((rs.getString("status")));
                    }       //replace patient number with patient name
                    output.set(1, search("patient", "patientnumber", output.get(1)).get(0) + " " + search("patient", "patientnumber", output.get(1)).get(1));
                    break;
                }
            default:
                System.out.println("Invalid table name");
                break;
        }
        return output;
    }

    //------------------------------------------------//
    // SEARCH FOR USERNAMES TAKING USERTYPE AS STRING //
    //------------------------------------------------//
    public ArrayList<String> getAllUsernames(String type) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = null;

        //If type given is "all" return all usernames, else therapist, receptionist or manager
        switch (type) {
            case "all":
                sql = "SELECT username FROM login";
                break;
            case "therapist":
                sql = "SELECT username FROM login WHERE usertype = \"therapist\"";
                break;
            case "receptionist":
                sql = "SELECT username FROM login WHERE usertype = \"receptionist\"";
                break;
            case "manager":
                sql = "SELECT username FROM login WHERE usertype = \"manager\"";
                break;
            default:
                break;
        }
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add((rs.getString("username")));
        }
        return output;
    }

    public boolean checkTherapistExists(String username) throws SQLException {
        boolean exists = false;

        ArrayList<String> checkUser = search("login", "username", username);

        if (checkUser.size() == 5) {
            exists = true;
        }

        return exists;
    }

    //---------------------------------//
    // SEARCH FOR ALL THERAPISTS NAMES //
    //---------------------------------//
    public ArrayList<String> getTherapistNames() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT firstname, surname FROM login WHERE usertype = \"therapist\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add((rs.getString("firstname")) + " " + (rs.getString("surname")));
        }
        return output;
    }

    //-----------------------------------//
    // COUNT RECORDS IN GIVEN TABLE NAME //
    //-----------------------------------//
    public int countRecords(String table) throws SQLException {
        int numRecords = 0;

        String sql = "SELECT COUNT(*) FROM " + table;
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            numRecords = rs.getInt(1);
        }
        return numRecords;
    }

    //------------------------------------------------------------------------------------//
    // SEARCH FOR APPOINTMENTS BY DATE AND THERAPIST - RETURN APP NUM, PATIENT NAME, TIME //
    //------------------------------------------------------------------------------------//
    public ArrayList<String> getShortAppointments(LocalDate date, String therapistUsername) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT appointmentnumber, patientnumber, time FROM appointment WHERE therapist = \"" + therapistUsername + "\" AND date = \"" + date + "\"";

        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add("Appointment: " + (rs.getString("appointmentnumber"))
                    + " Patient: "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(0) + " "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(1)
                    + " Time: " + (rs.getString("time")));
        }
        if (output.size() < 1) {
            return output;
        }

        return output;
    }

    //------------------------------------------------------------------------------------//
    // SEARCH FOR APPOINTMENTS BY DATE AND THERAPIST - RETURN APP NUM, PATIENT NAME, TIME //
    //------------------------------------------------------------------------------------//
    public ArrayList<String> getAllShortAppointments(LocalDate date) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT appointmentnumber, patientnumber, time FROM appointment WHERE date = \"" + date + "\"";

        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add("Appointment: " + (rs.getString("appointmentnumber"))
                    + " Patient: "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(0) + " "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(1)
                    + " Time: " + (rs.getString("time")));
        }
        if (output.size() < 1) {
            output.clear();
            return output;
        }

        return output;
    }

    //----------------------------//
    // EDIT RECORD IN LOGIN TABLE //
    //----------------------------//
    public void updateLogin(String username, String firstname, String surname, String usertype) throws SQLException {

        String sql = "UPDATE login SET firstname = ? , surname = ? , usertype = ? WHERE username = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, usertype);
        query.setString(4, username);

        query.executeUpdate();
    }

    //----------------------------//
    // EDIT RECORD IN LOGIN TABLE //
    //----------------------------//
    public void updateLoginPassword(String username, String password) throws SQLException {

        String sql = "UPDATE login SET password = ? WHERE username = ?";

        Hash h1 = new Hash();
        password = h1.hash(password);

        query = conn.prepareStatement(sql);

        query.setString(1, password);
        query.setString(2, username);

        query.executeUpdate();
    }

    //------------------------------//
    // EDIT RECORD IN PATIENT TABLE //
    //------------------------------//
    public void updatePatient(String firstname, String surname, String email, String mobile, String dob, String gender, String postcode, String patientnumer) throws SQLException {

        String sql = "UPDATE patient SET firstname = ? , surname = ? , email = ? , mobile = ? , dob = ? , gender = ? , postcode = ? WHERE patientnumber = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, email);
        query.setString(4, mobile);
        query.setString(5, dob);
        query.setString(6, postcode);
        query.setString(6, patientnumer);

        query.executeUpdate();
    }

    //----------------------------------//
    // EDIT RECORD IN APPOINTMENT TABLE //
    //----------------------------------//
    public void updateAppointment(String AppointmentNumber, String PatientNumber, String Therapist, LocalDate Date, LocalTime Time, String Service, String Cost, String Status) throws SQLException {

        String sql = "UPDATE appointment SET therapist = ? , date = ? , time = ? , service = ? , cost = ? , status = ? WHERE appointmentnumber = ?";

        query = conn.prepareStatement(sql);
        query.setString(1, Therapist);
        query.setString(2, Date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        query.setString(3, Time.format(DateTimeFormatter.ofPattern("HH:mm")));
        query.setString(4, Service);
        query.setString(5, Cost);
        query.setString(6, Status);
        query.executeUpdate();
    }

    //--------------------------------//
    // DELETE RECORD FROM GIVEN TABLE //
    //--------------------------------//
    public void deleteRecord(String table, String searchField, String searchQuery) throws SQLException{

            String sql = "DELETE FROM " + table + " WHERE " + searchField + " = ?";

            query = conn.prepareStatement(sql);
            
            //query.setString(1, table);
            //query.setString(2, searchField);
            query.setString(1, searchQuery);
            
            query.executeUpdate();
    }
}
