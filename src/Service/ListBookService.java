/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connect.GetConnectDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PHAM_HUNG
 */
public class ListBookService {
    public boolean Add(int idcustomer, int idbook){
        try {
            String sql = "INSERT INTO listbook VALUES(?,?,?)";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setInt(1, 1);
            preStatement.setInt(2, idbook);
            preStatement.setInt(3, idcustomer);
            return preStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ListBookService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Vector<Integer> searchForId(int id, String namePutIn, String namePutOut){
        try {
            Vector<Integer> listId = new Vector<>();
            String sql = "SELECT * FROM listbook WHERE " + namePutIn + "=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setInt(1, id);
            ResultSet result = preStatement.executeQuery();
            while(result.next()){
                listId.add(result.getInt(namePutOut));
            }
            return listId;
        } catch (SQLException ex) {
            Logger.getLogger(ListBookService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
