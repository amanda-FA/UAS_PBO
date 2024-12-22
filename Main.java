import java.sql.*;
import java.util.*;

// Class untuk mendapatkan koneksi database
class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/UAS_PBO"; 
        String username = "postgres"; 
        String password = "Buatbelajar"; 
        return DriverManager.getConnection(url, username, password);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Berhasil terhubung ke database.");

            boolean isRunning = true;
            while (isRunning) {
                System.out.println("\n=== Menu Manajemen Guru Sekolah ===");
                System.out.println("1. Tambah Data Guru");
                System.out.println("2. Lihat Data Guru dan Jadwal");
                System.out.println("3. Update Data Guru");
                System.out.println("4. Hapus Data Guru");
                System.out.println("5. Keluar");
                System.out.print("Pilih menu: ");
                int menu = scanner.nextInt();

                switch (menu) {
                    case 1 -> tambahGuruDanJadwal(connection, scanner);
                    case 2 -> lihatGuruDanJadwal(connection);
                    case 3 -> updateDataGuru(connection, scanner);
                    case 4 -> hapusDataGuru(connection, scanner);
                    case 5 -> isRunning = false;
                    default -> System.out.println("Menu tidak valid.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal terhubung ke database: " + e.getMessage());
        }
    }

    private static void tambahGuruDanJadwal(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Masukkan ID Guru: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        System.out.print("Masukkan Nama Guru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Mata Pelajaran: ");
        String mapel = scanner.nextLine();
        System.out.print("Masukkan Gaji Pokok: ");
        double gaji = scanner.nextDouble();
        System.out.print("Masukkan Tunjangan: ");
        double tunjangan = scanner.nextDouble();

        String insertGuru = "INSERT INTO guru (id_guru, nama_guru, mata_pelajaran, gaji_pokok, tunjangan) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psGuru = connection.prepareStatement(insertGuru)) {
            psGuru.setInt(1, id);
            psGuru.setString(2, nama);
            psGuru.setString(3, mapel);
            psGuru.setDouble(4, gaji);
            psGuru.setDouble(5, tunjangan);
            psGuru.executeUpdate();
        }

        System.out.print("Berapa jumlah jadwal yang ingin ditambahkan? ");
        int jumlahJadwal = scanner.nextInt();

        String insertJadwal = "INSERT INTO jadwal_mengajar (id_guru, tanggal_mengajar) VALUES (?, ?)";
        try (PreparedStatement psJadwal = connection.prepareStatement(insertJadwal)) {
            for (int i = 0; i < jumlahJadwal; i++) {
                System.out.print("Masukkan tanggal jadwal ke-" + (i + 1) + " (yyyy-MM-dd HH:mm:ss): ");
                scanner.nextLine(); // Clear buffer
                String tanggal = scanner.nextLine();
                Timestamp tanggalMengajar = Timestamp.valueOf(tanggal);

                psJadwal.setInt(1, id);
                psJadwal.setTimestamp(2, tanggalMengajar);
                psJadwal.executeUpdate();
            }
            System.out.println("Data guru dan jadwal berhasil ditambahkan.");
        }
    }

    private static void lihatGuruDanJadwal(Connection connection) throws SQLException {
        String sql = "SELECT g.id_guru, g.nama_guru, g.mata_pelajaran, g.gaji_pokok, g.tunjangan, j.tanggal_mengajar " +
                     "FROM guru g LEFT JOIN jadwal_mengajar j ON g.id_guru = j.id_guru " +
                     "ORDER BY g.id_guru, j.tanggal_mengajar";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\nData Guru dan Jadwal Mengajar:");
            int currentId = -1;
            while (resultSet.next()) {
                int idGuru = resultSet.getInt("id_guru");
                if (currentId != idGuru) {
                    System.out.printf("\nID: %d, Nama: %s, Mapel: %s, Gaji Pokok: %.2f, Tunjangan: %.2f%n",
                            idGuru,
                            resultSet.getString("nama_guru"),
                            resultSet.getString("mata_pelajaran"),
                            resultSet.getDouble("gaji_pokok"),
                            resultSet.getDouble("tunjangan"));
                    System.out.println("Jadwal Mengajar:");
                    currentId = idGuru;
                }

                Timestamp tanggal = resultSet.getTimestamp("tanggal_mengajar");
                if (tanggal != null) {
                    System.out.printf("  - %s%n", tanggal.toString());
                }
            }
        }
    }

    private static void updateDataGuru(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Masukkan ID Guru yang ingin diupdate: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Masukkan Nama Guru baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Mata Pelajaran baru: ");
        String mapel = scanner.nextLine();
        System.out.print("Masukkan Gaji Pokok baru: ");
        double gaji = scanner.nextDouble();
        System.out.print("Masukkan Tunjangan baru: ");
        double tunjangan = scanner.nextDouble();

        String updateGuru = "UPDATE guru SET nama_guru = ?, mata_pelajaran = ?, gaji_pokok = ?, tunjangan = ? WHERE id_guru = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateGuru)) {
            ps.setString(1, nama);
            ps.setString(2, mapel);
            ps.setDouble(3, gaji);
            ps.setDouble(4, tunjangan);
            ps.setInt(5, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data guru berhasil diperbarui.");
            } else {
                System.out.println("ID Guru tidak ditemukan.");
            }
        }
    }

    private static void hapusDataGuru(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Masukkan ID Guru yang ingin dihapus: ");
        int id = scanner.nextInt();

        String deleteJadwal = "DELETE FROM jadwal_mengajar WHERE id_guru = ?";
        String deleteGuru = "DELETE FROM guru WHERE id_guru = ?";

        try (PreparedStatement psJadwal = connection.prepareStatement(deleteJadwal);
             PreparedStatement psGuru = connection.prepareStatement(deleteGuru)) {
            psJadwal.setInt(1, id);
            psJadwal.executeUpdate();

            psGuru.setInt(1, id);
            int rowsAffected = psGuru.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data guru dan jadwal berhasil dihapus.");
            } else {
                System.out.println("ID Guru tidak ditemukan.");
            }
        }
    }
}
