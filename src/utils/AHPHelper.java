/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author USER
 */
public class AHPHelper {
    public static String getKeterangan(double nilai) {
    if (Math.abs(nilai - 1) < 0.001) return "1 - Sama Penting";
    if (Math.abs(nilai - 2) < 0.001) return "2 - Mendekati Sedikit Lebih Penting";
    if (Math.abs(nilai - 3) < 0.001) return "3 - Sedikit Lebih Penting";
    if (Math.abs(nilai - 4) < 0.001) return "4 - Mendekati Lebih Penting";
    if (Math.abs(nilai - 5) < 0.001) return "5 - Lebih Penting";
    if (Math.abs(nilai - 6) < 0.001) return "6 - Mendekati Sangat Penting";
    if (Math.abs(nilai - 7) < 0.001) return "7 - Sangat Penting";
    if (Math.abs(nilai - 8) < 0.001) return "8 - Mendekati Mutlak Lebih Penting";
    if (Math.abs(nilai - 9) < 0.001) return "9 - Mutlak Lebih Penting";
    if (Math.abs(nilai - (1.0/2)) < 0.001) return "1/2 - Mendekati Sama Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/3)) < 0.001) return "1/3 - Sedikit Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/4)) < 0.001) return "1/4 - Mendekati Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/5)) < 0.001) return "1/5 - Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/6)) < 0.001) return "1/6 - Mendekati Sangat Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/7)) < 0.001) return "1/7 - Sangat Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/8)) < 0.001) return "1/8 - Mendekati Mutlak Kurang Penting (kebalikan)";
    if (Math.abs(nilai - (1.0/9)) < 0.001) return "1/9 - Mutlak Kurang Penting (kebalikan)";
    return nilai + " - Tidak Diketahui";
}
}
