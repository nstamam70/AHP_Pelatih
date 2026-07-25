package dao;

import configs.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import models.Pelatih;

public class PerbandinganAlternatifDAO {

    private Connection conn;

    public PerbandinganAlternatifDAO() {
        this.conn = KoneksiDB.getConnection();
    }

    /**
     * Simpan/update perbandingan pelatih untuk subkriteria tertentu
     */
    public boolean simpan(int idSub, int idPelatih1, int idPelatih2, double nilai) {
        // Pastikan selalu simpan dengan id kecil di kolom 1
        if (idPelatih1 > idPelatih2) {
            int tmp = idPelatih1;
            idPelatih1 = idPelatih2;
            idPelatih2 = tmp;
            nilai = 1.0 / nilai;
        }

        String sql = "INSERT INTO pairwise_alternatif (id_sub, id_pelatih1, id_pelatih2, nilai) "
                + "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nilai = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub);
            ps.setInt(2, idPelatih1);
            ps.setInt(3, idPelatih2);
            ps.setDouble(4, nilai);
            ps.setDouble(5, nilai);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Simpan Perbandingan Alternatif: " + e.getMessage());
        }
        return false;
    }

    /**
     * Hapus perbandingan
     */
    
   public ResultSet getDataPerbandinganAlternatif() {
    String sql = "SELECT pa.id_pairwise, "
               + "p1.nama_pelatih AS alternatif1, "
               + "p2.nama_pelatih AS alternatif2, "
               + "pa.nilai "
               + "FROM pairwise_alternatif pa "
               + "JOIN pelatih p1 ON pa.id_pelatih1 = p1.id_pelatih "
               + "JOIN pelatih p2 ON pa.id_pelatih2 = p2.id_pelatih "
               + "ORDER BY pa.id_pairwise ASC";
    try {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    } catch (SQLException e) {
        System.err.println("Error getDataPerbandinganAlternatif: " + e.getMessage());
        return null;
    }
}
    public boolean hapus(int idSub, int idPelatih1, int idPelatih2) {
        if (idPelatih1 > idPelatih2) {
            int tmp = idPelatih1;
            idPelatih1 = idPelatih2;
            idPelatih2 = tmp;
        }

        String sql = "DELETE FROM pairwise_alternatif WHERE id_sub = ? AND id_pelatih1 = ? AND id_pelatih2 = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub);
            ps.setInt(2, idPelatih1);
            ps.setInt(3, idPelatih2);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Hapus Perbandingan Alternatif: " + e.getMessage());
        }
        return false;
    }

    /**
     * Ambil nilai perbandingan untuk subkriteria tertentu
     */
    public double getNilai(int idSub, int idPelatih1, int idPelatih2) {
        if (idPelatih1 == idPelatih2) return 1.0;

        boolean swapped = idPelatih1 > idPelatih2;
        int a = swapped ? idPelatih2 : idPelatih1;
        int b = swapped ? idPelatih1 : idPelatih2;

        String sql = "SELECT nilai FROM pairwise_alternatif WHERE id_sub = ? AND id_pelatih1 = ? AND id_pelatih2 = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub);
            ps.setInt(2, a);
            ps.setInt(3, b);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double val = rs.getDouble("nilai");
                    return swapped ? 1.0 / val : val;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Get Nilai Alternatif: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Bangun matriks NxN pelatih untuk subkriteria tertentu
     */
    public double[][] buildMatriks(int idSub, List<Pelatih> pelatihList) {
        int n = pelatihList.size();
        double[][] matriks = new double[n][n];

        for (int i = 0; i < n; i++) {
            matriks[i][i] = 1.0;
            for (int j = i + 1; j < n; j++) {
                double val = getNilai(idSub,
                        pelatihList.get(i).getIdPelatih(),
                        pelatihList.get(j).getIdPelatih());
                if (val == 0) val = 1.0;
                matriks[i][j] = val;
                matriks[j][i] = 1.0 / val;
            }
        }
        return matriks;
    }

    /**
     * Hapus semua perbandingan untuk subkriteria tertentu
     */
    public boolean hapusBySub(int idSub) {
        String sql = "DELETE FROM pairwise_alternatif WHERE id_sub = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSub);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Hapus By Sub: " + e.getMessage());
        }
        return false;
    }

    /**
     * Hapus semua data perbandingan alternatif
     */
    public boolean hapusAll() {
        String sql = "DELETE FROM pairwise_alternatif";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Hapus All Alternatif: " + e.getMessage());
        }
        return false;
    }
}
