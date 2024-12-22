import java.util.Date;

public class JadwalMengajar extends Guru {
    private Date jadwal; // Jadwal mengajar

    public JadwalMengajar(int idGuru, String namaGuru, String mataPelajaran, double gajiPokok, int jamMengajar, Date jadwal) {
        super(idGuru, namaGuru, mataPelajaran, gajiPokok, jamMengajar);
        this.jadwal = jadwal;
    }

    public Date getJadwal() { return jadwal; }

    @Override
    public String toString() {
        return super.toString() + ", Jadwal Mengajar: " + jadwal.toString();
    }
}
