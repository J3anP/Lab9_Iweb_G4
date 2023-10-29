package com.example.lab9_iweb_g4.servlets;

import com.example.lab9_iweb_g4.beans.Employee;
import com.example.lab9_iweb_g4.daos.EmployeeDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        String offset = request.getParameter("offset") == null ? "0": request.getParameter("offset");
        String limit = request.getParameter("limit") == null ? "100": request.getParameter("limit");

        EmployeeDao employeeDao = new EmployeeDao();

        switch (action){
            case "lista":
                //saca del modelo
                ArrayList<Employee> list = employeeDao.list(offset,limit);

                //mandar la lista a la vista -> job/lista.jsp
                request.setAttribute("lista",list);
                //request.setAttribute("offset",offset);
                //request.setAttribute("limit",limit);
                RequestDispatcher rd = request.getRequestDispatcher("employee/lista.jsp");
                rd.forward(request,response);
                break;
            case "new":
                request.getRequestDispatcher("employee/form_new.jsp").forward(request,response);
                break;
            case "edit":
                String id = request.getParameter("id");
                Employee employee = employeeDao.buscarPorId(id);

                if(employee != null){
                    request.setAttribute("employee", employee);
                    request.getRequestDispatcher("employee/form_edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                }
                break;
            case "del":
                String idd = request.getParameter("id");
                Employee employee1 = employeeDao.buscarPorId(idd);

                if(employee1 != null){
                    try {
                        employeeDao.borrar(idd);
                    } catch (SQLException e) {
                        System.out.println("Log: excepcion: " + e.getMessage());
                    }
                }
                response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        ArrayList<Employee> lista = new ArrayList<>();

        EmployeeDao employeeDao = new EmployeeDao();

        String action = request.getParameter("action") == null ? "crear" : request.getParameter("action");

        Employee employee = new Employee();

        if(action.equals("crear") || action.equals("e")){
            employee.setBirthDate(request.getParameter("birthDate"));
            employee.setFirstName(request.getParameter("firstName"));
            employee.setLastName(request.getParameter("lastName"));
            employee.setGender(request.getParameter("gender"));
            employee.setHireDate(request.getParameter("hireDate"));
        }

        switch (action){
            case "crear":
                employee.setEmpNo(employeeDao.searchLastId()+1);
                employeeDao.create(employee);
                response.sendRedirect(request.getContextPath()+"/EmployeeServlet");
                break;
            case "e":
                employee.setEmpNo(Integer.parseInt(request.getParameter("empNo")));
                employeeDao.actualizar(employee);
                response.sendRedirect(request.getContextPath()+"/EmployeeServlet");
                break;
            case "s":
                String textBuscar = request.getParameter("textoBuscar");
                lista = employeeDao.searchByName(textBuscar);

                request.setAttribute("lista",lista);
                request.setAttribute("busqueda",textBuscar);
                request.getRequestDispatcher("employee/lista.jsp").forward(request,response);
                break;

            /* Método de paginado por POST
                case "limit":

                String offset = request.getParameter("offset") == null ? "0": request.getParameter("offset");
                String limit = request.getParameter("limit") == null ? "100": request.getParameter("limit");

                lista = employeeDao.list(offset, limit);

                request.setAttribute("lista",lista);
                request.setAttribute("offset",offset);
                request.setAttribute("limit",limit);

                request.getRequestDispatcher("employee/lista.jsp").forward(request,response);
                break;*/
        }
    }
}
