/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package modul;

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modul.dashboard;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.Locale;
import java.util.Date;
import config.db;
import config.helpers;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author itdev
 */
public class pegawai extends javax.swing.JFrame {
    Connection conn = db.getConnection();
    public helpers helpers;
    /**
     * Creates new form jabatan
     */
    public pegawai() {
        initComponents();
        helpers = new helpers();
        tombolNormal();
        dataComboJabatan();
        datatables();
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
        code_pegawai.setText("");
        nama_pegawai.setText("");
        tempat_lahir.setText("");
        gaji_pokok.setText("");
        tgl_lahir.setDate(null);
        status_jabatan.setSelectedItem("Staff");
        status_pegawai.setSelectedItem("Pegawai Tetap");
    }
    
    public void datatables(){
        Object header[]={"No","NIP","Nama","Tempat Lahir","Tanggal Lahir","Jenis Kelamin","Jabatan","Gaji","Status"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tabelPegawai.setModel(data);
        String sql = "select id, nip, nama_lengkap, tempat_lahir, tanggal_lahir, jenis_kelamin, status_kepegawaian, jabatan, gaji FROM data_pegawai order by id desc";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            int no = 1; 
            while (rs.next())
            {
                String kolom1 = String.valueOf(no);  // Nomor urut
                String kolom2 = rs.getString("nip");
                String kolom3 = rs.getString("nama_lengkap");
                String kolom4 = rs.getString("tempat_lahir");
                String kolom5 = rs.getString("tanggal_lahir");
                String kolom6 = rs.getString("jenis_kelamin");
                String kolom7 = rs.getString("jabatan");
                String kolom8 = rs.getString("gaji");
                String kolom9 = rs.getString("status_kepegawaian");
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,};
                data.addRow(kolom);
                no++; 
            }
            System.out.println("Sukses terhubung ke database. ");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database. " + e.getMessage());
        }
    }
    
    public void dataComboJabatan()
    {
        String sql = "SELECT nama_jabatan FROM jabatan";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next())
            {
                status_jabatan.addItem(rs.getString("nama_jabatan"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
            System.out.println("Sukses terhubung ke combo jabatan. ");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database. " + e.getMessage());
        }
    }

    private void simpan(){
        try{
	    String sql = "INSERT INTO data_pegawai (nip, nama_lengkap, tempat_lahir, tanggal_lahir, jenis_kelamin, status_kepegawaian, jabatan, gaji) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	    // Create PreparedStatement
	    PreparedStatement stmt = conn.prepareStatement(sql);

            String selectdate = tgl_lahir.getDate().toString();
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
            
            System.out.println(selectdate);
            String JenKel="";
            if (rdLaki.isSelected()){
                JenKel = rdLaki.getText();
            }else{
                JenKel = rdPerempuan.getText();
            }
            
	    // Set parameters
	    stmt.setString(1, code_pegawai.getText());
	    stmt.setString(2, nama_pegawai.getText());
	    stmt.setString(3, tempat_lahir.getText());
            stmt.setString(4, formattedDate);
	    stmt.setString(5, JenKel);
	    stmt.setString(6, status_pegawai.getSelectedItem().toString());
	    stmt.setString(7, status_jabatan.getSelectedItem().toString());
	    stmt.setString(8, gaji_pokok.getText());
	        // Execute update
	    int rowsInserted = stmt.executeUpdate();
	        
	        // Check if insert was successful
	    if (rowsInserted > 0) {
	        JOptionPane.showMessageDialog(null, "Simpan Pegawai Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
	    } else  {
                JOptionPane.showMessageDialog(null, "Simpan Pegawai Gagal", "", JOptionPane.WARNING_MESSAGE);
            }
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan Pegawai "+e);
        }
        datatables();
    }

    private void edit(){
        String sql = "UPDATE data_pegawai SET nama_lengkap = ?, tempat_lahir = ?, tanggal_lahir = ?, jenis_kelamin = ?, status_kepegawaian = ?, jabatan = ?, gaji = ? WHERE nip = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String selectdate = tgl_lahir.getDate().toString();
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
            
            //System.out.println(selectdate);
            String JenKel="";
            if (rdLaki.isSelected()){
                JenKel = rdLaki.getText();
            }else{
                JenKel = rdPerempuan.getText();
            }
            
	    // Set parameters
	    stmt.setString(1, nama_pegawai.getText());
	    stmt.setString(2, tempat_lahir.getText());
            stmt.setString(3, formattedDate);
	    stmt.setString(4, JenKel);
	    stmt.setString(5, status_pegawai.getSelectedItem().toString());
	    stmt.setString(6, status_jabatan.getSelectedItem().toString());
	    stmt.setString(7, gaji_pokok.getText());
	    stmt.setString(8, code_pegawai.getText());
            
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
        String sql="delete from data_pegawai WHERE nip = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_pegawai.getText());
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

        btn_jk = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        code_pegawai = new javax.swing.JTextField();
        nama_pegawai = new javax.swing.JTextField();
        status_jabatan = new javax.swing.JComboBox<>();
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_home = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        status_pegawai = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        gaji_pokok = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tempat_lahir = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdLaki = new javax.swing.JRadioButton();
        rdPerempuan = new javax.swing.JRadioButton();
        tgl_lahir = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPegawai = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        text_cari = new javax.swing.JTextField();
        btn_refresh = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1020, 768));
        setPreferredSize(new java.awt.Dimension(1020, 768));
        setResizable(false);
        setSize(new java.awt.Dimension(900, 654));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("NIP");

        jLabel3.setText("Nama Pegawai");

        jLabel4.setText("Jabatan");

        code_pegawai.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        code_pegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                code_pegawaiActionPerformed(evt);
            }
        });

        nama_pegawai.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        nama_pegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_pegawaiActionPerformed(evt);
            }
        });

        status_jabatan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        status_jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                status_jabatanActionPerformed(evt);
            }
        });

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
        jLabel5.setText("Menu Pegawai");

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

        jLabel6.setText("Status");

        status_pegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pegawai Tetap", "Pegawai Kontrak", "Keluar" }));
        status_pegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                status_pegawaiActionPerformed(evt);
            }
        });

        jLabel7.setText("Gaji Pokok");

        gaji_pokok.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        gaji_pokok.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gaji_pokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gaji_pokokActionPerformed(evt);
            }
        });

        jLabel8.setText("Jenis kelamin");

        tempat_lahir.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tempat_lahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempat_lahirActionPerformed(evt);
            }
        });

        jLabel9.setText("Tempat Lahir");

        jLabel10.setText("Tanggal Lahir");

        btn_jk.add(rdLaki);
        rdLaki.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        rdLaki.setText("Laki-Laki");

        btn_jk.add(rdPerempuan);
        rdPerempuan.setText("Perempuan");
        rdPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdPerempuanActionPerformed(evt);
            }
        });

        tgl_lahir.setDateFormatString("yyyy-MM-dd");
        tgl_lahir.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N

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
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_simpan)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(63, 63, 63)
                                        .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_hapus))
                                    .addComponent(gaji_pokok, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(status_jabatan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(status_pegawai, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(386, 386, 386)
                                .addComponent(nama_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdLaki)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(156, 156, 156)
                                        .addComponent(rdPerempuan))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(code_pegawai, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(tempat_lahir))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(354, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_home)
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_home))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(code_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(nama_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(tempat_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(tgl_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdLaki)
                        .addComponent(rdPerempuan))
                    .addComponent(jLabel8))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(status_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(gaji_pokok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_batal)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1000, 350));

        tabelPegawai.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tabelPegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tabelPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPegawai);
        if (tabelPegawai.getColumnModel().getColumnCount() > 0) {
            tabelPegawai.getColumnModel().getColumn(0).setMaxWidth(65);
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
        text_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_cariActionPerformed(evt);
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

        btn_cari.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_cari.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/glass_11034892_24.png")); // NOI18N
        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(text_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)
                        .addGap(7, 7, 7)
                        .addComponent(btn_refresh)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_tambah)
                        .addComponent(text_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cari))
                    .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 1000, 380));

        jLabel1.setBackground(new java.awt.Color(125, 194, 189));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/background.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -50, 1030, 830));

        setSize(new java.awt.Dimension(1020, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void code_pegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_code_pegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_code_pegawaiActionPerformed

    private void nama_pegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_pegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_pegawaiActionPerformed

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
            //datatables();
        } else{
            Object header[]={"No","NIP","Nama","Tempat Lahir","Tanggal Lahir","Jenis Kelamin","Jabatan","Gaji","Status"};
            DefaultTableModel data = new DefaultTableModel(null,header);
            tabelPegawai.setModel(data);

            String sql = "select id, nip, nama_lengkap, tempat_lahir, tanggal_lahir, jenis_kelamin, status_kepegawaian, jabatan, gaji FROM data_pegawai where nama_lengkap like ?";
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
                    String kolom4 = rs.getString("tempat_lahir");
                    String kolom5 = rs.getString("tanggal_lahir");
                    String kolom6 = rs.getString("jenis_kelamin");
                    String kolom7 = rs.getString("jabatan");
                    String kolom8 = rs.getString("gaji");
                    String kolom9 = rs.getString("status_kepegawaian");
                
                    String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,};
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
            try {
                String nip = helpers.generateNIP();
                code_pegawai.setText(nip);    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menghasilkan NIP: " + e.getMessage());
                return;
            }
            //String nip = helpers.generateNIP();

            //code_pegawai.setText(nip);    
            code_pegawai.setEnabled(false);
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
        if(code_pegawai.getText().isEmpty() || nama_pegawai.getText().isEmpty()
                || tempat_lahir.getText().isEmpty()
                || gaji_pokok.getText().isEmpty()
          ){
            JOptionPane.showMessageDialog(null, "Mohon Lengkapi Inputan Data!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{
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
            code_pegawai.setEnabled(false);
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
        code_pegawai.setEnabled(true);
        bersih();
        tombolNormal();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void tabelPegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPegawaiMouseClicked
        // TODO add your handling code here:
        int baris = tabelPegawai.getSelectedRow();
        code_pegawai.setText(tabelPegawai.getModel().getValueAt(baris, 1).toString());
        nama_pegawai.setText(tabelPegawai.getModel().getValueAt(baris, 2).toString());
        tempat_lahir.setText(tabelPegawai.getModel().getValueAt(baris, 3).toString());
        
        SimpleDateFormat tglview = new SimpleDateFormat("yyyy-MM-dd");
        Date dateView = null;
        try {
            dateView = tglview.parse((String) tabelPegawai.getValueAt(baris, 4));
        } catch (ParseException ex) {
                Logger.getLogger(pegawai.class.getName()).log(Level.SEVERE, null, ex);
            }        
        String jenis = tabelPegawai.getModel().getValueAt(baris, 5).toString();
        if(jenis.equals("Laki-laki")){rdLaki.setSelected(true);
        }else { rdPerempuan.setSelected(true);}
        tgl_lahir.setDate(dateView);
        //tgl_lahir.setText(tabelPegawai.getModel().getValueAt(baris, 4).toString());
        status_jabatan.setSelectedItem(tabelPegawai.getModel().getValueAt(baris, 6).toString());
        gaji_pokok.setText(tabelPegawai.getModel().getValueAt(baris, 7).toString());
        status_pegawai.setSelectedItem(tabelPegawai.getModel().getValueAt(baris, 8).toString());
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_ubah.setEnabled(true);
    }//GEN-LAST:event_tabelPegawaiMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        text_cari.setText("");
        datatables();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void text_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_cariActionPerformed

    private void gaji_pokokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gaji_pokokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gaji_pokokActionPerformed

    private void status_jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_status_jabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_status_jabatanActionPerformed

    private void status_pegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_status_pegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_status_pegawaiActionPerformed

    private void tempat_lahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempat_lahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempat_lahirActionPerformed

    private void rdPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdPerempuanActionPerformed

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
            java.util.logging.Logger.getLogger(pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_home;
    private javax.swing.ButtonGroup btn_jk;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JTextField code_pegawai;
    private javax.swing.JTextField gaji_pokok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nama_pegawai;
    private javax.swing.JRadioButton rdLaki;
    private javax.swing.JRadioButton rdPerempuan;
    private javax.swing.JComboBox<String> status_jabatan;
    private javax.swing.JComboBox<String> status_pegawai;
    private javax.swing.JTable tabelPegawai;
    private javax.swing.JTextField tempat_lahir;
    private javax.swing.JTextField text_cari;
    private com.toedter.calendar.JDateChooser tgl_lahir;
    // End of variables declaration//GEN-END:variables
}
