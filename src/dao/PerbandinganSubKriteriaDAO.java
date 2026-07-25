package dao;

import configs.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import models.SubKriteria;

public class PerbandinganSubKriteriaDAO {

    private Connection conn;

    public PerbandinganSubKriteriaDAO() {
        this.conn = KoneksiDB.getConnection();
    }

    /**
     * Simpan/update perbandingan (hanya segitiga atas: id_sub1 < id_sub2)
     */
    public boolean simpan(int idSub1, int idSub2, double nilai) {
        // Pastikan selalu simpan dengan id kecil di kolom 1
        if (idSub1 > idSub2) {
            int tmp = idSub1;
            idSub1 = idSub2;
            idSub2 = tmp;
            nilai = 1.0 / nilai;
        }

        String sql = "INSERT INTO pairwise_kriteria (id_sub1, id_sub2, nilai) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nilai = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub1);
            ps.setInt(2, idSub2);
            ps.setDouble(3, nilai);
            ps.setDouble(4, nilai);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Simpan Perbandingan SubKriteria: " + e.getMessage());
        }
        return false;
    }

    /**
     * Hapus perbandingan
     */
    public boolean hapus(int idSub1, int idSub2) {
        if (idSub1 > idSub2) {
            int tmp = idSub1;
            idSub1 = idSub2;
            idSub2 = tmp;
        }

        String sql = "DELETE FROM pairwise_kriteria WHERE id_sub1 = ? AND id_sub2 = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub1);
            ps.setInt(2, idSub2);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Hapus Perbandingan SubKriteria: " + e.getMessage());
        }
        return false;
    }

    /**
     * Ambil nilai perbandingan (return 0 jika belum diisi)
     */
    public double getNilai(int idSub1, int idSub2) {
        if (idSub1 == idSub2) return 1.0;

        boolean swapped = idSub1 > idSub2;
        int a = swapped ? idSub2 : idSub1;
        int b = swapped ? idSub1 : idSub2;

        String sql = "SELECT nilai FROM pairwise_kriteria WHERE id_sub1 = ? AND id_sub2 = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a);
            ps.setInt(2, b);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double val = rs.getDouble("nilai");
                    return swapped ? 1.0 / val : val;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Get Nilai Perbandingan: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Bangun matriks NxN dari data di DB. Diagonal = 1, reciprocal otomatis.
     */
    public double[][] buildMatriks(List<SubKriteria> subList) {
        int n = subList.size();
        double[][] matriks = new double[n][n];

        for (int i = 0; i < n; i++) {
            matriks[i][i] = 1.0;
            for (int j = i + 1; j < n; j++) {
                double val = getNilai(subList.get(i).getIdSub(), subList.get(j).getIdSub());
                if (val == 0) val = 1.0; // default jika belum diisi
                matriks[i][j] = val;
                matriks[j][i] = 1.0 / val;
            }
        }
        return matriks;
    }

    /**
     * Hapus semua data perbandingan
     */
    public boolean hapusAll() {
        String sql = "DELETE FROM pairwise_kriteria";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Hapus All Perbandingan: " + e.getMessage());
        }
        return false;
    }
}
