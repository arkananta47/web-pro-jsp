package com.example.model;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String prodi;
    private int semester;
    private String email;
    private String nohp;

    public Mahasiswa(String nim, String nama, String prodi, int semester, String email, String nohp) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.semester = semester;
        this.email = email;
        this.nohp = nohp;
    }

    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getProdi() { return prodi; }
    public int getSemester() { return semester; }
    public String getEmail() { return email; }
    public String getNohp() { return nohp; }

    public void setNama(String nama) { this.nama = nama; }
    public void setProdi(String prodi) { this.prodi = prodi; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setEmail(String email) { this.email = email; }
    public void setNohp(String nohp) { this.nohp = nohp; }
}
