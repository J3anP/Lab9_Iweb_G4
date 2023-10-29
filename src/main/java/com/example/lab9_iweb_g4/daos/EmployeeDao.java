package com.example.lab9_iweb_g4.daos;

import com.example.lab9_iweb_g4.beans.Employee;

import java.sql.*;
import java.util.ArrayList;
public class EmployeeDao {
    private static final String username = "root";
    private static final String password = "root";

    public ArrayList<Employee> list(){

        ArrayList<Employee> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "select * from employees limit 100";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpNo(rs.getInt(1));
                employee.setBirthDate(rs.getString(2));
                employee.setFirstName(rs.getString(3));
                employee.setLastName(rs.getString(4));
                employee.setGender(rs.getString(4));
                employee.setHireDate(rs.getString(4));

                lista.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public void create(Employee employee){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "INSERT INTO employees(emp_no,birth_date,first_name,last_name,gender,hire_date) VALUES  (?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url,username, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setInt(1,employee.getEmpNo());
            pstmt.setString(2,employee.getBirthDate());
            pstmt.setString(3,employee.getFirstName());
            pstmt.setString(4, employee.getLastName());
            pstmt.setString(5,employee.getGender());
            pstmt.setString(6,employee.getHireDate());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee buscarPorId(String id){

        Employee employee = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "select * from employees where emp_no = ?";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,id);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    employee = new Employee();
                    employee.setEmpNo(rs.getInt(1));
                    employee.setBirthDate(rs.getString(2));
                    employee.setFirstName(rs.getString(3));
                    employee.setLastName(rs.getString(4));
                    employee.setGender(rs.getString(4));
                    employee.setHireDate(rs.getString(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    public void actualizar(Employee employee){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "UPDATE employees SET birth_date = ?,first_name = ?,last_name = ?,gender = ?, hire_date = ? WHERE emp_no=?";

        try(Connection conn = DriverManager.getConnection(url,username, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1,employee.getBirthDate());
            pstmt.setString(2,employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4,employee.getGender());
            pstmt.setString(5,employee.getHireDate());
            pstmt.setInt(6,employee.getEmpNo());

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

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "delete from employees where emp_no = ?";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,employeeNo);
            pstmt.executeUpdate();

        }
    }

    public ArrayList<Employee> searchByName(String name) {
        // TODO
        return null;
    }

    public int searchLastId() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/employees";

        String sql = "SELECT emp_no from employees ORDER BY emp_no DESC LIMIT 1";

        String lastId = "0";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lastId = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Integer.parseInt(lastId);
    }
}
