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
import javax.swing.JComboBox;
import models.ComboItem;
import models.SubKriteria;

/**
 *
 * @author USER
 */
public class SubKriteriaDAO {

    private Connection conn;

    public SubKriteriaDAO() {
        this.conn = KoneksiDB.getConnection();
    }

    public List<SubKriteria> getAll() {
        List<SubKriteria> list = new ArrayList<>();

        String sql = "SELECT * FROM sub_kriteria";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error Get All Subkriteria: " + e.getMessage());
        }

        return list;
    }

    public SubKriteria getByKode(String kode) {

        String sql = "SELECT * FROM sub_kriteria WHERE kode_sub = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kode);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Kode Subkriteria: " + e.getMessage());
        }

        return null;
    }

    public boolean insert(SubKriteria sub) {

        String sql = "INSERT INTO sub_kriteria(id_kriteria, kode_sub, nama_sub) VALUES(?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sub.getIdKriteria());
            ps.setString(2, sub.getKodeSub());
            ps.setString(3, sub.getNamaSub());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Insert Subkriteria: " + e.getMessage());
        }

        return false;
    }

    public boolean update(SubKriteria sub) {

        String sql = "UPDATE sub_kriteria SET "
                + "id_kriteria=?, "
                + "kode_sub=?, "
                + "nama_sub=? "
                + "WHERE id_sub=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sub.getIdKriteria());
            ps.setString(2, sub.getKodeSub());
            ps.setString(3, sub.getNamaSub());
            ps.setInt(4, sub.getIdSub());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Update Subkriteria: " + e.getMessage());
        }

        return false;
    }

    public boolean delete(int idSub) {

        String sql = "DELETE FROM sub_kriteria WHERE id_sub=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSub);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Delete Subkriteria: " + e.getMessage());
        }

        return false;
    }

    public String generateKode() {

        String sql = "SELECT kode_sub FROM sub_kriteria ORDER BY id_sub DESC LIMIT 1";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {

                String lastKode = rs.getString("kode_sub");
                int number = Integer.parseInt(lastKode.replace("SK-", "")) + 1;

                return String.format("SK-%03d", number);
            }

        } catch (SQLException e) {
            System.err.println("Error Generate Kode: " + e.getMessage());
        }

        return "SK-001";
    }

    public List<SubKriteria> search(String keyword) {

        List<SubKriteria> list = new ArrayList<>();

        String sql = "SELECT "
                + "s.id_sub, "
                + "s.id_kriteria, "
                + "k.nama_kriteria, "
                + "s.kode_sub, "
                + "s.nama_sub "
                + "FROM sub_kriteria s "
                + "INNER JOIN kriteria k "
                + "ON s.id_kriteria = k.id_kriteria "
                + "WHERE s.kode_sub LIKE ? "
                + "OR s.nama_sub LIKE ? "
                + "OR k.nama_kriteria LIKE ? "
                + "ORDER BY s.kode_sub ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            String cari = "%" + keyword + "%";

            ps.setString(1, cari);
            ps.setString(2, cari);
            ps.setString(3, cari);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SubKriteria sub = new SubKriteria();

                    sub.setIdSub(rs.getInt("id_sub"));
                    sub.setIdKriteria(rs.getInt("id_kriteria"));
                    sub.setNamaKriteria(rs.getString("nama_kriteria"));
                    sub.setKodeSub(rs.getString("kode_sub"));
                    sub.setNamaSub(rs.getString("nama_sub"));

                    list.add(sub);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Search Subkriteria: " + e.getMessage());
        }

        return list;
    }

    public List<SubKriteria> getByKriteria(int idKriteria) {

        List<SubKriteria> list = new ArrayList<>();

        String sql = "SELECT * FROM sub_kriteria WHERE id_kriteria=? ORDER BY kode_sub ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKriteria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Kriteria: " + e.getMessage());
        }

        return list;
    }

    private SubKriteria mapResultSet(ResultSet rs) throws SQLException {

        SubKriteria sub = new SubKriteria();

        sub.setIdSub(rs.getInt("id_sub"));
        sub.setIdKriteria(rs.getInt("id_kriteria"));
        sub.setKodeSub(rs.getString("kode_sub"));
        sub.setNamaSub(rs.getString("nama_sub"));

        return sub;
    }

    public ResultSet getDataSubkriteria() {

        String sql = "SELECT "
                + "s.id_sub, "
                + "k.id_kriteria, "
                + "k.nama_kriteria, "
                + "s.kode_sub, "
                + "s.nama_sub "
                + "FROM sub_kriteria s "
                + "INNER JOIN kriteria k "
                + "ON s.id_kriteria = k.id_kriteria "
                + "ORDER BY k.nama_kriteria, s.kode_sub";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error Get Data Subkriteria : " + e.getMessage());
        }

        return null;
    }

    public SubKriteria getById(int idSub) {

        String sql = "SELECT * FROM sub_kriteria WHERE id_sub=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSub);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Id : " + e.getMessage());
        }

        return null;
    }

    public boolean isKodeExist(String kodeSub) {

        String sql = "SELECT COUNT(*) FROM sub_kriteria WHERE kode_sub=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kodeSub);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Cek Kode : " + e.getMessage());
        }

        return false;
    }

    public int countData() {

        String sql = "SELECT COUNT(*) FROM sub_kriteria";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error Count Data : " + e.getMessage());
        }

        return 0;
    }

    public List<SubKriteria> getByNamaKriteria(String namaKriteria) {

        List<SubKriteria> list = new ArrayList<>();

        String sql = "SELECT s.* "
                + "FROM sub_kriteria s "
                + "INNER JOIN kriteria k "
                + "ON s.id_kriteria = k.id_kriteria "
                + "WHERE k.nama_kriteria = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, namaKriteria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }

            }

        } catch (SQLException e) {
            System.err.println("Error Get By Nama Kriteria : " + e.getMessage());
        }

        return list;
    }

    public void loadSubKriteriaToComboBox(JComboBox comboBox) {

        comboBox.removeAllItems();

        String sql = "SELECT * FROM sub_kriteria ORDER BY kode_sub";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                comboBox.addItem(new ComboItem(
                        rs.getInt("id_sub"),
                        rs.getString("kode_sub"),
                        rs.getString("nama_sub")
                ));

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
