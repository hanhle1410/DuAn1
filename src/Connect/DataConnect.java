/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import View.QuanLyBanHang;
import View.InPutUI;
import View.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author PHAM_HUNG
 */
public class DataConnect {
    
    public static boolean Login(String user, String pass){
        boolean test = false;
        Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
        int type;
        if(conn != null){
            String sql = "select * from account where username=? and password=?";
            try{
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, user);
                statement.setString(2, pass);
                ResultSet result = statement.executeQuery();
                if(result.next()){
                    JOptionPane.showMessageDialog(null, "Đăng Nhập Thành Công !");
                    test=true;
                    type = result.getInt("type");
                    switch(type){
                        case 0:
                            QuanLyBanHang ui0 = new QuanLyBanHang();
                            ui0.main();
                            break;
                        case 1:
                            InPutUI ui1 = new InPutUI();
                            ui1.main();
                            break;
                        case 2:
                            HoaDon ui2 = new HoaDon();
                            ui2.main();
                            break;
                    }
                    
                }
                else JOptionPane.showMessageDialog(null, "Đăng Nhập Thất Bại !");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return test;
    }
}
