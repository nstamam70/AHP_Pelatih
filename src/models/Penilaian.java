/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author USER
 */
public class Penilaian {
    private int idPenilaian;
    private int idPelatih;
    private int idKriteria;
    private int idSub;
    private double nilai;

    public Penilaian() {
    }

    public Penilaian(int idPelatih, int idKriteria, int idSub, double nilai) {
        this.idPelatih = idPelatih;
        this.idKriteria = idKriteria;
        this.idSub = idSub;
        this.nilai = nilai;
    }

    public Penilaian(int idPenilaian, int idPelatih, int idKriteria, int idSub, double nilai) {
        this.idPenilaian = idPenilaian;
        this.idPelatih = idPelatih;
        this.idKriteria = idKriteria;
        this.idSub = idSub;
        this.nilai = nilai;
    }

    public int getIdPenilaian() {
        return idPenilaian;
    }

    public void setIdPenilaian(int idPenilaian) {
        this.idPenilaian = idPenilaian;
    }

    public int getIdPelatih() {
        return idPelatih;
    }

    public void setIdPelatih(int idPelatih) {
        this.idPelatih = idPelatih;
    }

    public int getIdKriteria() {
        return idKriteria;
    }

    public void setIdKriteria(int idKriteria) {
        this.idKriteria = idKriteria;
    }

    public int getIdSub() {
        return idSub;
    }

    public void setIdSub(int idSub) {
        this.idSub = idSub;
    }

    public double getNilai() {
        return nilai;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }
}
