public class Guru {
    private int idGuru;
    private String namaGuru;
    private String mataPelajaran;
    private double gajiPokok;
    private double tunjangan;
    private int jamMengajar; // Jam mengajar per minggu

    // Constructor
    public Guru(int idGuru, String namaGuru, String mataPelajaran, double gajiPokok, int jamMengajar) {
        this.idGuru = idGuru;
        this.namaGuru = namaGuru;
        this.mataPelajaran = mataPelajaran;
        this.gajiPokok = gajiPokok;
        this.jamMengajar = jamMengajar;
        this.tunjangan = hitungTunjangan();
    }

    // Hitung tunjangan jika jam mengajar lebih dari 24
    private double hitungTunjangan() { // perhitugan matematika untuk menghitung tunjangan
        int jamBerlebih = Math.max(0, jamMengajar - 24); // Hitung jam lebih 
        return jamBerlebih * 300000; // perhitungan matematika
    }

    public double hitungTotalGaji() {
        return gajiPokok + tunjangan;
    }

    // Getter dan Setter
    public int getIdGuru() { return idGuru; }
    public String getNamaGuru() { return namaGuru; }
    public String getMataPelajaran() { return mataPelajaran; }
    public double getGajiPokok() { return gajiPokok; }
    public double getTunjangan() { return tunjangan; }
    public int getJamMengajar() { return jamMengajar; }

    @Override
    public String toString() {
        return "ID Guru: " + idGuru + 
               ", Nama: " + namaGuru + 
               ", Mata Pelajaran: " + mataPelajaran + 
               ", Jam Mengajar: " + jamMengajar + 
               ", Tunjangan: " + tunjangan + 
               ", Total Gaji: " + hitungTotalGaji();
    }
}
