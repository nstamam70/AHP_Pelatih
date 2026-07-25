package utils;

public class AHPCalculator {

    // RI (Random Index) untuk n=1..15
    private static final double[] RI = {
        0, 0, 0, 0.58, 0.90, 1.12, 1.24, 1.32, 1.41, 1.45,
        1.49, 1.51, 1.48, 1.56, 1.57, 1.59
    };

    public static class HasilAHP {
        public double[][] matriksNormalisasi;
        public double[] bobotPrioritas;
        public double lambdaMax;
        public double ci;
        public double cr;
        public boolean konsisten;
    }

    /**
     * Proses AHP: normalisasi → bobot → lambda → CI → CR
     */
    public static HasilAHP proses(double[][] matriks) {
        int n = matriks.length;
        HasilAHP hasil = new HasilAHP();

        // 1. Normalisasi (bagi tiap sel dengan jumlah kolomnya)
        double[] jumlahKolom = new double[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                jumlahKolom[j] += matriks[i][j];
            }
        }

        hasil.matriksNormalisasi = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                hasil.matriksNormalisasi[i][j] = jumlahKolom[j] == 0 ? 0 : matriks[i][j] / jumlahKolom[j];
            }
        }

        // 2. Bobot prioritas (rata-rata tiap baris)
        hasil.bobotPrioritas = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += hasil.matriksNormalisasi[i][j];
            }
            hasil.bobotPrioritas[i] = sum / n;
        }

        // 3. Lambda max
        // Kalikan matriks asli × bobot, lalu bagi tiap elemen dengan bobotnya
        double lambdaSum = 0;
        for (int i = 0; i < n; i++) {
            double weighted = 0;
            for (int j = 0; j < n; j++) {
                weighted += matriks[i][j] * hasil.bobotPrioritas[j];
            }
            if (hasil.bobotPrioritas[i] > 0) {
                lambdaSum += weighted / hasil.bobotPrioritas[i];
            }
        }
        hasil.lambdaMax = lambdaSum / n;

        // 4. CI & CR
        if (n <= 2) {
            hasil.ci = 0;
            hasil.cr = 0;
        } else {
            hasil.ci = (hasil.lambdaMax - n) / (n - 1);
            double ri = n < RI.length ? RI[n] : RI[RI.length - 1];
            hasil.cr = ri == 0 ? 0 : hasil.ci / ri;
        }

        hasil.konsisten = hasil.cr <= 0.10;
        return hasil;
    }

    /**
     * Hitung nilai akhir: skor = Σ(bobot_sub × bobot_lokal_pelatih)
     * @param bobotSub bobot subkriteria [nSub]
     * @param bobotAlternatif bobot lokal pelatih [nSub][nPelatih]
     * @return nilaiAkhir [nPelatih]
     */
    public static double[] hitungNilaiAkhir(double[] bobotSub, double[][] bobotAlternatif) {
        int nSub = bobotSub.length;
        int nPelatih = bobotAlternatif[0].length;
        double[] nilaiAkhir = new double[nPelatih];

        for (int p = 0; p < nPelatih; p++) {
            for (int s = 0; s < nSub; s++) {
                nilaiAkhir[p] += bobotSub[s] * bobotAlternatif[s][p];
            }
        }
        return nilaiAkhir;
    }

    /**
     * Return indeks pelatih diurutkan dari skor tertinggi
     */
    public static int[] getRangking(double[] nilaiAkhir) {
        int n = nilaiAkhir.length;
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        // Selection sort descending — ponytail: O(n²) fine for <100 coaches
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (nilaiAkhir[indices[j]] > nilaiAkhir[indices[maxIdx]]) {
                    maxIdx = j;
                }
            }
            int tmp = indices[i];
            indices[i] = indices[maxIdx];
            indices[maxIdx] = tmp;
        }
        return indices;
    }
}
