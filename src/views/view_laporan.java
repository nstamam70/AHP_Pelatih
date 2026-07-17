/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

//import dao.AuditorDAO;
//import dao.KriteriaDAO;
//import dao.PerbandinganDAO;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import models.Auditor;
//import models.Kriteria;
//import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
//import utils.AHPCalculator;
//import utils.ReportGenerator;

/**
 *
 * @author USER
 */
public class view_laporan extends javax.swing.JPanel {

    /**
     * Creates new form view_dashboard
     */
    public view_laporan() {
        initComponents();
//        btnlaporanhasilahp.setText("Perbandingan Alternatif");
//        btnlaporandatakriteria.addActionListener(e -> ReportGenerator.showReport("/reports/report_kriteria.jrxml"));
//        btnlaporanperangkingan.addActionListener(e -> cetakLaporanPerangkingan());
//        btnlaporanhasilahp.addActionListener(e -> cetakLaporanPerbandinganAlternatif());
//        btnlaporandataauditor.addActionListener(e -> ReportGenerator.showReport("/reports/report_auditor.jrxml"));
    }
//
//    private void cetakLaporanPerangkingan() {
//        KriteriaDAO kriteriaDAO = new KriteriaDAO();
//        AuditorDAO auditorDAO = new AuditorDAO();
//        PerbandinganDAO perbandinganDAO = new PerbandinganDAO();
//
//        List<Kriteria> kriteriaList = kriteriaDAO.getAll();
//        List<Auditor> auditorList = auditorDAO.getAll();
//
//        if (kriteriaList.isEmpty() || auditorList.isEmpty()) {
//            javax.swing.JOptionPane.showMessageDialog(this, "Data kriteria atau auditor kosong!");
//            return;
//        }
//
//        int nKriteria = kriteriaList.size();
//        int nAlternatif = auditorList.size();
//
//        double[][] matriksKriteria = perbandinganDAO.buildMatriksKriteria(kriteriaList);
//        double[] bobotKriteria = AHPCalculator.hitungBobotPrioritas(matriksKriteria);
//
//        double[][] bobotAlternatif = new double[nKriteria][nAlternatif];
//        for (int k = 0; k < nKriteria; k++) {
//            int idKriteria = kriteriaList.get(k).getIdKriteria();
//            double[][] matriksAlt = perbandinganDAO.buildMatriksAlternatif(idKriteria, auditorList);
//            bobotAlternatif[k] = AHPCalculator.hitungBobotPrioritas(matriksAlt);
//        }
//
//        double[] nilaiAkhir = AHPCalculator.hitungNilaiAkhir(bobotKriteria, bobotAlternatif);
//        int[] rangking = AHPCalculator.getRangking(nilaiAkhir);
//
//        List<Map<String, ?>> dataList = new ArrayList<>();
//        for (int rank = 0; rank < rangking.length; rank++) {
//            int idx = rangking[rank];
//            Map<String, Object> row = new HashMap<>();
//            row.put("ranking", rank + 1);
//            row.put("kode_auditor", auditorList.get(idx).getKodeAuditor());
//            row.put("nama_auditor", auditorList.get(idx).getNamaAuditor());
//            row.put("nilai_akhir", nilaiAkhir[idx]);
//            row.put("persentase", String.format("%.1f%%", nilaiAkhir[idx] * 100));
//            dataList.add(row);
//        }
//
//        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataList);
//        ReportGenerator.showReportWithDataSource("/reports/report_perangkingan.jrxml", dataSource);
//    }
//
//    private void cetakLaporanPerbandinganAlternatif() {
//        KriteriaDAO kriteriaDAO = new KriteriaDAO();
//        AuditorDAO auditorDAO = new AuditorDAO();
//        PerbandinganDAO perbandinganDAO = new PerbandinganDAO();
//
//        List<Kriteria> kriteriaList = kriteriaDAO.getAll();
//        List<Auditor> auditorList = auditorDAO.getAll();
//
//        if (kriteriaList.isEmpty() || auditorList.isEmpty()) {
//            javax.swing.JOptionPane.showMessageDialog(this, "Data kriteria atau auditor kosong!");
//            return;
//        }
//
//        int nKriteria = kriteriaList.size();
//        int nAlternatif = auditorList.size();
//
//        // Hitung bobot prioritas alternatif per kriteria
//        double[][] bobotAlternatif = new double[nKriteria][nAlternatif];
//        for (int k = 0; k < nKriteria; k++) {
//            double[][] matriks = perbandinganDAO.buildMatriksAlternatif(kriteriaList.get(k).getIdKriteria(), auditorList);
//            bobotAlternatif[k] = AHPCalculator.hitungBobotPrioritas(matriks);
//        }
//
//        // Header kolom = nama kriteria
//        Map<String, Object> params = new HashMap<>();
//        for (int k = 0; k < nKriteria && k < 5; k++) {
//            params.put("col" + (k + 1) + "_header", kriteriaList.get(k).getNamaKriteria());
//        }
//
//        // Baris = alternatif, kolom = bobot per kriteria
//        List<Map<String, ?>> dataList = new ArrayList<>();
//        for (int i = 0; i < nAlternatif; i++) {
//            Map<String, Object> row = new HashMap<>();
//            row.put("alternatif", auditorList.get(i).getNamaAuditor());
//            for (int k = 0; k < nKriteria && k < 5; k++) {
//                row.put("col" + (k + 1), String.format("%.4f", bobotAlternatif[k][i]));
//            }
//            dataList.add(row);
//        }
//
//        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataList);
//        ReportGenerator.showReportWithDataSource("/reports/report_perbandinganAlternatif.jrxml", params, dataSource);
//    }

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
        jPanel2 = new javax.swing.JPanel();
        btnlaporandatakriteria = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnlaporandataauditor = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnlaporanhasilahp = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnlaporanperangkingan = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data Laporan");

        btnlaporandatakriteria.setBackground(new java.awt.Color(204, 204, 204));
        btnlaporandatakriteria.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnlaporandatakriteria.setText("Laporan Data Kriteria");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporandatakriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporandatakriteria, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnlaporandataauditor.setBackground(new java.awt.Color(204, 204, 204));
        btnlaporandataauditor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnlaporandataauditor.setText("Laporan Data Auditor");
        btnlaporandataauditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlaporandataauditorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporandataauditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporandataauditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnlaporanhasilahp.setBackground(new java.awt.Color(204, 204, 204));
        btnlaporanhasilahp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnlaporanhasilahp.setText("Hasil Proses AHP");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporanhasilahp, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporanhasilahp, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnlaporanperangkingan.setBackground(new java.awt.Color(204, 204, 204));
        btnlaporanperangkingan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnlaporanperangkingan.setText("Perangkingan");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporanperangkingan, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlaporanperangkingan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(502, 502, 502))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(117, 117, 117))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnlaporandataauditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlaporandataauditorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnlaporandataauditorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlaporandataauditor;
    private javax.swing.JButton btnlaporandatakriteria;
    private javax.swing.JButton btnlaporanhasilahp;
    private javax.swing.JButton btnlaporanperangkingan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
