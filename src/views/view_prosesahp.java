/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import dao.HasilDAO;
import dao.PelatihDAO;
import dao.PerbandinganAlternatifDAO;
import dao.PerbandinganSubKriteriaDAO;
import dao.SubKriteriaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Pelatih;
import models.SubKriteria;
import utils.AHPCalculator;

/**
 *
 * @author USER
 */
public class view_prosesahp extends javax.swing.JPanel {

    private SubKriteriaDAO subDAO;
    private PelatihDAO pelatihDAO;
    private PerbandinganSubKriteriaDAO perbandinganSubDAO;
    private PerbandinganAlternatifDAO perbandinganAltDAO;
    private HasilDAO hasilDAO;
    private List<SubKriteria> subList;
    private List<Pelatih> pelatihList;

    public view_prosesahp() {
        initComponents();
        subDAO = new SubKriteriaDAO();
        pelatihDAO = new PelatihDAO();
        perbandinganSubDAO = new PerbandinganSubKriteriaDAO();
        perbandinganAltDAO = new PerbandinganAlternatifDAO();
        hasilDAO = new HasilDAO();

        // Navigasi ke halaman perbandingan
        jButton1.addActionListener(e -> navigateTo(new view_perbandingankriteria()));
        jButton4.addActionListener(e -> navigateTo(new view_perbandinganalternatif()));

        // Buat text field read-only
        lblCi.setEditable(false);
        lblCr.setEditable(false);
        lblLambda.setEditable(false);
        lblCi1.setEditable(false);
        lblCr1.setEditable(false);
        lblLambda1.setEditable(false);
    }

