/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import config.db;


/**
 *
 * @author itdev
 */
public class helpers {
    Connection conn = db.getConnection();
    
    public helpers() {
        
    }

    private static final String[] huruf = {
        "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"
    };
    
    public String generateNIP() throws SQLException {
        LocalDate now = LocalDate.now();
        String month = String.format("%02d", now.getMonthValue());
        String year = String.valueOf(now.getYear()).substring(2);
        String nip = month + year + month;
        
        //String sql = "SELECT COUNT(*) AS count FROM data_pegawai WHERE nip like ?";
        String sql = "SELECT COUNT(*) AS count,max(cast(substr(nip, 7, 2) AS UNSIGNED)) nomor FROM data_pegawai WHERE nip like ? order by nip desc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nip + "%");
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt("count") + 1;
        
        if(rs.getInt("count") != 0) {
            count = rs.getInt("nomor") + 1;
        }
        System.out.println(count);
        String countStr = String.format("%02d", count);
        
        return month + year + month + countStr;
    }    

    private static String penyebut(double nilai) {
        nilai = Math.abs(nilai);
        String temp = "";
        
        if (nilai < 12) {
            temp = " " + huruf[(int) nilai];
        } else if (nilai < 20) {
            temp = penyebut(nilai - 10) + " belas";
        } else if (nilai < 100) {
            temp = penyebut(nilai / 10) + " puluh" + penyebut(nilai % 10);
        } else if (nilai < 200) {
            temp = " seratus" + penyebut(nilai - 100);
        } else if (nilai < 1000) {
            temp = penyebut(nilai / 100) + " ratus" + penyebut(nilai % 100);
        } else if (nilai < 2000) {
            temp = " seribu" + penyebut(nilai - 1000);
        } else if (nilai < 1000000) {
            temp = penyebut(nilai / 1000) + " ribu" + penyebut(nilai % 1000);
        } else if (nilai < 1000000000) {
            temp = penyebut(nilai / 1000000) + " juta" + penyebut(nilai % 1000000);
        } else if (nilai < 1000000000000L) {
            temp = penyebut(nilai / 1000000000) + " milyar" + penyebut(nilai % 1000000000);
        } else if (nilai < 1000000000000000L) {
            temp = penyebut(nilai / 1000000000000L) + " trilyun" + penyebut(nilai % 1000000000000L);
        }

        return temp;
    }

    public static String terbilang(double nilai) {
        String hasil;
        if (nilai < 0) {
            hasil = "minus " + penyebut(nilai).trim();
        } else {
            hasil = penyebut(nilai).trim();
        }
        return hasil;
    }

}
