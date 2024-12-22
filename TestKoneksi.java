import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestKoneksi {
    public static void main(String[] args) {
        // URL, USER, dan PASSWORD untuk PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/UAS_PBO";  
        String user = "postgres";  
        String password = "Buatbelajar";  
        try {
           // Mendaftarkan driver PostgreSQL secara eksplisit (opsional)
            Class.forName("org.postgresql.Driver");
            
            // Coba koneksi ke database
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil!");

            conn.close(); // untuk menutup koneksi setelah selesai

        } catch (SQLException e) {
            System.out.println("Kesalahan koneksi: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL tidak ditemukan: " + e.getMessage());
        }
    }
}

