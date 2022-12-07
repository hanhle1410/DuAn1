/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author PHAM_HUNG
 */
public class KhachHang {
    private int id;
    private String name;
    private String address;
    private String phone;
    private Vector<Sach> listBook;

    public KhachHang(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        listBook = new Vector<Sach>();
    }

    public KhachHang() {
        listBook = new Vector<Sach>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Vector<Sach> getListBook() {
        return listBook;
    }

    public void setListBook(Vector<Sach> listBook) {
        this.listBook = listBook;
    }
    
    public void addBook(Sach book){
        listBook.add(book);
    }
}
