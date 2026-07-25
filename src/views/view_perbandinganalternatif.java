/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import dao.PelatihDAO;
import dao.PerbandinganAlternatifDAO;
import dao.SubKriteriaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Pelatih;
import models.SubKriteria;

/**
 *
 * @author USER
 */
public class view_perbandinganalternatif extends javax.swing.JPanel {

    private PerbandinganAlternatifDAO perbandinganDAO;
    private List<Pelatih> pelatihList;
    private List<SubKriteria> subList;
    private java.util.ArrayList<String> allSubItems = new java.util.ArrayList<>();
    private java.util.List<int[]> pairIndices = new java.util.ArrayList<>();
    private boolean isFiltering = false;
    private boolean listMode = true;

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

    public view_perbandinganalternatif() {
        initComponents();
        perbandinganDAO = new PerbandinganAlternatifDAO();

        loadData();
        refreshTabel();

        tnama.addActionListener(e -> refreshTabel());

        // ponytail: click cell to input value directly
        tblmatriksperbandinganalternatif.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleTableClick(e);
            }
        });
    }

    private void loadData() {
        SubKriteriaDAO subDAO = new SubKriteriaDAO();
        PelatihDAO pelatihDAO = new PelatihDAO();

        subList = subDAO.getAll();
        pelatihList = pelatihDAO.getAll();

        allSubItems.clear();
        tnama.removeAllItems();
        for (SubKriteria sub : subList) {
            String label = sub.getKodeSub() + " - " + sub.getNamaSub();
            allSubItems.add(label);
            tnama.addItem(label);
        }

        setupAutoComplete(tnama, allSubItems);
    }

    private void setupAutoComplete(javax.swing.JComboBox<String> comboBox, java.util.ArrayList<String> allItems) {
        comboBox.setEditable(true);
        javax.swing.JTextField editor = (javax.swing.JTextField) comboBox.getEditor().getEditorComponent();
        editor.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("control A"), "select-all");
        editor.getActionMap().put("select-all", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                editor.selectAll();
            }
        });
        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    if (comboBox.getItemCount() > 0) {
                        comboBox.setSelectedIndex(0);
                    }
                    comboBox.hidePopup();
                    return;
                }
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN
                        || e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    return;
                }
                String text = editor.getText();
                isFiltering = true;
                comboBox.removeAllItems();
                for (String item : allItems) {
                    if (item.toLowerCase().contains(text.toLowerCase())) {
                        comboBox.addItem(item);
                    }
                }
                isFiltering = false;
                editor.setText(text);
                if (comboBox.getItemCount() > 0) {
                    comboBox.showPopup();
                }
            }
        });
    }

    private SubKriteria getSelectedSubKriteria() {
        Object selected = tnama.getSelectedItem();
        if (selected == null || selected.toString().isEmpty()) return null;
        String kode = selected.toString().split(" - ")[0].trim();
        for (SubKriteria sub : subList) {
            if (sub.getKodeSub().equals(kode)) return sub;
        }
        return null;
    }

    private void handleTableClick(java.awt.event.MouseEvent e) {
        int row = tblmatriksperbandinganalternatif.rowAtPoint(e.getPoint());
        int col = tblmatriksperbandinganalternatif.columnAtPoint(e.getPoint());
        SubKriteria sub = getSelectedSubKriteria();
        if (sub == null || pelatihList == null) return;

        Pelatih p1, p2;
        if (listMode) {
            if (row < 0 || row >= pairIndices.size()) return;
            int[] pair = pairIndices.get(row);
            p1 = pelatihList.get(pair[0]);
            p2 = pelatihList.get(pair[1]);
        } else {
            if (row < 0 || col <= 0 || row == col - 1) return;
            if (pelatihList.size() <= row || pelatihList.size() <= col - 1) return;
            p1 = pelatihList.get(row);
            p2 = pelatihList.get(col - 1);
        }

        String title = p1.getNamaPelatih() + " vs " + p2.getNamaPelatih();
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

        boolean ok = perbandinganDAO.simpan(sub.getIdSub(), p1.getIdPelatih(), p2.getIdPelatih(), nilai);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan perbandingan!");
        }
        refreshTabel();
    }

    private void refreshTabel() {
        if (isFiltering) return;
        SubKriteria sub = getSelectedSubKriteria();
        if (sub == null || pelatihList == null || pelatihList.isEmpty()) {
            tblmatriksperbandinganalternatif.setModel(new DefaultTableModel());
            jLabel2.setText("Perbandingan Alternatif");
            return;
        }

        int idSub = sub.getIdSub();
        int n = pelatihList.size();

        if (listMode) {
            refreshListView(idSub, n);
        } else {
            refreshMatrixView(idSub, n);
        }
    }

    private void refreshListView(int idSub, int n) {
        int totalPairs = n * (n - 1) / 2;
        String[] header = {"No", "Pelatih 1", "Pelatih 2", "Nilai"};
        pairIndices.clear();
        Object[][] data = new Object[totalPairs][4];
        int row = 0;
        int terisi = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double val = perbandinganDAO.getNilai(idSub,
                        pelatihList.get(i).getIdPelatih(),
                        pelatihList.get(j).getIdPelatih());
                data[row][0] = row + 1;
                data[row][1] = pelatihList.get(i).getKodePelatih() + " - " + pelatihList.get(i).getNamaPelatih();
                data[row][2] = pelatihList.get(j).getKodePelatih() + " - " + pelatihList.get(j).getNamaPelatih();
                data[row][3] = formatNilai(val);
                if (val != 0) terisi++;
                pairIndices.add(new int[]{i, j});
                row++;
            }
        }

        tblmatriksperbandinganalternatif.setModel(new DefaultTableModel(data, header));
        tblmatriksperbandinganalternatif.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblmatriksperbandinganalternatif.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblmatriksperbandinganalternatif.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblmatriksperbandinganalternatif.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblmatriksperbandinganalternatif.getColumnModel().getColumn(3).setPreferredWidth(100);
        jLabel2.setText("Terisi: " + terisi + " / " + totalPairs);
    }

    private void refreshMatrixView(int idSub, int n) {
        double[][] matriks = perbandinganDAO.buildMatriks(idSub, pelatihList);

        String[] header = new String[n + 1];
        header[0] = "Pelatih";
        for (int i = 0; i < n; i++) {
            header[i + 1] = pelatihList.get(i).getKodePelatih();
        }

        Object[][] data = new Object[n][n + 1];
        for (int i = 0; i < n; i++) {
            data[i][0] = pelatihList.get(i).getKodePelatih() + " - " + pelatihList.get(i).getNamaPelatih();
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    data[i][j + 1] = "1.000";
                } else {
                    data[i][j + 1] = formatNilai(matriks[i][j]);
                }
            }
        }

        tblmatriksperbandinganalternatif.setModel(new DefaultTableModel(data, header));
        tblmatriksperbandinganalternatif.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblmatriksperbandinganalternatif.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i <= n; i++) {
            tblmatriksperbandinganalternatif.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
        jLabel2.setText("Matriks Perbandingan (" + n + "x" + n + ")");
    }

    private String formatNilai(double val) {
        if (val == 0) return "Belum diisi";
        if (val < 1) return "1/" + Math.round(1.0 / val);
        return String.format("%.3f", val);
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
        jLabel6 = new javax.swing.JLabel();
        tnama = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmatriksperbandinganalternatif = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnToggleView = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Perbandingan Alternatif");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setText("Sub Kriteria");

        tnama.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        tblmatriksperbandinganalternatif.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblmatriksperbandinganalternatif);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Tabel Matriks Perbandingan Alternatif");

        btnToggleView.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnToggleView.setText("Tampilan Matriks");
        btnToggleView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToggleViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(451, 451, 451)
                                        .addComponent(jLabel1))))
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel6)
                                .addGap(86, 86, 86)
                                .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnToggleView)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnToggleView))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
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

    private void btnToggleViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleViewActionPerformed
        listMode = !listMode;
        btnToggleView.setText(listMode ? "Tampilan Matriks" : "Tampilan List");
        refreshTabel();
    }//GEN-LAST:event_btnToggleViewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnToggleView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblmatriksperbandinganalternatif;
    private javax.swing.JComboBox<String> tnama;
    // End of variables declaration//GEN-END:variables
}
