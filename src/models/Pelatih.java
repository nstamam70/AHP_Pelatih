package models;

public class Pelatih {

    private int idPelatih;
    private String kodePelatih;
    private String namaPelatih;
    private String alamat;
    private String noHp;
    private String lisensi;
    private String status;

    public int getIdPelatih() {
        return idPelatih;
    }

    public void setIdPelatih(int idPelatih) {
        this.idPelatih = idPelatih;
    }

    public String getKodePelatih() {
        return kodePelatih;
    }

    public void setKodePelatih(String kodePelatih) {
        this.kodePelatih = kodePelatih;
    }

    public String getNamaPelatih() {
        return namaPelatih;
    }

    public void setNamaPelatih(String namaPelatih) {
        this.namaPelatih = namaPelatih;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getLisensi() {
        return lisensi;
    }

    public void setLisensi(String lisensi) {
        this.lisensi = lisensi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
