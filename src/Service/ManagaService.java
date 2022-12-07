/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connect.GetConnectDB;
import Model.HoaDon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;

/**
 *
 * @author PHAM_HUNG
 */
public class ManagaService {
    
    public Vector<HoaDon> SelectAll(){
        try {
            Vector<HoaDon> listManaga = new Vector<>();
            String sql = "SELECT * FROM managa";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            ResultSet result = preStatement.executeQuery();
            while(result.next()){
                HoaDon managa = new HoaDon();
                managa.setIdcustomer(result.getInt("idcustomer"));
                managa.setTotalMoney(result.getInt("totalmoney"));
                String dateInString = result.getString("date");
                managa.setListid(result.getString("listid"));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                try {
                    managa.setDateBuy(sdf.parse(dateInString));
                } catch (ParseException ex) {
                    Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                }
                listManaga.add(managa);
            }
            return listManaga;
        
        } catch (SQLException ex) {
            Logger.getLogger(ManagaService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public boolean Add(HoaDon managa){
        try {
            String sql = "INSERT INTO managa VALUES(?,?,?,?)";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            String dateInString = sdf.format(managa.getDateBuy());
            preStatement.setString(1, dateInString);
            preStatement.setInt(2, managa.getIdcustomer());
            preStatement.setInt(3, managa.getTotalMoney());
            preStatement.setString(4, managa.getListid());
            return preStatement.executeUpdate() >0;
        } catch (SQLException ex) {
            Logger.getLogger(ManagaService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public Vector<HoaDon> searchForId(int id, String namePutIn){
        try {
            Vector<HoaDon> listManaga = new Vector<>();
            String sql = "SELECT * FROM managa WHERE " + namePutIn + "=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setInt(1, id);
            ResultSet result = preStatement.executeQuery();
            while(result.next()){
                HoaDon managa = new HoaDon();
                managa.setIdcustomer(result.getInt("idcustomer"));
                managa.setListid(result.getString("listid"));
                managa.setTotalMoney(result.getInt("totalmoney"));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String dateInString = result.getString("date");
                try {
                    managa.setDateBuy(sdf.parse(dateInString));
                } catch (ParseException ex) {
                    Logger.getLogger(ManagaService.class.getName()).log(Level.SEVERE, null, ex);
                }
                listManaga.add(managa);
            }
            return listManaga;
        } catch (SQLException ex) {
            Logger.getLogger(ListBookService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vector<Integer> getTop(){
        Vector<HoaDon> listManaga = this.SelectAll();
        ArrayList<HoaDon> list = new ArrayList<>();
        for(HoaDon managa : listManaga){
            list.add(managa);
        }
        Collections.sort(list);
        Vector<Integer> listID = new Vector<>();
        int i= 0;
        for(HoaDon managa : list){
            listID.add(managa.getIdcustomer());
            if(i > 10) break;
            i++;
        }
        return listID;
    }
    
    public Vector<Integer> getToDay(){
        Vector<HoaDon> listManaga = this.SelectAll();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date time = new Date();
        String today = sdf.format(time.getTime());
        Vector<Integer> listID = new Vector<>();
        for(HoaDon managa : listManaga){
            if(today.equals(sdf.format(managa.getDateBuy()))){
                listID.add(managa.getIdcustomer());
            }
        }
        return listID;
    }
}
