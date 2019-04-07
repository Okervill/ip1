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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to connect to database");
            alert.setContentText("Unable to connect to database, try agian later");
            alert.showAndWait();
            return null;
        }
        return conn;
    }

    //-----------------------------//
    // ADD NEW DATA TO LOGIN TABLE //
    //-----------------------------//
    public void addToLogin(String username, String password, String firstname, String surname, String usertype, String active) throws SQLException {

        String sql = "INSERT INTO login (username, password, firstname, surname, usertype, active) VALUES(?,?,?,?,?,?)";

        Hash h1 = new Hash();
        password = h1.hash(password);

        query = conn.prepareStatement(sql);

        query.setString(1, username);
        query.setString(2, password);
        query.setString(3, firstname);
        query.setString(4, surname);
        query.setString(5, usertype);
        query.setString(6, active);

        query.executeUpdate();
        query.close();
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
        query.close();
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
        query.close();
    }

    //-------------------------------//
    // ADD NEW DATA TO SERVICE TABLE //
    //-------------------------------//
    public void addToService(String serviceNumber, String ServiceName, String ServiceActive, String ServiceColour) throws SQLException {

        String sql = "INSERT INTO service (servicenumber, name, active, colour) VALUES(?,?,?,?)";

        query = conn.prepareStatement(sql);

        query.setString(1, serviceNumber);
        query.setString(2, ServiceName);
        query.setString(3, ServiceActive);
        query.setString(4, ServiceColour);

        query.executeUpdate();
        query.close();
    }

    //-------------------------------//
    // ADD NEW DATA TO HOLIDAY TABLE //
    //-------------------------------//
    public void addToHoliday(String id, String employee, LocalDate start, LocalDate end, String approved, String updated) throws SQLException {

        String sql = "INSERT INTO holiday (id, employee, datestart, dateend, approved, updated) VALUES(?,?,?,?,?,?)";

        query = conn.prepareStatement(sql);

        query.setString(1, id);
        query.setString(2, employee);
        query.setString(3, start.toString());
        query.setString(4, end.toString());
        query.setString(5, approved);
        query.setString(6, updated);

        query.executeUpdate();
        query.close();
    }

    //-----------------------------//
    // COUNT FIELDS IN GIVEN TABLE //
    //-----------------------------//
    public int countFields(String table) throws SQLException {
        int numFields = 0;

        String sql = "SELECT * FROM " + table;
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        numFields = rsmd.getColumnCount();

        query.close();

        return numFields;
    }

    //-----------------------------------//
    // SEARCH FOR ITEM IN FIELD IN TABLE //
    //-----------------------------------//
    public ArrayList<String> search(String table, String searchField, String searchQuery) throws SQLException {

        ArrayList<String> output = new ArrayList<>();

        switch (table) {
            case "login": {
                String sql = "SELECT username, password, firstname, surname, usertype, active FROM login WHERE " + searchField + " = \"" + searchQuery + "\"";
                query = conn.prepareStatement(sql);
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    output.add((rs.getString("username")));
                    output.add((rs.getString("password")));
                    output.add((rs.getString("firstname")));
                    output.add((rs.getString("surname")));
                    output.add((rs.getString("usertype")));
                    output.add((rs.getString("active")));
                }
                break;
            }
            case "patient": {
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
                }
                break;
            }
            case "appointment": {
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
                }
                //replace patient number with patient name
                if (output.isEmpty()) {
                    break;
                }
                output.set(1, search("patient", "patientnumber", output.get(1)).get(0) + " " + search("patient", "patientnumber", output.get(1)).get(1));
                break;
            }
            case "service": {
                String sql = "SELECT servicenumber, name, active, colour FROM service WHERE " + searchField + " = \"" + searchQuery + "\"";
                query = conn.prepareStatement(sql);
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    output.add((rs.getString("servicenumber")));
                    output.add((rs.getString("name")));
                    output.add((rs.getString("active")));
                    output.add((rs.getString("colour")));
                }
                break;
            }
            case "holiday": {
                String sql = "SELECT id, employee, datestart, dateend, approved FROM holiday WHERE " + searchField + " = \"" + searchQuery + "\"";
                query = conn.prepareStatement(sql);
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    output.add((rs.getString("id")));
                    output.add((rs.getString("employee")));
                    output.add((rs.getString("datestart")));
                    output.add((rs.getString("dateend")));
                    output.add((rs.getString("approved")));
                }
                break;
            }
            default:
                System.out.println("Invalid table name");
                break;
        }

        query.close();
        return output;
    }

    //
    // MAINSCREEN PATIENT SEARCH //
    //
    public ArrayList<String> searchPatientDetails(ArrayList<String> searchQuery) throws SQLException {

        if (searchQuery.get(0).equals(" ") && searchQuery.get(1).equals(" ") && searchQuery.get(2).equals(" ")) {
            return null;
        }

        ArrayList<String> output = new ArrayList<>();

        String searchFirstname = searchQuery.get(0);
        String searchSurname = searchQuery.get(1);
        String searchPostcode = searchQuery.get(2);

        if (searchPostcode.equals(" ") && searchFirstname.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE surname LIKE \"%" + searchSurname + "%\"";

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
            }
            return output;
        } else if (searchSurname.equals(" ") && searchFirstname.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE postcode LIKE \"%" + searchPostcode + "%\"";

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
            }
            return output;
        } else if (searchPostcode.equals(" ") && searchSurname.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE firstname LIKE \"%" + searchFirstname + "%\"";

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
            }
            return output;
        } else if (searchFirstname.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE surname LIKE \"%" + searchSurname + "%\" AND postcode LIKE \"%" + searchPostcode + "%\"";

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
            }
            return output;
        } else if (searchSurname.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE firstname LIKE \"%" + searchFirstname + "%\" AND postcode LIKE \"%" + searchPostcode + "%\"";

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
            }
            return output;
        } else if (searchPostcode.equals(" ")) {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE firstname LIKE \"%" + searchFirstname + "%\" and surname LIKE \"%" + searchSurname + "%\"";

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
            }
            return output;
        } else {
            String sql = "SELECT firstname, surname, email, mobile, dob, gender, postcode, patientnumber FROM patient WHERE firstname LIKE \"%" + searchFirstname + "%\" AND surname LIKE \"%" + searchSurname + "%\" AND postcode LIKE \"%" + searchPostcode + "%\"";

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
            }
            return output;
        }
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
                sql = "SELECT username FROM login WHERE usertype = \"therapist\" AND active = \"true\"";
                break;
            case "receptionist":
                sql = "SELECT username FROM login WHERE usertype = \"receptionist\" AND active = \"true\"";
                break;
            case "manager":
                sql = "SELECT username FROM login WHERE usertype = \"manager\" AND active = \"true\"";
                break;
            default:
                break;
        }
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add((rs.getString("username")));
        }

        query.close();
        return output;
    }

    //---------------------------------------//
    // SEARCH LOGIN TABLE FOR GIVEN USERNAME //
    //---------------------------------------//
    public boolean checkUserExists(String username) throws SQLException {
        boolean exists = false;

        ArrayList<String> checkUser = search("login", "username", username);

        if (checkUser.size() == 6) {
            exists = true;
        }

        query.close();
        return exists;
    }

    //-------------------------//
    // CHECK IF HOLIDAY EXISTS //
    //-------------------------//
    public boolean checkHolidayExists(String id) throws SQLException {
        boolean exists = false;

        ArrayList<String> checkUser = search("holiday", "id", id);

        if (checkUser.size() == 5) {
            exists = true;
        }

        query.close();
        return exists;
    }

    //-----------------------------------//
    // GET UPDATED HOLIDAYS PER EMPLOYEE //
    //-----------------------------------//
    public boolean getUpdatedHolidays(String employee) throws SQLException {

        boolean updated = false;
        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT id, employee, datestart, dateend, approved, updated FROM holiday WHERE employee = \"" + employee + "\" AND updated = \"1\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(
                    "ID: " + rs.getString("id")
                    + " Employee: " + rs.getString("employee")
                    + " Start: " + rs.getString("datestart")
                    + " End: " + rs.getString("dateend")
                    + " Status: " + rs.getString("approved")
                    + " Updated: " + rs.getString("updated")
            );
        }

        if (output.size() >= 1) {
            updated = true;
            updateHolidaySeen(output);
        }

        query.close();
        return updated;
    }

    public void updateHolidaySeen(ArrayList<String> id) throws SQLException {

        ArrayList<String> holidayIds = new ArrayList<>();

        for (int i = 0; i < id.size(); i++) {
            holidayIds.add(id.get(i).substring(id.get(i).indexOf("D:") + 3, id.get(i).indexOf(" E", 0)));
        }

        for (int i = 0; i < holidayIds.size(); i++) {
            String sql = "UPDATE holiday SET updated = \"0\"  WHERE id = \"" + holidayIds.get(i) + "\"";
            query = conn.prepareStatement(sql);
            query.executeUpdate();
            query.close();
        }
    }

    //-----------------------------------------//
    // SEARCH LOGIN TABLE TO CHECK USER ACTIVE //
    //-----------------------------------------//
    public boolean checkUserActive(String username) throws SQLException {
        boolean active = false;

        ArrayList<String> checkUser = search("login", "username", username);

        if (checkUser.get(5).equals("true")) {
            active = true;
        }

        query.close();
        return active;
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

        query.close();
        return output;
    }

    //---------------------------------//
    // SEARCH FOR HOLIDAY BY THERAPIST //
    //---------------------------------//
    public ArrayList<String> getTherapistHoliday(String employee) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT id, employee, datestart, dateend, approved FROM holiday WHERE employee = \"" + employee + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(
                    "ID: " + rs.getString("id")
                    + " Start: " + rs.getString("datestart")
                    + " End: " + rs.getString("dateend")
                    + " Status: " + rs.getString("approved")
            );
        }

        query.close();
        return output;
    }

    //------------------//
    // GET ALL HOLIDAYS //
    //------------------//
    public ArrayList<String> getAllHolidays() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT id, employee, datestart, dateend, approved FROM holiday";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(
                    "ID: " + rs.getString("id")
                    + " Employee: " + rs.getString("employee")
                    + " Start: " + rs.getString("datestart")
                    + " End: " + rs.getString("dateend")
                    + " Status: " + rs.getString("approved")
            );
        }

        query.close();
        return output;
    }

    //----------------------------------//
    // SEARCH FOR HOLIDAY BY UNAPPROVED //
    //----------------------------------//
    public ArrayList<String> getUnapprovedHoliday(String approved) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT id, employee, datestart, dateend, approved FROM holiday WHERE approved = \"" + approved + "\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(
                    "ID: " + rs.getString("id")
                    + " Employee: " + rs.getString("employee")
                    + " Start: " + rs.getString("datestart")
                    + " End: " + rs.getString("dateend")
                    + " Status: " + rs.getString("approved")
            );
        }

        query.close();
        return output;
    }

    //--------------------------------//
    // SEARCH FOR ALL SERVICE COLOURS //
    //--------------------------------//
    public ArrayList<String> getServiceColour(String serviceName) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT colour FROM service WHERE name = \"" + serviceName + "\"";

        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("colour"));
        }

        query.close();
        return output;
    }

    //------------------------------//
    // SEARCH FOR ALL SERVICE NAMES //
    //------------------------------//
    public ArrayList<String> getAllServices() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT name FROM service";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("name"));
        }

        query.close();
        return output;
    }

    //------------------------------//
    // SEARCH FOR ALL SERVICE NAMES //
    //------------------------------//
    public ArrayList<String> getAllActiveServices() throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT name FROM service WHERE active = \"True\"";
        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(rs.getString("name"));
        }

        query.close();
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

        query.close();
        return numRecords;
    }

    //------------------------------------------------------------------------------------//
    // SEARCH FOR APPOINTMENTS BY DATE AND THERAPIST - RETURN APP NUM, PATIENT NAME, TIME //
    //------------------------------------------------------------------------------------//
    public ArrayList<String> getShortAppointments(LocalDate date, String therapistUsername) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT appointmentnumber, patientnumber, time, service FROM appointment WHERE therapist = \"" + therapistUsername + "\" AND date = \"" + date + "\"";

        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add("Time: " + (rs.getString("time")) + "\n"
                    + "Patient: "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(0) + " "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(1) + "\n"
                    + "Appointment: " + (rs.getString("appointmentnumber")) + "\n"
                    + "Service: " + (rs.getString("service")));
        }
        if (output.size() < 1) {
            output.clear();
            return output;
        }

        sortByTime(output);

        query.close();
        return output;
    }

    //------------------------------------------------------------------------------------//
    // SEARCH FOR APPOINTMENTS BY DATE AND THERAPIST - RETURN APP NUM, PATIENT NAME, TIME //
    //------------------------------------------------------------------------------------//
    public ArrayList<String> getAllShortAppointments(LocalDate date) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT appointmentnumber, patientnumber, time, service FROM appointment WHERE date = \"" + date + "\"";

        query = conn.prepareStatement(sql);
        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add("Time: " + (rs.getString("time")) + "\n"
                    + "Patient: "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(0) + " "
                    + search("patient", "patientnumber", (rs.getString("patientnumber"))).get(1) + "\n"
                    + "Appointment: " + (rs.getString("appointmentnumber")) + "\n"
                    + "Service: " + (rs.getString("service")));
        }
        if (output.size() < 1) {
            output.clear();
            return output;
        }

        sortByTime(output);

        query.close();
        return output;
    }

    //-----------------------------//
    // GET APPOINTMENTS BY PATIENT //
    //-----------------------------//
    public ArrayList<String> getPatientAppointments(String patientNumber) throws SQLException {

        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT appointmentnumber, therapist, date, time, service, status FROM appointment WHERE patientnumber = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, patientNumber);

        ResultSet rs = query.executeQuery();

        while (rs.next()) {
            output.add(
                    "Date: " + (rs.getString("date")) + " "
                    + "Time: " + (rs.getString("time")) + " "
                    + "Appointment: " + (rs.getString("appointmentnumber")) + " "
                    + "Service: " + (rs.getString("service")) + " "
                    + "Status: " + (rs.getString("status"))
            );
        }
        if (output.size() < 1) {
            output.clear();
            return output;
        }

        Collections.sort(output, Collections.reverseOrder());

        query.close();
        return output;
    }

    //---------------------------//
    // SORT APPOINTMENTS BY TIME //
    //---------------------------//
    public ArrayList<String> sortByTime(ArrayList<String> appointments) {
        //https://stackoverflow.com/questions/13056178/java-sorting-an-string-array-by-a-substring-of-characters
        Collections.sort(appointments);
        return appointments;
    }

    //----------------------------//
    // EDIT RECORD IN LOGIN TABLE //
    //----------------------------//
    public void updateLogin(String username, String firstname, String surname, String usertype, String active) throws SQLException {

        String sql = "UPDATE login SET firstname = ? , surname = ? , usertype = ?, active = ? WHERE username = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, usertype);
        query.setString(4, active);
        query.setString(5, username);

        query.executeUpdate();
        query.close();
    }

    //------------------------------//
    // EDIT PASSWORD IN LOGIN TABLE //
    //------------------------------//
    public void updateLoginPassword(String username, String password) throws SQLException {

        String sql = "UPDATE login SET password = ? WHERE username = ?";

        Hash h1 = new Hash();
        password = h1.hash(password);

        query = conn.prepareStatement(sql);

        query.setString(1, password);
        query.setString(2, username);

        query.executeUpdate();
        query.close();
    }

    //------------------------------//
    // EDIT RECORD IN PATIENT TABLE //
    //------------------------------//
    public void updatePatient(String firstname, String surname, String email, String mobile, LocalDate dob, String gender, String postcode, String patientnumer) throws SQLException {

        String sql = "UPDATE patient SET firstname = ? , surname = ? , email = ? , mobile = ? , dob = ? , gender = ? , postcode = ? WHERE patientnumber = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, firstname);
        query.setString(2, surname);
        query.setString(3, email);
        query.setString(4, mobile);
        query.setString(5, dob.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        query.setString(6, gender);
        query.setString(7, postcode);
        query.setString(8, patientnumer);

        query.executeUpdate();
        query.close();
    }

    //----------------------------------//
    // EDIT RECORD IN APPOINTMENT TABLE //
    //----------------------------------//
    public void updateAppointment(String AppointmentNumber, String Therapist, LocalDate Date, LocalTime Time, String Service, String Cost, String Status) throws SQLException {

        String sql = "UPDATE appointment SET therapist = ? , date = ? , time = ? , service = ? , cost = ? , status = ? WHERE appointmentnumber = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, Therapist);
        query.setString(2, Date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        query.setString(3, Time.format(DateTimeFormatter.ofPattern("HH:mm")));
        query.setString(4, Service);
        query.setString(5, Cost);
        query.setString(6, Status);
        query.setString(7, AppointmentNumber);

        query.executeUpdate();
        query.close();
    }

    //------------------------------//
    // EDIT RECORD IN SERVICE TABLE //
    //------------------------------//
    public void updateService(String ServiceNumber, String ServiceName, String ServiceActive, String ServiceColour) throws SQLException {

        String sql = "UPDATE service SET name = ?, active = ?, colour = ? WHERE servicenumber = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, ServiceName);
        query.setString(2, ServiceActive);
        query.setString(3, ServiceColour);
        query.setString(4, ServiceNumber);

        query.executeUpdate();
        query.close();

    }

    //------------------------------//
    // EDIT RECORD IN HOLIDAY TABLE //
    //------------------------------//
    public void updateHoliday(String id, String employee, LocalDate start, LocalDate end, String approved, String updated) throws SQLException {

        String sql = "UPDATE holiday SET datestart = ?, dateend = ?, approved = ?, updated = ? WHERE id = ?";

        query = conn.prepareStatement(sql);

        query.setString(1, start.toString());
        query.setString(2, end.toString());
        query.setString(3, approved);
        query.setString(4, updated);
        query.setString(5, id);

        query.executeUpdate();
        query.close();

    }

    //--------------------------------//
    // DELETE RECORD FROM GIVEN TABLE //
    //--------------------------------//
    public void deleteRecord(String table, String searchField, String searchQuery) throws SQLException {

        String sql = "DELETE FROM " + table + " WHERE " + searchField + " = ?";

        query = conn.prepareStatement(sql);
        query.setString(1, searchQuery);

        query.executeUpdate();
        query.close();
    }
}
