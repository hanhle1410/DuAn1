/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connect.GetConnectDB;
import Model.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class BookService {

    public Vector<Sach> SelectAll() {
        String sql = "SELECT * FROM book";
        Vector<Sach> listBook = null;
        try {
            listBook = new Vector<Sach>();
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next() == true) {
                Sach book = new Sach();
                book.setId(result.getInt("id"));
                book.setName(result.getString("name"));
                book.setPrice(result.getInt("price"));
                book.setAuthor(result.getString("author"));
                book.setIdType(result.getInt("idtype"));
                String dateInString = result.getString("publicationdate");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    book.setPublicationDate(sdf.parse(dateInString));
                } catch (ParseException ex) {
                    Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                }
                book.setAmount(result.getInt("amount"));
                listBook.add((Sach) book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listBook;
    }

    public boolean Delete(int id) {
        try {
            String sql = "DELETE FROM book WHERE id=?;";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setInt(1, id);
            return !preStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TypeBookService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean Add(String name, int price, int idtype, String author, String dateInString, int amount) {
        try {
            String sql = "INSERT INTO book( id, name, price, idtype, author, publicationdate, amount ) VALUES(?,?,?,?,?,?,?);";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setInt(1, 1);
            preStatement.setString(2, name);
            preStatement.setInt(3, price);
            preStatement.setInt(4, idtype);
            preStatement.setString(5, author);
            preStatement.setString(6, dateInString);
            preStatement.setInt(7, amount);
            return preStatement.executeUpdate() >= 1;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean Update(Sach book) {

        try {
            String sql = "UPDATE book SET name=?, price=?, idtype=?, author=?, publicationdate=?, amount=? WHERE id=?;";
            Connection conn = GetConnectDB.getConnectMSAccess("database/bookstore.accdb");
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setString(1, book.getName());
            preStatement.setInt(2, book.getPrice());
            preStatement.setInt(3, book.getIdType());
            preStatement.setString(4, book.getAuthor());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            preStatement.setString(5, sdf.format(book.getPublicationDate()));
            preStatement.setInt(6, book.getAmount());
            preStatement.setInt(7, book.getId());

            return preStatement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            Logger.getLogger(TypeBookService.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
    }

    public Vector<Sach> searchBy(String nameColums, String keyWord) {
        Vector<Sach> listSearched = new Vector<>();
        try {
            String sql;
            sql = "SELECT * FROM book WHERE " + nameColums + " like '%" + keyWord + "%';";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                Sach book = new Sach();
                book.setId(result.getInt("id"));
                book.setPrice(result.getInt("price"));
                book.setIdType(result.getInt("idtype"));
                book.setAuthor(result.getString("author"));
                book.setAmount(result.getInt("amount"));
                book.setName(result.getString("name"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    book.setPublicationDate(sdf.parse(result.getString("publicationdate")));
                } catch (ParseException ex) {
                    Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                }
                listSearched.add(book);
            }
            return listSearched;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Vector<Sach> searchByIdType(Vector<Integer> listId) {
        Vector<Sach> listBook = new Vector<Sach>();
        for (int idtype : listId) {
            try {
                String sql = "SELECT * FROM book WHERE idtype=" + idtype + ";";
                PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
                ResultSet result = preStatement.executeQuery();
                while (result.next()) {
                    Sach book = new Sach();
                    book.setId(result.getInt("id"));
                    book.setPrice(result.getInt("price"));
                    book.setIdType(result.getInt("idtype"));
                    book.setAuthor(result.getString("author"));
                    book.setAmount(result.getInt("amount"));
                    book.setName(result.getString("name"));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        book.setPublicationDate(sdf.parse(result.getString("publicationdate")));
                    } catch (ParseException ex) {
                        Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listBook.add(book);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return listBook;
    }

    public Vector<Sach> searchByAll(String keyWord) {
        Vector<Sach> listSearched = new Vector<>();
        try {
            String sql;
            sql = "SELECT * FROM book WHERE id LIKE '%" + keyWord + "%' OR name LIKE '%" + keyWord + "%' OR author LIKE '%" + keyWord + "%' OR price LIKE '%" + keyWord + "%' OR publicationdate LIKE '%" + keyWord + "%';";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                Sach book = new Sach();
                book.setId(result.getInt("id"));
                book.setPrice(result.getInt("price"));
                book.setIdType(result.getInt("idtype"));
                book.setAuthor(result.getString("author"));
                book.setAmount(result.getInt("amount"));
                book.setName(result.getString("name"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    book.setPublicationDate(sdf.parse(result.getString("publicationdate")));
                } catch (ParseException ex) {
                    Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                }
                listSearched.add(book);
            }
            Vector<Sach> listTamp = this.searchByIdType(new TypeBookService().search(keyWord));
            listSearched.addAll(listTamp);
            return listSearched;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Sach searchByNameAndAuthor(String name, String author) {
        try {
            Sach book = new Sach();
            String sql = "SELECT * FROM book WHERE name=? and author=?";
            PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
            preStatement.setString(1, name);
            preStatement.setString(2, author);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                book.setId(result.getInt("id"));
                book.setPrice(result.getInt("price"));
                book.setIdType(result.getInt("idtype"));
                book.setAuthor(result.getString("author"));
                book.setAmount(result.getInt("amount"));
                book.setName(result.getString("name"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    book.setPublicationDate(sdf.parse(result.getString("publicationdate")));
                } catch (ParseException ex) {
                    Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return book;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Vector<Sach> getListBookFromId(Vector<Integer> listId){
        Vector<Sach> listBook = new Vector<Sach>();
        for (int id : listId) {
            try {
                String sql = "SELECT * FROM book WHERE id=" + id + ";";
                PreparedStatement preStatement = GetConnectDB.getConnectMSAccess("database/bookstore.accdb").prepareStatement(sql);
                ResultSet result = preStatement.executeQuery();
                while (result.next()) {
                    Sach book = new Sach();
                    book.setId(result.getInt("id"));
                    book.setPrice(result.getInt("price"));
                    book.setIdType(result.getInt("idtype"));
                    book.setAuthor(result.getString("author"));
                    book.setAmount(result.getInt("amount"));
                    book.setName(result.getString("name"));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        book.setPublicationDate(sdf.parse(result.getString("publicationdate")));
                    } catch (ParseException ex) {
                        Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listBook.add(book);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return listBook;
    }
}
