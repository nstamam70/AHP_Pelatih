/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USER
 */
public class ComboItem {
    private int id;
    private String kode;
    private String nama;

    public ComboItem(int id, String kode, String nama) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return kode + " - " + nama;
    }
}
