/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PHAM_HUNG
 */
public class GetConnectDB {
    public static Connection getConnectMSAccess(String path){
        try {
            String strConnect = "jdbc:ucanaccess://" + path;
            return DriverManager.getConnection(strConnect);
        } catch (SQLException ex) {
            Logger.getLogger(GetConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
