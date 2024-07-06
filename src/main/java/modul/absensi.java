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
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import modul.dashboard;
import config.db;

/**
 *
 * @author itdev
 */
public class absensi extends javax.swing.JFrame {
    Connection conn = db.getConnection();
    /**
     * Creates new form jabatan
     */
    public absensi() {
        initComponents();
        tombolNormal();
        dataComboPegawai();
        datatables();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        try {
            Date parsedDate = dateFormat.parse(formattedDate);
            tgl.setDate(parsedDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void tombolNormal(){
        btn_tambah.setEnabled(true);
        btn_tambah.setText("Tambah");
        btn_simpan.setEnabled(false);
        btn_batal.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_ubah.setEnabled(false);
    }

    private void bersih(){
        datapegawai.setSelectedItem(null);
        kehadiran.setSelectedItem(null);
        tgl.setEnabled(true);
        
        gelar.setText("");
    }
    
    public void datatables(){
        Object header[]={"No","NIP","Nama","Tanggal","Kehadiran","Keterangan"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tabelAbsensi.setModel(data);

        tabelAbsensi.getColumnModel().getColumn(0).setPreferredWidth(15);  // No
        tabelAbsensi.getColumnModel().getColumn(1).setPreferredWidth(20);  // NIP
        tabelAbsensi.getColumnModel().getColumn(4).setPreferredWidth(50);  // MASUK
        tabelAbsensi.getColumnModel().getColumn(5).setPreferredWidth(150);  // LULUS

        String sql = "SELECT a.nip,b.nama_lengkap,a.tanggal,a.kehadiran,a.keterangan FROM absensi_kehadiran a join data_pegawai b on b.nip=a.nip order by a.id desc";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            int no = 1; 
            while (rs.next())
            {
                String kolom1 = String.valueOf(no);  // Nomor urut
                String kolom2 = rs.getString("nip");
                String kolom3 = rs.getString("nama_lengkap");
                String kolom4 = rs.getString("tanggal");
                String kolom5 = rs.getString("kehadiran");
                String kolom6 = rs.getString("keterangan");
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
                no++; 
            }
            System.out.println("Suksesterhubung ke database. ");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database. " + e.getMessage());
        }
    }

    public void dataComboPegawai()
    {
        String sql = "SELECT nip, nama_lengkap FROM data_pegawai";
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
        try{
	    String sql = "INSERT INTO absensi_kehadiran (nip, tanggal, kehadiran, keterangan) VALUES (?, ?, ?, ?)";
	
	    // Create PreparedStatement
	    PreparedStatement stmt = conn.prepareStatement(sql);
	        
	    // Set parameters
            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");
            
	    stmt.setString(1, parts[0]);

            String selectdate = tgl.getDate().toString();
            String formattedDate = "";
            if (selectdate != null) {
                SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = originalFormat.parse(selectdate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(date);
            } else {
                java.util.Date currentDate = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(currentDate);
            }            
            
            stmt.setString(2, formattedDate);
            stmt.setString(3, kehadiran.getSelectedItem().toString());
	    stmt.setString(4, gelar.getText());
	        
	        // Execute update
	    int rowsInserted = stmt.executeUpdate();
	        
	        // Check if insert was successful
	    if (rowsInserted > 0) {
	        JOptionPane.showMessageDialog(null, "Simpan Absensi Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
	    } else  {
                JOptionPane.showMessageDialog(null, "Simpan Absensi Gagal", "", JOptionPane.WARNING_MESSAGE);
            }
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan "+e);
        }
        datatables();
    }

    private void edit(){
        String sql = "UPDATE absensi_kehadiran SET kehadiran = ?, keterangan = ? WHERE nip = ? and tanggal = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");

            String selectdate = tgl.getDate().toString();
            String formattedDate = "";
            if (selectdate != null) {
                SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = originalFormat.parse(selectdate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(date);
            } else {
                java.util.Date currentDate = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(currentDate);
            }            
            
            stmt.setString(1, kehadiran.getSelectedItem().toString());
	    stmt.setString(2, gelar.getText());
            
            stmt.setString(3, parts[0]);
            stmt.setString(4, formattedDate);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Edit Data Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "", JOptionPane.WARNING_MESSAGE);
            }
            btn_tambah.setText("Tambah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah " + e.getMessage());
        }
        datatables();
    }

    private void hapus(){
        String sql="delete from absensi_kehadiran WHERE nip = ? and tanggal = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String selectedItem = datapegawai.getSelectedItem().toString();
            String[] parts = selectedItem.split("-");

            stmt.setString(1, parts[0]);
            String selectdate = tgl.getDate().toString();
            String formattedDate = "";
            if (selectdate != null) {
                SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = originalFormat.parse(selectdate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(date);
            } else {
                java.util.Date currentDate = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(currentDate);
            }
            stmt.setString(2, formattedDate);
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
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_home = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        datapegawai = new javax.swing.JComboBox<>();
        kehadiran = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        gelar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tgl = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelAbsensi = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        text_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(900, 654));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel2.setText("Data Pegawai");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel3.setText("Kehadiran");

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
        jLabel5.setText("Menu Absensi");

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

        kehadiran.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        kehadiran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hadir", "Sakit", "Izin", "Cuti", "Tanpa Keterangan" }));
        kehadiran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kehadiranActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Keterangan");

        gelar.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

        jLabel4.setText("Tanggal");

        tgl.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_home))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_batal)
                                .addGap(67, 67, 67)
                                .addComponent(btn_ubah)
                                .addGap(26, 26, 26)
                                .addComponent(btn_hapus))
                            .addComponent(gelar, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tgl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(datapegawai, javax.swing.GroupLayout.Alignment.LEADING, 0, 224, Short.MAX_VALUE)))
                        .addGap(0, 233, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(kehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(gelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_batal)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus))
                .addGap(18, 18, 18))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 870, 260));

        tabelAbsensi.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tabelAbsensi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelAbsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelAbsensiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelAbsensi);
        if (tabelAbsensi.getColumnModel().getColumnCount() > 0) {
            tabelAbsensi.getColumnModel().getColumn(0).setPreferredWidth(25);
            tabelAbsensi.getColumnModel().getColumn(4).setPreferredWidth(30);
            tabelAbsensi.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        btn_tambah.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(0, 153, 0));
        btn_tambah.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/add_15526434_24.png")); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_tambahMouseClicked(evt);
            }
        });

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
                        .addComponent(btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(btn_cari)
                    .addComponent(btn_tambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 870, 320));

        jLabel1.setBackground(new java.awt.Color(125, 194, 189));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/background.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 654));

        setSize(new java.awt.Dimension(900, 654));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ? Pastikan NIP dan Tanggal sesuai!","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
            Object header[]={"No","NIP","Nama","Tanggal","Kehadiran","Keterangan"};
            DefaultTableModel data = new DefaultTableModel(null,header);
            tabelAbsensi.setModel(data);
            
            tabelAbsensi.getColumnModel().getColumn(0).setPreferredWidth(15);  // No
            tabelAbsensi.getColumnModel().getColumn(1).setPreferredWidth(20);  // NIP
            tabelAbsensi.getColumnModel().getColumn(4).setPreferredWidth(50);  // MASUK
            tabelAbsensi.getColumnModel().getColumn(5).setPreferredWidth(150);  // LULUS


            String sql = "SELECT a.nip,b.nama_lengkap,a.tanggal,a.kehadiran,a.keterangan FROM absensi_kehadiran a join data_pegawai b on b.nip=a.nip where b.nama_lengkap like ?";
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
                    String kolom4 = rs.getString("tanggal");
                    String kolom5 = rs.getString("kehadiran");
                    String kolom6 = rs.getString("keterangan");
                
                    String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};

                    data.addRow(kolom);
                    no++; 
                }
                System.out.println("Sukses terhubung ke database. ");
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

    private void btn_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahMouseClicked
        // TODO add your handling code here:
        if(btn_tambah.getText().equalsIgnoreCase("tambah")){
            //btn_tambah.setText("Refresh");
            bersih();

            datapegawai.setEnabled(true);
            kehadiran.setEnabled(true);
            btn_tambah.setEnabled(false);
            btn_batal.setEnabled(true);
            btn_simpan.setEnabled(true);
            btn_hapus.setEnabled(false);
            btn_ubah.setEnabled(false);
        } else{
            btn_tambah.setText("Tambah");
            bersih();
//            siapIsi(false);
            tombolNormal();
            datatables();
        }
    }//GEN-LAST:event_btn_tambahMouseClicked

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        if(datapegawai.getSelectedItem().toString().isEmpty() || gelar.getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "Mohon Lengkapi Inputan Data!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{
            //System.out.println(btn_tambah.getText());
            //System.out.println(btn_ubah.getText());
            //System.out.println(btn_simpan.getText());
            if(btn_tambah.getText().equalsIgnoreCase("tambah")){
                if(btn_simpan.getText().equalsIgnoreCase("simpan")){
                    System.out.println("Proses Simpan");
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            System.out.println(btn_ubah.getText());
            if(btn_tambah.getText().isEmpty() && btn_ubah.getText().equalsIgnoreCase("edit")){
                if(btn_simpan.getText().equalsIgnoreCase("simpan")){
                    System.out.println("Proses Edit");
                    edit();
                } else{
                    JOptionPane.showMessageDialog(null, "EDIT DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            bersih();
            btn_tambah.setText("Tambah");
            btn_ubah.setText("Edit");
            tombolNormal();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(btn_ubah.getText().equalsIgnoreCase("edit")){
            btn_tambah.setText("");
            //btn_ubah.setText("Batal");
            datapegawai.setEnabled(false);
            btn_tambah.setEnabled(false);
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

    private void tabelAbsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelAbsensiMouseClicked
        // TODO add your handling code here:
        int baris = tabelAbsensi.getSelectedRow();
        String dp = tabelAbsensi.getModel().getValueAt(baris, 1).toString()+"-"+tabelAbsensi.getModel().getValueAt(baris, 2).toString();
        datapegawai.setSelectedItem(dp);

        SimpleDateFormat tglview = new SimpleDateFormat("yyyy-MM-dd");
        Date dateView = null;
        try {
            dateView = tglview.parse((String) tabelAbsensi.getValueAt(baris, 3));
        } catch (ParseException ex) {
                Logger.getLogger(pegawai.class.getName()).log(Level.SEVERE, null, ex);
            }        
        tgl.setDate(dateView);

        kehadiran.setSelectedItem(tabelAbsensi.getModel().getValueAt(baris, 4).toString());
        gelar.setText(tabelAbsensi.getModel().getValueAt(baris, 5).toString());
        
        datapegawai.setEnabled(false);
        tgl.setEnabled(false);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_ubah.setEnabled(true);
    }//GEN-LAST:event_tabelAbsensiMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        text_cari.setText("");
        datatables();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void datapegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datapegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datapegawaiActionPerformed

    private void kehadiranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kehadiranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kehadiranActionPerformed

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
            java.util.logging.Logger.getLogger(absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new absensi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox<String> datapegawai;
    private javax.swing.JTextField gelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> kehadiran;
    private javax.swing.JTable tabelAbsensi;
    private javax.swing.JTextField text_cari;
    private com.toedter.calendar.JDateChooser tgl;
    // End of variables declaration//GEN-END:variables
}
