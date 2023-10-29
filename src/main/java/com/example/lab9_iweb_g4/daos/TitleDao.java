package com.example.lab9_iweb_g4.daos;

import com.example.lab9_iweb_g4.beans.Title;

import java.sql.*;
import java.util.ArrayList;
public class TitleDao {

    private static final String username = "root";
    private static final String password = "root";

    public ArrayList<Title> list(){

        ArrayList<Title> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        // TODO: update query
        String sql = "select * from titles limit 100";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Title title = new Title();
                title.setEmpNo(rs.getInt(1));
                title.setTitle(rs.getString(2));
                title.setFromDate(rs.getString(3));
                title.setToDate(rs.getString(4));

                lista.add(title);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
    public void create(Title title){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/title";

        String sql = "INSERT INTO titles(emp_no,title,from_date,to_date) VALUES  (?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url,username, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setInt(1,title.getEmpNo());
            pstmt.setString(2,title.getTitle());
            pstmt.setString(3, title.getFromDate());
            pstmt.setString(4, title.getToDate());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrar(String employeeNo) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/title";

        String sql = "delete from titles where emp_no = ?";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,employeeNo);
            pstmt.executeUpdate();

        }
    }
    public void actualizar(Title title){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/title";

        String sql = "UPDATE titles SET to_date = ?,from_date = ?,title = ? WHERE emp_no=?";

        try(Connection conn = DriverManager.getConnection(url,username, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1, title.getToDate());
            pstmt.setString(2,title.getFromDate());
            pstmt.setString(3, title.getTitle());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Title buscarPorId(String id){

        Title title = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "select * from titles where emp_no = ?";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,id);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    title = new Title();
                    title.setEmpNo(rs.getInt(1));
                    title.setTitle(rs.getString(2));
                    title.setFromDate(rs.getString(3));
                    title.setToDate(rs.getString(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return title;
    }
}
