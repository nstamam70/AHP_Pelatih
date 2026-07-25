/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import dao.PerbandinganSubKriteriaDAO;
import dao.SubKriteriaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.SubKriteria;

/**
 *
 * @author USER
 */
public class view_perbandingankriteria extends javax.swing.JPanel {

    private PerbandinganSubKriteriaDAO perbandinganDAO;
    private List<SubKriteria> subList;

    // ponytail: nilai options reused by dialog, defined once
    private static final String[] NILAI_OPTIONS = {
        "1 - Sama Penting",
        "2 - Mendekati Sedikit Lebih Penting",
        "3 - Sedikit Lebih Penting",
        "4 - Mendekati Lebih Penting",
        "5 - Lebih Penting",
        "6 - Mendekati Sangat Penting",
        "7 - Sangat Penting",
        "8 - Mendekati Mutlak Lebih Penting",
        "9 - Mutlak Lebih Penting",
        "1/2 - Mendekati Sama Penting (kebalikan)",
        "1/3 - Sedikit Kurang Penting (kebalikan)",
        "1/4 - Mendekati Kurang Penting (kebalikan)",
        "1/5 - Kurang Penting (kebalikan)",
        "1/6 - Mendekati Sangat Kurang Penting (kebalikan)",
        "1/7 - Sangat Kurang Penting (kebalikan)",
        "1/8 - Mendekati Mutlak Kurang Penting (kebalikan)",
        "1/9 - Mutlak Kurang Penting (kebalikan)"
    };

    public view_perbandingankriteria() {
        initComponents();
        perbandinganDAO = new PerbandinganSubKriteriaDAO();

        // ponytail: hide old form inputs — all input now via table click
        jLabel3.setVisible(false);
        tkriteria1.setVisible(false);
        jLabel4.setVisible(false);
        tkriteria2.setVisible(false);
        jLabel5.setVisible(false);
        tnilai.setVisible(false);
        btnsimpan.setVisible(false);
        btnubah.setVisible(false);
        btnhapus.setVisible(false);
        btnbatal.setVisible(false);
        jSeparator3.setVisible(false);
        jSeparator4.setVisible(false);

        loadData();
        refreshTabel();

        tblmatriksperbandingankriteria.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleTableClick(e);
            }
        });
    }

    private void loadData() {
        SubKriteriaDAO subDAO = new SubKriteriaDAO();
        subList = subDAO.getAll();
    }

    private void handleTableClick(java.awt.event.MouseEvent e) {
        int row = tblmatriksperbandingankriteria.rowAtPoint(e.getPoint());
        int col = tblmatriksperbandingankriteria.columnAtPoint(e.getPoint());
        if (row < 0 || col <= 0) return;
        if (row == col - 1) return;

        if (subList == null || subList.size() <= row || subList.size() <= col - 1) return;

        SubKriteria s1 = subList.get(row);
        SubKriteria s2 = subList.get(col - 1);

        String title = s1.getNamaSub() + " vs " + s2.getNamaSub();
        Object result = JOptionPane.showInputDialog(this, title, "Pilih Nilai Perbandingan",
                JOptionPane.PLAIN_MESSAGE, null, NILAI_OPTIONS, NILAI_OPTIONS[0]);
        if (result == null) return;

        String angka = result.toString().split(" - ")[0].trim();
        double nilai;
        if (angka.startsWith("1/")) {
            nilai = 1.0 / Integer.parseInt(angka.substring(2));
        } else {
            nilai = Double.parseDouble(angka);
        }

        boolean ok = perbandinganDAO.simpan(s1.getIdSub(), s2.getIdSub(), nilai);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan perbandingan!");
        }
        refreshTabel();
    }

    private void refreshTabel() {
        if (subList == null || subList.isEmpty()) return;

        int n = subList.size();
        double[][] matriks = perbandinganDAO.buildMatriks(subList);

        String[] header = new String[n + 1];
        header[0] = "SubKriteria";
        for (int i = 0; i < n; i++) {
            header[i + 1] = subList.get(i).getKodeSub();
        }

        Object[][] data = new Object[n][n + 1];
        for (int i = 0; i < n; i++) {
            data[i][0] = subList.get(i).getKodeSub() + " - " + subList.get(i).getNamaSub();
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    data[i][j + 1] = "1.000";
                } else {
                    double val = matriks[i][j];
                    if (val < 1 && val > 0) {
                        int denom = (int) Math.round(1.0 / val);
                        data[i][j + 1] = "1/" + denom;
                    } else {
                        data[i][j + 1] = String.format("%.3f", val);
                    }
                }
            }
        }
        tblmatriksperbandingankriteria.setModel(new DefaultTableModel(data, header));
        tblmatriksperbandingankriteria.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblmatriksperbandingankriteria.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i <= n; i++) {
            tblmatriksperbandingankriteria.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tkriteria1 = new javax.swing.JComboBox<>();
        tkriteria2 = new javax.swing.JComboBox<>();
        tnilai = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        btnsimpan = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblmatriksperbandingankriteria = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Perbandingan Kriteria");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Kriteria 1");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setText("Kriteria 2");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Nilai");

        tkriteria1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        tkriteria2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        tnilai.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        btnsimpan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnsimpan.setText("Simpan");

        btnubah.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnubah.setText("Ubah");

        btnhapus.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnhapus.setText("Hapus");

        btnbatal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnbatal.setText("Batal");

        tblmatriksperbandingankriteria.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblmatriksperbandingankriteria);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Tabel Matriks Perbandingan Kriteria");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(btnsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnubah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tkriteria1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tkriteria2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tnilai, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(457, 457, 457)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tkriteria1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tkriteria2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tnilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btnubah)
                    .addComponent(btnhapus)
                    .addComponent(btnbatal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnubah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tblmatriksperbandingankriteria;
    private javax.swing.JComboBox<String> tkriteria1;
    private javax.swing.JComboBox<String> tkriteria2;
    private javax.swing.JComboBox<String> tnilai;
    // End of variables declaration//GEN-END:variables
}
