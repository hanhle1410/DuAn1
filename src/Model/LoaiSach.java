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
public class LoaiSach {

    private int id;
    private String name;
    private Vector<Sach> listBook;

    public LoaiSach() {
        listBook = new Vector<Sach>();
    }

    public LoaiSach(int id, String name) {
        this.id = id;
        this.name = name;
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

    public Vector<Sach> getListBook() {
        return listBook;
    }

    public void setListBook(Vector<Sach> listBook) {
        this.listBook = listBook;
    }

    public void addBook(Sach book) {
        book.setIdType(this.id);
        listBook.add(book);
    }

    @Override
    public String toString() {
        return this.getName(); //To change body of generated methods, choose Tools | Templates.
    }

}
