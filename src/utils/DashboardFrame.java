/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author USER
 */




/**
 * Dashboard SPK - Java Swing
 * Cara pakai di NetBeans:
 * 1. Buat New Project > Java Application (misal nama: DashboardSPK)
 * 2. Replace isi class Main / buat file baru "DashboardFrame.java" lalu paste kode ini
 * 3. Buat file "Main.java" terpisah berisi method main yang memanggil DashboardFrame
 *    (atau langsung jalankan method main di bawah ini)
 * 4. Run Project (F6)
 */
public class DashboardFrame extends JFrame {

    private static final Color BG = new Color(0xF2, 0xF4, 0xF8);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BLUE = new Color(0x2F, 0x6F, 0xED);
    private static final Color TEXT_DARK = new Color(0x1F, 0x29, 0x37);
    private static final Color TEXT_GRAY = new Color(0x8A, 0x93, 0xA3);

    public DashboardFrame() {
        setTitle("Dashboard SPK - AHP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setBackground(BG);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(title);
        root.add(Box.createRigidArea(new Dimension(0, 15)));

        // Row 1: 4 stat cards
        JPanel statRow = new JPanel(new GridLayout(1, 4, 15, 0));
        statRow.setOpaque(false);
        statRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        statRow.add(buildStatCard("\uD83D\uDC64", "Total Pelatih", "35", "Orang", new Color(0x2E, 0xC4, 0x8E)));
        statRow.add(buildStatCard("\uD83D\uDD12", "Total Kriteria", "7", "Kriteria", new Color(0x2F, 0x6F, 0xED)));
        statRow.add(buildStatCard("\uD83D\uDCC5", "Periode Aktif", "2024 / Genap", "", new Color(0x9B, 0x59, 0xD6)));
        statRow.add(buildStatCard("\uD83D\uDCCB", "Total Penilaian", "12", "Penilaian", new Color(0x2F, 0x6F, 0xED)));

        root.add(statRow);
        root.add(Box.createRigidArea(new Dimension(0, 15)));

        // Row 2: donut chart card + ranking card
        JPanel chartRow = new JPanel(new GridLayout(1, 2, 15, 0));
        chartRow.setOpaque(false);
        chartRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        Map<String, Double> bobot = new LinkedHashMap<>();
        bobot.put("Kompetensi Materi", 25.0);
        bobot.put("Metode Penyampaian", 20.0);
        bobot.put("Komunikasi", 15.0);
        bobot.put("Pengelolaan Kelas", 15.0);
        bobot.put("Profesionalisme", 10.0);
        bobot.put("Evaluasi Pembelajaran", 10.0);
        bobot.put("Kepuasan Peserta", 5.0);

        Color[] donutColors = {
            new Color(0x2F, 0x6F, 0xED), new Color(0xE8, 0x7E, 0x2F),
            new Color(0x8C, 0xC6, 0x3F), new Color(0x2E, 0xC4, 0x8E),
            new Color(0xF2, 0xC0, 0x3E), new Color(0x4F, 0xC3, 0xE0),
            new Color(0xE0, 0x5C, 0x4B)
        };

        chartRow.add(buildDonutCard("Bobot Kriteria", bobot, donutColors));

        Map<String, Double> ranking = new LinkedHashMap<>();
        ranking.put("1. Andi Wijoyo", 0.923);
        ranking.put("2. Budi Santoso", 0.891);
        ranking.put("3. Citra Lestari", 0.872);
        ranking.put("4. Dedi Kurniawan", 0.845);
        ranking.put("5. Eka Pratama", 0.812);

        chartRow.add(buildRankingCard("Top 5 Ranking Pelatih", ranking));

        root.add(chartRow);

        setContentPane(root);
    }

    private JPanel buildStatCard(String icon, String label, String value, String unit, Color iconColor) {
        JPanel card = new RoundedPanel(16, CARD_BG);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        top.setOpaque(false);

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setOpaque(true);
        iconLbl.setBackground(new Color(iconColor.getRed(), iconColor.getGreen(), iconColor.getBlue(), 35));
        iconLbl.setForeground(iconColor);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLbl.setPreferredSize(new Dimension(32, 32));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelLbl.setForeground(TEXT_GRAY);

        top.add(iconLbl);
        top.add(labelLbl);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(8, 4, 0, 0));

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        valueLbl.setForeground(TEXT_DARK);

        JLabel unitLbl = new JLabel(unit);
        unitLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        unitLbl.setForeground(TEXT_GRAY);

        bottom.add(valueLbl);
        if (!unit.isEmpty()) bottom.add(unitLbl);

        card.add(top, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildDonutCard(String title, Map<String, Double> data, Color[] colors) {
        JPanel card = new RoundedPanel(16, CARD_BG);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLbl.setForeground(TEXT_DARK);
        card.add(titleLbl, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(new DonutChartPanel(data, colors), BorderLayout.CENTER);

        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBorder(new EmptyBorder(0, 10, 0, 0));
        int i = 0;
        for (Map.Entry<String, Double> e : data.entrySet()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
            row.setOpaque(false);
            JLabel dot = new JLabel("\u25A0");
            dot.setForeground(colors[i % colors.length]);
            JLabel text = new JLabel(e.getKey() + "  " + e.getValue().intValue() + "%");
            text.setFont(new Font("SansSerif", Font.PLAIN, 12));
            text.setForeground(TEXT_DARK);
            row.add(dot);
            row.add(text);
            legend.add(row);
            i++;
        }
        content.add(legend, BorderLayout.EAST);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildRankingCard(String title, Map<String, Double> data) {
        JPanel card = new RoundedPanel(16, CARD_BG);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLbl.setForeground(TEXT_DARK);
        card.add(titleLbl, BorderLayout.NORTH);

        card.add(new BarChartPanel(data), BorderLayout.CENTER);
        return card;
    }

    // ---- Rounded panel with subtle shadow look ----
    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;

        RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 18));
            g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, radius, radius);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 6, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---- Donut chart ----
    static class DonutChartPanel extends JPanel {
        private final Map<String, Double> data;
        private final Color[] colors;

        DonutChartPanel(Map<String, Double> data, Color[] colors) {
            this.data = data;
            this.colors = colors;
            setOpaque(false);
            setPreferredSize(new Dimension(200, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            double start = 90;
            int i = 0;
            for (Map.Entry<String, Double> e : data.entrySet()) {
                double angle = e.getValue() / 100.0 * 360.0;
                g2.setColor(colors[i % colors.length]);
                Arc2D arc = new Arc2D.Double(x, y, size, size, start, -angle, Arc2D.PIE);
                g2.fill(arc);
                start -= angle;
                i++;
            }
            // inner hole to make donut
            int holeSize = (int) (size * 0.55);
            int hx = x + (size - holeSize) / 2;
            int hy = y + (size - holeSize) / 2;
            g2.setColor(CARD_BG);
            g2.fillOval(hx, hy, holeSize, holeSize);
            g2.dispose();
        }
    }

    // ---- Horizontal bar chart ----
    static class BarChartPanel extends JPanel {
        private final Map<String, Double> data;

        BarChartPanel(Map<String, Double> data) {
            this.data = data;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));

            int n = data.size();
            int padTop = 10, padBottom = 25, padLeft = 130, padRight = 50;
            int chartW = getWidth() - padLeft - padRight;
            int chartH = getHeight() - padTop - padBottom;
            int rowH = chartH / n;

            double maxVal = 1.0;
            int i = 0;
            for (Map.Entry<String, Double> e : data.entrySet()) {
                int rowY = padTop + i * rowH;
                String label = e.getKey();
                double val = e.getValue();
                int barW = (int) (chartW * (val / maxVal));

                g2.setColor(TEXT_DARK);
                g2.drawString(label, 0, rowY + rowH / 2 + 5);

                g2.setColor(BLUE);
                int barH = Math.max(10, rowH - 14);
                g2.fillRoundRect(padLeft, rowY + (rowH - barH) / 2, barW, barH, 6, 6);

                g2.setColor(TEXT_DARK);
                g2.drawString(String.valueOf(val), padLeft + barW + 8, rowY + rowH / 2 + 5);
                i++;
            }

            // x axis
            int axisY = padTop + chartH + 5;
            g2.setColor(TEXT_GRAY);
            g2.drawLine(padLeft, axisY, padLeft + chartW, axisY);
            for (double v = 0; v <= 1.0; v += 0.5) {
                int xPos = padLeft + (int) (chartW * v);
                g2.drawString(String.valueOf(v), xPos - 8, axisY + 15);
            }
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));
    }
}