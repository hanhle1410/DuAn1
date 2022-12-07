/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connect.GetConnectDB;
import Model.Sach;
import Model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class CustomerService {

    public Vector<KhachHang> SelectAll() {
        try {
            String sql = "SELECT * FROM customer";
            Vector<KhachHang> listCustomer = new Vector<>();
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                KhachHang customer = new KhachHang();
                customer.setId(result.getInt("id"));
                customer.setAddress(result.getString("address"));
                customer.setName(result.getString("name"));
                customer.setPhone(result.getString("phone"));
                listCustomer.add(customer);
            }
            return listCustomer;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public boolean Delete(int id) {
        try {
            String sql = "DELETE FROM customer WHERE id=?;";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setInt(1, id);
            return !preStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TypeBookService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public void Update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean Add(KhachHang customer) {
        try {
            String sql = "INSERT INTO customer VALUES(?,?,?,?)";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setInt(1, 1);
            preStatement.setString(2, customer.getName());
            preStatement.setString(3, customer.getAddress());
            preStatement.setString(4, customer.getPhone());
            return preStatement.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean checkForExistence(String name, String phone, String address) {
        try {
            String sql = "SELECT * FROM customer WHERE name=? and address=? and phone=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setString(1, name);
            preStatement.setString(2, address);
            preStatement.setString(3, phone);
            ResultSet result = preStatement.executeQuery();
            if(result.next()){
                return true;
            }
            else return false;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int getIdCustomer(String name, String phone, String address) {
        try {
            String sql = "SELECT * FROM customer WHERE name=? and address=? and phone=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setString(1, name);
            preStatement.setString(2, address);
            preStatement.setString(3, phone);
            ResultSet result = preStatement.executeQuery();
            if(result.next()){
                return result.getInt("id");
            }
            else return -1;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public Vector<KhachHang> getListCustomerFromId(Vector<Integer> listId) {
        Vector<KhachHang> listCustomer = new Vector<>();
        for (int id : listId) {
            try {
                String sql = "SELECT * FROM customer WHERE id=?";
                PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
                preStatement.setInt(1, id);
                ResultSet result = preStatement.executeQuery();
                if (result.next()) {
                    KhachHang customer = new KhachHang();
                    customer.setId(result.getInt("id"));
                    customer.setAddress(result.getString("address"));
                    customer.setName(result.getString("name"));
                    customer.setPhone(result.getString("phone"));
                    listCustomer.add(customer);
                }

            } catch (SQLException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
               
            }
        }
        return listCustomer;
    }
}
