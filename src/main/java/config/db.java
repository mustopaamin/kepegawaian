/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.*;

/**
 *
 * @author itdev
 */
public class db {

    private static final String URL = "jdbc:mysql://localhost:3306/kepegawaian?serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection;

    private db() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Gagal terhubung ke database. " + e.getMessage());
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi. " + e.getMessage());
            }
        }
    }        
}