    private void hitungHasilAkhir() {
        subList = subDAO.getAll();
        pelatihList = pelatihDAO.getAll();

        if (subList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data sub-kriteria belum tersedia!");
            return;
        }
        if (pelatihList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data pelatih belum tersedia!");
            return;
        }

        int nSub = subList.size();
        int nPelatih = pelatihList.size();

        // 1. Hitung bobot sub-kriteria dari matriks 9x9
        double[][] matriksSub = perbandinganSubDAO.buildMatriks(subList);
        AHPCalculator.HasilAHP hasilSub = AHPCalculator.proses(matriksSub);

        // Update bobot di database
        for (int i = 0; i < nSub; i++) {
            SubKriteria sub = subList.get(i);
            sub.setBobot(hasilSub.bobotPrioritas[i]);
            subDAO.update(sub);
        }

        // Tampilkan tabel normalisasi + bobot sub-kriteria
        tampilTabelSubKriteria(hasilSub);

        lblCi.setText(String.format("%.3f", hasilSub.ci));
        lblCr.setText(String.format("%.3f", hasilSub.cr));
        lblLambda.setText(String.format("%.3f", hasilSub.lambdaMax));

        if (!hasilSub.konsisten) {
            JOptionPane.showMessageDialog(this,
                "Perbandingan sub-kriteria TIDAK konsisten!\n"
                + "CR = " + String.format("%.3f", hasilSub.cr) + " (harus <= 0.10)\n"
                + "Silakan perbaiki nilai perbandingan.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        }

        // 2. Hitung bobot lokal pelatih per sub-kriteria
        double[][] bobotAlternatif = new double[nSub][nPelatih];
        double ciAltTotal = 0, crAltTotal = 0, lambdaAltTotal = 0;
        boolean allConsistent = true;

        for (int k = 0; k < nSub; k++) {
            int idSub = subList.get(k).getIdSub();
            double[][] matriksAlt = perbandinganAltDAO.buildMatriks(idSub, pelatihList);
            AHPCalculator.HasilAHP hasilAlt = AHPCalculator.proses(matriksAlt);
            bobotAlternatif[k] = hasilAlt.bobotPrioritas;
            ciAltTotal += hasilAlt.ci;
            crAltTotal += hasilAlt.cr;
            lambdaAltTotal += hasilAlt.lambdaMax;
            if (!hasilAlt.konsisten) allConsistent = false;
        }

        tampilTabelAlternatif(bobotAlternatif);

        lblCi1.setText(String.format("%.3f", ciAltTotal / nSub));
        lblCr1.setText(String.format("%.3f", crAltTotal / nSub));
        lblLambda1.setText(String.format("%.3f", lambdaAltTotal / nSub));

        if (!allConsistent) {
            JOptionPane.showMessageDialog(this,
                "Beberapa perbandingan alternatif TIDAK konsisten!\n"
                + "Silakan periksa CR untuk setiap sub-kriteria.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        }

        // 3. Hitung skor akhir & ranking
        double[] nilaiAkhir = AHPCalculator.hitungNilaiAkhir(hasilSub.bobotPrioritas, bobotAlternatif);
        int[] rangking = AHPCalculator.getRangking(nilaiAkhir);

        // Simpan ke database
        hasilDAO.hapusAll();
        for (int rank = 0; rank < rangking.length; rank++) {
            int idx = rangking[rank];
            hasilDAO.simpanHasil(pelatihList.get(idx).getIdPelatih(), nilaiAkhir[idx], rank + 1);
        }

        tampilTabelHasilAkhir(nilaiAkhir, rangking);
        JOptionPane.showMessageDialog(this, "Perhitungan AHP selesai! Hasil telah disimpan.");
    }

    private void tampilTabelSubKriteria(AHPCalculator.HasilAHP hasil) {
        int n = subList.size();

        String[] header = new String[n + 2];
        header[0] = "SubKriteria";
        for (int i = 0; i < n; i++) {
            header[i + 1] = subList.get(i).getKodeSub();
        }
        header[n + 1] = "Bobot";

        Object[][] data = new Object[n][n + 2];
        for (int i = 0; i < n; i++) {
            data[i][0] = subList.get(i).getKodeSub() + " - " + subList.get(i).getNamaSub();
            for (int j = 0; j < n; j++) {
                data[i][j + 1] = String.format("%.3f", hasil.matriksNormalisasi[i][j]);
            }
            data[i][n + 1] = String.format("%.3f", hasil.bobotPrioritas[i]);
        }
        jTable1.setModel(new DefaultTableModel(data, header));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i < n + 2; i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
    }

    private void tampilTabelAlternatif(double[][] bobotAlternatif) {
        int nSub = subList.size();
        int nPelatih = pelatihList.size();

        String[] header = new String[nSub + 1];
        header[0] = "Pelatih";
        for (int k = 0; k < nSub; k++) {
            header[k + 1] = subList.get(k).getKodeSub();
        }

        Object[][] data = new Object[nPelatih][nSub + 1];
        for (int i = 0; i < nPelatih; i++) {
            data[i][0] = pelatihList.get(i).getKodePelatih() + " - " + pelatihList.get(i).getNamaPelatih();
            for (int k = 0; k < nSub; k++) {
                data[i][k + 1] = String.format("%.3f", bobotAlternatif[k][i]);
            }
        }
        tablePerbandinganAlternatif.setModel(new DefaultTableModel(data, header));
        tablePerbandinganAlternatif.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablePerbandinganAlternatif.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i <= nSub; i++) {
            tablePerbandinganAlternatif.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
    }

    private void tampilTabelHasilAkhir(double[] nilaiAkhir, int[] rangking) {
        String[] header = {"Peringkat", "Kode", "Nama Pelatih", "Nilai Akhir", "Persentase"};
        Object[][] data = new Object[rangking.length][5];
        for (int rank = 0; rank < rangking.length; rank++) {
            int idx = rangking[rank];
            data[rank][0] = rank + 1;
            data[rank][1] = pelatihList.get(idx).getKodePelatih();
            data[rank][2] = pelatihList.get(idx).getNamaPelatih();
            data[rank][3] = String.format("%.3f", nilaiAkhir[idx]);
            data[rank][4] = String.format("%.2f%%", nilaiAkhir[idx] * 100);
        }
        jTable3.setModel(new DefaultTableModel(data, header));
    }

    private void navigateTo(javax.swing.JPanel view) {
        java.awt.Container parent = this.getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(view);
            parent.repaint();
            parent.revalidate();
        }
    }

    private void resetHasil() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Reset akan menghapus semua hasil perhitungan.\nLanjutkan?",
            "Konfirmasi Reset", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        hasilDAO.hapusAll();
        jTable1.setModel(new DefaultTableModel());
        tablePerbandinganAlternatif.setModel(new DefaultTableModel());
        jTable3.setModel(new DefaultTableModel());
        lblCi.setText("CI");
        lblCr.setText("CR");
        lblLambda.setText("Lambda");
        lblCi1.setText("CI");
        lblCr1.setText("CR");
        lblLambda1.setText("Lambda");
        JOptionPane.showMessageDialog(this, "Hasil telah direset!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePerbandinganAlternatif = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCi = new javax.swing.JTextField();
        lblCr = new javax.swing.JTextField();
        lblLambda = new javax.swing.JTextField();
        lblCi1 = new javax.swing.JTextField();
        lblCr1 = new javax.swing.JTextField();
        lblLambda1 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Proses AHP");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Hasil Perbandingan Kriteria");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Hasil Perbandingan Alternatif");

        tablePerbandinganAlternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablePerbandinganAlternatif);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setText("Hasil Akhir");

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton3.setText("Reset Hasil");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setText("Hitung Hasil Akhir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setText("Perbandingan Kriteria");

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton4.setText("Perbandingan Alternatif");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("CI, CR, & Lambda Perbandnigan Kriteria");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setText("CI, CR, & Lambda Perbandnigan Alternatif");

        lblCi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblCi.setText("CI");
        lblCi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblCiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblCiFocusLost(evt);
            }
        });

