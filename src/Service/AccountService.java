/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connect.GetConnectDB;
import Model.TaiKhoan;
import Model.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class AccountService {

    public Vector<TaiKhoan> SelectAll() {
        //To change body of generated methods, choose Tools | Templates.
        String sql = "SELECT * FROM account";
        Vector<TaiKhoan> listAccount = null;
        try {
            listAccount = new Vector<TaiKhoan>();
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next() == true) {
                TaiKhoan account = new TaiKhoan();
                account.setId(result.getInt("id"));
                account.setUsername(result.getString("username"));
                account.setPassword(result.getString("password"));
                account.setType(result.getInt("type"));
                listAccount.add(account);
            }

            return listAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean Delete(int id) {
        //To change body of generated methods, choose Tools | Templates.
        try {
            String sql = "DELETE FROM account WHERE id=?;";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setInt(1, id);
            return !preStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TypeBookService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean Add(TaiKhoan account) {
        
        try {
            String sql = "INSERT INTO account VALUES(?,?,?,?);";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setInt(1, 1);
            preStatement.setString(2, account.getUsername());
            preStatement.setString(3, account.getPassword());
            preStatement.setInt(4, account.getType());
            return preStatement.executeUpdate() >= 1;

        } catch (SQLException ex) {
            return false;
        }

    }

    public TaiKhoan searchWithUser(String username) {
        try {
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setString(1, username);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                TaiKhoan account = new TaiKhoan();
                account.setId(result.getInt("id"));
                account.setUsername(result.getString("username"));
                account.setPassword(result.getString("password"));
                account.setType(result.getInt("type"));
                return account;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
