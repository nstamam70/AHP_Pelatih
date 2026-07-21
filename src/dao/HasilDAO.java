package dao;

import configs.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HasilDAO {

    private Connection conn;

    public HasilDAO() {
        this.conn = KoneksiDB.getConnection();
    }

    /**
     * Simpan hasil perangkingan
     */
    public boolean simpanHasil(int idPelatih, double nilaiAkhir, int ranking) {
        String sql = "INSERT INTO hasil (id_pelatih, nilai_akhir, ranking, tanggal) VALUES (?, ?, ?, CURDATE())";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPelatih);
            ps.setDouble(2, nilaiAkhir);
            ps.setInt(3, ranking);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Simpan Hasil: " + e.getMessage());
        }
        return false;
    }

    /**
     * Ambil ranking (JOIN ke pelatih untuk nama)
     */
    public ResultSet getRangking() {
        String sql = "SELECT h.ranking, p.kode_pelatih, p.nama_pelatih, h.nilai_akhir, h.tanggal "
                + "FROM hasil h "
                + "INNER JOIN pelatih p ON h.id_pelatih = p.id_pelatih "
                + "ORDER BY h.ranking ASC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error Get Rangking: " + e.getMessage());
        }
        return null;
    }

    /**
     * Hapus semua hasil
     */
    public boolean hapusAll() {
        String sql = "DELETE FROM hasil";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Hapus All Hasil: " + e.getMessage());
        }
        return false;
    }
}
