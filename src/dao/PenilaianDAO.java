/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configs.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Penilaian;

/**
 *
 * @author USER
 */
public class PenilaianDAO {
      private Connection conn;

    public PenilaianDAO() {
        this.conn = KoneksiDB.getConnection();
    }

    public List<Penilaian> getAll() {
        List<Penilaian> list = new ArrayList<>();

        String sql = "SELECT * FROM penilaian";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error Get All Penilaian: " + e.getMessage());
        }

        return list;
    }

    public Penilaian getById(int idPenilaian) {

        String sql = "SELECT * FROM penilaian WHERE id_penilaian = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPenilaian);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Id Penilaian: " + e.getMessage());
        }

        return null;
    }

    public boolean insert(Penilaian p) {

        String sql = "INSERT INTO penilaian(id_pelatih, id_kriteria, id_sub, nilai) VALUES(?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdPelatih());
            ps.setInt(2, p.getIdKriteria());
            ps.setInt(3, p.getIdSub());
            ps.setDouble(4, p.getNilai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Insert Penilaian: " + e.getMessage());
        }

        return false;
    }

    public boolean update(Penilaian p) {

        String sql = "UPDATE penilaian SET "
                + "id_pelatih=?, "
                + "id_kriteria=?, "
                + "id_sub=?, "
                + "nilai=? "
                + "WHERE id_penilaian=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdPelatih());
            ps.setInt(2, p.getIdKriteria());
            ps.setInt(3, p.getIdSub());
            ps.setDouble(4, p.getNilai());
            ps.setInt(5, p.getIdPenilaian());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Update Penilaian: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int idPenilaian) {

        String sql = "DELETE FROM penilaian WHERE id_penilaian=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPenilaian);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Delete Penilaian: " + e.getMessage());
        }

        return false;
    }

    public List<Penilaian> getByPelatih(int idPelatih) {

        List<Penilaian> list = new ArrayList<>();

        String sql = "SELECT * FROM penilaian WHERE id_pelatih=? ORDER BY id_kriteria";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelatih);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Pelatih: " + e.getMessage());
        }

        return list;
    }

    public Penilaian getByPelatihDanSub(int idPelatih, int idSub) {

        String sql = "SELECT * FROM penilaian WHERE id_pelatih=? AND id_sub=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelatih);
            ps.setInt(2, idSub);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Pelatih Dan Sub: " + e.getMessage());
        }

        return null;
    }

    public boolean deleteByPelatih(int idPelatih) {

        String sql = "DELETE FROM penilaian WHERE id_pelatih=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelatih);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Delete By Pelatih: " + e.getMessage());
        }

        return false;
    }

    public ResultSet getDataPenilaian() {

        String sql = "SELECT "
                + "pn.id_penilaian, "
                + "p.id_pelatih, "
                + "p.nama_pelatih, "
                + "k.id_kriteria, "
                + "k.nama_kriteria, "
                + "sk.id_sub, "
                + "sk.kode_sub, "
                + "sk.bobot, "
                + "pn.nilai "
                + "FROM penilaian pn "
                + "INNER JOIN pelatih p ON pn.id_pelatih = p.id_pelatih "
                + "INNER JOIN kriteria k ON pn.id_kriteria = k.id_kriteria "
                + "INNER JOIN sub_kriteria sk ON pn.id_sub = sk.id_sub "
                + "ORDER BY p.nama_pelatih, k.nama_kriteria";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error Get Data Penilaian: " + e.getMessage());
        }

        return null;
    }

    public int countData() {

        String sql = "SELECT COUNT(*) FROM penilaian";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error Count Data: " + e.getMessage());
        }

        return 0;
    }

    public boolean isSudahDinilai(int idPelatih, int idKriteria) {

        String sql = "SELECT COUNT(*) FROM penilaian WHERE id_pelatih=? AND id_kriteria=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelatih);
            ps.setInt(2, idKriteria);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Cek Sudah Dinilai: " + e.getMessage());
        }

        return false;
    }

    private Penilaian mapResultSet(ResultSet rs) throws SQLException {

        Penilaian p = new Penilaian();

        p.setIdPenilaian(rs.getInt("id_penilaian"));
        p.setIdPelatih(rs.getInt("id_pelatih"));
        p.setIdKriteria(rs.getInt("id_kriteria"));
        p.setIdSub(rs.getInt("id_sub"));
        p.setNilai(rs.getDouble("nilai"));

        return p;
    }
    
    public ResultSet getDataPerbandinganAlternatif() {
    String sql = "SELECT p.id_pelatih, p.kode_pelatih, p.nama_pelatih, "
               + "k.id_kriteria, k.kode_kriteria, k.nama_kriteria, pn.nilai "
               + "FROM penilaian pn "
               + "JOIN pelatih p ON pn.id_pelatih = p.id_pelatih "
               + "JOIN kriteria k ON pn.id_kriteria = k.id_kriteria "
               + "ORDER BY p.nama_pelatih ASC, k.kode_kriteria ASC";
    try {
        Connection conn = KoneksiDB.getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    } catch (SQLException e) {
        System.err.println("Error getDataPerbandinganAlternatif: " + e.getMessage());
        return null;
    }
}
}