        lblCr.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblCr.setText("CR");
        lblCr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblCrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblCrFocusLost(evt);
            }
        });

        lblLambda.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblLambda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblLambda.setText("Lambda");
        lblLambda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblLambdaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblLambdaFocusLost(evt);
            }
        });

        lblCi1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCi1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblCi1.setText("CI");
        lblCi1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblCi1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblCi1FocusLost(evt);
            }
        });

        lblCr1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCr1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblCr1.setText("CR");
        lblCr1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblCr1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblCr1FocusLost(evt);
            }
        });

        lblLambda1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblLambda1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblLambda1.setText("Lambda");
        lblLambda1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblLambda1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblLambda1FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCi, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCr, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLambda, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCi1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCr1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLambda1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLambda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLambda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(451, 451, 451)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(255, 255, 255))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1164, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        resetHasil();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        hitungHasilAkhir();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lblCiFocusGained(java.awt.event.FocusEvent evt) {}
    private void lblCiFocusLost(java.awt.event.FocusEvent evt) {}
    private void lblCrFocusGained(java.awt.event.FocusEvent evt) {}
    private void lblCrFocusLost(java.awt.event.FocusEvent evt) {}
    private void lblLambdaFocusGained(java.awt.event.FocusEvent evt) {}
    private void lblLambdaFocusLost(java.awt.event.FocusEvent evt) {}
    private void lblCi1FocusGained(java.awt.event.FocusEvent evt) {}
    private void lblCi1FocusLost(java.awt.event.FocusEvent evt) {}
    private void lblCr1FocusGained(java.awt.event.FocusEvent evt) {}
    private void lblCr1FocusLost(java.awt.event.FocusEvent evt) {}
    private void lblLambda1FocusGained(java.awt.event.FocusEvent evt) {}
    private void lblLambda1FocusLost(java.awt.event.FocusEvent evt) {}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField lblCi;
    private javax.swing.JTextField lblCi1;
    private javax.swing.JTextField lblCr;
    private javax.swing.JTextField lblCr1;
    private javax.swing.JTextField lblLambda;
    private javax.swing.JTextField lblLambda1;
    private javax.swing.JTable tablePerbandinganAlternatif;
    // End of variables declaration//GEN-END:variables
}
