/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integratedproject1;

import SQL.SQLHandler;
import java.sql.SQLException;

/**
 *
 * @author patrick
 */
public class Services {

    String serviceNumber;
    String serviceName;

    SQLHandler sql = new SQLHandler();

    public Services(String name, String cost, String duration) throws SQLException {

        this.serviceName = name;
        this.serviceNumber = String.valueOf(sql.countRecords("service") + 1);

        sql.addToService(serviceNumber, serviceName);

    }

}
