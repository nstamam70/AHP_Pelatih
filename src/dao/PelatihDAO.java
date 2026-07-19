package dao;

import configs.KoneksiDB;
import models.Pelatih;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelatihDAO {

    private Connection conn = KoneksiDB.getConnection();

    public List<Pelatih> getAll() {
        List<Pelatih> list = new ArrayList<>();
        String sql = "SELECT * FROM pelatih";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pelatih p = new Pelatih();
                p.setIdPelatih(rs.getInt("id_pelatih"));
                p.setKodePelatih(rs.getString("kode_pelatih"));
                p.setNamaPelatih(rs.getString("nama_pelatih"));
                p.setAlamat(rs.getString("alamat"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setLisensi(rs.getString("lisensi"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Pelatih> search(String keyword) {
        List<Pelatih> list = new ArrayList<>();
        String sql = "SELECT * FROM pelatih WHERE kode_pelatih LIKE ? OR nama_pelatih LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pelatih p = new Pelatih();
                p.setIdPelatih(rs.getInt("id_pelatih"));
                p.setKodePelatih(rs.getString("kode_pelatih"));
                p.setNamaPelatih(rs.getString("nama_pelatih"));
                p.setAlamat(rs.getString("alamat"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setLisensi(rs.getString("lisensi"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Pelatih getByKode(String kode) {
        String sql = "SELECT * FROM pelatih WHERE kode_pelatih = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pelatih p = new Pelatih();
                p.setIdPelatih(rs.getInt("id_pelatih"));
                p.setKodePelatih(rs.getString("kode_pelatih"));
                p.setNamaPelatih(rs.getString("nama_pelatih"));
                p.setAlamat(rs.getString("alamat"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setLisensi(rs.getString("lisensi"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Pelatih p) {
        String sql = "INSERT INTO pelatih (kode_pelatih, nama_pelatih, alamat, no_telp, lisensi, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getKodePelatih());
            ps.setString(2, p.getNamaPelatih());
            ps.setString(3, p.getAlamat());
            ps.setString(4, p.getNoTelp());
            ps.setString(5, p.getLisensi());
            ps.setString(6, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Pelatih p) {
        String sql = "UPDATE pelatih SET kode_pelatih=?, nama_pelatih=?, alamat=?, no_telp=?, lisensi=?, status=? WHERE id_pelatih=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getKodePelatih());
            ps.setString(2, p.getNamaPelatih());
            ps.setString(3, p.getAlamat());
            ps.setString(4, p.getNoTelp());
            ps.setString(5, p.getLisensi());
            ps.setString(6, p.getStatus());
            ps.setInt(7, p.getIdPelatih());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pelatih WHERE id_pelatih = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateKode() {
        String kode = "PLT001";
        String sql = "SELECT kode_pelatih FROM pelatih ORDER BY id_pelatih DESC LIMIT 1";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String last = rs.getString("kode_pelatih");
                int num = Integer.parseInt(last.substring(3)) + 1;
                kode = String.format("PLT%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kode;
    }
}
