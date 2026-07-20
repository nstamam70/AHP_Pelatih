package dao;

import configs.KoneksiDB;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardDAO {

    private Connection conn = KoneksiDB.getConnection();

    public int countPelatih() {
        return countTable("pelatih");
    }

    public int countKriteria() {
        return countTable("kriteria");
    }

    public int countPenilaian() {
        return countTable("hasil");
    }

    private int countTable(String table) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table);
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Kriteria dengan jumlah sub_kriteria-nya (untuk donut chart)
    public Map<String, Integer> getKriteriaWithSubCount() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT k.nama_kriteria, COUNT(s.id_sub) as jumlah " +
                     "FROM kriteria k LEFT JOIN sub_kriteria s ON k.id_kriteria = s.id_kriteria " +
                     "GROUP BY k.id_kriteria, k.nama_kriteria";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("nama_kriteria"), rs.getInt("jumlah"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    // Top 5 pelatih berdasarkan nilai_akhir
    public Map<String, Double> getTop5Ranking() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT p.nama_pelatih, h.nilai_akhir " +
                     "FROM hasil h JOIN pelatih p ON h.id_pelatih = p.id_pelatih " +
                     "ORDER BY h.nilai_akhir DESC LIMIT 5";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                map.put(rs.getString("nama_pelatih"), rs.getDouble("nilai_akhir"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
