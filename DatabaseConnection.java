import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection; // Tambahkan variabel connection

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                // Memuat driver PostgreSQL
                Class.forName("org.postgresql.Driver");

                // URL koneksi
                String url = "jdbc:postgresql://127.0.0.1:5432/UAS_PBO";
                String user = "postgres";
                String password = "Buatbelajar";

                // Koneksi ke database
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi berhasil ke database.");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver PostgreSQL tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Gagal menghubungkan ke database: " + e.getMessage());
                throw e; // Lempar kembali SQLException untuk penanganan lebih lanjut
            }
        }
        return connection;
    }
}
