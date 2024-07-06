/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package modul;

import java.sql.*;
import com.toedter.calendar.JYearChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import modul.dashboard;
import config.db;
import config.helpers;

/**
 *
 * @author itdev
 */
public class gaji extends javax.swing.JFrame {
    Connection conn = db.getConnection();
    public helpers helpers;
    /**
     * Creates new form jabatan
     */
    public gaji() {
        initComponents();
        tombolNormal();
        dataComboPegawai();
        datatables();
    }

    private void tombolNormal(){
        //btn_tambah.setEnabled(true);
        textArea.setEnabled(true);
        //btn_tambah.setText("Tambah");
        proses_btn.setText("Proses...");
        datapegawai.setEnabled(true);
        bulan.setEnabled(true);
        tahun2.setEnabled(true);
        btn_cek.setEnabled(true);
        btn_simpan.setEnabled(false);
        btn_batal.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_ubah.setEnabled(false);
    }

    private void bersih(){
        datapegawai.setSelectedItem("-");
        gaji.setText("0");
        textArea.setText("");
        tunjangan.setText("0");
        //lembur.setText("0");
        //total_masuk.setText("0");
        pinjam.setText("0");
        takehome.setText("0");
    }
    
    public void datatables(){
        Object header[]={"No","NIP","Nama","Periode","Gaji Pokok","Tunjangan","Pinjaman","Gaji Bersih"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tabelGpegawai.setModel(data);

        tabelGpegawai.getColumnModel().getColumn(0).setPreferredWidth(15);  // No
        tabelGpegawai.getColumnModel().getColumn(1).setPreferredWidth(20);  // NIP
        tabelGpegawai.getColumnModel().getColumn(3).setPreferredWidth(20);  // MASUK
        tabelGpegawai.getColumnModel().getColumn(4).setPreferredWidth(20);  // MASUK
        tabelGpegawai.getColumnModel().getColumn(5).setPreferredWidth(20);  // LULUS
        tabelGpegawai.getColumnModel().getColumn(6).setPreferredWidth(20);  // GELAR
        tabelGpegawai.getColumnModel().getColumn(7).setPreferredWidth(20);  // GELAR

        String sql = "SELECT a.nip,b.nama_lengkap,a.bulan,a.tahun,a.gaji_pokok,a.tunjangan,a.pinjaman,a.gaji_bersih FROM daftar_gaji a join data_pegawai b on b.nip=a.nip order by a.id desc";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            int no = 1; 
            while (rs.next())
            {
                String kolom1 = String.valueOf(no);  // Nomor urut
                String kolom2 = rs.getString("nip");
                String kolom3 = rs.getString("nama_lengkap");
                String kolom4 = rs.getString("bulan")+"-"+rs.getString("tahun");

                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                int gajiValue = rs.getInt("gaji_pokok");
                String formattedGaji = numberFormat.format(gajiValue);
                String kolom5 = formattedGaji;
                int tunjanganValue = rs.getInt("tunjangan");
                String formattedTunjangan = numberFormat.format(tunjanganValue);
                String kolom6 = formattedTunjangan;
                int pinjamanValue = rs.getInt("pinjaman");
                String formattedPinjaman = numberFormat.format(pinjamanValue);
                String kolom7 = formattedPinjaman;
                int gbValue = rs.getInt("gaji_bersih");
                String formattedGB = numberFormat.format(gbValue);
                String kolom8 = formattedGB;
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,};
                data.addRow(kolom);
                no++; 
            }
            System.out.println("Sukses terhubung ke database.");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database. " + e.getMessage());
        }
    }

    public void dataComboPegawai()
    {
        String sql = "SELECT nip, nama_lengkap FROM data_pegawai";
            datapegawai.addItem("-");
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next())
            {
                datapegawai.addItem(rs.getString("nip")+"-"+rs.getString("nama_lengkap"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
            System.out.println("Sukses terhubung ke combo pegawai.");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke combo pegawai. " + e.getMessage());
        }
    }

    private void simpan(){
        String sql="select count(*) from daftar_gaji WHERE nip = ? and bulan = ? and tahun = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");
            String nip = parts[0];
            int select_bulan = bulan.getMonth() + 1;
            int select_tahun = tahun2.getYear();

            stmt.setString(1, nip);
            stmt.setInt(2, select_bulan);
            stmt.setInt(3, select_tahun);

            //System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Data sudah ada untuk NIP: " + nip + " pada bulan: " + select_bulan + " tahun: " + select_tahun, "", JOptionPane.WARNING_MESSAGE);
            } else {
                String sql1 = "INSERT INTO daftar_gaji(nip, bulan, tahun, gaji_pokok, tunjangan, pinjaman, gaji_bersih) VALUES (?,?,?,?,?,?,?)";
                
                PreparedStatement stmt1 = conn.prepareStatement(sql1);
                
                stmt1.setString(1, nip);
                stmt1.setInt(2, select_bulan);
                stmt1.setInt(3, select_tahun);
                
                // hapus format ke int
                NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
                Number gajiNumber = format.parse(gaji.getText());
                Number tunjanganNumber = format.parse(tunjangan.getText());
                Number pinjamNumber = format.parse(pinjam.getText());
            
                int gaji = gajiNumber.intValue();
                int tunjangan = tunjanganNumber.intValue();
                int pinjam = pinjamNumber.intValue();

                // Lakukan operasi aritmatika
                int total = gaji + tunjangan - pinjam;                
                
                stmt1.setInt(4, gaji);
                stmt1.setInt(5, tunjangan);
                stmt1.setInt(6, pinjam);
                stmt1.setInt(7, total);
                
                // Execute update
                int rowsInserted1 = stmt1.executeUpdate();
                // Check if insert was successful
                if (rowsInserted1 > 0) {
                    JOptionPane.showMessageDialog(null, "Simpan Gaji Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Simpan Gaji Gagal", "", JOptionPane.WARNING_MESSAGE);
                }

            }
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan "+e);
        }
        datatables();
    }

    private void edit(){
        String sql = "UPDATE daftar_gaji SET gaji_pokok = ?, tunjangan = ?, pinjaman = ?, gaji_bersih= ? WHERE nip = ? and bulan = ? and tahun = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            Number gajiNumber = format.parse(gaji.getText());
            Number tunjanganNumber = format.parse(tunjangan.getText());
            Number pinjamNumber = format.parse(pinjam.getText());

            int gaji = gajiNumber.intValue();
            int tunjangan = tunjanganNumber.intValue();
            int pinjam = pinjamNumber.intValue();

            // Lakukan operasi aritmatika
            int total = gaji + tunjangan - pinjam;                
            stmt.setInt(1, gaji);
            stmt.setInt(2, tunjangan);
            stmt.setInt(3, pinjam);
            stmt.setInt(4, total);

            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");
            String nip = parts[0];

            stmt.setString(5, nip);
            int select_bulan = bulan.getMonth() + 1;
            int select_tahun = tahun2.getYear();
            stmt.setInt(6, select_bulan);
            stmt.setInt(7, select_tahun);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Edit Data Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "", JOptionPane.WARNING_MESSAGE);
            }
            //btn_tambah.setText("Tambah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah " + e.getMessage());
        }
        datatables();
    }

    private void hapus(){
        String sql="delete from daftar_gaji WHERE nip = ? and bulan = ? and tahun = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");

            String nip = parts[0];
            int select_bulan = bulan.getMonth() + 1;
            int select_tahun = tahun2.getYear();

            stmt.setString(1, nip);
            stmt.setInt(2, select_bulan);
            stmt.setInt(3, select_tahun);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah " + e.getMessage());
        }
        datatables();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_home = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        datapegawai = new javax.swing.JComboBox<>();
        tahun2 = new com.toedter.calendar.JYearChooser();
        bulan = new com.toedter.calendar.JMonthChooser();
        btn_cek = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        gaji = new javax.swing.JFormattedTextField();
        tunjangan = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pinjam = new javax.swing.JFormattedTextField();
        terbilang_angka = new javax.swing.JLabel();
        btnHitung = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        takehome = new javax.swing.JFormattedTextField();
        proses_btn = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelGpegawai = new javax.swing.JTable();
        text_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(900, 900));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel2.setText("Data  Pegawai");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel3.setText("Bulan");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel4.setText("Tahun");

        btn_simpan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(0, 153, 102));
        btn_simpan.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/floppy_4283060_24.png")); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_ubah.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(204, 204, 0));
        btn_ubah.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/edit_8832466_24.png")); // NOI18N
        btn_ubah.setText("Edit");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(204, 0, 0));
        btn_hapus.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/garbage_10109917_24.png")); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel5.setText("Menu Gaji Kepegawaian");

        btn_home.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/building_13680375_24.png")); // NOI18N
        btn_home.setText("Dashboard");
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
        });
        btn_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_homeActionPerformed(evt);
            }
        });

        btn_batal.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(204, 0, 0));
        btn_batal.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/close_8832204_24.png")); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        datapegawai.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        datapegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datapegawaiActionPerformed(evt);
            }
        });

        btn_cek.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_cek.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/glass_11034892_24.png")); // NOI18N
        btn_cek.setText("Cek");
        btn_cek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cekActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pemasukan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 15))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabel6.setText("Gaji Pokok");

        jLabel7.setText("Tunjangan");

        gaji.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        gaji.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gaji.setText("0");
        gaji.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

        tunjangan.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        tunjangan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tunjangan.setText("0");
        tunjangan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(gaji, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(19, 19, 19)
                        .addComponent(tunjangan, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(gaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tunjangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengurangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 15))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 225));

        jLabel9.setText("Pinjaman");

        pinjam.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        pinjam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pinjam.setText("0");
        pinjam.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        terbilang_angka.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        terbilang_angka.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        terbilang_angka.setText("terbilang");

        btnHitung.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btnHitung.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/abacus-calculator_12335622_24.png")); // NOI18N
        btnHitung.setText("Hitung");
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        takehome.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        takehome.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        takehome.setText("0");
        takehome.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

        proses_btn.setText("Proses...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnHitung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(proses_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(95, 95, 95)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btn_simpan)
                                                .addGap(27, 27, 27)
                                                .addComponent(btn_batal)
                                                .addGap(30, 30, 30)
                                                .addComponent(btn_ubah)
                                                .addGap(41, 41, 41)
                                                .addComponent(btn_hapus))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(terbilang_angka, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(6, 6, 6)
                                                            .addComponent(takehome))
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 27, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_home)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tahun2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bulan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(datapegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cek)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_home))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(datapegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(bulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tahun2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cek))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHitung)
                            .addComponent(takehome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(terbilang_angka))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_batal)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus)
                    .addComponent(proses_btn))
                .addGap(0, 53, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 870, 450));

        tabelGpegawai.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tabelGpegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "5", "thLulus", "7"
            }
        ));
        tabelGpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelGpegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelGpegawai);
        if (tabelGpegawai.getColumnModel().getColumnCount() > 0) {
            tabelGpegawai.getColumnModel().getColumn(0).setPreferredWidth(25);
            tabelGpegawai.getColumnModel().getColumn(4).setPreferredWidth(30);
            tabelGpegawai.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        text_cari.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        text_cari.setToolTipText("Cari Jabatan");

        btn_cari.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_cari.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/glass_11034892_24.png")); // NOI18N
        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        btn_refresh.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/circular_11417901_24.png")); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(text_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 870, 340));

        jLabel1.setBackground(new java.awt.Color(125, 194, 189));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/background.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 850));

        setSize(new java.awt.Dimension(897, 880));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ? Pastikan NIP, Bulan dan Tahun sesuai!","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(pesan==JOptionPane.YES_OPTION){
            if(pesan==JOptionPane.YES_OPTION){
                hapus();
                bersih();
//                siapIsi(false);
                tombolNormal();
            } else{
                JOptionPane.showMessageDialog(null, "HAPUS DATA GAGAL :(");
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        if(text_cari.getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "Inputan tidak boleh kosong!!!","",JOptionPane.INFORMATION_MESSAGE);
            datatables();
        } else{
            Object header[]={"No","NIP","Nama","Periode","Gaji Pokok","Tunjangan","Pinjaman","Gaji Bersih"};
            DefaultTableModel data = new DefaultTableModel(null,header);
            tabelGpegawai.setModel(data);
            
            tabelGpegawai.getColumnModel().getColumn(0).setPreferredWidth(15);  // No
            tabelGpegawai.getColumnModel().getColumn(1).setPreferredWidth(20);  // NIP
            tabelGpegawai.getColumnModel().getColumn(3).setPreferredWidth(20);  // MASUK
            tabelGpegawai.getColumnModel().getColumn(4).setPreferredWidth(20);  // MASUK
            tabelGpegawai.getColumnModel().getColumn(5).setPreferredWidth(20);  // LULUS
            tabelGpegawai.getColumnModel().getColumn(6).setPreferredWidth(20);  // GELAR
            tabelGpegawai.getColumnModel().getColumn(7).setPreferredWidth(20);  // GELAR

            String sql = "SELECT a.nip,b.nama_lengkap,a.bulan,a.tahun,a.gaji_pokok,a.tunjangan,a.pinjaman,a.gaji_bersih FROM daftar_gaji a join data_pegawai b on b.nip=a.nip where b.nama_lengkap like ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + text_cari.getText() + "%");
                //System.out.println(sql);
                ResultSet rs= stmt.executeQuery();
                int no = 1; 
                while (rs.next())
                {
                    String kolom1 = String.valueOf(no);  // Nomor urut
                    String kolom2 = rs.getString("nip");
                    String kolom3 = rs.getString("nama_lengkap");
                    String kolom4 = rs.getString("bulan")+"-"+rs.getString("tahun");

                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    int gajiValue = rs.getInt("gaji_pokok");
                    String formattedGaji = numberFormat.format(gajiValue);
                    String kolom5 = formattedGaji;
                    int tunjanganValue = rs.getInt("tunjangan");
                    String formattedTunjangan = numberFormat.format(tunjanganValue);
                    String kolom6 = formattedTunjangan;
                    int pinjamanValue = rs.getInt("pinjaman");
                    String formattedPinjaman = numberFormat.format(pinjamanValue);
                    String kolom7 = formattedPinjaman;
                    int gbValue = rs.getInt("gaji_bersih");
                    String formattedGB = numberFormat.format(gbValue);
                    String kolom8 = formattedGB;

                    String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,};
                    data.addRow(kolom);
                    no++; 
                }
                System.out.println("Suksesterhubung ke database. ");
            } catch (Exception e) {
                System.out.println("Gagal terhubung ke database. " + e.getMessage());
            }            
        }        
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        // TODO add your handling code here:
            dashboard a = new dashboard();
            this.dispose();
            a.setVisible(true);
    }//GEN-LAST:event_btn_homeMouseClicked

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        if(datapegawai.getSelectedItem().toString().isEmpty() || datapegawai.getSelectedItem() == "-"){
            JOptionPane.showMessageDialog(null, "Mohon Lengkapi Inputan Data!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{
            //System.out.println(btn_tambah.getText());
            //System.out.println(btn_ubah.getText());
            //System.out.println(btn_simpan.getText()); proses_btn
            if(proses_btn.getText().equalsIgnoreCase("proses tambah")){
                if(btn_simpan.getText().equalsIgnoreCase("simpan")){
                    System.out.println("Proses Simpan");
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(proses_btn.getText().equalsIgnoreCase("proses ubah") && btn_ubah.getText().equalsIgnoreCase("edit")){
                if(btn_simpan.getText().equalsIgnoreCase("simpan")){
                    System.out.println("Proses Edit");
                    edit();
                } else{
                    JOptionPane.showMessageDialog(null, "EDIT DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            bersih();
            //btn_tambah.setText("Tambah");
            btn_ubah.setText("Edit");
            proses_btn.setText("Proses...");
            tombolNormal();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(btn_ubah.getText().equalsIgnoreCase("edit")){
            proses_btn.setText("Proses Ubah");
            //btn_tambah.setText("");
            //btn_ubah.setText("Batal");
            datapegawai.setEnabled(false);
            //btn_tambah.setEnabled(false);
            btn_batal.setEnabled(true);
            btn_simpan.setEnabled(true);
            btn_hapus.setEnabled(false);
            btn_ubah.setEnabled(false);
        } else{
            //btn_ubah.setText("Edit");
            bersih();
//            siapIsi(false);
            tombolNormal();
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        datapegawai.setEnabled(true);
        bersih();
        tombolNormal();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void tabelGpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelGpegawaiMouseClicked
        // TODO add your handling code here:
        int baris = tabelGpegawai.getSelectedRow();
        String dp = tabelGpegawai.getModel().getValueAt(baris, 1).toString()+"-"+tabelGpegawai.getModel().getValueAt(baris, 2).toString();
        datapegawai.setSelectedItem(dp);
        
        String selectedItem = tabelGpegawai.getModel().getValueAt(baris, 3).toString();
        String[] parts = selectedItem.split("-");

        bulan.setMonth(Integer.parseInt(parts[0])-1);
        tahun2.setYear(Integer.parseInt(parts[1]));

        gaji.setText(tabelGpegawai.getModel().getValueAt(baris, 4).toString());
        tunjangan.setText(tabelGpegawai.getModel().getValueAt(baris, 5).toString());
        pinjam.setText(tabelGpegawai.getModel().getValueAt(baris, 6).toString());
        String gb = tabelGpegawai.getModel().getValueAt(baris, 7).toString();
        takehome.setText(gb);
        
        try {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            Number gajiNumber = format.parse(gb);
            
            int gajiTotal = gajiNumber.intValue();
            String nominal = helpers.terbilang(gajiTotal);
            terbilang_angka.setText(nominal);


        } catch (ParseException e) {
          System.out.println(e.getMessage());  
        }
        
        try {
            String pegawaiQuery = "SELECT * FROM data_pegawai WHERE nip = ?";
            PreparedStatement pegawaiStmt = conn.prepareStatement(pegawaiQuery);
            pegawaiStmt.setString(1, tabelGpegawai.getModel().getValueAt(baris, 1).toString());
            
            String to_text = "";
            ResultSet pegawaiRs = pegawaiStmt.executeQuery();
            if (pegawaiRs.next()) {
                    // Ambil data dari tabel data_pegawai dan tampilkan di JTextArea
                    to_text += "NIP\t: "+pegawaiRs.getString("nip")+"\n"
                                    +"Nama\t: "+pegawaiRs.getString("nama_lengkap")+"\n"
                                    +"Tempat Lahir\t: "+pegawaiRs.getString("tempat_lahir")+"\n"
                                    +"Tanggal Lahir\t: "+pegawaiRs.getString("tanggal_lahir")+"\n"
                                    +"Jenis Kelamin\t: "+pegawaiRs.getString("jenis_kelamin")+"\n"
                                    +"Status Kepegawaian\t:\n "+pegawaiRs.getString("status_kepegawaian")+"\n"
                                    +"Jabatan\t: "+pegawaiRs.getString("jabatan")+"\n";
               
            }
            String kehadiranQuery = "SELECT kehadiran, COUNT(id) as total FROM absensi_kehadiran WHERE nip = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ? GROUP BY kehadiran";
            PreparedStatement kehadiranStmt = conn.prepareStatement(kehadiranQuery);
            kehadiranStmt.setString(1, tabelGpegawai.getModel().getValueAt(baris, 1).toString());
            kehadiranStmt.setInt(2, Integer.parseInt(parts[0])-1);
            kehadiranStmt.setInt(3, Integer.parseInt(parts[1]));
            //System.out.println(kehadiranStmt);
            ResultSet rs_hadir = kehadiranStmt.executeQuery();

            to_text +="KEHADIRAN-----------\n";
            while (rs_hadir.next()) {
                    String kehadiran = rs_hadir.getString("kehadiran");
                    int total = rs_hadir.getInt("total");
                    to_text += kehadiran+"\t: "+total+"\n";
            }            
            textArea.setText(to_text);
        } catch (Exception e) {
            
        }
        
        datapegawai.setEnabled(false);
        bulan.setEnabled(false);
        tahun2.setEnabled(false);
        btn_cek.setEnabled(false);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_ubah.setEnabled(true);
    }//GEN-LAST:event_tabelGpegawaiMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        text_cari.setText("");
        datatables();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void datapegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datapegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datapegawaiActionPerformed

    private void btn_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cekActionPerformed
        // TODO add your handling code here:
        String sql="select count(*) from daftar_gaji WHERE nip = ? and bulan = ? and tahun = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");
            String nip = parts[0];
            int select_bulan = bulan.getMonth() + 1;
            int select_tahun = tahun2.getYear();

            stmt.setString(1, nip);
            stmt.setInt(2, select_bulan);
            stmt.setInt(3, select_tahun);

            //System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Data sudah ada untuk NIP: " + nip + " pada bulan: " + select_bulan + " tahun: " + select_tahun, "", JOptionPane.WARNING_MESSAGE);
            } else {
                String pegawaiQuery = "SELECT * FROM data_pegawai WHERE nip = ?";
                PreparedStatement pegawaiStmt = conn.prepareStatement(pegawaiQuery);
                pegawaiStmt.setString(1, nip);
                
                String to_text = "";
                ResultSet pegawaiRs = pegawaiStmt.executeQuery();
                if (pegawaiRs.next()) {
                    // Ambil data dari tabel data_pegawai dan tampilkan di JTextArea
                    to_text += "NIP\t: "+pegawaiRs.getString("nip")+"\n"
                            +"Nama\t: "+pegawaiRs.getString("nama_lengkap")+"\n"
                            +"Tempat Lahir\t: "+pegawaiRs.getString("tempat_lahir")+"\n"
                            +"Tanggal Lahir\t: "+pegawaiRs.getString("tanggal_lahir")+"\n"
                            +"Jenis Kelamin\t: "+pegawaiRs.getString("jenis_kelamin")+"\n"
                            +"Status Kepegawaian\t:\n "+pegawaiRs.getString("status_kepegawaian")+"\n"
                            +"Jabatan\t: "+pegawaiRs.getString("jabatan")+"\n";
                    
                    int gajiValue = pegawaiRs.getInt("gaji");
                    //NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    String formattedGaji = numberFormat.format(gajiValue);
                    gaji.setText(formattedGaji);
                    //gaji.setText(String.valueOf(pegawaiRs.getInt("gaji")));

                } else {
                    gaji.setText("0");
                }

                String pinjamanQuery = "SELECT sum(nominal) total FROM data_pinjaman WHERE nip = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ?";
                PreparedStatement pinjamanStmt = conn.prepareStatement(pinjamanQuery);
                pinjamanStmt.setString(1, nip);
                pinjamanStmt.setInt(2, select_bulan);
                pinjamanStmt.setInt(3, select_tahun);
                
                 ResultSet pinjamanRs = pinjamanStmt.executeQuery();
                if (pinjamanRs.next()) {
                    // Ambil data dari tabel data_pegawai dan tampilkan di JTextArea
                    int pinjamValue = pinjamanRs.getInt("total");
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    String formattedPinjaman = numberFormat.format(pinjamValue);
                    pinjam.setText(formattedPinjaman);
                    //pinjam.setText(String.valueOf(pinjamanRs.getInt("total")));
                } else {
                    pinjam.setText("0");
                }                

                String kehadiranQuery = "SELECT kehadiran, COUNT(id) as total FROM absensi_kehadiran WHERE nip = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ? GROUP BY kehadiran";
                PreparedStatement kehadiranStmt = conn.prepareStatement(kehadiranQuery);
                kehadiranStmt.setString(1, nip);
                kehadiranStmt.setInt(2, select_bulan);
                kehadiranStmt.setInt(3, select_tahun);
                //System.out.println(kehadiranStmt);
                ResultSet rs_hadir = kehadiranStmt.executeQuery();
            
                to_text +="KEHADIRAN-----------\n";
                while (rs_hadir.next()) {
                    String kehadiran = rs_hadir.getString("kehadiran");
                    int total = rs_hadir.getInt("total");
                    to_text += kehadiran+"\t: "+total+"\n";
                }
                
                textArea.setText(to_text);
                proses_btn.setText("Proses Tambah");
                //btn_tambah.setEnabled(false);
                btn_simpan.setEnabled(false);
                btn_batal.setEnabled(false);
                //JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah " + e.getMessage());
        }
    }//GEN-LAST:event_btn_cekActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        // TODO add your handling code here:
        int total = 0;
        try {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            Number gajiNumber = format.parse(gaji.getText());
            Number tunjanganNumber = format.parse(tunjangan.getText());
            Number pinjamNumber = format.parse(pinjam.getText());
            
            int gaji = gajiNumber.intValue();
            int tunjangan = tunjanganNumber.intValue();
            int pinjam = pinjamNumber.intValue();

            // Lakukan operasi aritmatika
            total = gaji + tunjangan - pinjam;
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah " + e.getMessage());
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        String formattedTotal = numberFormat.format(total);
        //int total_gaji = Integer.parseInt(gaji.getText())+ Integer.parseInt(tunjangan.getText()) - Integer.parseInt(pinjam.getText());
        
        String nominal = helpers.terbilang(total);
        takehome.setText(formattedTotal);
        //takehome.setText(Integer.toString(total_gaji));
        terbilang_angka.setText(nominal);
        
        //btn_tambah.setEnabled(false);
        btn_cek.setEnabled(false);
        datapegawai.setEnabled(false);
        bulan.setEnabled(false);
        tahun2.setEnabled(false);
        btn_simpan.setEnabled(true);
        btn_batal.setEnabled(true);
    }//GEN-LAST:event_btnHitungActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gaji().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cek;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private com.toedter.calendar.JMonthChooser bulan;
    private javax.swing.JComboBox<String> datapegawai;
    private javax.swing.JFormattedTextField gaji;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField pinjam;
    private javax.swing.JLabel proses_btn;
    private javax.swing.JTable tabelGpegawai;
    private com.toedter.calendar.JYearChooser tahun2;
    private javax.swing.JFormattedTextField takehome;
    private javax.swing.JLabel terbilang_angka;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField text_cari;
    private javax.swing.JFormattedTextField tunjangan;
    // End of variables declaration//GEN-END:variables
}
