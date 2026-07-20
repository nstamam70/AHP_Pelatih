/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USER
 */
public class SubKriteria {

    private int idSub;
    private int idKriteria;
    private String namaKriteria;
    private String kodeSub;
    private String namaSub;
    private double bobot;

    public SubKriteria() {
    }

    public SubKriteria(int idSub, int idKriteria, String kodeSub, String namaSub, double bobot) {
        this.idSub = idSub;
        this.idKriteria = idKriteria;
        this.kodeSub = kodeSub;
        this.namaSub = namaSub;
        this.bobot = bobot;
    }

    public int getIdSub() {
        return idSub;
    }

    public void setIdSub(int idSub) {
        this.idSub = idSub;
    }

    public int getIdKriteria() {
        return idKriteria;
    }

    public void setIdKriteria(int idKriteria) {
        this.idKriteria = idKriteria;
    }

    public String getKodeSub() {
        return kodeSub;
    }

    public void setKodeSub(String kodeSub) {
        this.kodeSub = kodeSub;
    }

    public String getNamaKriteria() {
        return namaKriteria;
    }

    public void setNamaKriteria(String namaKriteria) {
        this.namaKriteria = namaKriteria;
    }

    public String getNamaSub() {
        return namaSub;
    }

    public void setNamaSub(String namaSub) {
        this.namaSub = namaSub;
    }

    public double getBobot() {
        return bobot;
    }

    public void setBobot(double bobot) {
        this.bobot = bobot;
    }
}
