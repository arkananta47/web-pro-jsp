package com.example.dao;

import com.example.model.Mahasiswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {

    private Connection conn;

    public MahasiswaDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // --- UPDATE KONEKSI: MENGAMBIL DATA DARI RAILWAY LANGSUNG ---
            String dbHost = System.getenv("MYSQLHOST");
            String dbPort = System.getenv("MYSQLPORT");
            String dbUser = System.getenv("MYSQLUSER");
            String dbPass = System.getenv("MYSQLPASSWORD");
            String dbName = System.getenv("MYSQLDATABASE");

            // Jaga-jaga kalau sedang dijalankan di Laptop (Localhost) agar tidak error
            if (dbHost == null) {
                dbHost = "localhost";
                dbPort = "3306";
                dbUser = "root";
                dbPass = ""; // Isi password laptop Anda jika perlu
                dbName = "siakad";
            }

            // Setting tambahan agar koneksi lancar (matikan SSL & izinkan public key)
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=false";

            conn = DriverManager.getConnection(url, dbUser, dbPass);
            
            // --- BUAT TABEL OTOMATIS ---
            String sqlCreate = "CREATE TABLE IF NOT EXISTS mahasiswa ("
                    + "nim VARCHAR(20) PRIMARY KEY, "
                    + "nama VARCHAR(100), "
                    + "prodi VARCHAR(50), "
                    + "semester INT, "
                    + "email VARCHAR(100), "
                    + "nohp VARCHAR(20)"
                    + ")";
            Statement stmt = conn.createStatement();
            stmt.execute(sqlCreate);

            System.out.println(">>> KONEKSI DATABASE BERHASIL! <<<");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>> KONEKSI DATABASE GAGAL! CEK LOG DI BAWAH <<<");
        }
    }

    // ================== PAGINATION + SORTING ==================
    public List<Mahasiswa> getPagedList(int start, int limit, String sort, String order) {
        List<Mahasiswa> list = new ArrayList<>();

        try {
            if (sort == null || sort.isEmpty()) sort = "nama";
            if (order == null || order.isEmpty()) order = "asc";

            String sql = "SELECT * FROM mahasiswa ORDER BY " + sort + " " + order + " LIMIT ?, ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, start);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getString("email"),
                    rs.getString("nohp")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Hitung total data
    public int countMahasiswa() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM mahasiswa";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ================== SEARCH ==================
    public List<Mahasiswa> search(String keyword) {
        List<Mahasiswa> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM mahasiswa WHERE nim LIKE ? OR nama LIKE ? OR prodi LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            String key = "%" + keyword + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getString("email"),
                    rs.getString("nohp")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== INSERT BATCH FOR CSV IMPORT ==================
    public void insertBatch(List<Mahasiswa> list) {
        try {
            String sql = "INSERT INTO mahasiswa VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (Mahasiswa m : list) {
                ps.setString(1, m.getNim());
                ps.setString(2, m.getNama());
                ps.setString(3, m.getProdi());
                ps.setInt(4, m.getSemester());
                ps.setString(5, m.getEmail());
                ps.setString(6, m.getNohp());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== CRUD ==================
    public void insert(Mahasiswa m) {
        try {
            String sql = "INSERT INTO mahasiswa VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getProdi());
            ps.setInt(4, m.getSemester());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getNohp());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Mahasiswa getByNim(String nim) {
        Mahasiswa m = null;
        try {
            String sql = "SELECT * FROM mahasiswa WHERE nim=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nim);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getString("email"),
                    rs.getString("nohp")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public void update(Mahasiswa m) {
        try {
            String sql = "UPDATE mahasiswa SET nama=?, prodi=?, semester=?, email=?, nohp=? WHERE nim=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, m.getNama());
            ps.setString(2, m.getProdi());
            ps.setInt(3, m.getSemester());
            ps.setString(4, m.getEmail());
            ps.setString(5, m.getNohp());
            ps.setString(6, m.getNim());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String nim) {
        try {
            String sql = "DELETE FROM mahasiswa WHERE nim=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nim);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== CSV EXPORT ==================
    public List<Mahasiswa> getAll() {
        List<Mahasiswa> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM mahasiswa";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getString("email"),
                    rs.getString("nohp")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
