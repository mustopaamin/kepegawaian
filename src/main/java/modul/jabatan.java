/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package modul;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modul.dashboard;
import config.db;

/**
 *
 * @author itdev
 */
public class jabatan extends javax.swing.JFrame {
    Connection conn = db.getConnection();
    /**
     * Creates new form jabatan
     */
    public jabatan() {
        initComponents();
        tombolNormal();
        datatables();
    }

    private void tombolNormal(){
        btn_tambah.setEnabled(true);
        btn_tambah.setText("Tambah");
        btn_simpan.setEnabled(false);
        btn_cancel.setVisible(false);;
        btn_batal.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_ubah.setEnabled(false);
    }

    private void bersih(){
        code_jabatan.setText("");
        nama_jabatan.setText("");
        status_jabatan.setSelectedItem("Aktif");
    }
    
    public void datatables(){
        Object header[]={"No","Code Jabatan","Nama Jabatan","Status"};
        DefaultTableModel data = new DefaultTableModel(null,header);
        tabelJabatan.setModel(data);
        String sql = "SELECT code, nama_jabatan, aktif FROM jabatan";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery(sql);
            int no = 1; 
            while (rs.next())
            {
                String kolom1 = String.valueOf(no);  // Nomor urut
                String kolom2 = rs.getString("code");
                String kolom3 = rs.getString("nama_jabatan");
                String kolom4 = rs.getString("aktif");
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,};
                data.addRow(kolom);
                no++; 
            }
            System.out.println("Suksesterhubung ke database. ");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database. " + e.getMessage());
        }
    }

    private void simpan(){
        try{
	    String sql = "INSERT INTO jabatan (code, nama_jabatan, aktif) VALUES (?, ?, ?)";
	
	    // Create PreparedStatement
	    PreparedStatement stmt = conn.prepareStatement(sql);
	        
	    // Set parameters
	    stmt.setString(1, code_jabatan.getText());
	    stmt.setString(2, nama_jabatan.getText());
	    stmt.setString(3, status_jabatan.getSelectedItem().toString());
	        
	        // Execute update
	    int rowsInserted = stmt.executeUpdate();
	        
	        // Check if insert was successful
	    if (rowsInserted > 0) {
	        JOptionPane.showMessageDialog(null, "Simpan Jabatan Berhasil", "", JOptionPane.INFORMATION_MESSAGE);
	    } else  {
                JOptionPane.showMessageDialog(null, "Simpan Jabatan Gagal", "", JOptionPane.WARNING_MESSAGE);
            }
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan "+e);
        }
        datatables();
    }

    private void edit(){
        String sql = "UPDATE jabatan SET nama_jabatan = ?, aktif = ? WHERE code = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nama_jabatan.getText());
            stmt.setString(2, status_jabatan.getSelectedItem().toString());
            stmt.setString(3, code_jabatan.getText());

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
        String sql="delete from jabatan WHERE code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_jabatan.getText());
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
        code_jabatan = new javax.swing.JTextField();
        nama_jabatan = new javax.swing.JTextField();
        status_jabatan = new javax.swing.JComboBox<>();
        btn_simpan = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_home = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelJabatan = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        text_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(900, 654));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Code Jabatan");

        jLabel3.setText("Nama Jabatan");

        jLabel4.setText("Status Jabatan");

        code_jabatan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        code_jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                code_jabatanActionPerformed(evt);
            }
        });

        nama_jabatan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        nama_jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_jabatanActionPerformed(evt);
            }
        });

        status_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Non Aktif" }));

        btn_simpan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(0, 153, 102));
        btn_simpan.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/floppy_4283060_24.png")); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_cancel.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(0, 204, 204));
        btn_cancel.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/circular_11417901_24.png")); // NOI18N
        btn_cancel.setText("Refresh");
        btn_cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cancelMouseClicked(evt);
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
        jLabel5.setText("Menu Jabatan");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
                        .addComponent(btn_home))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nama_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(code_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(status_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cancel)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ubah)
                                .addGap(18, 18, 18)
                                .addComponent(btn_hapus)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(code_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nama_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(status_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_cancel)
                    .addComponent(btn_ubah)
                    .addComponent(btn_hapus)
                    .addComponent(btn_batal))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 870, 230));

        tabelJabatan.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tabelJabatan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelJabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelJabatanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelJabatan);

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

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 870, 340));

        jLabel1.setBackground(new java.awt.Color(125, 194, 189));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("/home/itdev/NetBeansProjects/tas/src/main/java/assets/background.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 654));

        setSize(new java.awt.Dimension(900, 654));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void code_jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_code_jabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_code_jabatanActionPerformed

    private void nama_jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_jabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_jabatanActionPerformed

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
            datatables();
        } else{
            Object header[]={"No","Code Jabatan","Nama Jabatan","Status"};
            DefaultTableModel data = new DefaultTableModel(null,header);
            tabelJabatan.setModel(data);

            String sql = "SELECT code, nama_jabatan, aktif FROM jabatan where nama_jabatan like ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + text_cari.getText() + "%");
                //System.out.println(sql);
                ResultSet rs= stmt.executeQuery();
                int no = 1; 
                while (rs.next())
                {
                    String kolom1 = String.valueOf(no);  // Nomor urut
                    String kolom2 = rs.getString("code");
                    String kolom3 = rs.getString("nama_jabatan");
                    String kolom4 = rs.getString("aktif");
                
                    String kolom[]={kolom1,kolom2,kolom3,kolom4};
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

    private void btn_cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cancelMouseClicked
        // TODO add your handling code here:
        bersih();
    }//GEN-LAST:event_btn_cancelMouseClicked

    private void btn_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahMouseClicked
        // TODO add your handling code here:
        if(btn_tambah.getText().equalsIgnoreCase("tambah")){
            //btn_tambah.setText("Refresh");
            bersih();

            code_jabatan.setEnabled(true);
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
        if(code_jabatan.getText().isEmpty() || nama_jabatan.getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "Mohon Lengkapi Inputan Data!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{
            System.out.println(btn_tambah.getText());
            System.out.println(btn_ubah.getText());
            System.out.println(btn_simpan.getText());
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
            code_jabatan.setEnabled(false);
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
        code_jabatan.setEnabled(true);
        bersih();
        tombolNormal();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void tabelJabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelJabatanMouseClicked
        // TODO add your handling code here:
        int baris = tabelJabatan.getSelectedRow();
        code_jabatan.setText(tabelJabatan.getModel().getValueAt(baris, 1).toString());
        nama_jabatan.setText(tabelJabatan.getModel().getValueAt(baris, 2).toString());
        status_jabatan.setSelectedItem(tabelJabatan.getModel().getValueAt(baris, 3).toString());
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_ubah.setEnabled(true);
    }//GEN-LAST:event_tabelJabatanMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        text_cari.setText("");
        datatables();
    }//GEN-LAST:event_btn_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jabatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jabatan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JTextField code_jabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nama_jabatan;
    private javax.swing.JComboBox<String> status_jabatan;
    private javax.swing.JTable tabelJabatan;
    private javax.swing.JTextField text_cari;
    // End of variables declaration//GEN-END:variables
}
