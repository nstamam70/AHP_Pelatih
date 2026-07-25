/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import auths.auth_login;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import views.vform.vform_datakriteria;
import views.vform.vform_datapelatih;
import views.vform.vform_datasubkriteria;
import views.view_dashboard;
import views.view_datakriteria;
import views.view_datapelatih;
import views.view_datasubkriteria;
import views.view_hasilrangking;
import views.view_laporan;
import views.view_perbandinganalternatif;
import views.view_perbandingankriteria;
import views.view_prosesahp;


/**
 *
 * @author Asus
 */
public class Main extends javax.swing.JFrame {

    private main_Menuitem selectedMenuItem = null;

    /**
     * Creates new form dashboard
     */
    public Main() {
        initComponents();
        execute();
    }


    private void execute() {
        //Icon Menu
        ImageIcon iconDashboard = new ImageIcon(getClass().getResource("/assets/icons8-dashboard-25.png"));
        ImageIcon iconMasterData = new ImageIcon(getClass().getResource("/assets/icons8-paper-25.png"));
        ImageIcon iconData = new ImageIcon(getClass().getResource("/assets/icons8-person-25.png"));
        ImageIcon iconProsesAHP = new ImageIcon(getClass().getResource("/assets/icons8-process-25.png"));
        ImageIcon iconPerbandingan = new ImageIcon(getClass().getResource("/assets/icons8-scales-25.png"));
        ImageIcon iconHasil = new ImageIcon(getClass().getResource("/assets/icons8-result-25.png"));
        ImageIcon iconReport = new ImageIcon(getClass().getResource("/assets/icons8-pdf-25.png"));
        ImageIcon iconKeluar = new ImageIcon(getClass().getResource("/assets/icons8-logout-25.png"));

        //Menu Utama
        main_Menuitem menuDashboard = new main_Menuitem(iconDashboard, false, null, "Dashboard", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_dashboard());
//                pn_utama.add(new DashboardFrame());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });
        main_Menuitem Keluar = new main_Menuitem(iconKeluar, false, null, "Keluar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Apakah Anda yakin ingin keluar dari aplikasi?",
                        "Konfirmasi Keluar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                }
            }
        });

        //SubKriteria 1
        main_Menuitem DataPelatih = new main_Menuitem(null, true, iconData, "Data Pelatih", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new vform_datapelatih());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });
        main_Menuitem Kriteria = new main_Menuitem(null, true, iconData, "Kriteria", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new vform_datakriteria());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });
        main_Menuitem subKriteria = new main_Menuitem(null, true, iconData, "Sub Kriteria", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new vform_datasubkriteria());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });
        
        //SubKriteria 2
        main_Menuitem PerbandinganKriteria = new main_Menuitem(null, true, iconPerbandingan, "Perbandingan Kriteria", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_perbandingankriteria());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });  
        main_Menuitem PerbandinganAlternatif = new main_Menuitem(null, true, iconPerbandingan, "Perbandingan Alternatif", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_perbandinganalternatif());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });        
        main_Menuitem ProsesAHP = new main_Menuitem(null, true, iconPerbandingan, "Proses AHP ", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_prosesahp());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });
        
        //SubKriteria 2
        main_Menuitem hasilAkhir = new main_Menuitem(null, true, iconHasil, "Hasil Perangkingan", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_hasilrangking());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });        
        main_Menuitem Laporan = new main_Menuitem(null, true, iconReport, "Laporan ", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn_utama.removeAll();
                pn_utama.add(new view_laporan());
                pn_utama.repaint();
                pn_utama.revalidate();
            }
        });

        main_Menuitem menuData = new main_Menuitem(iconMasterData, false, null, "Master Data", null, DataPelatih, Kriteria,subKriteria);
        main_Menuitem menuProsesAHP = new main_Menuitem(iconProsesAHP, false, null, "Perhitungan AHP", null, PerbandinganAlternatif,PerbandinganKriteria, ProsesAHP);
        main_Menuitem menuHasil = new main_Menuitem(iconHasil, false, null, "Hasil", null, hasilAkhir,Laporan);
        addMenu(menuDashboard, menuData,menuProsesAHP,  menuHasil, Keluar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pn_sidebar = new javax.swing.JPanel();
        pn_menu = new javax.swing.JPanel();
        pn_content = new javax.swing.JPanel();
        pn_utama = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_navbar.setBackground(new java.awt.Color(1, 30, 60));
        pn_navbar.setPreferredSize(new java.awt.Dimension(827, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SPK - AHP");

        javax.swing.GroupLayout pn_navbarLayout = new javax.swing.GroupLayout(pn_navbar);
        pn_navbar.setLayout(pn_navbarLayout);
        pn_navbarLayout.setHorizontalGroup(
            pn_navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_navbarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_navbarLayout.setVerticalGroup(
            pn_navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_navbarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_sidebar.setBackground(new java.awt.Color(204, 204, 204));
        pn_sidebar.setPreferredSize(new java.awt.Dimension(250, 419));

        pn_menu.setBackground(new java.awt.Color(1, 30, 60));
        pn_menu.setLayout(new javax.swing.BoxLayout(pn_menu, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout pn_sidebarLayout = new javax.swing.GroupLayout(pn_sidebar);
        pn_sidebar.setLayout(pn_sidebarLayout);
        pn_sidebarLayout.setHorizontalGroup(
            pn_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pn_sidebarLayout.setVerticalGroup(
            pn_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
        );

        pn_content.setBackground(new java.awt.Color(153, 153, 153));

        pn_utama.setBackground(new java.awt.Color(255, 255, 255));
        pn_utama.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pn_contentLayout = new javax.swing.GroupLayout(pn_content);
        pn_content.setLayout(pn_contentLayout);
        pn_contentLayout.setHorizontalGroup(
            pn_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_utama, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );
        pn_contentLayout.setVerticalGroup(
            pn_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_utama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pn_navbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(pn_sidebar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(pn_content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pn_sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE))
            .addComponent(pn_content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        pn_utama.add(new view_dashboard());
        pn_utama.repaint();
        pn_utama.revalidate();

    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new auth_login().setVisible(true);
                
              
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pn_content;
    private javax.swing.JPanel pn_menu;
    private javax.swing.JPanel pn_navbar;
    private javax.swing.JPanel pn_sidebar;
    public javax.swing.JPanel pn_utama;
    // End of variables declaration//GEN-END:variables

    private void addMenu(main_Menuitem... menu) {
        for (main_Menuitem menuItem : menu) {
            pn_menu.add(menuItem);

            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedMenuItem != null) {
                        selectedMenuItem.setBackground(new java.awt.Color(1,30,60)); 
                    }

                    menuItem.setBackground(new java.awt.Color(13, 76, 169)); 
                    selectedMenuItem = menuItem;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setCursor(Cursor.getDefaultCursor());
                }
            });

            ArrayList<main_Menuitem> subMenu = menuItem.getSubMenu();
            for (main_Menuitem m : subMenu) {
                addMenu(m);
            }
        }
        pn_menu.revalidate();
    }
}
